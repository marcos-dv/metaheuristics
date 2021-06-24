package metaheuristics;


import control.Globals;
import control.Messages;
import parallelism.FitnessCalculator;
import problems.Problem;
import problems.cec2015.Cec2015Calculator;
import solutions.Solution;
import utils.Algorithms;
import utils.Pair;
import utils.Parallelizable;

public class GSA implements IMetaheuristic , Parallelizable {
	
	private boolean DEBUG = false;
	
	private Solution [] sols;
	private Solution globalBest;
	
	// Info to compute (index-correlated with sols array)
	private double [] fit;
	private double [] q; // normalized fitness
	private double [] mass; // proportional to fitness
	private double [][] a; // acceleration of sols[i] on each component
	private double [][] v; // vel of sols[i] on each component
	private Pair [] kbest; // kbest solutions (index, fitness)
	private int kSize; // kbest size for each iteration
	private double Gt; // Gravitatory constant (depends on current iteration)
	
	// Global params
	private int MAX_ITER;
	private int numIter;
	private double G0;
	private double alfa;
	private double epsilon;
	
	private double worstFitness;
	private double bestFitness;
	private Problem targetProblem;

	private double R[][];

	private boolean parallel = false;

	private int numThreads;
	
	public GSA(Problem targetProblem) {
		this.targetProblem = targetProblem;
		if (targetProblem.getDim() <= 0) {
			Messages.warning("GSA: Target problem dimension equals 0.");
		}
		numIter = 0;
		// Default Hyperparameters
		G0 = 100;
		alfa = 20;
		epsilon = 1e-9;
		MAX_ITER = 10000*targetProblem.getDim();
	}

	public GSA(int popSize, Problem targetProblem) {
		this(targetProblem);
		sols = new Solution[popSize];
	}
	
	public GSA(Solution [] sols, Problem targetProblem) {
		this(targetProblem);
		this.sols = sols.clone();
	}

	public GSA(GSA GSA) {
		this(GSA.sols, GSA.targetProblem);
		numIter = GSA.getNumIter();
	}
	
	@Override
	public void initPop() {
		// Init population
		if (sols == null) {
			Messages.error("GSA: solutions are not initialized");
			return ;
		}
		// Random init
		for (int i = 0; i < sols.length; ++i) {
			sols[i] = new Solution(targetProblem);
			sols[i].randomInit();
		}
		// Init structures
		reset();
		// Update best solution
		updateGlobalBest();
	}

	@Override
	public Solution[] getSols() {
		return sols;
	}

	public void setSols(Solution[] sols) {
		this.sols = sols;
	}
	
	public void reset() {
		fit = new double[sols.length];
		q = new double[sols.length];
		mass = new double[sols.length];
		a = new double[sols.length][targetProblem.getDim()];
		v = new double[sols.length][targetProblem.getDim()];
		kbest = null;
	}

	public boolean checkCorrectness() {
		boolean ok = true;
		if (sols == null) {
			Messages.warning("GSA: solutions are not initialized");
			ok = false;
		}
		else {
			for (int i = 0; i < sols.length; ++i) {
				if (sols[i] == null) {
					Messages.warning("GSA: sols["+i+"] is not initialized");
					ok = false;
				}
			}
		}
		if (numIter < 0) {
			Messages.warning("GSA: number of iterations < 0");
			ok = false;
		}
		
		if (fit == null || q == null || mass == null || a == null || v == null || kbest == null) {
			Messages.warning("GSA: fitness, q, mass, acceleration, velocity , or kbest are not initialized");
			ok = false;
		}
		if (G0 <= 0) {
			Messages.warning("GSA: G0 = " + G0);
		}
		if (alfa <= 0) {
			Messages.warning("GSA: alfa = " + alfa);
		}
		if (epsilon <= 0) {
			Messages.warning("GSA: epsilon = " + epsilon);
			ok = false;
		}

		return ok;
	}
	
	/**
	 * Normalize k between 1 and sols.length
	 * @param k
	 * @return
	 */
	protected int normalizeK(int k) {
		int newK = Math.min(k, sols.length);
		return Math.max(newK, 1);
	}
	
	protected int computeK() {
		if (MAX_ITER >= sols.length) {
			// Each kRate iterations reduce k by 1
			int kRate = MAX_ITER / sols.length;
			return normalizeK(sols.length-numIter/kRate);
		}
		// So last iteration end with the best sol
		return normalizeK(MAX_ITER-numIter);
	}
	
	protected void computeFitness() {
		if (isParallel())
			computeParallelFitness();
		else {
			for(int i = 0; i < sols.length; ++i) {
				fit[i] = sols[i].getFitness();
			}
		}
		/*  Multiple sols
			Cec2015Problem p = (Cec2015Problem) targetProblem;
			try {
				fit = p.contestFitnessMultipleSols(getSols());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 */
	}
	
	// TODO check that 8!!
	protected void computeParallelFitness() {
		setNumThreads(8);
		FitnessCalculator [] fitnessThread = new FitnessCalculator[getNumThreads()];
		int rate = sols.length/getNumThreads();
		// System.out.println("Rate " + rate);
		for(int i = 0; i < getNumThreads()-1; ++i) {
			fitnessThread[i] = new FitnessCalculator(i*rate, rate, sols, targetProblem, fit);
			//fitnessThread[i] = new Cec2015Calculator(i*rate, rate, sols, targetProblem, fit);
		}
		fitnessThread[getNumThreads()-1] = new FitnessCalculator((getNumThreads()-1)*rate, rate+(sols.length%getNumThreads()), sols, targetProblem, fit);
//		fitnessThread[getNumThreads()-1] = new Cec2015Calculator((getNumThreads()-1)*rate, rate+(sols.length%getNumThreads()), sols, targetProblem, fit);
		for (FitnessCalculator fitnessCalculator : fitnessThread) {
			fitnessCalculator.start();
		}
		for (FitnessCalculator fitnessCalculator : fitnessThread) {
			try {
				fitnessCalculator.join();
			} catch (InterruptedException e) {
				Messages.error("GSA: Error while waiting threads");
			}
		}
	}

	// First step of iteration
	protected void processFitness() {
		// Get the best and worst fitness
		bestFitness = Double.POSITIVE_INFINITY;
		worstFitness = Double.NEGATIVE_INFINITY;
		for(int i = 0; i < sols.length; ++i) {
			bestFitness = Math.min(bestFitness, fit[i]);
			worstFitness = Math.max(worstFitness, fit[i]);
		}
		
		// Compute best K fitness
		kSize = computeK();
		if (DEBUG)
			System.out.println("K = " + kSize);
		kbest = Pair.sortIdxSolutions(sols);
		
		if (DEBUG) {
			System.out.println("# Fitness sorted");
			for(int i = 0; i < sols.length; ++i) {
				System.out.println(i + " val = " + fit[i]);
			}
			// Print best K
			for(int i = 0; i < sols.length; ++i) {
				System.out.println(i + ". " + kbest[i].index
						+ " val = " + kbest[i].value);
			}
		}

	}
	
	protected void computeMass() {
		// Compute q = normalized fitness
		double accumq = 0;
		for(int i = 0; i < sols.length; ++i) {
			q[i] = (fit[i]-worstFitness) / (bestFitness-worstFitness);
			accumq += q[i];
		}
		
		// Compute mass in [0,1] value by fitness
		for(int i = 0; i < sols.length; ++i) {
			mass[i] = q[i] / accumq;
		}
		
		if (DEBUG) {
			System.out.println("# q's");
			for(int i = 0; i < sols.length; ++i) {
				System.out.println("q[i] = " + q[i]);
			}
			System.out.println("# mass");
			for(int i = 0; i < sols.length; ++i) {
				System.out.println("mass[i] = " + mass[i]);
			}
		}
	}
	
	protected double calcGravity() {
		double localGt = G0*Math.exp(-alfa*(double)numIter/MAX_ITER);
		// Check G(t) is ok
		if (DEBUG)
			System.out.println("# G(t) = " + localGt);
		return localGt;
	}
	
	protected double[][] calcDistances() {
		double [][] distances = new double[sols.length][kSize];
		for(int i = 0; i < sols.length; ++i) {
			for(int j = 0; j < kSize; ++j) {
				// Dist
				distances[i][j] = Algorithms.d2(
						sols[i].getCoords(), sols[kbest[j].index].getCoords());
				if (DEBUG)
					System.out.println("R["+i+"]["+j+"] = " + distances[i][j]);
			}
		}
		return distances;
	}

	protected void updateVelocity() {
		// Update velocity
		if (DEBUG)
			System.out.println("# velocity");
		for (int i = 0; i < sols.length; ++i) {
			double r = Globals.getRandomGenerator().randomUniform();
			for (int d = 0; d < targetProblem.getDim(); ++d) {
				v[i][d] = r*v[i][d]+a[i][d];
			}
			if (DEBUG) {
				System.out.print("v[" + i + "] = (");
				for(int d = 0; d < targetProblem.getDim(); ++d)
					System.out.print(v[i][d]+" ");
				System.out.println(')');
			}
		}
	}
	
	protected void updatePosition() {
		// Update position
		if (DEBUG)
			System.out.println("# position");
		for (int i = 0; i < sols.length; ++i) {
			double [] x = sols[i].getCoords();
			for (int d = 0; d < targetProblem.getDim(); ++d) {
				x[d] += v[i][d];
			}
			sols[i].setCoords(x);
			if (DEBUG) {
				System.out.print("x[" + i + "] = (");
				for(int d = 0; d < targetProblem.getDim(); ++d)
					System.out.print(v[i][d]+" ");
				System.out.println(')');
			}
		}
	}
	
	protected void updateAcceleration() {
		// Influence of j-th particle
		double [] factorj = new double[kSize];
		for(int j = 0; j < kSize; ++j) {
			// Factor j
			factorj[j] = Globals.getRandomGenerator().randomUniform();
			factorj[j] *= mass[kbest[j].index];

		}
		
		for(int i = 0; i < sols.length; ++i) {
			if (DEBUG)
				System.out.println("# Update acceleration " + i);
			// Accumulate forces in component 'd'
			for (int d = 0; d < targetProblem.getDim(); ++d) {
				a[i][d] = 0;
				for(int j = 0; j < kSize; ++j) {
					if (i == kbest[j].index)
						continue;
					// x[i][d]-x[k][d]
					double xdiff = sols[i].getCoords()[d]-sols[kbest[j].index].getCoords()[d];
					if (DEBUG)
						System.out.println("x["+i+"]["+d+"]-x["+kbest[j].index+"]["+d
								+"] = " + xdiff);
					// acceleration update
					a[i][d] += factorj[j] * (-xdiff) / (R[i][j]+epsilon);
				}
				a[i][d] *= Gt;
			}
			if (DEBUG) {
				System.out.print("a[" + i + "] = (");
				for(int d = 0; d < targetProblem.getDim(); ++d)
					System.out.print(a[i][d]+" ");
				System.out.println(')');
			}
		}
	}
	
	public void updateGlobalBest() {
		Solution curIterationBest = Algorithms.getGlobalOptimum(sols);
		if ((globalBest == null)
				|| (curIterationBest.getFitness() < globalBest.getFitness())) {
			globalBest = new Solution(curIterationBest);
		}
	}
	
	@Override
	public void nextIter() {
		// Next iteration
		if (DEBUG)
			System.out.println("\n# Begin iteration: " + numIter);

		if (numIter > MAX_ITER) {
			System.out.println("GSA: Maximum number of iterations reached: " + MAX_ITER);
			return ;
		}
		
		// Initialize structures
		
		// 1. Update fitnesses, best, worst, kbest
		computeFitness();
		
		processFitness();
		
		// 2. Compute q and mass
		computeMass();
		
		// 3. Compute gravity constant
		Gt = calcGravity();

		// 4. Compute distances between particles
		R = calcDistances();
		
		// 5. Update acceleration
		updateAcceleration();
		
		updateVelocity();

		updatePosition();
		
		updateGlobalBest();
		
		numIter++;
		
		// Check final fitness
		if (DEBUG) {
			System.out.println("# position");
			for (int i = 0; i < sols.length; ++i) {
				System.out.println("f[" + i + "] = " + sols[i].getFitness());
			}
		}
	}

	@Override
	public int getNumIter() {
		return numIter;
	}

	public void setNumIter(int numIter) {
		this.numIter = numIter;
	}

	public double getG0() {
		return G0;
	}

	public void setG0(double g0) {
		G0 = g0;
	}

	public double getAlfa() {
		return alfa;
	}

	public void setAlfa(double alfa) {
		this.alfa = alfa;
	}

	public double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	public int getMAX_ITER() {
		return MAX_ITER;
	}

	public void setMAX_ITER(int mAX_ITER) {
		MAX_ITER = mAX_ITER;
	}
	
	@Override
	public Solution getGlobalOptimum() {
		updateGlobalBest();
		return globalBest;
	}

	public boolean isParallel() {
		return parallel;
	}

	public void setParallel(boolean pARALLEL) {
		parallel = pARALLEL;
	}

	public int getNumThreads() {
		return numThreads;
	}

	public void setNumThreads(int nthreads) {
		this.numThreads = nthreads;
	}
	
}
