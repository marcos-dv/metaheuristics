package mason;

import sim.engine.*;
import sim.field.continuous.Continuous2D;
import sim.util.Double2D;
import utils.Globals;
import utils.RandomGenerator;

public class ContinuousMetaSimulation extends SimState implements Steppable {

	private static final long serialVersionUID = 1L;
	public int width = 40;
	public int height = 20;
	public double discretization = 1;
	public Continuous2D grid = null;
	public int numParticles = 10;
	RandomGenerator randomGen = Globals.getRandomGenerator();
	
	public ContinuousMetaSimulation(long seed, int w, int h, double discretization) {
		super(seed);
		width = w;
		height = h;
		this.discretization = discretization;
	}

	public ContinuousMetaSimulation(long seed) {
		super(seed);
	}

	private void seedGrid() {
		for(int i = 0; i < numParticles; ++i) {
			double x = randomGen.randomDouble()*width;
			double y = randomGen.randomDouble()*height;
			Double2D xy = new Double2D(x, y);
			grid.setObjectLocation(i, xy);
		}
		// Special points
		grid.setObjectLocation(-1, new Double2D(1, 1));
		grid.setObjectLocation(-2, new Double2D(50, 50));
		grid.setObjectLocation(-3, new Double2D(1, 50));
		grid.setObjectLocation(-4, new Double2D(50, 1));
	}

	@Override
	public void step(SimState arg0) {
		System.out.println("New step");
		// Random movements
		for(int i = 0; i < numParticles; ++i) {
			Double2D p = grid.getObjectLocation(i);
			double newX = p.x + randomGen.randomDouble()-0.5;
			double newY = p.y + randomGen.randomDouble()-0.5;
			Double2D newP = new Double2D(newX, newY);
			grid.setObjectLocation(i, newP);
		}
		printGrid();
	}

	public void start() {
		super.start();
		grid = new Continuous2D(discretization, width, height);
		// clear the grid
		grid.clear();
		seedGrid();
		schedule.scheduleRepeating(this);
	}

	public void printGrid() {
		System.out.println("Printing points");
		for (int i = 0; i < numParticles; ++i) {
			Double2D p = grid.getObjectLocation(i);
			System.out.println(i + ". (" + p.x + ", " + p.y + ")");
		}
	}

}
