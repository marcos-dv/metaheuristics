package problems.utils;

import problems.Problem;
import solutions.Solution;

public class FitnessCalculator extends Thread {

	protected int firstIdx;
	protected int incr_i;

	protected Solution [] sols;
	protected Problem problem;
	protected double [] f;
	
	public FitnessCalculator(int i0, int incr_i, Solution [] sols, Problem problem, double[] f) {
		this.sols = sols;
		this.problem = problem;
		this.firstIdx = i0;
		this.incr_i = incr_i;
		this.f = f;
		// System.out.println("Start at " + i0 + " End at " + (i0+incr_i-1));
	}
	
	@Override
	public void run() {
		for(int i = firstIdx; i < firstIdx+incr_i && i < sols.length; ++i) {
			double fit = problem.fitness(sols[i]);
			f[i] = fit;
		}
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public int getIncr_i() {
		return incr_i;
	}

	public void setIncr_i(int incr_i) {
		this.incr_i = incr_i;
	}

	public Solution[] getSols() {
		return sols;
	}

	public void setSols(Solution[] sols) {
		this.sols = sols;
	}

	public double[] getF() {
		return f;
	}

	public void setF(double[] f) {
		this.f = f;
	}

	public int getFirstIndex() {
		return firstIdx;
	}

	public void setFirstIndex(int i0) {
		this.firstIdx = i0;
	}
}
