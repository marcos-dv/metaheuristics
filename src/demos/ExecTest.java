package demos;

import control.Globals;
import metaheuristics.GSA;
import metaheuristics.PSO;
import metaheuristics.PSOAll;
import metaheuristics.PTGSA;
import metaheuristics.PTGSA;
import problems.Cec2015Problem;
import problems.Problem;
import solutions.Solution;
import utils.SimpleClock;

public class ExecTest {

	public static void demoCec2015(int func, int dim, int popsize, int maxiter) {
		Problem problem = new Cec2015Problem(func, dim);
		
		/*
		GSA gsa = new GSA(popsize, problem);
		gsa.setMAX_ITER(maxiter);
		double [] alfas = {10, 12.5, 15, 17.5, 20, 22.5, 25, 27.5, 30};
		
		PTGSAVariant algo = new PTGSAVariant(gsa, alfas);
		algo.initPop();
		algo.setTemporalWeight(0.5);
		*/
		
		PSO pso = new PSO(popsize, problem);
		double [] values = {.1, .2, .3, .4};
		PSOAll algo = new PSOAll(pso);
		algo.setNewParam("v", values);
		algo.setNewParam("local", values);
		algo.setNewParam("global", values);
		algo.initPop();
		for(int i = 1; i <= maxiter; ++i) {
			algo.nextIter();
		}
		Solution globalBest = algo.getGlobalOptimum();
		System.out.println("Func: " + func);
		System.out.println("Best fitness: " + globalBest.getFitness());
	}

	
	public static void main(String[] args) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
		int [] funcs = new int[]{1, 5, 11};
		int dim = 30;
		int popsize = 50;
		int maxiter = dim*10000;
		for (int func = 1; func <= 15; func++) {
			SimpleClock clock = new SimpleClock();
			clock.start();
			demoCec2015(func, dim, popsize, maxiter);
			clock.end();
			clock.displayTime();
		}
	}

}
