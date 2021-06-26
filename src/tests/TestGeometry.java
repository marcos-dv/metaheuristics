package tests;

import utils.Geometry;

public class TestGeometry {

	public static void basicOperations() {
		double [] x = new double[]{10, 20};
		double [] y = new double[]{2, 3};
		double [] z = new double[]{-2, -3};
		double [] w = new double[]{1, -1};
		double k = 3;
		double [] res;
		double val;
		System.out.println("-- Sum");
		res = Geometry.sum(x, y);
		Geometry.display(res);

		System.out.println("-- Diff");
		res = Geometry.diff(y, z);
		Geometry.display(res);
		
		System.out.println("-- Mult");
		res = Geometry.mult(y, k);
		Geometry.display(res);
		
		System.out.println("-- Div");
		res = Geometry.div(y, k);
		Geometry.display(res);
		res = Geometry.div(y, 0);
		Geometry.display(res);
		
		System.out.println("-- Inner product");
		val = Geometry.innerProduct(x, y);
		System.out.println(val);
		
		System.out.println("-- Angle");
		val = Geometry.angle(x, w);
		System.out.println(val);
		val = Geometry.angle(w, x);
		System.out.println(val);

		
		System.out.println("-- Fill");
		res = Geometry.fill(5, 1111);
		Geometry.display(res);

		System.out.println("-- Zero");
		res = Geometry.zero(2);
		Geometry.display(res);

		System.out.println("-- Neg");
		res = Geometry.neg(x);
		Geometry.display(res);

		System.out.println("-- Norm");
		val = Geometry.norm(w);
		System.out.println(val);

		System.out.println("-- Unitary");
		res = Geometry.unitary(x);
		Geometry.display(res);
		System.out.println(Geometry.norm(res));
		res = Geometry.unitary(w);
		Geometry.display(res);
		System.out.println(Geometry.norm(res));

		System.out.println("-- Is zero?");
		System.out.println(Geometry.isZero(Geometry.diff(y, Geometry.neg(z))));
		System.out.println(Geometry.isZero(Geometry.diff(z, y)));


	}

	
	public static void testDist2Segment() {
		double [] p = new double[] {0, -.5};
		double [] q = new double[] {0, .5};
		double [] x = new double[] {-1, 0};
		double distx = Geometry.distPoint2Segment(p, q, x);
		System.out.println(distx);
		System.out.println();

		x = new double[] {1, .5};
		distx = Geometry.distPoint2Segment(p, q, x);
		System.out.println(distx);
		System.out.println(Math.min(Geometry.dist(x, p), Geometry.dist(x, q)));
		System.out.println();
		
		x = new double[] {-10, -10};
		distx = Geometry.distPoint2Segment(p, q, x);
		System.out.println(distx);
		System.out.println(Math.min(Geometry.dist(x, p), Geometry.dist(x, q)));
		System.out.println();

		x = new double[] {-10, 10};
		distx = Geometry.distPoint2Segment(p, q, x);
		System.out.println(distx);
		System.out.println(Math.min(Geometry.dist(x, p), Geometry.dist(x, q)));
		System.out.println();

		x = new double[] {10, 10};
		distx = Geometry.distPoint2Segment(p, q, x);
		System.out.println(distx);
		System.out.println(Math.min(Geometry.dist(x, p), Geometry.dist(x, q)));
		System.out.println();

		x = new double[] {10, -10};
		distx = Geometry.distPoint2Segment(p, q, x);
		System.out.println(distx);
		System.out.println(Math.min(Geometry.dist(x, p), Geometry.dist(x, q)));
		System.out.println();

	}
		
	public static void main(String[] args) {
		// basicOperations();
		testDist2Segment();
	}
}
