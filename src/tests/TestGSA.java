package tests;

import control.Globals;
import metaheuristics.GSA;
import problems.RandomProblem;
import solutions.Solution;
import problems.MinSumProblem;

public class TestGSA {
	// GSA
	public static void dummyGSATest(int dim, int popsize, int maxiter) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);

		RandomProblem problem = new RandomProblem();
		problem.setDim(dim);
		// popsize
		GSA gsa = new GSA(popsize, problem);
		gsa.initPop();
		gsa.setMAX_ITER(maxiter);
		for (int numiter = 0; numiter < maxiter; ++numiter) {
			gsa.nextIter();		
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// GSA with minsum
	public static void easyGSATest(int dim, int popsize) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);

		MinSumProblem problem = new MinSumProblem(dim);
		// popsize
		GSA gsa = new GSA(popsize, problem);
		gsa.initPop();
//		double [] xx = {0, 0, 0};
//		gsa.getSols()[0].setCoords(xx);
		for (int numiter = 0; numiter < 100; ++numiter) {
			gsa.nextIter();	
			Solution [] sols = gsa.getSols();
			double totalSum = 0;
			for (Solution sol : sols) {
				totalSum += sol.getFitness();
			}
			System.out.println("Total sum = " + totalSum);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		dummyGSATest(3, 10, 100);
	
	}

}
