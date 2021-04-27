package exec;

import metaheuristics.GSA;
import metaheuristics.PTGSA;
import problems.MinSqSumProblem;
import problems.Problem;
import solutions.Solution;
import utils.Globals;

public class DemoPTGSA {

	public static void demoRunPTGSA(int dim, int popsize, int maxiter) {
		// Fix seed for experiments
		Globals.getRandomGenerator().setSeed(1);
	
		Problem problem = new MinSqSumProblem(dim);
		// popsize
		GSA gsa = new GSA(popsize, problem);
		double [] alfas = {3, 10, 25, 50};
		PTGSA ptgsa = new PTGSA(gsa, alfas);
		ptgsa.initPop();
		for(int i = 1; i <= maxiter; ++i) {
			System.out.println("-- Iter " + i);
			ptgsa.nextIter();
	//		ptgsa.printParamRange();
			ptgsa.printEW();
			ptgsa.printMeans();

			// Global Best
			Solution globalBest = ptgsa.getGlobalOptimum();
			System.out.println("Best sol: ");
			double [] coords = globalBest.getCoords();
			System.out.print("(");
			for(int d = 0; d < coords.length; ++d) {
				System.out.print(coords[d] + " , ");
			}
			System.out.println(")");
			System.out.println("Fitness = " + globalBest.getFitness());
			
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		int dim = 3;
		int popsize = 50;
		int numiter = 50;
		System.out.println("Start test");
		demoRunPTGSA(dim, popsize, numiter);
		System.out.println("End testing");
	}

}
