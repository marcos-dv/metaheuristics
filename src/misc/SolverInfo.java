package misc;

import metaheuristics.IMetaheuristic;
import problems.Problem;

public class SolverInfo {
	
	private IMetaheuristic algorithm;
	private Problem targetProblem;
	private int popsize;
	
	public SolverInfo(IMetaheuristic algorithm, Problem targetProblem, int popsize) {
		setAlgorithm(algorithm);
		setTargetProblem(targetProblem);
		setPopsize(popsize);
	}

	public IMetaheuristic getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(IMetaheuristic algorithm) {
		this.algorithm = algorithm;
	}

	public Problem getTargetProblem() {
		return targetProblem;
	}

	public void setTargetProblem(Problem targetProblem) {
		this.targetProblem = targetProblem;
	}

	public int getPopsize() {
		return popsize;
	}

	public void setPopsize(int popsize) {
		this.popsize = popsize;
	}
	
	
}
