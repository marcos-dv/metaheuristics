package problems;

import control.Globals;
import solutions.Solution;

public class RandomProblem implements Problem {

	private double lowerBound = -100;
	private double upperBound = 100;
	private int dim;
	
	public RandomProblem() {
		lowerBound=-100;
		upperBound=100;
	}

	public RandomProblem(double lb, double ub) {
		lowerBound=lb;
		upperBound=ub;
	}

	public RandomProblem(double lb, double ub, int dim) {
		this(lb, ub);
		this.dim=dim;
	}

	@Override
	public double fitness(Solution sol) {
		return Globals.getRandomGenerator().randomUniform(lowerBound, upperBound);
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
