package metaheuristics;

import java.util.ArrayList;
import java.util.Arrays;

import problems.Problem;
import problems.Solution;
import utils.Globals;

public abstract class PTAlgorithm {
	private IMetaheuristic metaheuristic;
	private double [] paramRange;
	private double [] meansEW;
	private double [] prevFitness;
	private double [][] EW;
	private Problem problem;
	private int popsize;

	private boolean DEBUG = true;
	private double curAlfa; // current value for the parameter alfa
	
	private double SIGN_TOLERANCE = 0;
	private double DELTA_THRESHOLD = 1e-7;
	
	public PTAlgorithm(IMetaheuristic metaheuristic) {
		this.metaheuristic = metaheuristic;
		popsize = metaheuristic.getSols().length;
		if (popsize <= 0) {
			System.out.println("Warning-PTAlgorithm: metaheuristic has no population");
		}
	}

	public PTAlgorithm(IMetaheuristic metaheuristic, double [] range) {
		this(metaheuristic);
		setParamRange(range);
	}

	public double[] getParamRange() {
		return paramRange;
	}

	public void setParamRange(double[] paramRange) {
		this.paramRange = paramRange.clone();
		Arrays.sort(this.paramRange);
	}

	public double getCurAlfa() {
		return curAlfa;
	}

	public void setCurAlfa(double curAlfa) {
		this.curAlfa = curAlfa;
	}

	public IMetaheuristic getMetaheuristic() {
		return metaheuristic;
	}

	public void setMetaheuristic(IMetaheuristic metaheuristic) {
		this.metaheuristic = metaheuristic;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	
	public void printParamRange() {
		System.out.println("Parameter domain:");
		for(int i = 0; i < paramRange.length; ++i) {
			System.out.println(paramRange[i] + ' ');
		}
	}

	public void printEW() {
		System.out.println("Elementary Weights Matrix:");
		for(int i = 0; i < EW.length; ++i) {
			for(int j = 0; j < EW[i].length; ++j) {
				System.out.print(EW[i][j] + "  ");
			}	
			System.out.println();
		}
	}
	
	public double[][] getEW() {
		return EW;
	}

	public void setEW(double[][] eW) {
		EW = eW.clone();
	}

	public int getPopsize() {
		return popsize;
	}

	public void setPopsize(int popsize) {
		this.popsize = popsize;
	}
	
	public void setup() {
		metaheuristic.initPop();
		if (paramRange == null) {
			System.out.println("Warning-PTAlgorithm: range of the parameter is not initialized");
		}
		// Initialize EW matrix and means
		EW = new double[paramRange.length][popsize];
		meansEW = new double[paramRange.length];
		for(int i = 0; i < paramRange.length; ++i) {
			for(int j = 0; j < popsize; ++j)
				EW[i][j] = 1;
			meansEW[i] = calcMean(i);
		}
		// Initialize previous fitness for each iteration
		prevFitness = null;
	}

	// Return the index of the best param
	public int selectParamValue() {
		double bestMean = Double.NEGATIVE_INFINITY;
		ArrayList<Integer> bestIndexes = new ArrayList<>();
		for(int i = 0; i < paramRange.length; ++i) {
			// better value
			if (meansEW[i] > bestMean) {
				bestIndexes.clear();
				bestIndexes.add(i);
				bestMean = meansEW[i];
			}
			// new candidate
			else if (meansEW[i] == bestMean) {
				bestIndexes.add(i);
			}
		}
		if (bestIndexes.size() == 0) {
			System.out.println("Warning-PTAlgorithm: not best parameter value found");
		}
		if (DEBUG) {
			System.out.println("# Best indexes and values:");
			for (Integer index: bestIndexes) {
				System.out.println(index + " -> " + paramRange[index]);
			}
		}
		int idx = Globals.getRandomGenerator().randomInt(0, bestIndexes.size());
		return bestIndexes.get(idx);
	}
	
	// Update in the metaheuristic the proper parameter!
	public abstract void setNewParam(double alfa);
	
	public double calcMean(int row) {
		double mean = 0;
		for(int j = 0; j < popsize; ++j) {
			mean += EW[row][j];
		}
		return mean / (double)popsize;
	}
	
	public void initPrevFitness() {
		// Only for first iteration, initialize prev fitness
		if (prevFitness == null) {
			Solution[] sols = metaheuristic.getSols();
			if(popsize != sols.length) {
				System.out.println("Warning-PTAlgorithm: popsize != sols.length in metaheuristic!\n\tpopsize = " 
						+ popsize + " and sols.length = " + sols.length);
			}
			prevFitness = new double[popsize];
			for(int i = 0; i < popsize; ++i) {
				prevFitness[i] = sols[i].getFitness();
			}
		}
	}
	
	public void updateCaseAllGood(int row, double [] delta) {
		for(int j = 0; j < popsize; ++j) {
			EW[row][j] = Math.tanh(delta[j]/popsize);
		}
	}

	public void updateCaseAllBad(int row, double [] delta) {
		// Negative weights
		for(int j = 0; j < popsize; ++j) {
			EW[row][j] = Math.tanh(delta[j]/popsize);
		}
	}

	public void updateCaseMixed(int row, double [] delta) {
		for(int j = 0; j < popsize; ++j) {
			EW[row][j] = Math.abs(Math.tanh(delta[j]/popsize));
		}
	}

	public void updateCaseStagnition(int row, double [] delta) {
		for(int j = 0; j < popsize; ++j) {
			EW[row][j] = -2;
		}
	}

	public void updateRow(int row, double [] delta) {
		assert(popsize == delta.length);
		int cntPlus = 0;
		int cntMinus = 0;
		int cntZero = 0;
		int cntStagnition = 0;

		// SIGN_TOLERANCE ~ 0
		// Analysis of the landscape
		for(int i = 0; i < popsize; ++i) {
			// Magnitude
			if (Math.abs(delta[i]) <= DELTA_THRESHOLD) {
				cntStagnition++;
			}
			// Sign
			if (delta[i] > SIGN_TOLERANCE) {
				cntPlus++;
			}
			else if (delta[i] < -SIGN_TOLERANCE) {
				cntMinus++;
			}
			else if (Math.abs(delta[i]) <= SIGN_TOLERANCE) {
				cntZero++;
			}
		}
		// Update
		// Case 6 -> Stagnition
		if (cntStagnition == popsize) {
			updateCaseStagnition(row, delta);
		}
		// Cases 1 and 2, improvement
		else if (cntMinus == 0) {
			updateCaseAllGood(row, delta);
		}
		// Cases 3 and 4, bad improvement
		else if (cntPlus + cntZero == 0) {
			updateCaseAllBad(row, delta);
		}
		// Cases 5, exploration
		else if ((cntPlus + cntZero > 0) && (cntMinus > 0)) {
			updateCaseMixed(row, delta);
		}
		else {
			System.out.println("Warning-PTAlgorithm: Unknown landscape case");
		}
	}
	
	
	public void updateEW(int row) {
		// first iteration
		if (prevFitness == null) {
			initPrevFitness();
		}
		else {
			double [] deltaFitness = new double[popsize];
			Solution [] sols = metaheuristic.getSols();
			// Compute differences
			for(int i = 0; i < popsize; ++i) {
				deltaFitness[i] = sols[i].getFitness() - prevFitness[i];
			}
			updateRow(row, deltaFitness);
		}
	}
	
	public void nextIter() {
		int idx = selectParamValue();
		curAlfa = paramRange[idx];
		setNewParam(curAlfa);
		metaheuristic.nextIter();
		updateEW(idx);
	}

	public void run(int numiter) {
		setup();
		for(int i = 0; i < numiter; ++i) {
			nextIter();
		}
	}
	
}
