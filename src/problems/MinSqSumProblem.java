package problems;

import solutions.Solution;

public class MinSqSumProblem implements Problem {

	private final double lowerBound = 0;
	private final double upperBound = 1;
	public int dim;
	
	public MinSqSumProblem(int dim) {
		setDim(dim);
	}

	@Override
	public double fitness(Solution sol) {
		double res = 0;
		for (int i = 0; i < sol.dim; ++i) {
			res += sol.coords[i]*sol.coords[i];
		}
		return res;
	}

	@Override
	public double getUB() {
		return upperBound;
	}

	@Override
	public double getLB() {
		return lowerBound;
	}

	@Override
	public int getDim() {
		return dim;
	}

	public void setDim(int dim) {
		this.dim = dim;
	}
}
