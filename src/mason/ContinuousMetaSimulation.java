package mason;

import control.Globals;
import control.Messages;
import metaheuristics.GSA;
import metaheuristics.IMetaheuristic;
import problems.Problem;
import sim.engine.*;
import sim.field.continuous.Continuous2D;
import sim.util.Double2D;
import solutions.Solution;
import utils.RandomGenerator;

public class ContinuousMetaSimulation extends SimState implements Steppable {

	private static final long serialVersionUID = 1L;
	public int width = 40;
	public int height = 20;
	public double discretization = 1;
	public Continuous2D grid = null;
	public int popsize = 500;
	int problemDimension;
	int maxiter;
	Problem targetProblem;
	
	IMetaheuristic algorithm;
	
	RandomGenerator randomGenerator = Globals.getRandomGenerator();
	
	public ContinuousMetaSimulation(long seed, int w, int h, double discretization, Problem p) {
		super(seed);
		setTargetProblem(p);
		width = w;
		height = h;
		this.discretization = discretization;
	}

	public ContinuousMetaSimulation(long seed, Problem p) {
		// W = 40, H = 20, Discretization = 1 by default
		this(seed, 40, 20, 1, p);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public double getDiscretization() {
		return discretization;
	}

	public void setDiscretization(double discretization) {
		this.discretization = discretization;
	}

	public Continuous2D getGrid() {
		return grid;
	}

	public void setGrid(Continuous2D grid) {
		this.grid = grid;
	}

	public int getPopsize() {
		return popsize;
	}

	public void setPopsize(int popsize) {
		this.popsize = popsize;
	}

	public int getProblemDimension() {
		return problemDimension;
	}

	public void setProblemDimension(int problemDimension) {
		this.problemDimension = problemDimension;
	}

	public int getMaxiter() {
		return maxiter;
	}

	public void setMaxiter(int maxiter) {
		this.maxiter = maxiter;
	}

	public Problem getTargetProblem() {
		return targetProblem;
	}

	public void setTargetProblem(Problem targetProblem) {
		this.targetProblem = targetProblem;
	}
	
	public IMetaheuristic getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(IMetaheuristic algorithm) {
		this.algorithm = algorithm;
	}
	
	@Override
	public void step(SimState arg0) {
		System.out.println("New step");
		updateAlgorithm();
		updateGridPositions();
//		printGrid();
		printSols();
	}
	
	private Double2D realSpace2Screen(double [] coords) {
		double x = coords[0];
		double y = coords[1];
		// Translate to the origin = (w/2, h/2)
		double xx = x + (double)width/2;
		double yy = y + (double)height/2;

		// Rotate Y axis ->
		// y <- MAX_Y - y
		return new Double2D(xx, height-yy);
	}
	
	private void updateGridPositions() {
		// Just mapping algorithm positions -> screen positions
		Solution [] sols = algorithm.getSols();
		if (sols.length != popsize) {
			Messages.error("ContinuousMetaSimulationUI: number of solutions (" 
					+ sols.length + ") and popsize (" 
					+ popsize + " are not equal");
		}
		for(int i = 0; i < popsize; ++i) {
			double [] spacePosition = sols[i].getCoords();
			grid.setObjectLocation(i, realSpace2Screen(spacePosition));
		}
	}

	private void updateAlgorithm() {
		System.out.println("-- Iter " + algorithm.getNumIter());
		algorithm.nextIter();
		// Global Best
		Solution globalBest = algorithm.getGlobalOptimum();
		System.out.println("Best sol: ");
		System.out.println(globalBest);
		System.out.println();
	}

	public void start() {
		super.start();
		grid = new Continuous2D(discretization, width, height);
		// clear the grid
		grid.clear();
		setupAlgorithm();
		setupGrid();
		schedule.scheduleRepeating(this);
	}

	private void setupAlgorithm() {
		maxiter = 1000;
		GSA gsa = new GSA(popsize, targetProblem);
		gsa.setMAX_ITER(maxiter);
		gsa.initPop();
		setAlgorithm(gsa);
	}
	
	private void setupGrid() {
		updateGridPositions();
		// Reference points
		grid.setObjectLocation(-1, realSpace2Screen(new double[]{1, 1}));
		grid.setObjectLocation(-2, realSpace2Screen(new double[]{50, 50}));
		grid.setObjectLocation(-3, realSpace2Screen(new double[]{1, 50}));
		grid.setObjectLocation(-4, realSpace2Screen(new double[]{50, 1}));
		// Center
		double [] center = new double[2];
		center[0] = (double) width/2;
		center[1] = (double) height/2;
		grid.setObjectLocation(-5, realSpace2Screen(center));
	}
	
	public void printGrid() {
		System.out.println("Printing points");
		for (int i = 0; i < popsize; ++i) {
			Double2D p = grid.getObjectLocation(i);
			System.out.println(i + ". (" + p.x + ", " + p.y + ")");
		}
	}

	public void printSols() {
		System.out.println("Printing solutions");
		Solution[] sols = algorithm.getSols();
		for(int i = 0; i < sols.length; ++i) {
			System.out.println(sols[i]);
		}
	}

}
