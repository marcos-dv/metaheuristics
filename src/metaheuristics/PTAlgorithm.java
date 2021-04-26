package metaheuristics;

import java.util.ArrayList;
import java.util.Arrays;

import problems.Problem;
import utils.Globals;

public abstract class PTAlgorithm {
	private IMetaheuristic metaheuristic;
	private double [] paramRange;
	private double [] meansEW;
	private double [][] EW;
	private Problem problem;
	private int popsize;

	private boolean DEBUG = true;
	private double curAlfa; // current value for the parameter alfa
	
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
	
	protected void updateEW(int row) {
		
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
