package problems.cec2015;

import parallelism.FitnessCalculator;
import problems.Cec2015Problem;
import problems.Problem;
import solutions.Solution;

public class Cec2015Calculator extends FitnessCalculator {

	public Cec2015Calculator(int i0, int incr_i, Solution[] sols, Problem problem, double[] f) {
		super(i0, incr_i, sols, problem, f);
	}
	
	@Override
	public void run() {
		Solution [] subSols = new Solution[incr_i];
		for(int i = 0; i < incr_i; ++i) {
			subSols[i] = sols[i+firstIdx];
		}
		Cec2015Problem problem = (Cec2015Problem) this.problem;
		try {
			double[] fit = problem.contestFitnessMultipleSols(subSols);
			for(int i = 0; i < incr_i; ++i) {
				f[i+firstIdx] = fit[i];
			}
		} catch (Exception e) {
			System.out.println("Error-Cec2015Calculator: multiple solutions evaluation did not work.");
		}
	}

	
}
