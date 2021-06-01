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
		double curMin = Double.POSITIVE_INFINITY;
		Solution curSol = null;

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
	
	public static Solution getNeighbour(Solution sol, double step) {
		double [] newCoords = Geometry.mutationPerAxis(sol.getCoords(), step);
		return new Solution(newCoords, sol.getTargetProblem());
	}
	
	
}
