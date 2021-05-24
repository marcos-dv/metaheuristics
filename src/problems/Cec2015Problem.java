package problems;

import problems.cec2015.testfunc;
import solutions.Solution;

public class Cec2015Problem implements AcademicProblem {
	
	private final double lb = -100;
	private final double ub = 100;

	private int dim;
	private int funcNumber;
	private double [] optimum;
	
	public Cec2015Problem(int funcNumber, int dim) {
		this.funcNumber = funcNumber;
		this.dim = dim;
		// optimum x* of problem -> fn(x*) = 100*n
		optimum = new double[] {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500};
	}
	
	public double contestFitness1Sol(Solution sol) throws Exception {
		if (dim <= 0) {
			System.out.println("Warning-CEC2015: dimension = " + dim);
		}
		if (funcNumber <= 0 || funcNumber > 15) {
			System.out.println("Warning-CEC2015: function number = " + funcNumber);
		}
		
		double [] x = sol.getCoords().clone();
		double [] f; // returned fitness
		int numberOfSolutions = 1;
		f = new double[numberOfSolutions];
		
		testfunc tf = new testfunc();

		tf.test_func(x,f,dim,numberOfSolutions,funcNumber);
		return f[0];
	}

	public double [] contestFitnessMultipleSols(Solution [] sols) throws Exception {
		if (dim <= 0) {
			System.out.println("Warning-CEC2015: dimension = " + dim);
		}
		if (funcNumber <= 0 || funcNumber > 15) {
			System.out.println("Warning-CEC2015: function number = " + funcNumber);
		}
		
		double [] x = new double[sols.length*dim];
		for(int i = 0; i < sols.length; ++i) {
			for(int j = 0; j < dim; ++j) {
				x[i*dim+j] = sols[i].coords[j];
			}
		}
		double [] f; // returned fitness
		int numberOfSolutions = sols.length;
		f = new double[numberOfSolutions];
		
		testfunc tf = new testfunc();

		tf.test_func(x,f,dim,numberOfSolutions,funcNumber);
		return f;
	}
	
	public double [] multipleFitness(Solution sols[]) {
		try {
			double [] f = contestFitnessMultipleSols(sols);
			for(int i = 0; i < sols.length; ++i)
				f[i] -= getOptimum();
			return f;
		} catch (Exception e) {
			System.err.println("Error-Cec2015Problem: Evaluating test_func. Fitness returned +infinity");
			System.err.println("Solution not evaluated: " + sols);
			e.printStackTrace();
		}
		return null;
	}

	
	@Override
	public double getOptimum() {
		if (funcNumber <= 0 || funcNumber > 15) {
			System.out.println("Warning-CEC2015: function number = " + funcNumber);
		}
		return optimum[funcNumber-1];
	}

	@Override
	public double fitness(Solution sol) {
		try {
			return contestFitness1Sol(sol)-getOptimum();
		} catch (Exception e) {
			System.err.println("Error-Cec2015Problem: Evaluating test_func. Fitness returned +infinity");
			System.err.println("Solution not evaluated: " + sol);
			e.printStackTrace();
		}
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public double getUB() {
		return ub;
	}

	@Override
	public double getLB() {
		return lb;
	}

	@Override
	public int getDim() {
		return dim;
	}

	public void setDim(int dim) {
		this.dim = dim;
	}

	public int getFuncNumber() {
		return funcNumber;
	}

	public void setFuncNumber(int fnumber) {
		this.funcNumber = fnumber;
	}

}
