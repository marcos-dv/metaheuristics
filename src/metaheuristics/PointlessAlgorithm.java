package metaheuristics;

import control.Globals;
import control.Messages;
import problems.Problem;
import solutions.Solution;
import utils.Algorithms;

public class PointlessAlgorithm implements IMetaheuristic {

	private boolean DEBUG = false;
	
	private Solution [] sols;
	private Solution globalBest;
	private Problem targetProblem;
	private int numIter;

	public PointlessAlgorithm(Problem targetProblem) {
		this.targetProblem = targetProblem;
		if (targetProblem.getDim() <= 0) {
			Messages.warning("PointlessAlgorithm: Target problem dimension equals 0.");
		}
		numIter = 0;
	}

	public PointlessAlgorithm(int popSize, Problem targetProblem) {
		this(targetProblem);
		sols = new Solution[popSize];
	}
	
	public PointlessAlgorithm(Solution [] sols, Problem targetProblem) {
		this(targetProblem);
		this.sols = sols.clone();
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
		// Update best solution
		updateGlobalBest();
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
		numIter++;
	}

	@Override
	public int getNumIter() {
		return numIter;
	}

	public void setNumIter(int numIter) {
		this.numIter = numIter;
	}

	public void setSols(Solution[] sols) {
		this.sols = sols;
	}

	@Override
	public Solution[] getSols() {
		return sols;
	}

	@Override
	public Solution getGlobalOptimum() {
		updateGlobalBest();
		return globalBest;
	}

}
