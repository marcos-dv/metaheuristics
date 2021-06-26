package solutions;

import java.util.StringTokenizer;

import control.Globals;
import control.Messages;
import problems.Problem;
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

	public Solution(Solution sol) {
		this(sol.getCoords(), sol.getTargetProblem());
		setFitness(sol.getFitness());
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
			Messages.warning("Solution: euclidean distance between 2 different dimensions");
		}		
		return dist(coords, coords2);
	}
	
	public void randomInit() {
	    RandomGenerator rand = Globals.getRandomGenerator();
	    coords = new double[dim];
	    //populate the array with doubles
	    for (int i = 0; i < coords.length; ++i) {
			coords[i] = rand.randomUniform(targetProblem.getLB(), targetProblem.getUB());
		}
	}

	@Override
	public String toString() {
		String coordsString = "( ";
		for (int i = 0; i < coords.length-1; ++i) {
			coordsString += Double.toString(coords[i]) + " , ";
		}
		coordsString += Double.toString(coords[coords.length-1]) + " )\n";
		
		return "Solution fitness = " + getFitness()
				+ " dim = " + dim 
				+ " targetProblem = " + targetProblem
				+ " coords:\n"
				+ coordsString;
	}
	

	public String save() {
		String str = "";
		str += Integer.toString(dim) + ' ';
		for (int i = 0; i < coords.length; ++i) {
			str += Double.toString(coords[i]) + ' ';
		}
		str += getFitness() + '\n';
		return str;
	}
	
	public void build(String str) {
		StringTokenizer tokenizer = new StringTokenizer(str, " ");
		dim = Integer.parseInt(tokenizer.nextToken());
		coords = new double[dim];
		for(int i = 0; i < dim; ++i) {
			coords[i] = Double.parseDouble(tokenizer.nextToken());
		}
		if (tokenizer.hasMoreTokens())
			fitness = Double.parseDouble(tokenizer.nextToken());
	}
	
	public static Solution[] copyOf(Solution[] sols) {
		Solution[] copy = new Solution[sols.length];
		for(int i = 0; i < sols.length; ++i) {
			copy[i] = new Solution(sols[i]);
		}
		return copy;
	}
	
}
