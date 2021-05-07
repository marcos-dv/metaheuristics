package tests;

import commombenchmarks.RosebrockProblem;
import commombenchmarks.SphereProblem;
import metaheuristics.GSA;
import metaheuristics.PTGSA;
import problems.Problem;
import solutions.Solution;
import utils.Globals;

public class TestGSAvsPTGSA {
	
	public static double testGSA(int dim, int popsize, int maxiter) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
	
		Problem problem = new SphereProblem(dim);
		// popsize
		GSA gsa = new GSA(popsize, problem);
		gsa.setAlfa(20);
		gsa.initPop();
		double best = Double.POSITIVE_INFINITY;
		for(int i = 1; i <= maxiter; ++i) {
			System.out.println("-- Iter " + i);
			gsa.nextIter();

			// Global Best
			Solution globalBest = gsa.getGlobalOptimum();
			best = Math.min(best, globalBest.getFitness());
			System.out.println("Best sol: ");
			System.out.println(globalBest);
			
			System.out.println();
		}
		return best;
	}

	public static double testPTGSA(int dim, int popsize, int maxiter) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
	
		Problem problem = new SphereProblem(dim);
		// popsize
		GSA gsa = new GSA(popsize, problem);
//		double [] alfas = {3, 10, 25, 50};
		double [] alfas = {20};
		PTGSA ptgsa = new PTGSA(gsa, alfas);
		ptgsa.initPop();
		double best = Double.POSITIVE_INFINITY;
		for(int i = 1; i <= maxiter; ++i) {
			System.out.println("-- Iter " + i);
			ptgsa.nextIter();

			// Global Best
			Solution globalBest = ptgsa.getGlobalOptimum();
			best = Math.min(best, globalBest.getFitness());
			System.out.println("Best sol: ");
			System.out.println(globalBest);
			
			System.out.println();
		}
		return best;
	}

	
	public static void main(String[] args) {
		int dim = 20;
		int popsize = 50;
		int numiter = 20;
		System.out.println(" --- Start test --- ");
		double bestgsa = testGSA(dim, popsize, numiter);
		double bestptgsa = testPTGSA(dim, popsize, numiter);
		
		System.out.println("GSA: " + bestgsa);
		System.out.println("PTGSA: " + bestptgsa);
		
		System.out.println(" --- End testing --- ");
	}

}
