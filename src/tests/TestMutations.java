package tests;

import control.Globals;
import problems.Problem;
import problems.RandomProblem;
import solutions.Solution;
import utils.Algorithms;
import utils.Geometry;

public class TestMutations {

	public static void display(double [] x) {
		String s = "(";
		
		for (int i = 0; i < x.length-1; ++i) {
			s += Double.toString(x[i]) + ", ";
		}
		s += Double.toString(x[x.length-1]) + ")";
		System.out.println(s);
	}
	
	public static void testRandomPerAxis() {
		Problem p = new RandomProblem();
		double[] x = new double[]{1, 2, 3};
		Solution sol = new Solution(x, p);
		System.out.println("Old solution:");
		System.out.println(sol);
		System.out.println("Mutation:");
		System.out.println(Algorithms.getNeighbour(sol, 0.3));
		System.out.println("Mutation:");
		System.out.println(Algorithms.getNeighbour(sol, 0.3));
		System.out.println("Mutation:");
		System.out.println(Algorithms.getNeighbour(sol, 0.3));
	}

	public static void testRandomOneAxis() {
		double[] x = new double[]{1, 2, 3};
		System.out.println("Old solution:");
		display(x);
		System.out.println("Mutation:");
		display(Geometry.mutationAnyAxis(x, 0.3));
		System.out.println("Mutation:");
		display(Geometry.mutationAnyAxis(x, 0.3));
		System.out.println("Mutation:");
		display(Geometry.mutationAnyAxis(x, 0.3));
	}

	public static void main(String[] args) {
		Globals.getRandomGenerator().setSeed(1);
		testRandomPerAxis();
		testRandomOneAxis();
	}
}
