package tests;

import control.Globals;
import problems.Cec2015Problem;
import problems.Problem;
import solutions.Solution;

public class TestManyCec15 {
	
	static int popsize = 5;
	
	public static void oneCecTest(int dim, int func_number) {
		Problem problem = new Cec2015Problem(func_number, dim);
		for(int kk = 0; kk < popsize; ++kk) {
			Solution sol = new Solution(problem);
			for(int i = 0; i < sol.coords.length; ++i) {
				sol.coords[i] = Globals.getRandomGenerator().randomUniform(-100, 100);
			}
			sol.getFitness();
			System.out.println(sol);
		}
	}


	public static void main(String[] args) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
		int dim = 30;
		for (int func_number = 1; func_number <= 15; ++func_number) {
			oneCecTest(dim, func_number);
		}
	}

}
