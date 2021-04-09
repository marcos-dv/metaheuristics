package exec;

import metaheuristics.GSA;
import problems.*;
import utils.Globals;

public class ExecTest {

	// Basic Solutions and problems
	public static void dummyTest1() {
		int dim = 30;
		MinSumProblem problem = new MinSumProblem();
		Solution sol = new Solution(dim, problem);
		sol.randomInit();
		System.out.println(sol);
	}

	// Basic Solutions and problems
	public static void dummyTest2() {
		int dim = 5;
		NearestPointProblem problem = new NearestPointProblem();
		Solution [] sols = new Solution[5];
		int i = 1;
		for (Solution solution : sols) {
			solution = new Solution(dim, problem);
			solution.randomInit();
			System.out.println("Solution " + i + " fitness = " + solution.getFitness());
			i++;
		}
		
	}

	// GSA
	public static void dummyTest3(int dim, int popsize) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);

		DummyProblem problem = new DummyProblem();
		problem.setDim(dim);
		// popsize
		GSA gsa = new GSA(popsize, problem);
		gsa.initPop();
		for (int numiter = 0; numiter < 100; ++numiter) {
			gsa.nextIter();		
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// GSA with minsum
	public static void easyTest(int dim, int popsize) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);

		MinSumProblem problem = new MinSumProblem();
		problem.setDim(dim);
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
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		easyTest(3, 5);
	
	}

}
