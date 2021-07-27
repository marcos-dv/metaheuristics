package solutions;

import control.Globals;
import control.Messages;
import problems.Problem;
import utils.Geometry;
import utils.RandomGenerator;

public class SolutionGenerator {
	
	public static Solution getNeighbour(Solution sol, double step) {
		double [] newCoords = Geometry.mutationPerAxis(sol.getCoords(), step);
		return new Solution(newCoords, sol.getTargetProblem());
	}
	
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

	// Only for dim = 2
	// Sampling polar coords within the circle
	public static Solution[] insideCircle(int popsize, Problem problem, double ratio) {
		if (problem.getDim() != 2) {
			Messages.error("SolutionGenerator: overCircle in problem with dimension (" 
				+ problem.getDim() + ") not equals to 2");
		}
		Solution [] sols = new Solution[popsize];
		double[][] x = new double[popsize][problem.getDim()];
		for(int i = 0; i < popsize; ++i) {
			double r = ratio*Math.min(1,1.5*Globals.getRandomGenerator().randomUniform(0, 1));
			double theta = Globals.getRandomGenerator().randomUniform(0, 2*Math.PI);
			x[i][0] = r*Math.cos(theta);
			x[i][1] = r*Math.sin(theta);
			sols[i] = new Solution(x[i], problem);
		}
		return sols;
	}

	
	public static Solution[] overSegment(int popsize, double [] p, double [] q, Problem problem) {
		if ((problem.getDim() != p.length) || (p.length != q.length)) {
			Messages.error("SolutionGenerator: overSegment dimensions don't match: problem dim = " 
					+ problem.getDim() + " p dim = " + p.length + " q dim = " + q.length);
		}
		/*
		System.out.println("Segment:");
		Geometry.display(p);
		Geometry.display(q);
		*/
		Solution [] sols = new Solution[popsize];
		double[][] x = new double[popsize][problem.getDim()];
		for(int i = 0; i < popsize; ++i) {
			double lambda = Globals.getRandomGenerator().randomUniform(0, 1);
			double [] lambdap = Geometry.mult(p, lambda);
			double [] lambdaq = Geometry.mult(q, 1-lambda);
			x[i] = Geometry.sum(lambdap, lambdaq);
			// Geometry.display(x[i]);
			sols[i] = new Solution(x[i], problem);
		}
		return sols;
	}

	public static Solution[] overPolygon(int popsize, double [][] polygon, boolean open, Problem problem) {
		Solution [] sols = new Solution[popsize];
		double[][] x = new double[popsize][problem.getDim()];
		int numberOfSegments;
		if (open)
			numberOfSegments = polygon.length-1;
		else
			numberOfSegments = polygon.length;
		for(int i = 0; i < popsize; ++i) {
			int targetSegment = Globals.getRandomGenerator().randomInt(0, numberOfSegments);
			double [] vertex1 = polygon[targetSegment];
			double [] vertex2 = polygon[(targetSegment+1)%polygon.length];
			Solution [] singleSol = overSegment(1, vertex1, vertex2, problem);
			sols[i] = new Solution(singleSol[0]);
		}
		return sols;
	}

	public static Solution[] randomInit(int popsize, Problem targetProblem) {
		Solution [] sols = new Solution[popsize];
		for (int i = 0; i < sols.length; ++i) {
			sols[i] = new Solution(targetProblem);
			sols[i].randomInit();
		}
		return sols;
	}

	
}
