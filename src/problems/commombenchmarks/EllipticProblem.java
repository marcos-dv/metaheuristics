package problems.commombenchmarks;

import control.Messages;
import problems.AcademicProblem;
import solutions.Solution;

/**
 * 
 * Academic problem: Elliptic
 * 
 * This unimodal function with single global
 * best solution 0 found at f(x * )=[0,0,...,0] within the search
 * space of [-100,100].
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
public class EllipticProblem implements AcademicProblem {
	
	private int dim;
	private double upperBound = 100;
	private double lowerBound = -100;
	private boolean WARNING = true;
	
	@Override
	public int getDim() {
		return dim;
	}

	public EllipticProblem(int dim) {
		this.dim = dim;
	}

	public EllipticProblem(int dim, double lb, double ub) {
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
		for(int i = 0; i < dim; ++i) {
			double cte = Math.pow(1E6, i/(double)(dim-1));
			fit += cte*coords[i]*coords[i];
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
