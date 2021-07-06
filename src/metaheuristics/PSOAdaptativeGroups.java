package metaheuristics;

import problems.Problem;
import solutions.Solution;

/**
 * PSO with adaptatives groups. The groups are becoming bigger with iterations.
 * After some iterations, there is only one group (as in usual PSO)
 * @author Marcos Dominguez Velad
 *
 */
public class PSOAdaptativeGroups extends PSOGroups {
	// Hyperparameters
	protected int iterPeriod;

	public PSOAdaptativeGroups(int popSize, Problem targetProblem) {
		super(popSize, targetProblem);
		iterPeriod = 20;
	}

	public PSOAdaptativeGroups(Solution[] sols, Problem targetProblem) {
		this(sols.length, targetProblem);
		this.setSols(sols.clone());
	}

	public PSOAdaptativeGroups(PSOAdaptativeGroups pso) {
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
