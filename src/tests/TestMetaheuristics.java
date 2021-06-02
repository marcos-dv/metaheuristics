package tests;

import control.Globals;
import metaheuristics.GSA;
import metaheuristics.IMetaheuristic;
import metaheuristics.MultiSimulatedAnnealing;
import metaheuristics.Pointless;
import problems.MinSqSumProblem;
import problems.Problem;
import problems.RandomProblem;
import solutions.Solution;

public class TestMetaheuristics {
	
	public static void testProblem(IMetaheuristic meta, int dim, int popsize, int maxiter) {
		// popsize
		meta.initPop();
		double best = Double.POSITIVE_INFINITY;
		for (int numiter = 0; numiter < maxiter; ++numiter) {
			System.out.println("--Iter " + numiter);
			meta.nextIter();		
			// Global Best
			Solution globalBest = meta.getGlobalOptimum();
			best = Math.min(best, globalBest.getFitness());
			System.out.println("Best sol: ");
			System.out.println(globalBest);
			System.out.println();
		}
	}

	public static void testMultiSimulatedAnneling() {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
		int popsize = 10;
		int dim = 3;
		int maxiter = 1000;
		Problem problem = new MinSqSumProblem(dim);
		MultiSimulatedAnnealing meta = new MultiSimulatedAnnealing(popsize, problem);
		testProblem(meta, dim, popsize, maxiter);
	}

	public static void testPointless() {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
		int popsize = 10;
		int dim = 3;
		int maxiter = 1000;
		Problem problem = new MinSqSumProblem(dim);
		Pointless meta = new Pointless(popsize, problem);
		testProblem(meta, dim, popsize, maxiter);
	}

	
	public static void main(String[] args) {
		testMultiSimulatedAnneling();
		// testPointless();
	}
	
}
