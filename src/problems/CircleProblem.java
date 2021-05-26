package problems;

import solutions.Solution;

public class CircleProblem implements Problem {

	private final double lowerBound = -50;
	private final double upperBound = 50;
	private double ratio = 10;
	public int dim;
	
	public CircleProblem(int dim) {
		setDim(dim);
	}

	public CircleProblem(int dim, double ratio) {
		this(dim);
		setRatio(ratio);
	}

	@Override
	public double fitness(Solution sol) {
		double res = 0;
		for (int i = 0; i < sol.dim; ++i) {
			res += sol.coords[i]*sol.coords[i];
		}
		res = Math.abs(res-ratio*ratio);
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

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
}
