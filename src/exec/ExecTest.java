package exec;

import control.Globals;
import metaheuristics.GSA;
import metaheuristics.PTGSA;
import metaheuristics.PTGSAVariant;
import problems.Cec2015Problem;
import problems.Problem;
import solutions.Solution;
import utils.SimpleClock;

public class ExecTest {

	public static void demoCec2015(int func, int dim, int popsize, int maxiter) {
		Problem problem = new Cec2015Problem(func, dim);
		// popsize
		GSA gsa = new GSA(popsize, problem);
		gsa.setMAX_ITER(maxiter);
		double [] alfas = {10, 12.5, 15, 17.5, 20, 22.5, 25, 27.5, 30};
		PTGSAVariant ptgsa = new PTGSAVariant(gsa, alfas);
		ptgsa.initPop();
		ptgsa.setTemporalWeight(0.5);
		for(int i = 1; i <= maxiter; ++i) {
			ptgsa.nextIter();
		}
		Solution globalBest = ptgsa.getGlobalOptimum();
		System.out.println("Func: " + func);
		System.out.println("Best fitness: " + globalBest.getFitness());
	}

	
	public static void main(String[] args) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
		int [] funcs = new int[]{1, 5, 11};
		int dim = 30;
		int popsize = 50;
		int maxiter = 150000;
		for (int func = 1; func <= 15; func++) {
			SimpleClock clock = new SimpleClock();
			clock.start();
			demoCec2015(func, dim, popsize, maxiter);
			clock.end();
			clock.displayTime();
		}
	}

}
