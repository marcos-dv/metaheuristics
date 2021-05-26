package commombenchmarks;

import control.Messages;
import problems.AcademicProblem;
import solutions.Solution;

/**
 * 
 * Academic problem: Schwefel 2.22
 * 
 * This function is a unimodal with search
 * space usually spread over [-10,10] values. The global
 * minimum 0 is located at f(x * )=[0,0,...,0].
 * 
 * Based on the paper:
 * Common Benchmark Functions for Metaheuristic Evaluation: A Review
 * Hussain, K., Salleh, M. N. M., Cheng, S., & Naseem, R. (2017).
 * JOIV: International Journal on Informatics Visualization, 1(4-2), 218-223.
 *
 * @author Marcos Dominguez Velad
 * 
 * email : marcos.dominguezv.dev@gmail.com ; marcos.dominguezv0@gmail.com
 */
public class Schwefel222Problem implements AcademicProblem {
	
	private int dim;
	private double upperBound = 10;
	private double lowerBound = -10;
	private boolean WARNING = true;
	
	@Override
	public int getDim() {
		return dim;
	}

	public Schwefel222Problem(int dim) {
		this.dim = dim;
	}

	public Schwefel222Problem(int dim, double lb, double ub) {
		this(dim);
		lowerBound = lb;
		upperBound = ub;
	}

	@Override
	public double fitness(Solution sol) {
		double [] coords = sol.getCoords();
		if (WARNING && coords.length != dim) {
			Messages.warning("AcademicProblem: the solution dimension (" 
					+ coords.length + ") not match the problem dimension (" + dim + ")");
		}
		
		double fit = 0;
		double sum = 0;
		for(int i = 0; i < dim; ++i) {
			sum += Math.abs(coords[i]);
		}
		double prod = 1;
		for(int i = 0; i < dim; ++i) {
			prod *= Math.abs(coords[i]);
		}
		fit = sum + prod;
		return fit;
	}
	
	@Override
	public double getOptimum() {
		return 0;
	}

	public Solution getOptimalSol() {
		// Optimum = (0, 0, ...)
		double [] coords = new double[dim];
		Solution opt = new Solution(coords, this);
		return opt;
	}

	@Override
	public double getUB() {
		return upperBound;
	}

	public void setUB(double ub) {
		upperBound = ub;
	}

	@Override
	public double getLB() {
		return lowerBound;
	}

	public void setLB(double lb) {
		lowerBound = lb;
	}

}
