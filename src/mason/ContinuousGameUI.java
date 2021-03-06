package mason;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.Portrayal;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.simple.OvalPortrayal2D;

public class ContinuousGameUI extends GUIState {
	public Display2D display;
	public JFrame displayFrame;

	public ContinuousGameUI() {
		super(new ContinuousGame(System.currentTimeMillis()));
	}

	public ContinuousGameUI(int w, int h, double discretization) {
		super(new ContinuousGame(System.currentTimeMillis(), w, h, discretization));
	}

	public ContinuousGameUI(SimState state) {
		super(state);
	}

	ContinuousPortrayal2D gridPortrayal = new ContinuousPortrayal2D();

	public void setupPortrayals() {
		ContinuousGame game = (ContinuousGame) state;

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
		// we now have a new grid. Set up the portrayals to reflect this
		setup();
	}

	public void init(Controller c) {
		super.init(c);
		ContinuousGame game = (ContinuousGame) state;
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