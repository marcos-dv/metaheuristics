package exec;


import commombenchmarks.AckleyProblem;
import metaheuristics.GSA;
import metaheuristics.PTGSA;
import problems.Cec2015Problem;
import problems.Problem;
import problems.RandomProblem;
import solutions.Solution;
import utils.Globals;
import utils.SimpleClock;

public class DemoCec2015Parallel {
	
	private static PTGSA ptgsaInit(int popsize, int maxiter, Problem problem) {
		GSA gsa = new GSA(popsize, problem);
		gsa.setParallel(true);
		gsa.setNumThreads(2);
		gsa.setMAX_ITER(maxiter);
		double [] alfas = {15, 20, 25, 30};
		PTGSA ptgsa = new PTGSA(gsa, alfas);
		ptgsa.initPop();
		return ptgsa;
	}

	public static void demoRandom(int dim, int popsize, int maxiter) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
	
		Problem problem = new Cec2015Problem(14, dim);
		// popsize
		PTGSA ptgsa = ptgsaInit(popsize, maxiter, problem);
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
		int numiter = 5;
//		int numiter = 200;
		System.out.println(" --- Start test --- ");
		SimpleClock cl=new SimpleClock();
		cl.start();
		demoRandom(dim, popsize, numiter);
		cl.end();
		System.out.println(" --- End testing --- ");
		cl.displayTime();
	}

}
