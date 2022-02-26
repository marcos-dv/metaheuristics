package tests;

import control.Globals;
import problems.Cec2015Problem;
import problems.Problem;
import solutions.Solution;

public class TestCec15Debug {
	public static void oneCecTest(int dim, int func_number) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);

		Problem problem = new Cec2015Problem(func_number, dim);
		Solution sol = new Solution(problem);
		for(int i = 0; i < sol.coords.length; ++i) {
			sol.coords[i] = Globals.getRandomGenerator().randomUniform(-100, 100);
		}
		System.out.println(sol);
	}

	public static void main(String[] args) {
		int func_number = 2;
		int dim = 30;
		int popsize = 20;
		oneCecTest(dim, func_number);
	}

}
