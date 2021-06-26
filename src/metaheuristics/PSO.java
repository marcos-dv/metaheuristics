package metaheuristics;

import control.Globals;
import control.Messages;
import problems.Problem;
import solutions.Solution;
import utils.Algorithms;
import utils.Geometry;
import utils.RandomGenerator;

public class PSO implements IMetaheuristic {
	private Solution [] sols;
	private Solution [] bestSols;
	private double [][] v;
	private Problem targetProblem;
	private int dimension;
	private int popSize;
	private int numIter;
	
	private boolean verbose = false;
	private boolean DEBUG = false;
	private boolean stochastic = true;

	// Hyperparameters
	private double coefSpeed; // impact of last speed over current speed
	private double coefLocalBest; // impact of best local position over current speed
	private double coefGlobalBest; // impact of global best over current speed
	private double learningRate; // impact of last speed over position

	
	public PSO(int popSize, Problem targetProblem) {
		this.targetProblem = targetProblem;
		dimension = targetProblem.getDim();
		numIter = 0;
		coefSpeed = .3;
		coefLocalBest = .3;
		coefGlobalBest = .3;
		learningRate = 0.5;
		this.popSize = popSize;
		bestSols = new Solution[popSize];
		sols = new Solution[popSize];
	}
	
	public PSO(Solution [] sols, Problem targetProblem) {
		this(sols.length, targetProblem);
		this.setSols(sols.clone());
	}

	public PSO(PSO pso) {
		this(pso.getSols(), pso.targetProblem);
	}

	public double [][] randomSpeed() {
		double[][] v = new double[popSize][dimension];
	    RandomGenerator rand = Globals.getRandomGenerator();
		for(int i = 0; i < popSize; ++i) {
			for(int d = 0; d < dimension; ++d) {
				v[i][d] = rand.randomUniform(targetProblem.getLB()-targetProblem.getUB(),
										targetProblem.getUB()-targetProblem.getLB());
			}
		}
		return v;
	}

	public double [] zeroSpeed() {
		return new double[getPopSize()];
	}

	@Override
	public void initPop() {
		if (sols == null) {
			Messages.error("PSO: solutions are not initialized");
			return ;
		}

		// Random init
		for (int i = 0; i < sols.length; ++i) {
			if (sols[i]== null) {
				sols[i] = new Solution(targetProblem);
				sols[i].randomInit();
			}
		}

		
		
		// Update local optimum for each particle
		updateBestPositions();
		
		// Init speed
		v = randomSpeed();
		
	}

	@Override
	public void nextIter() {
		numIter++;
		if (verbose) {
			System.out.println("Iteration " + numIter);
		}
		// Get new speed
		v = computeSpeed();
		updatePosition(v);
		updateBestPositions();
	}

	private void updateBestPositions() {
		for(int i = 0; i < popSize; ++i) {
			if ((bestSols[i] == null) 
				|| (sols[i].getFitness() < bestSols[i].getFitness())) {
				bestSols[i] = new Solution(sols[i]);
			}
		}
	}

	private double[][] computeSpeed() {
		// First, compute speed
		double [][] speed = new double[popSize][dimension];
		double [] globalBestPosition = Algorithms.getGlobalOptimum(bestSols).getCoords();
		for(int i = 0; i < sols.length; ++i) {
			double factorLocalBest = 1;
			double factorGlobalBest = 1;
			if (stochastic) {
				factorLocalBest = Globals.getRandomGenerator().randomUniform();
				factorGlobalBest = Globals.getRandomGenerator().randomUniform();
			}
			// Speed impact
			double [] speedImpact = Geometry.mult(v[i], coefSpeed);
			// Local best impact
			double [] x = sols[i].getCoords();
			double [] localBestImpact = Geometry.diff(bestSols[i].getCoords(), x);
			localBestImpact = Geometry.mult(localBestImpact, coefLocalBest*factorLocalBest);
			// Global best impact
			double [] globalBestImpact = Geometry.diff(globalBestPosition, x);
			globalBestImpact = Geometry.mult(globalBestImpact, coefGlobalBest*factorGlobalBest);
			// Sum
			speed[i] = Geometry.sum(speedImpact, localBestImpact);
			speed[i] = Geometry.sum(speed[i], globalBestImpact);
			if (DEBUG) {
				System.out.println(i);
				Geometry.display(speed[i]);
			}
		}
		return speed;
	}

	private void updatePosition(double [][] speed) {
		for(int i = 0; i < popSize; ++i) {
			double [] x = sols[i].getCoords();
			x = Geometry.sum(x, Geometry.mult(speed[i], learningRate));
			sols[i].setCoords(x);
		}
	}

	@Override
	public int getNumIter() {
		return numIter;
	}

	@Override
	public Solution[] getSols() {
		return sols;
	}

	@Override
	public Solution getGlobalOptimum() {
		return Algorithms.getGlobalOptimum(getSols());
	}

	public void setNumIter(int numIter) {
		this.numIter = numIter;
	}

	public void setSols(Solution [] sols) {
		this.sols = sols;
	}

	public Solution [] getBestSols() {
		return bestSols;
	}

	public void setBestSols(Solution [] bestSols) {
		this.bestSols = bestSols;
	}

	public double [][] getV() {
		return v;
	}

	public void setV(double [][] v) {
		this.v = v;
	}

	public int getPopSize() {
		return popSize;
	}

	public void setPopSize(int popSize) {
		this.popSize = popSize;
	}

	public boolean isStochastic() {
		return stochastic;
	}

	public void setStochastic(boolean stochastic) {
		this.stochastic = stochastic;
	}

	public double getCoefSpeed() {
		return coefSpeed;
	}

	public void setCoefSpeed(double coefSpeed) {
		this.coefSpeed = coefSpeed;
	}

	public double getCoefLocalBest() {
		return coefLocalBest;
	}

	public double setCoefLocalBest(double coefLocalBest) {
		this.coefLocalBest = coefLocalBest;
		return coefLocalBest;
	}

	public double getCoefGlobalBest() {
		return coefGlobalBest;
	}

	public double setCoefGlobalBest(double coefGlobalBest) {
		this.coefGlobalBest = coefGlobalBest;
		return coefGlobalBest;
	}

	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	
}
