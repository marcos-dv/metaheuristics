package utils;

public class Globals {
	private static RandomGenerator rand;
	
	public static RandomGenerator getRandomGenerator() {
		if (rand == null)
			// rand = new RandomGeneratorMersenne();
			rand = new RandomGeneratorJava();
		return rand;
	}
	

}
