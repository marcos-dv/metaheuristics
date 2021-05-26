package tests;

import utils.Geometry;

public class TestGeometry {
	
	public static void display(double [] x) {
		String s = "(";
		
		for (int i = 0; i < x.length-1; ++i) {
			s += Double.toString(x[i]) + ", ";
		}
		s += Double.toString(x[x.length-1]) + ")";
		System.out.println(s);
	}
	
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
		display(res);

		System.out.println("-- Diff");
		res = Geometry.diff(y, z);
		display(res);
		
		System.out.println("-- Mult");
		res = Geometry.mult(y, k);
		display(res);
		
		System.out.println("-- Div");
		res = Geometry.div(y, k);
		display(res);
		res = Geometry.div(y, 0);
		display(res);
		
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
		display(res);

		System.out.println("-- Zero");
		res = Geometry.zero(2);
		display(res);

		System.out.println("-- Neg");
		res = Geometry.neg(x);
		display(res);

		System.out.println("-- Norm");
		val = Geometry.norm(w);
		System.out.println(val);

		System.out.println("-- Unitary");
		res = Geometry.unitary(x);
		display(res);
		System.out.println(Geometry.norm(res));
		res = Geometry.unitary(w);
		display(res);
		System.out.println(Geometry.norm(res));

		System.out.println("-- Is zero?");
		System.out.println(Geometry.isZero(Geometry.diff(y, Geometry.neg(z))));
		System.out.println(Geometry.isZero(Geometry.diff(z, y)));


	}
	
	public static void main(String[] args) {
		basicOperations();
	}
}
