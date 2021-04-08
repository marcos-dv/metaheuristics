package metaheuristics;

import java.util.Arrays;


import problems.Problem;
import problems.Solution;
import utils.Algorithms;
import utils.Globals;
import utils.Pair;
import utils.RandomGenerator;

public class GSA {
	
	private boolean DEBUG = true;
	
	private Solution [] sols;
//	private Solution [] bestSols;
//	private double [] v;
	
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
		numIter = 0;
		// Hyperparameters
		G0 = 100;
		alfa = 20;
		epsilon = 1e-9;
		MAX_ITER = 51;
		
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
		// Init population
		if (sols == null) {
			System.out.println("GSA error: solutions are not initialized");
			return ;
		}
		// Random init
		for (int i = 0; i < sols.length; ++i) {
			sols[i] = new Solution(targetProblem);
			sols[i].randomInit();
		}
		// Init structures
		reset();
	}
	
	public void reset() {
		fit = new double[sols.length];
		q = new double[sols.length];
		mass = new double[sols.length];
		a = new double[sols.length][targetProblem.getDim()];
	}
	
	public void nextIter() {
		// Next iteration
		numIter++;
		if (DEBUG)
			System.out.println("\n# Begin iteration: " + numIter);
		
		// Update everything
		
		// 1. Update fitnesses
		// Also get best and worst
		bestFitness = Double.POSITIVE_INFINITY;
		worstFitness = Double.NEGATIVE_INFINITY;
		for(int i = 0; i < sols.length; ++i) {
			fit[i] = sols[i].getFitness();
			bestFitness = Math.min(bestFitness, fit[i]);
			worstFitness = Math.max(worstFitness, fit[i]);
		}
		
		// Compute best K

		double kRate = sols.length / MAX_ITER;
		int kSize = (int) (sols.length-(numIter-1)*kRate);
		kSize = Math.min(kSize, sols.length);
		kSize = Math.max(kSize, 1);
		if (DEBUG)
			System.out.println("K = " + kSize);

		Pair kbest[] = new Pair[sols.length];
		for(int i = 0; i < sols.length; ++i) {
			kbest[i] = new Pair(i, fit[i]);
		}
		Arrays.sort(kbest);
		
		if (DEBUG) {
			System.out.println("# Fitness sorted");
			for(int i = 0; i < sols.length; ++i) {
				System.out.println(i + " val = " + fit[i]);
			}
			// Print best K
			for(int i = 0; i < sols.length; ++i) {
				System.out.println(i + ". " + kbest[i].index
						+ " val = " + kbest[i].value);
			}
		}
		
		// Compute q = normalized fitness
		double accumq = 0;
		for(int i = 0; i < sols.length; ++i) {
			q[i] = (fit[i]-worstFitness) / (bestFitness-worstFitness);
			accumq += q[i];
		}
		
		// Compute mass in [0,1] value by fitness
		for(int i = 0; i < sols.length; ++i) {
			mass[i] = q[i] / accumq;
		}
		
		if (DEBUG) {
			System.out.println("# q's");
			for(int i = 0; i < sols.length; ++i) {
				System.out.println("q[i] = " + q[i]);
			}
			System.out.println("# mass");
			for(int i = 0; i < sols.length; ++i) {
				System.out.println("mass[i] = " + mass[i]);
			}
		}
		
		// Compute G(t) depending on G0
		double Gt = G0*Math.exp(-alfa*(double)numIter/MAX_ITER);

		// Check G(t) is ok
		if (DEBUG)
			System.out.println("# G(t) = " + Gt);
		
		// Compute total acceleration over each particle
		// First, radios (distances between particles and k-best)
		double R[][] = new double[sols.length][kSize];
		for(int i = 0; i < sols.length; ++i) {

			// Influence of j-th particle
			double [] factorj = new double[kSize];
			for(int j = 0; j < kSize; ++j) {
				factorj[j] = Globals.getRandomGenerator().randomDouble();
				factorj[j] *= mass[kbest[j].index];
				R[i][j] = Algorithms.d2(
						sols[i].getCoords(), sols[kbest[j].index].getCoords());
				if (DEBUG)
					System.out.println("Dist: " + R[i][j]);
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
