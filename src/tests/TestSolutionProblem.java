package tests;

import problems.*;
import solutions.Solution;
import utils.Polygons;

public class TestSolutionProblem {

	// Basic Solutions and problems
	public static void dummyTest1() {
		int dim = 30;
		Problem problem = new MinSumProblem(dim);
		Solution sol = new Solution(dim, problem);
		sol.randomInit();
		System.out.println(sol);
	}

	// Basic Solutions and problems
	public static void dummyTest2() {
		int dim = 5;
		double [] targetPoint = new double[]{1, 2, 3};
		NearestPointProblem problem = new NearestPointProblem(targetPoint);
		Solution [] sols = new Solution[5];
		int i = 1;
		for (Solution solution : sols) {
			solution = new Solution(dim, problem);
			solution.randomInit();
			System.out.println("Solution " + i + " fitness = " + solution.getFitness());
			i++;
		}
	}

	public static void PolygonProblem() {
		PolygonProblem problem = new PolygonProblem(Polygons.M, true);
		Solution [] sols = new Solution[5];
		int i = 1;
		int dim = 2;
		for (Solution solution : sols) {
			solution = new Solution(dim, problem);
			solution.randomInit();
			System.out.println("Solution " + i + " fitness = " + solution.getFitness());
			i++;
		}
	}

	public static void main(String[] args) {
		/*
		System.out.println("Start dummy test 1");
		dummyTest1();
		System.out.println("Start dummy test 2");
		dummyTest2();
		System.out.println("End testing");
		*/
		PolygonProblem();
	}

}
