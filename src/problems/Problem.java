package problems;

import solutions.Solution;

public interface Problem {
	public double fitness(Solution sol);
	public double getUB();
	public double getLB();
	public int getDim();
	
}
