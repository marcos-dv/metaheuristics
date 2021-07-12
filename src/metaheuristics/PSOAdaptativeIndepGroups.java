package metaheuristics;

import problems.Problem;
import solutions.Solution;

public class PSOAdaptativeIndepGroups extends PSOIndependantGroups {
	// Hyperparameters
	protected int iterPeriod;

	public PSOAdaptativeIndepGroups(int popSize, Problem targetProblem) {
		super(popSize, targetProblem);
		iterPeriod = 20;
	}

	public PSOAdaptativeIndepGroups(Solution[] sols, Problem targetProblem) {
		this(sols.length, targetProblem);
		this.setSols(sols.clone());
	}

	public PSOAdaptativeIndepGroups(PSOAdaptativeIndepGroups pso) {
		this(pso.getSols(), pso.targetProblem);
	}

	@Override
	public void nextIter() {
		numIter++;
		if (verbose) {
			System.out.println("Iteration " + numIter);
		}
		if (groupSize < sols.length && (numIter % iterPeriod == 0)) {
			groupSize++;
		}
		// Get new speed
		v = computeSpeed();
		updatePosition(v);
		updateBestPositions();
	}

	public int getIterPeriod() {
		return iterPeriod;
	}

	public void setIterPeriod(int iterPeriod) {
		this.iterPeriod = iterPeriod;
	}

}
