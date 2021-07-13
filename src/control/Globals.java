package control;

import org.apache.commons.math3.analysis.function.Sigmoid;

import utils.RandomGenerator;
import utils.RandomGeneratorJava;
import utils.RandomGeneratorMersenne;

public class Globals {
	public static Sigmoid sigmoid01 = new Sigmoid(0, 1);
	
	private static RandomGenerator rand;
	
	public static RandomGenerator getRandomGenerator() {
		if (rand == null)
			rand = new RandomGeneratorMersenne();
			// rand = new RandomGeneratorJava();
		return rand;
	}
	

}
