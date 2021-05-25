package mason;

public class TestMasonMeta {

	public static void contConsole() {
		ContinuousMetaSimulation game = new ContinuousMetaSimulation(System.currentTimeMillis(), 10, 10, 1);
		game.start();
		long steps = 0;
		game.printGrid();
		while (steps < 5) {
			if (!game.schedule.step(game))
				break;
			steps = game.schedule.getSteps();
		}
		game.printGrid();
		game.finish();
		System.exit(0);
	}
	
	public static void startContinuousSimulationUI() {
		int w = 200;
		int h = 150;
		double discretization = 1.0;
		new ContinuousMetaSimulationUI(w, h, discretization).createController();
	}
	
	public static void main(String[] args) {
		startContinuousSimulationUI();
	}

}
