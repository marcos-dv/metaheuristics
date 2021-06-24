package tests;

import control.Globals;
import metaheuristics.GSA;
import metaheuristics.PTGSAOne;
import problems.MinSqSumProblem;
import problems.Problem;

public class TestPTGSAOne {
	
	public static void testSetParam(int dim, int popsize, int maxiter) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
	
		Problem problem = new MinSqSumProblem(dim);
		// popsize
		GSA gsa = new GSA(popsize, problem);
		PTGSAOne ptgsa = new PTGSAOne(gsa);
		double [] alfas = {2., 5., 7.};
		double [] G0 = {40, 50, 60};
		ptgsa.setNewParam("alfa", alfas);
		ptgsa.setNewParam("G0", G0);
		ptgsa.run(maxiter);		
	}
	
	public static void testUpdateEW(int dim, int popsize, int maxiter) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
	
		Problem problem = new MinSqSumProblem(dim);
		// popsize
		GSA gsa = new GSA(popsize, problem);
		PTGSAOne ptgsa = new PTGSAOne(gsa);
		double [] alfas = {2., 5., 7.};
		double [] G0 = {40, 50, 60};
		ptgsa.setNewParam("alfa", alfas);
		ptgsa.setNewParam("G0", G0);
		ptgsa.setTemporalWeight(0.0);
		ptgsa.initPop();
		for(int i = 0; i < maxiter; ++i) {
			System.out.println("-- Iter " + i);
			ptgsa.nextIter();
	//		ptgsa.printParamRange();
			ptgsa.printEWs();
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
