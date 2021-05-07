package exec;

import metaheuristics.GSA;
import metaheuristics.PTGSA;
import problems.Cec2015Problem;
import problems.Problem;
import solutions.Solution;
import utils.Globals;

public class DemoCec2015 {
	
	public static void demoCec2015(int dim, int popsize, int maxiter) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
	
		Problem problem = new Cec2015Problem(5, dim);
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
		int popsize = 50;
		int numiter = 50;
		System.out.println(" --- Start test --- ");
		demoCec2015(dim, popsize, numiter);
		System.out.println(" --- End testing --- ");
	}

}
