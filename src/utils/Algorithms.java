package utils;

public class Algorithms {
	
	public static double d2(double [] p, double [] q) {
		if (p.length != q.length || p.length <= 0) {
			System.out.println("Error Algorithms d2: lengths of points are not equal");
			return 0;
		}
		double d = 0;
		for (int i = 0; i < p.length; ++i) {
			d += (p[i] - q[i])*(p[i] - q[i]);
		}
		return Math.sqrt(d);
	}

}
