package problems;

import utils.Globals;
import utils.RandomGenerator;

public class Solution {
	public double [] pop;
	public int dim;
	private double fitness;
	private Problem targetProblem;

	public double[] getPop() {
		return pop;
	}

	public void setPop(double[] pop) {
		this.pop = pop;
	}

	public int getDim() {
		return dim;
	}

	public void setDim(int dim) {
		this.dim = dim;
	}

	public Problem getTargetProblem() {
		return targetProblem;
	}

	public void setTargetProblem(Problem targetProblem) {
		this.targetProblem = targetProblem;
	}

	public double getFitness() {
		if (fitness == Double.POSITIVE_INFINITY) {
			fitness = targetProblem.fitness(this);
		}
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public Solution() {
		fitness = Double.POSITIVE_INFINITY;
	}

	public Solution(double [] pop, Problem p) {
		this.pop = pop.clone();
		dim = pop.length;
		fitness = Double.POSITIVE_INFINITY;
		targetProblem = p;
	}

	public Solution(int dim, Problem p) {
		this(new double[dim], p);
	}

	public Solution(Solution sol, Problem p) {
		this(sol.pop, p);
		setFitness(sol.getFitness());
	}
	
	public void randomInit() {
	    RandomGenerator rand = Globals.getRandomGenerator();
	    pop = new double[dim];
	    //populate the array with doubles
	    for (int i = 0; i < pop.length; ++i) {
			pop[i] = rand.randomDouble(targetProblem.getLB(), targetProblem.getUB());
		}
	}

	@Override
	public String toString() {
		String popString = "";
		for (double p : this.pop) {
			popString += Double.toString(p) + '\n';
		}
		
		return "Solution dim = " + dim 
				+ " fitness = " + getFitness() 
				+ " targetProblem =" + targetProblem
				+ " population:\n"
				+ popString;
	}
	
	
	
}
