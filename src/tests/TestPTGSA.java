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
	
	public static void main(String[] args) {
		System.out.println("Start test");
		testSetParam(5, 3, 5);
		System.out.println("End testing");
	}

}
