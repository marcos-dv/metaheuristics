package commombenchmarks;

import control.Messages;
import problems.AcademicProblem;
import solutions.Solution;

/**
 * 
 * Academic problem: SumSquare
 * 
 * This function is also known as Axis
 * Parallel Hyper-Ellipsoid function which maintains no local
 * optima but single global optima f(x * )=[0,0,...,0]. The
 * function is normally evaluated with continuous values within
 * the range of [-10,10].
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
public class SumSquareProblem implements AcademicProblem {
	
	private int dim;
	private double upperBound = 10;
	private double lowerBound = -10;
	private boolean WARNING = true;
	
	@Override
	public int getDim() {
		return dim;
	}

	public SumSquareProblem(int dim) {
		this.dim = dim;
	}

	public SumSquareProblem(int dim, double lb, double ub) {
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
			fit += (i+1)*coords[i]*coords[i];
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
