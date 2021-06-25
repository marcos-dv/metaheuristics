package utils;

import control.Globals;
import control.Messages;
import solutions.Solution;

public class Geometry {
	
	private static final double EPS = 1e-14;

	public static void display(double [] x) {
		String s = "(";
		
		for (int i = 0; i < x.length-1; ++i) {
			s += Double.toString(x[i]) + ", ";
		}
		s += Double.toString(x[x.length-1]) + ")";
		System.out.println(s);
	}
	
	public static double[] neg(double [] x) {
		double[] xx = new double[x.length];
		for (int i = 0; i < x.length; ++i) {
			xx[i] = -x[i];
		}
		return xx;
	}

	public static double[] mult(double [] x, double k) {
		double[] xx = new double[x.length];
		for (int i = 0; i < xx.length; ++i) {
			xx[i] = x[i]*k;
		}
		return xx;
	}

	public static double[] div(double [] x, double k) {
		if (k == 0) {
			Messages.error("Geometry: div division by k == 0");
			return x;
		}
		return mult(x, 1/k);
	}

	public static double[] sum(double [] x, double [] y) {
		double[] sum = new double[x.length];
		if (x.length != y.length) {
			Messages.error("Geometry: sum x and y dont have the same dimension ("
				+ x.length + " and " + y.length + ")");
			return sum;
		}
		for (int i = 0; i < x.length; ++i) {
			sum[i] = x[i]+y[i];
		}
		return sum;
	}

	public static double[] diff(double [] x, double [] y) {
		double[] sum = new double[x.length];
		if (x.length != y.length) {
			Messages.error("Geometry: diff x and y dont have the same dimension ("
				+ x.length + " and " + y.length + ")");
			return sum;
		}
		for (int i = 0; i < x.length; ++i) {
			sum[i] = x[i]-y[i];
		}
		return sum;
	}

	public static double norm(double [] x) {
		double res = 0;
		for (int i = 0; i < x.length; ++i) {
			res += x[i]*x[i];
		}
		return Math.sqrt(res);
	}

	public static double dist(double [] x, double [] y) {
		if (x.length != y.length) {
			Messages.error("Geometry: dist x and y dont have the same dimension ("
				+ x.length + " and " + y.length + ")");
			return 0;
		}
		return norm(diff(x, y));
	}
	
	public static double innerProduct(double [] x, double [] y) {
		double prod = 0;
		if (x.length != y.length) {
			Messages.error("Geometry: innerProd x and y dont have the same dimension ("
				+ x.length + " and " + y.length + ")");
			return 0;
		}
		for (int i = 0; i < x.length; ++i) {
			prod += x[i]*y[i];
		}
		return prod;
	}

	public static double angle(double [] x, double [] y) {
		if (x.length != y.length) {
			Messages.error("Geometry: angle x and y dont have the same dimension ("
				+ x.length + " and " + y.length + ")");
			return 0;
		}
		double normx = norm(x);
		double normy = norm(y);
		if (normx==0 || normy==0) {
			Messages.error("Geometry: angle x or y have norm 0 ("
				+ normx + " and " + normy + ")");
			return 0;
		}
		double angle = Math.acos(innerProduct(x, y)/(norm(x)*norm(y)));
		return normalizeAngle(angle);
	}

	public static double[] unitary(double [] x) {
		double[] xx = new double[x.length];
		double norm = norm(x);
		for (int i = 0; i < x.length; ++i) {
			xx[i] = x[i]/norm;
		}
		return xx;
	}

	public static double[] fill(int dim, double value) {
		if (dim == 0) {
			Messages.error("Geometry: fill vector with dim == 0");
			return null;
		}
		double[] x = new double[dim];
		for (int i = 0; i < x.length; ++i) {
			x[i] = value;
		}
		return x;
	}
	
	public static double[] zero(int dim) {
		return fill(dim, 0);
	}

	public static boolean isZero(double [] x) {
		return norm(x) <= EPS;
	}

	public static double [] mutationPerAxis(double [] x, double step) {
		double [] mutation = new double[x.length];
		RandomGenerator rand = Globals.getRandomGenerator();
		for(int i = 0; i < mutation.length; ++i) {
			double offset = rand.randomUniform(-step, step);
			mutation[i] = x[i]+offset;
		}
		return mutation;
	}

	public static double [] mutationAnyAxis(double [] x, double step) {
		int randomAxis = Globals.getRandomGenerator().randomInt(0, x.length);
		return mutationOneAxis(x, randomAxis, step);
	}

	public static double [] mutationOneAxis(double [] x, int axis, double step) {
		double [] mutation = new double[x.length];
		RandomGenerator rand = Globals.getRandomGenerator();
		for(int i = 0; i < mutation.length; ++i) {
			mutation[i] = x[i];
		}
		mutation[axis] += rand.randomUniform(-step, step);
		return mutation;
	}

	public static double distPoint2Polygon(double [][] polygon, boolean open, double [] x) {
		double res = Double.POSITIVE_INFINITY;
		int numberOfSegments;
		if (open)
			numberOfSegments = polygon.length-1;
		else
			numberOfSegments = polygon.length;
		for(int v = 0; v < numberOfSegments; ++v) {
			double [] vertex1 = polygon[v];
			double [] vertex2 = polygon[(v+1)%polygon.length];
			res = Math.min(res, distPoint2Segment(vertex1, vertex2, x));
		}
		return res;
	}
	
	public static double normalizeAngle(double angle) {
		while (angle < -Math.PI) {
			angle += 2*Math.PI;
		}
		while (angle >= Math.PI) {
			angle -= 2*Math.PI;
		}
		return angle;
	}
	
	// TODO add tests for this
	public static double distPoint2Segment(double [] p, double [] q, double [] x) {
		if (p.length != q.length || x.length != q.length) {
			Messages.error("Geometry: distPoint2Segment p or q or x dont have the same dimension ("
				+ p.length + " , " + q.length + " , " + x.length + ")");
			return 0;
		}
		double [] pq = diff(q, p);
		double [] px = diff(x, p);
		double anglep = Math.abs(angle(pq, px));
		double [] qp = diff(p, q);
		double [] qx = diff(x, q);
		double angleq = Math.abs(angle(qp, qx));
		if (anglep > Math.PI/2 || angleq > Math.PI/2) {
			return Math.min(dist(p, x), dist(q, x));
		}
		return distPoint2Line2D(p, q, x);
	}

	public static double distPoint2Line2D(double[] p, double[] q, double[] x) {
		if (p.length != q.length || x.length != q.length || x.length != 2) {
			Messages.error("Geometry: distPoint2Segment p or q or x dont have dimension 2 ("
				+ p.length + " , " + q.length + " , " + x.length + ")");
			return 0;
		}
		if (isZero(diff(p, q))) { // p = q
			return dist(x, p);
		}
		double up = (q[0]-p[0])*(p[1]-x[1])-(p[0]-x[0])*(q[1]-p[0]);
		up = Math.abs(up);
		double down = norm(diff(p, q));
		return up/down;
	}

	
}
