package mason;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import misc.SolverInfo;
import problems.Problem;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.Portrayal;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.simple.OvalPortrayal2D;

public class ContinuousMetaSimulationUI extends GUIState {
	public Display2D display;
	public JFrame displayFrame;
	
	public ContinuousMetaSimulationUI(SolverInfo solverInfo) {
		super(new ContinuousMetaSimulation(System.currentTimeMillis(), solverInfo));
	}

	public ContinuousMetaSimulationUI(int w, int h, double discretization, long seed, SolverInfo solverInfo) {
		super(new ContinuousMetaSimulation(seed, w, h, discretization, solverInfo));
	}

	public ContinuousMetaSimulationUI(int w, int h, double discretization, SolverInfo solverInfo) {
		this(w, h, discretization, System.currentTimeMillis(), solverInfo);
	}


	public ContinuousMetaSimulationUI(SimState state) {
		super(state);
	}

	ContinuousPortrayal2D gridPortrayal = new ContinuousPortrayal2D();

	public void setupPortrayals() {
		ContinuousMetaSimulation game = (ContinuousMetaSimulation) state;

		// tell the portrayals what to portray and how
		// to portray them
		gridPortrayal.setField(game.grid);
		// particles
		gridPortrayal.setPortrayalForAll((Portrayal) new OvalPortrayal2D() {
			private static final long serialVersionUID = 1L;
			public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
				int i = (int) object;
				int r = 255;
				int g = 50;
				int b = 200;
				// (0, 0)
				if (i < 0) {
					r = 0;
					g = 0;
					b = 0;
				}
				paint = new Color(r, g, b);
				super.draw(object, graphics, info);
			}
		});
		// axis
		gridPortrayal.setAxes(true);
		gridPortrayal.setBorder(true);
	}

	private void setup() {
		setupPortrayals();
		// specify the backdrop color -- what gets painted behind the displays
		display.reset();
		Color white = new Color(255, 255, 255);
		display.setBackdrop(white);
		// redraw the display
		display.repaint();
	}

	// set up our portrayals
	// reschedule the displayer
	// redraw the display
	public void start() {
		super.start();
		setup();
	}

	public void load(SimState state) {
		super.load(state);
		// Now we have a new grid. Set up the portrayals to reflect this
		setup();
	}

	public void init(Controller c) {
		super.init(c);
		ContinuousMetaSimulation game = (ContinuousMetaSimulation) state;
		// Make the Display2D. We'll have it display stuff later.
		display = new Display2D(game.width * 4, game.height * 4, this);
		displayFrame = display.createFrame();
		// register the frame so it appears in the "Display" list
		c.registerFrame(displayFrame);
		displayFrame.setVisible(true);
		// attach the portrayals
		display.attach(gridPortrayal, "Continuous");
	}

	public void quit() {
		super.quit();
		if (displayFrame != null)
			displayFrame.dispose();
		displayFrame = null;
		display = null;
	}

}