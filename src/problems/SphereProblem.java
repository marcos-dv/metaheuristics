package problems;

import solutions.Solution;

/**
 * 
 * Academic problem: Sphere
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
public class SphereProblem implements AcademicProblem {
	
	private int dim;
	
	@Override
	public int getDim() {
		return dim;
	}

	public SphereProblem(int dim) {
		this.dim = dim;
	}
	
	@Override
	public double fitness(Solution sol) {
		return 0;
	}

	@Override
	public double getUB() {
		return 0;
	}

	@Override
	public double getLB() {
		return 0;
	}

	@Override
	public double getOptimum() {
		return 0;
	}

}
