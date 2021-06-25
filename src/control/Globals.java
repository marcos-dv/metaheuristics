package control;

import utils.RandomGenerator;
import utils.RandomGeneratorJava;
import utils.RandomGeneratorMersenne;

public class Globals {
	private static RandomGenerator rand;
	
	public static RandomGenerator getRandomGenerator() {
		if (rand == null)
			rand = new RandomGeneratorMersenne();
			// rand = new RandomGeneratorJava();
		return rand;
	}
	

}
