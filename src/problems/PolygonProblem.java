package problems;

import solutions.Solution;
import utils.Geometry;
import utils.Polygons;

public class PolygonProblem implements Problem {

	private double lowerBound = -50;
	private double upperBound = 50;
	private double [][] polygon;
	private int dim;
	private boolean open;
	
	public PolygonProblem(double[][] polygon, boolean open) {
		setDim(2);
		this.polygon = polygon.clone();
		this.open = open;
	}

	@Override
	public double fitness(Solution sol) {
		return Geometry.distPoint2Polygon(polygon, open, sol.getCoords());
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
