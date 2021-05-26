package tests;

import control.Globals;
import metaheuristics.GSA;
import problems.MinSumProblem;
import problems.Problem;
import solutions.Solution;

public class TestSaveBuild {
	// Solution
	public static void buildSol(int dim) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);

		Problem problem = new MinSumProblem(dim);
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
	
	}

	
	
}
