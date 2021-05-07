package tests;

import problems.Cec2015Problem;
import problems.Problem;
import solutions.Solution;
import utils.Globals;

public class TestCec15 {
	public static void oneCecTest(int dim, int func_number) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);

		
		Problem problem = new Cec2015Problem(func_number, dim);
		Solution sol = new Solution(problem);
		for(int i = 0; i < sol.coords.length; ++i) {
			sol.coords[i] = Globals.getRandomGenerator().randomDouble(-100, 100);
		}
		System.out.println(sol);
	}

	public static void multipleCecTest(int dim, int popsize, int func_number) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);

		
		Cec2015Problem problem = new Cec2015Problem(func_number, dim);
		Solution [] sols = new Solution[popsize];
		for(int i = 0; i < popsize; ++i) {
			sols[i] = new Solution(problem);
		}
		for(int j = 0; j < popsize; ++j)
		for(int i = 0; i < sols[j].coords.length; ++i) {
			sols[j].coords[i] = Globals.getRandomGenerator().randomDouble(-100, 100);
		}
		double [] f = null;
		try {
			f = problem.multipleFitness(sols);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i = 0; i < popsize; ++i) {
			sols[i].setFitness(f[i]);
			System.out.println(sols[i]);
		}
		
	}

	public static void main(String[] args) {
		int func_number = 1;
		int dim = 30;
		int popsize = 20;
		oneCecTest(dim, func_number);
		multipleCecTest(dim, popsize, func_number);	
	}

}
