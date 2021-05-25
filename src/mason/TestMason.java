package mason;

public class TestMason {

	public static void contConsole() {
		ContinuousGame game = new ContinuousGame(System.currentTimeMillis(), 10, 10, 1);
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
	
	public static void contUI() {
		int w = 200;
		int h = 150;
		double disc = 1.0;
		new ContinuousGameUI(w, h, disc).createController();
	}
	
	public static void main(String[] args) {
		contUI();
	}

}
