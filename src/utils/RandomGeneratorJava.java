package utils;

import java.util.Random;

public class RandomGeneratorJava implements RandomGenerator {

	Random rand;
	
	public RandomGeneratorJava() {
		rand = new Random();
	}

	@Override
	public void setSeed(long seed) {
		rand = new Random(seed);
	}

	@Override
	public int randomInt() {
		return rand.nextInt();
	}

	@Override
	public int randomInt(int lowerBound, int upperBound) {
		int r = randomInt() % (upperBound-lowerBound);
		if (r < 0)
			return r + upperBound;
		return r + lowerBound;
	}

	@Override
	public double randomDouble() {
		return rand.nextDouble();
	}

	@Override
	public double randomDouble(double lowerBound, double upperBound) {
		return lowerBound + (upperBound-lowerBound)*randomDouble();
	}

}
