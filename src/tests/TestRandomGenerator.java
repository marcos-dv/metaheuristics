package tests;

import utils.RandomGenerator;
import utils.RandomGeneratorJava;

public class TestRandomGenerator {

	public static void testGenerator(RandomGenerator rand) {
		System.out.println(rand.randomInt(13, -13));
		System.out.println(rand.randomInt(-7, 7));
		System.out.println(rand.randomInt(123456, 123456));

		System.out.println(rand.randomUniform(13, -13));
		System.out.println(rand.randomUniform(-7, 7));
		System.out.println(rand.randomUniform(123456, 123456));
	}
	
	public static void main(String[] args) {
		long seed = 1;
		RandomGenerator rand = new RandomGeneratorJava();
		rand.setSeed(seed);
		testGenerator(rand);
	}
}
