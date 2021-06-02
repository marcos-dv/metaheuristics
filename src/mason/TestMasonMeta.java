package mason;

import commombenchmarks.AckleyProblem;
import commombenchmarks.SphereProblem;
import problems.Cec2015Problem;
import problems.CircleProblem;
import problems.PolygonProblem;
import problems.Problem;
import utils.SomePolygons;

public class TestMasonMeta {

	static long seed = 1;
//	static Problem targetProblem = new CircleProblem(2, 5);
	static Problem targetProblem = new PolygonProblem(SomePolygons.hexagon, false);
	
	public static void startContinuousSimulationUI() {
		int w = 250;
		int h = 160;
		double discretization = 1.0;
		ContinuousMetaSimulationUI simulation = new ContinuousMetaSimulationUI(w, h, discretization, seed, targetProblem);
		simulation.createController();
	}
	
	public static void main(String[] args) {
		startContinuousSimulationUI();
	}

}
