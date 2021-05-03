package commombenchmarks;

import problems.AcademicProblem;
import solutions.Solution;

/**
 * 
 * Academic problem: Ackley
 * 
 * This multimodal function is one of the most
 * commonly used test function for metaheuristic algorithm
 * evaluation. It has numerous local minima but one global
 * optimal solution found in deep narrow basin in the middle.
 * The best solution 0 is found at f(x * )=[0,0,...,0]
 * in domain [-32,32].
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
public class AckleyProblem implements AcademicProblem {
	
	private int dim;
	private double upperBound = 32;
	private double lowerBound = -32;
	private boolean WARNING = true;
	
	@Override
	public int getDim() {
		return dim;
	}

	public AckleyProblem(int dim) {
		this.dim = dim;
	}

	public AckleyProblem(int dim, double lb, double ub) {
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
		double sum1 = 0;
		for(int i = 0; i < dim; ++i) {
			sum1 += coords[i]*coords[i];
		}
		sum1 = Math.sqrt(sum1/dim);
		
		double sum2 = 0;
		for(int i = 0; i < dim; ++i) {
			sum2 += Math.cos(2*Math.PI*coords[i]);
		}
		sum2 /= dim;
		fit = -20*Math.exp(sum1) -Math.exp(sum2) + 20 + Math.E;
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
