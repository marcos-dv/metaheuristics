package metaheuristics;

import java.util.Arrays;

import problems.Problem;
import utils.Globals;

public abstract class PTAlgorithm {
	private IMetaheuristic metaheuristic;
	private double [] paramRange;
	private double [][] EW;
	private Problem problem;
	private int popsize;

	private boolean DEBUG = true;
	
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
		EW = new double[paramRange.length][popsize];
	}

	public int selectParamValue() {
		// Random pick
		return Globals.getRandomGenerator().randomInt(0, paramRange.length);
	}
	
	// Update in the metaheuristic the proper parameter!
	public abstract void setNewParam(double alfa);
	
	public void nextIter() {
		int idx = selectParamValue();
		double alfa = paramRange[idx];
		setNewParam(alfa);
		metaheuristic.nextIter();
	}

	public void run(int numiter) {
		setup();
		for(int i = 0; i < numiter; ++i) {
			nextIter();
		}
	}
	
}
