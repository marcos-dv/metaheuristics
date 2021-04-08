package problems;

import utils.Globals;

public class DummyProblem implements Problem {

	private double lowerBound = 0;
	private double upperBound = 100;
	private int dim;
	
	public DummyProblem() {
		lowerBound=0;
		upperBound=100;
	}

	public DummyProblem(double lb, double ub) {
		lowerBound=lb;
		upperBound=ub;
	}

	public DummyProblem(double lb, double ub, int dim) {
		this(lb, ub);
		this.dim=dim;
	}

	@Override
	public double fitness(Solution sol) {
		return Globals.getRandomGenerator().randomDouble(lowerBound, upperBound);
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
