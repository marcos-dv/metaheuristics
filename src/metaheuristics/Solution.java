package metaheuristics;

import java.util.Random;

public class Solution {
	public double [] pop;
	public int dim;
	private double fitness;
	private Problem targetProblem;
	
	public Solution(double [] pop, Problem p) {
		this.pop = pop.clone();
		dim = pop.length;
		fitness = 777.777;
		targetProblem = p;
	}

	public Solution(int dim, Problem p) {
		this(new double[dim], p);
	}

	public Solution(Solution sol, Problem p) {
		this(sol.pop, p);
		setFitness(sol.getFitness());
	}
	
	public double getFitness() {
		if (fitness == 777.777) {
			fitness = targetProblem.fitness(this);
		}
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public void randomInit() {
	    Random rand = new Random();
	    pop = new double[dim];
	    //populate the array with doubles
	    for (int i = 0; i < pop.length; ++i) {
			pop[i] = targetProblem.getLB()
					+(targetProblem.getUB()-targetProblem.getLB())*rand.nextDouble();
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
