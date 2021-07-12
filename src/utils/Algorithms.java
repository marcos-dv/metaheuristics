package utils;

import control.Messages;
import solutions.Solution;

public class Algorithms {
	
	public static double d2(double [] p, double [] q) {
		if (p.length != q.length || p.length <= 0) {
			Messages.error("Algorithms d2: lengths of points are not equal");
			return 0;
		}
		double d = 0;
		for (int i = 0; i < p.length; ++i) {
			d += (p[i] - q[i])*(p[i] - q[i]);
		}
		return Math.sqrt(d);
	}
	
	public static Solution getGlobalOptimum(Solution [] sols) {
		if (sols == null) {
			Messages.warning("Algorithms: getGlobalOptimum function, solutions parameter is null");
			return null;
		}
		Solution curSol = sols[0];
		double curMin = curSol.getFitness();

		for (int i = 0; i < sols.length; ++i) {
			if (sols[i] == null)
				continue;
			if (sols[i].getFitness() < curMin) {
				curMin = sols[i].getFitness();
				curSol = sols[i];
			}
		}
		return curSol;
	}
	
	public static double[] uniformSample(double a, double b, int numPoints) {
		double[] partition = new double[numPoints];
		for(int i = 0; i < numPoints; ++i) {
			partition[i] = a+i*(b-a)/(double)(numPoints-1);
		}
		return partition;
	}

	public static double[] forwardSample(double a, double r, int numPoints) {
		double[] partition = new double[numPoints];
		partition[0] = a;
		for(int i = 1; i < numPoints; ++i) {
			partition[i] = partition[i-1]+r;
		}
		return partition;
	}

}
