package mason;

import commombenchmarks.AckleyProblem;
import commombenchmarks.SphereProblem;
import problems.Problem;

public class TestMasonMeta {

	static long seed = 1;
	static Problem targetProblem = new AckleyProblem(2, -20, 20);
	
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
