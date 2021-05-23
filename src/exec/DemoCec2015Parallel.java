package exec;


import metaheuristics.GSA;
import metaheuristics.PTGSA;
import problems.Cec2015Problem;
import problems.Problem;
import solutions.Solution;
import utils.Globals;

public class DemoCec2015Parallel {
	
	private static PTGSA ptgsaInit(int popsize, int maxiter, Problem problem) {
		GSA gsa = new GSA(popsize, problem);
		gsa.setParallel(true);
		gsa.setNumThreads(8);
		gsa.setMAX_ITER(maxiter);
		double [] alfas = {15, 20, 25, 30};
		PTGSA ptgsa = new PTGSA(gsa, alfas);
		ptgsa.initPop();
		return ptgsa;
	}

	public static void demoRandom(int dim, int popsize, int maxiter) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
	
		Problem problem = new Cec2015Problem(1, dim);
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
		int numiter = 10;
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
