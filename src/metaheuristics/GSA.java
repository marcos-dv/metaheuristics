package metaheuristics;

import java.util.List;

import problems.Problem;
import problems.Solution;
import utils.Globals;
import utils.RandomGenerator;

public class GSA {
	private Solution [] sols;
//	private Solution [] bestSols;
//	private double [] v;
	private List<Solution> [] kbest;
	
	// Info to compute
	private double [] fit;
	private double [] q; // normalized fitness
	private double [] mass;
	private double [][] a; // acceleration
	
	// Global params
	private int MAX_ITER;
	private int numIter;
	private double G0;
	private double alfa;
	private double epsilon;
	
	private double worstFitness;
	private double bestFitness;
	private Solution gSol;
	private Problem targetProblem;
	private int popSize;
	
	public GSA(Problem targetProblem) {
		this.targetProblem = targetProblem;
		numIter = 1;
	}

	public GSA(int popSize, Problem targetProblem) {
		this(targetProblem);
		this.popSize = popSize;
		sols = new Solution[popSize];
	}
	
	public GSA(Solution [] sols, Problem targetProblem) {
		this(targetProblem);
		this.sols = sols.clone();
		this.popSize = sols.length;
	}

	public GSA(GSA GSA) {
		this(GSA.sols, GSA.targetProblem);
		numIter = GSA.getNumIter();
	}
	
	public void initPop() {
		if (sols == null) {
			System.out.println("GSA error: solutions are not initialized");
			return ;
		}
		// Random init
		for (Solution sol : sols) {
			sol.randomInit();
		}
	}
	
	public void nextIter() {
		// Update everything
		
		// 1. Update fitnesses
		// Also get best and worst
		bestFitness = Double.POSITIVE_INFINITY;
		worstFitness = Double.NEGATIVE_INFINITY;
		// TODO get KBEST !!! (heap with maximum size = k)
		for(int i = 0; i < sols.length; ++i) {
			fit[i] = sols[i].getFitness();
			bestFitness = Math.min(bestFitness, fit[i]);
			worstFitness = Math.max(worstFitness, fit[i]);
		}
		
		// Compute q = normalized fitness
		double accumq = 0;
		for(int i = 0; i < sols.length; ++i) {
			q[i] = (fit[i]-worstFitness) / (bestFitness-worstFitness);
			accumq += q[i];
		}
		
		// Compute mass = 0..1 value by fitness
		for(int i = 0; i < sols.length; ++i) {
			mass[i] = q[i] / accumq;
		}
		
		// Compute G(t) depending on G0
		double Gt = G0*Math.exp(-alfa*(double)numIter/MAX_ITER);
		// Check G(t) is ok
		System.out.println("G(t) = " + Gt);
		
		// Compute total acceleration over each particle
		for(int i = 0; i < sols.length; ++i) {

			// Influence of j-th particle
			double [] factorj = new double[sols.length];
			for(int j = 0; j < sols.length; ++j) {
				factorj[j] = Globals.getRandomGenerator().randomDouble();
			}
			
			for(int j = 0; j < sols.length; ++j) {
				if (i == j)
					continue;
				
			
			}
		}
		
		
		
	}

	public int getNumIter() {
		return numIter;
	}

	public void setNumIter(int numIter) {
		this.numIter = numIter;
	}

	public double getG0() {
		return G0;
	}

	public void setG0(double g0) {
		G0 = g0;
	}

	public double getAlfa() {
		return alfa;
	}

	public void setAlfa(double alfa) {
		this.alfa = alfa;
	}

	public double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}
	
}
