package problems;

public class NearestPointProblem implements Problem {

	private final double lowerBound = -100;
	private final double upperBound = 100;
	private double [] targetPoint = {1, 2, 3};
	
	public NearestPointProblem() {
	}

	public NearestPointProblem(double [] point) {
		targetPoint = point;
	}

	protected double dist(double [] p, double [] q) {
		double d = 0;
		for (int i = 0; i < Math.min(p.length, q.length); ++i) {
			d += (p[i] - q[i])*(p[i] - q[i]);
		}
		return Math.sqrt(d);
	}
	
	@Override
	public double fitness(Solution sol) {
		return dist(targetPoint, sol.coords);
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
