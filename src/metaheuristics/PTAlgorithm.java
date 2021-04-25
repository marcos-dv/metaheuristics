package metaheuristics;

import java.util.Arrays;

import problems.Problem;

public abstract class PTAlgorithm {
	private IMetaheuristic metaheuristic;
	private double [] paramRange;
	private double [][] EW;
	private Problem problem;
	
	public PTAlgorithm(IMetaheuristic metaheuristic) {
		this.metaheuristic = metaheuristic;
		int popsize = metaheuristic.getSols().length;
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

	
	public void setup() {
		metaheuristic.initPop();
		if (paramRange == null) {
			System.out.println("Warning-PTAlgorithm: range of the parameter is not initialized");
		}
	}

	public void nextIter() {
		metaheuristic.nextIter();
	}

	public void run(int numiter) {
		setup();
		for(int i = 0; i < numiter; ++i) {
			nextIter();
		}
	}
	
}
