package exec;

import metaheuristics.GSA;
import metaheuristics.PTGSA;
import problems.Cec2015Problem;
import problems.MinSumProblem;
import problems.Problem;
import problems.RandomProblem;
import solutions.Solution;
import utils.Globals;
import utils.SimpleClock;

public class DemoCommandLine {

	static int NUM_ITER = 10;
	static int NUM_THREADS = 0;
	static int ID_PROBLEM = 1;

	private static PTGSA ptgsaInit(int popsize, Problem problem) {
		GSA gsa = new GSA(popsize, problem);
		if (NUM_THREADS == 0) {
			System.out.println("Paralellism OFF");
			gsa.setParallel(false);
		}
		else {
			System.out.println("Paralellism ON");
			System.out.println("Threads:" + NUM_THREADS);
			gsa.setParallel(true);
			gsa.setNumThreads(NUM_THREADS);
		}
		gsa.setMAX_ITER(NUM_ITER);
		double [] alfas = {15, 20, 25, 30};
		PTGSA ptgsa = new PTGSA(gsa, alfas);
		ptgsa.initPop();
		return ptgsa;
	}

	public static void demoParallel(int dim, int popsize) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
	
		Problem problem = new Cec2015Problem(ID_PROBLEM, dim);
		// popsize
		PTGSA ptgsa = ptgsaInit(popsize, problem);
		for(int i = 1; i <= NUM_ITER; ++i) {
			System.out.println("-- Iteration " + i);
			ptgsa.nextIter();
			if (i % 20 == 0) {
		//		ptgsa.printParamRange();
		//		ptgsa.printEW();
		//		ptgsa.printMeans();
			}
			// Global Best
			Solution globalBest = ptgsa.getGlobalOptimum();
			System.out.println("Best sol: ");
			System.out.println(globalBest);
			
			System.out.println();
		}
	}

	
	
	public static void main(String[] args) {
		boolean ok = parseArgs(args);
		if (!ok)
			return ;
		int dim = 30;
		int popsize = 50;
		System.out.println(" --- Start test --- ");
		System.out.println("Running " + NUM_ITER + " iterations");
		System.out.println("Popsize " + popsize);
		SimpleClock cl=new SimpleClock();
		cl.start();
		demoParallel(dim, popsize);
		cl.end();
		System.out.println(" --- End testing --- ");
		cl.displayTime();
	}

	private static boolean parseArgs(String[] args) {
		if (args.length <= 0)
			return false;
		if (args[0].equals("-h")) {
			System.out.println("./exec NUM_ITER NUM_THREADS ID_PROBLEM");
			System.out.println("\tNUM_ITER: number of iterations. Default: " + NUM_ITER);
			System.out.println("\tNUM_THREADS: number of threads, if it is 0, no parallelism. Default: 0");
			System.out.println("\tID_PROBLEM: id of the problem to solve. Range of ids: from 1 to 15. Default: 1");
			return false;
		}
		NUM_ITER = Integer.parseInt(args[0]);
		if (args.length >= 2)
			NUM_THREADS = Integer.parseInt(args[1]);
		if (args.length >= 3)
			ID_PROBLEM = Integer.parseInt(args[2]);
		
		return true;
	}
}
