package exec;

import control.Globals;
import metaheuristics.GSA;
import metaheuristics.PTGSA;
import problems.MinSqSumProblem;
import problems.Problem;
import problems.commombenchmarks.AckleyProblem;
import problems.commombenchmarks.GriewankProblem;
import solutions.Solution;

public class DemoPTGSA {

	public static void demoRunPTGSA(int dim, int popsize, int maxiter) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(2);
	
		Problem problem = new GriewankProblem(dim);
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
//		int dim = 3;
//		int popsize = 50;
//		int numiter = 20;
		int dim = 30;
		int popsize = 50;
		int numiter = 300000;
		System.out.println("Start test");
		demoRunPTGSA(dim, popsize, numiter);
		System.out.println("End testing");
	}

}
