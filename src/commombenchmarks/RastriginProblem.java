package commombenchmarks;

import problems.AcademicProblem;
import solutions.Solution;

/**
 * 
 * Academic problem: Rastringin
 * 
 * This multimodal function is difficult to
 * solve as it presents numerous local minima locations where
 * an optimization algorithm, with poor explorative capability,
 * has high chances of being trapped. The function’s only
 * globally best solution 0 is found at f(x * )=[0,0,...,0] within the
 * domain of [-5.12,5.12].
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
public class RastriginProblem implements AcademicProblem {
	
	private int dim;
	private double upperBound = 5.12;
	private double lowerBound = -5.12;
	private boolean WARNING = true;
	
	@Override
	public int getDim() {
		return dim;
	}

	public RastriginProblem(int dim) {
		this.dim = dim;
	}

	public RastriginProblem(int dim, double lb, double ub) {
		this(dim);
		lowerBound = lb;
		upperBound = lb;
	}

	@Override
	public double fitness(Solution sol) {
		double [] coords = sol.getCoords();
		if (WARNING && coords.length != dim) {
			System.out.println("Warning-AcademicProblem: the solution dimension (" 
					+ coords.length + ") not match the problem dimension (" + dim + ")");
		}
		
		double fit = 0;
		for(int i = 0; i < dim; ++i) {
			fit += coords[i]*coords[i] -10*Math.cos(2*Math.PI*coords[i]) + 10;
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
