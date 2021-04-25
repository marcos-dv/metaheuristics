package tests;

import problems.*;

public class TestSolutionProblem {

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

	public static void main(String[] args) {
		System.out.println("Start dummy test 1");
		dummyTest1();
		System.out.println("Start dummy test 2");
		dummyTest2();
		System.out.println("End testing");
	}

}
