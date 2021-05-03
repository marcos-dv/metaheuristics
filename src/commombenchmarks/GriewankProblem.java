package commombenchmarks;

import problems.AcademicProblem;
import solutions.Solution;

/**
 * 
 * Academic problem: Griewank
 * 
 * It is a multimodal function with widespread
 * suboptimal solutions spread all over the search environment.
 * This function has one global optimum solution 0 to be
 * located at f(x * )=[0,0,...,0]. The function is solved with range
 * of [-600,600].
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
public class GriewankProblem implements AcademicProblem {
	
	private int dim;
	private double upperBound = 600;
	private double lowerBound = -600;
	private boolean WARNING = true;
	
	@Override
	public int getDim() {
		return dim;
	}

	public GriewankProblem(int dim) {
		this.dim = dim;
	}

	public GriewankProblem(int dim, double lb, double ub) {
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
		double sum = 0;
		for(int i = 0; i < dim; ++i) {
			sum += coords[i]*coords[i];
		}
		sum /= 4000;
		double prod = 1;
		for(int i = 0; i < dim; ++i) {
			prod *= Math.cos(coords[i]/Math.sqrt((double)(i+1)));
		}
		fit = sum - prod + 1;
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
