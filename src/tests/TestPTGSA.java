package tests;

import metaheuristics.GSA;
import metaheuristics.PTGSA;
import problems.MinSqSumProblem;
import problems.Problem;
import utils.Globals;

public class TestPTGSA {
	
	public static void testSetParam(int dim, int popsize, int maxiter) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
	
		Problem problem = new MinSqSumProblem(dim);
		// popsize
		GSA gsa = new GSA(popsize, problem);
		double [] alfas = {2., 5., 7.};
		PTGSA ptgsa = new PTGSA(gsa, alfas);
		ptgsa.run(maxiter);		
	}
	
	public static void testUpdateEW(int dim, int popsize, int maxiter) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
	
		Problem problem = new MinSqSumProblem(dim);
		// popsize
		GSA gsa = new GSA(popsize, problem);
		double [] alfas = {2., 5., 7.};
		PTGSA ptgsa = new PTGSA(gsa, alfas);
		ptgsa.setup();
		for(int i = 0; i < maxiter; ++i) {
			System.out.println("-- Iter " + i);
			ptgsa.nextIter();
	//		ptgsa.printParamRange();
			ptgsa.printEW();
			ptgsa.printMeans();
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Start test");
		testUpdateEW(3, 4, 100);
		System.out.println("End testing");
	}

}
