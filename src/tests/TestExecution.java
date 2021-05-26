package tests;

import commombenchmarks.AckleyProblem;
import commombenchmarks.RosebrockProblem;
import control.Globals;
import metaheuristics.GSA;
import metaheuristics.PTGSA;
import problems.Cec2015Problem;
import problems.Problem;
import problems.RandomProblem;
import solutions.Solution;

public class TestExecution {
	
	public static void demoCec2015(int dim, int popsize, int maxiter) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
	
		Problem problem = new Cec2015Problem(1, dim);
		// popsize
		GSA gsa = new GSA(popsize, problem);
		gsa.setMAX_ITER(maxiter);
		double [] alfas = {10, 12.5, 15, 17.5, 20, 22.5, 25, 27.5, 30};
		PTGSA ptgsa = new PTGSA(gsa, alfas);
		ptgsa.initPop();
		for(int i = 1; i <= maxiter; ++i) {
			System.out.println("-- Iter " + i);
			ptgsa.nextIter();
			if (i % 20 == 0) {
		//		ptgsa.printParamRange();
				ptgsa.printEW();
				ptgsa.printMeans();
			}
			// Global Best
			Solution globalBest = ptgsa.getGlobalOptimum();
			System.out.println("Best sol: ");
			System.out.println(globalBest);
			
			System.out.println();
		}
	}

	public static void demoRandom(int dim, int popsize, int maxiter) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
	
		Problem problem = new Cec2015Problem(15, dim);
		// popsize
		GSA gsa = new GSA(popsize, problem);
		gsa.setMAX_ITER(maxiter);
		double [] alfas = {20};
		PTGSA ptgsa = new PTGSA(gsa, alfas);
		ptgsa.initPop();
		for(int i = 1; i <= maxiter; ++i) {
			System.out.println("-- Iter " + i);
			ptgsa.nextIter();
			if (i % 20 == 0) {
		//		ptgsa.printParamRange();
				ptgsa.printEW();
				ptgsa.printMeans();
			}
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
		int numiter = 300;
//		int numiter = 200;
		System.out.println(" --- Start test --- ");
		long t0 = System.currentTimeMillis();
		demoRandom(dim, popsize, numiter);
		long t1 = System.currentTimeMillis();
		System.out.println(" --- End testing --- ");
		long time = (t1-t0)/1000;
		long msec = t1-t0-time*1000;
		System.out.println(" --Time: " + time + '.' + msec + " s");
	}

}
