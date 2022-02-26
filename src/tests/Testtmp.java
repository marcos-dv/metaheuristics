package tests;

import control.Globals;
import metaheuristics.GSA;
import metaheuristics.PTGSA;
import problems.Cec2015Problem;
import problems.Problem;
import solutions.Solution;

public class Testtmp {
	
	public static void demoCec2015(int dim, int popsize, int maxiter) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
	
		Problem problem = new Cec2015Problem(2, dim);
		// popsize
		GSA gsa = new GSA(popsize, problem);
		gsa.setMAX_ITER(maxiter);
		double [] alfas = {0.5, 1, 1.5, 2};
		PTGSA ptgsa = new PTGSA(gsa, alfas);
		ptgsa.initPop();
		for(int i = 1; i <= maxiter; ++i) {
			ptgsa.nextIter();
			// Global Best
			Solution globalBest = ptgsa.getGlobalOptimum();
			if (i % 100 == 0) {
				System.out.println("-- Iter " + i);
				System.out.println("Best sol: ");
				System.out.println(globalBest);
				System.out.println();
			}
		}
	}


	public static void main(String[] args) {
		int dim = 30;
		int popsize = 50;
		int numiter = 300000;
		System.out.println(" --- Start test --- ");
		long t0 = System.currentTimeMillis();
		demoCec2015(dim, popsize, numiter);
		long t1 = System.currentTimeMillis();
		System.out.println(" --- End testing --- ");
	}

}
