package tests;

import control.Globals;
import metaheuristics.GSA;
import metaheuristics.PTGSA;
import metaheuristics.PTGSAVariant;
import problems.MinSqSumProblem;
import problems.Problem;

public class TestPTGSATemporalWeight {
	
	public static void testSetParam(int dim, int popsize, int maxiter) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
	
		Problem problem = new MinSqSumProblem(dim);
		// popsize
		GSA gsa = new GSA(popsize, problem);
		double [] alfas = {2., 5., 7.};
		PTGSAVariant ptgsa = new PTGSAVariant(gsa, alfas);
		ptgsa.run(maxiter);		
	}
	
	public static void testUpdateEW(int dim, int popsize, int maxiter) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
	
		Problem problem = new MinSqSumProblem(dim);
		// popsize
		GSA gsa = new GSA(popsize, problem);
		double [] alfas = {2., 5., 7.};
		PTGSAVariant ptgsa = new PTGSAVariant(gsa, alfas);
		ptgsa.setTemporalWeight(0.0);
		ptgsa.initPop();
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
