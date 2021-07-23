package metaheuristics;

import control.Globals;
import control.Messages;
import problems.Problem;
import solutions.Solution;
import utils.Algorithms;
import utils.Geometry;
import utils.RandomGenerator;

public class PSORingGroups extends PSO {
	private boolean DEBUG;
	// Hyperparameters
	private int ratio;

	public PSORingGroups(int popSize, Problem targetProblem) {
		super(popSize, targetProblem);
		ratio = 1;
	}

	public PSORingGroups(Solution[] sols, Problem targetProblem) {
		this(sols.length, targetProblem);
		this.setSols(sols.clone());
	}

	public PSORingGroups(PSORingGroups pso) {
		this(pso.getSols(), pso.targetProblem);
	}

	@Override
	protected double[][] computeSpeed() {
		// First, compute speed
		double[][] speed = new double[popSize][dimension];
		Solution[] groupBest = getBestOfGroup(sols, ratio);

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

	protected Solution[] getBestOfGroup(Solution[] sols, int ratio) {
		Solution[] groupBest = new Solution[sols.length];
		for (int i = 0; i < sols.length; ++i) {
			int groupSize = 1+2*ratio;
			Solution[] group = new Solution[groupSize];
			for (int j = i-ratio, k = 0; j <= i+ratio; ++j, k++) {
				int trueIndex = j;
				while (trueIndex < 0) // % operator return negative numbers
					trueIndex += sols.length;
				group[k] = sols[trueIndex % sols.length];
			}
			groupBest[i] = Algorithms.getGlobalOptimum(group);
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

	public int getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}

}
