package commombenchmarks;

import control.Messages;
import problems.AcademicProblem;
import solutions.Solution;

/**
 * 
 * Academic problem: Step
 * 
 * This function represents flat surface which is
 * often considered as difficult to solve as no proper direction
 * towards globally optimum location is easily found. Step is a
 * unimodal function where minimum solution 0 located at
 * f(x * )=[-0.5,-0.5,...,-0.5] within the values spread over [-100,100]
 * range.
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
public class StepProblem implements AcademicProblem {
	
	private int dim;
	private double upperBound = 100;
	private double lowerBound = -100;
	private boolean WARNING = true;
	
	@Override
	public int getDim() {
		return dim;
	}

	public StepProblem(int dim) {
		this.dim = dim;
	}

	public StepProblem(int dim, double lb, double ub) {
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
			fit += (coords[i]+0.5)*(coords[i]+0.5);
		}
		return fit;
	}
	
	@Override
	public double getOptimum() {
		return 0;
	}

	public Solution getOptimalSol() {
		// Optimum = (-0.5, -0.5, ...)
		double [] coords = new double[dim];
		for(int i = 0; i < coords.length; ++i)
			coords[i] = -0.5;
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
