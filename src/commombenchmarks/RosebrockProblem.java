package commombenchmarks;

import problems.AcademicProblem;
import solutions.Solution;

/**
 * 
 * Academic problem: Rosebrock
 * 
 * This is also a unimodal function which is
 * also known as banana function as its global minimum
 * solution 0 is found in the narrow valley, with optimum
 * solution f(x * )=[0,0,...,0]. The range of values of parameter
 * values is often set to [-5, 10].
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
public class RosebrockProblem implements AcademicProblem {
	
	private int dim;
	private double upperBound = 10;
	private double lowerBound = -5;
	private boolean WARNING = true;
	
	@Override
	public int getDim() {
		return dim;
	}

	public RosebrockProblem(int dim) {
		this.dim = dim;
	}

	public RosebrockProblem(int dim, double lb, double ub) {
		this(dim);
		lowerBound = lb;
		upperBound = ub;
	}

	@Override
	public double fitness(Solution sol) {
		double [] coords = sol.getCoords();
		if (WARNING && coords.length != dim) {
			System.out.println("Warning-AcademicProblem: the solution dimension (" 
					+ coords.length + ") not match the problem dimension (" + dim + ")");
		}
		
		double fit = 0;
		for(int i = 0; i < dim-1; ++i) {
			double aux = coords[i+1]+coords[i]*coords[i];
			fit += 100*aux*aux+(coords[i]-1)*(coords[i]-1);
		}
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
