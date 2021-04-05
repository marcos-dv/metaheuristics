package problems;

public class MinSumProblem implements Problem {

	private final double lowerBound = 0;
	private final double upperBound = 1;
	
	@Override
	public double fitness(Solution sol) {
		double res = 0;
		for (int i = 0; i < sol.dim; ++i) {
			res += sol.coords[i];
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
}
