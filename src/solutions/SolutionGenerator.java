package solutions;

import control.Globals;
import control.Messages;
import problems.Problem;
import utils.Geometry;

public class SolutionGenerator {
	
	// Only for dim = 2
	// Sampling polar coords within the circle
	public static Solution[] overCircle(int popsize, Problem problem, double ratio) {
		if (problem.getDim() != 2) {
			Messages.error("SolutionGenerator: overCircle in problem with dimension (" 
				+ problem.getDim() + ") not equals to 2");
		}
		Solution [] sols = new Solution[popsize];
		double[][] x = new double[popsize][problem.getDim()];
		for(int i = 0; i < popsize; ++i) {
			double theta = Globals.getRandomGenerator().randomUniform(0, 2*Math.PI);
			x[i][0] = ratio*Math.cos(theta);
			x[i][1] = ratio*Math.sin(theta);
			sols[i] = new Solution(x[i], problem);
		}
		return sols;
	}

	
	public static Solution[] overSegment(int popsize, double [] p, double [] q, Problem problem) {
		if ((problem.getDim() != p.length) || (p.length != q.length)) {
			Messages.error("SolutionGenerator: overSegment dimensions don't match: problem dim = " 
					+ problem.getDim() + " p dim = " + p.length + " q dim = " + q.length);
		}
		Solution [] sols = new Solution[popsize];
		double[][] x = new double[popsize][problem.getDim()];
		for(int i = 0; i < popsize; ++i) {
			double lambda = Globals.getRandomGenerator().randomUniform(0, 1);
			double [] lambdap = Geometry.mult(p, lambda);
			double [] lambdaq = Geometry.mult(q, 1-lambda);
			x[i] = Geometry.diff(lambdap, lambdaq);
		}
		return sols;
	}

}
