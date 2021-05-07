package exec;

import commombenchmarks.SphereProblem;
import metaheuristics.GSA;
import metaheuristics.PTGSA;
import problems.Cec2015Problem;
import problems.MinSqSumProblem;
import problems.Problem;
import solutions.Solution;
import utils.Globals;

public class DemoBenchmarks {
	
	public static void demoBenchmarks(int dim, int popsize, int maxiter) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
	
		Problem problem = new Cec2015Problem(2, dim);
		// popsize
		GSA gsa = new GSA(popsize, problem);
		double [] alfas = {3, 10, 25, 50};
		PTGSA ptgsa = new PTGSA(gsa, alfas);
		ptgsa.initPop();
		for(int i = 1; i <= maxiter; ++i) {
			System.out.println("-- Iter " + i);
			ptgsa.nextIter();
	//		ptgsa.printParamRange();
			ptgsa.printEW();
			ptgsa.printMeans();

			// Global Best
			Solution globalBest = ptgsa.getGlobalOptimum();
			System.out.println("Best sol: ");
			System.out.println(globalBest);
			
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		int dim = 30;
		int popsize = 40;
		int numiter = 20;
		System.out.println(" --- Start test --- ");
		demoBenchmarks(dim, popsize, numiter);
		System.out.println(" --- End testing --- ");
	}

}
