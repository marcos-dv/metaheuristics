package exec;

import commombenchmarks.EllipticProblem;
import commombenchmarks.QuarticProblem;
import commombenchmarks.RosebrockProblem;
import metaheuristics.GSA;
import metaheuristics.PTGSA;
import problems.Problem;
import solutions.Solution;
import utils.Globals;

public class DemoBenchmarks {
	
	public static void demoBenchmarks(int dim, int popsize, int maxiter) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
	
		Problem problem = new QuarticProblem(dim);
		// popsize
		GSA gsa = new GSA(popsize, problem);
	//	double [] alfas = {3, 10, 25, 50};
		double [] alfas = {20};
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
		int popsize = 50;
		int numiter = 200;
		System.out.println(" --- Start test --- ");
		demoBenchmarks(dim, popsize, numiter);
		System.out.println(" --- End testing --- ");
	}

}
