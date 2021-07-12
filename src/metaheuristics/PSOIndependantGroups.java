package metaheuristics;

import control.Globals;
import control.Messages;
import problems.Problem;
import solutions.Solution;
import utils.Algorithms;
import utils.Geometry;
import utils.RandomGenerator;

public class PSOIndependantGroups extends PSO {
	private boolean DEBUG;
	// Hyperparameters
	protected int groupSize;

	public PSOIndependantGroups(int popSize, Problem targetProblem) {
		super(popSize, targetProblem);
		setGroupSize(5);
	}

	public PSOIndependantGroups(Solution[] sols, Problem targetProblem) {
		this(sols.length, targetProblem);
		this.setSols(sols.clone());
	}

	public PSOIndependantGroups(PSOIndependantGroups pso) {
		this(pso.getSols(), pso.targetProblem);
	}

	@Override
	protected double[][] computeSpeed() {
		// First, compute speed
		double[][] speed = new double[popSize][dimension];
		Solution[] groupBest = getBestOfGroup(sols, getGroupSize());

		for (int i = 0; i < sols.length; ++i) {
			double factorLocalBest = 1;
			double factorGlobalBest = 1;
			if (stochastic) {
				factorLocalBest = Globals.getRandomGenerator().randomUniform();
				factorGlobalBest = Globals.getRandomGenerator().randomUniform();
			}
			// Speed impact
			double[] speedImpact = Geometry.mult(v[i], coefSpeed);
			// Local best impact
			double[] x = sols[i].getCoords();
			double[] localBestImpact = Geometry.diff(bestSols[i].getCoords(), x);
			localBestImpact = Geometry.mult(localBestImpact, coefLocalBest * factorLocalBest);
			// Group best impact
			double[] groupBestImpact = Geometry.diff(groupBest[i].getCoords(), x);
			groupBestImpact = Geometry.mult(groupBestImpact, coefGlobalBest * factorGlobalBest);
			// Sum
			speed[i] = Geometry.sum(speedImpact, localBestImpact);
			speed[i] = Geometry.sum(speed[i], groupBestImpact);
			if (DEBUG) {
				System.out.println(i);
				Geometry.display(speed[i]);
			}
		}
		return speed;
	}

	protected Solution[] getBestOfGroup(Solution[] sols, int groupSize) {
		Solution[] groupBest = new Solution[sols.length];
		for (int g = 0; g < sols.length / groupSize; ++g) {
			Solution[] group = new Solution[groupSize];
			for (int i = 0; i < groupSize; ++i) {
				group[i] = sols[g * groupSize + i];
			}
			Solution bestOfGroup = Algorithms.getGlobalOptimum(group);
			for (int i = 0; i < groupSize; ++i) {
				groupBest[g * groupSize + i] = bestOfGroup;
			}
		}
		if (sols.length % groupSize != 0) {
			int lastGroup = sols.length % groupSize;
			Solution[] group = new Solution[lastGroup];
			for (int i = 0; i < lastGroup; ++i) {
				group[i] = sols[(sols.length / groupSize) * groupSize + i];
			}
			Solution bestOfGroup = Algorithms.getGlobalOptimum(group);
			for (int i = 0; i < lastGroup; ++i) {
				groupBest[(sols.length / groupSize) * groupSize + i] = bestOfGroup;
			}
		}
		return groupBest;
	}

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

	public int getGroupSize() {
		return groupSize;
	}

	public void setGroupSize(int groupSize) {
		this.groupSize = groupSize;
	}

}
