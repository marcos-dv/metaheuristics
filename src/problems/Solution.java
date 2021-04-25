package problems;

import utils.Globals;
import utils.RandomGenerator;

public class Solution {
	public double [] coords;
	public int dim;
	private double fitness;
	private Problem targetProblem;
	
	public Solution() {
		fitness = Double.POSITIVE_INFINITY;
	}

	public Solution(double [] coords, Problem p) {
		this.coords = coords.clone();
		dim = coords.length;
		assert(p.getDim() == dim);
		fitness = Double.POSITIVE_INFINITY;
		targetProblem = p;
	}

	public Solution(int dim, Problem p) {
		this(new double[dim], p);
	}

	public Solution(Problem p) {
		this(p.getDim(), p);
	}

	public Solution(Solution sol, Problem p) {
		this(sol.coords, p);
		setFitness(sol.getFitness());
	}

	public Solution(Solution sol) {
		this(sol, sol.getTargetProblem());
	}

	public double[] getCoords() {
		return coords;
	}

	public void setCoords(double[] coords) {
		this.coords = coords.clone();
		fitness = Double.POSITIVE_INFINITY;
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

	private double dist(double [] p, double [] q) {
		double d = 0;
		for (int i = 0; i < Math.min(p.length, q.length); ++i) {
			d += (p[i] - q[i])*(p[i] - q[i]);
		}
		return Math.sqrt(d);
	}

	public double dist(Solution sol) {
		double [] coords2 = sol.getCoords();
		if (coords.length != coords2.length) {
			System.out.println("Solution warning: euclidean distance between 2 different dimensions");
		}		
		return dist(coords, coords2);
	}
	
	public void randomInit() {
	    RandomGenerator rand = Globals.getRandomGenerator();
	    coords = new double[dim];
	    //populate the array with doubles
	    for (int i = 0; i < coords.length; ++i) {
			coords[i] = rand.randomDouble(targetProblem.getLB(), targetProblem.getUB());
		}
	}

	@Override
	public String toString() {
		String coordsString = "";
		for (double p : this.coords) {
			coordsString += Double.toString(p) + '\n';
		}
		
		return "Solution dim = " + dim 
				+ " fitness = " + getFitness() 
				+ " targetProblem = " + targetProblem
				+ " coords:\n"
				+ coordsString;
	}
	
	
	
}
