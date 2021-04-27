package problems;

import solutions.Solution;
import utils.testfunc;

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
		x = new double[dim];
		int numberOfSolutions = 1;
		f = new double[numberOfSolutions];
		
		testfunc tf = new testfunc();

		tf.test_func(x,f,dim,numberOfSolutions,funcNumber);
		return f[0];
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
			return contestFitness1Sol(sol);
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