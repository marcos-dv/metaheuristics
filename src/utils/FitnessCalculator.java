package utils;

import problems.Problem;
import solutions.Solution;

public class FitnessCalculator extends Thread {

	int i0;
	int incr_i;
	Solution [] sols;
	Problem problem;
	double [] f;
	int totalTasks;
	
	public FitnessCalculator(int i0, int incr_i, Solution [] sols, Problem problem, double[] f) {
		this.sols = sols;
		this.problem = problem;
		this.i0 = i0;
		this.incr_i = incr_i;
		this.f = f;
		// System.out.println("Start at " + i0 + " End at " + (i0+incr_i-1));
	}
	
	@Override
	public void run() {
		for(int i = i0; i < i0+incr_i && i < sols.length; ++i) {
			double fit = problem.fitness(sols[i]);
			f[i] = fit;
		}
	}

}
