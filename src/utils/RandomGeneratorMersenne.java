package utils;

import org.apache.commons.math3.random.MersenneTwister;

import control.Messages;

public class RandomGeneratorMersenne implements RandomGenerator {

	MersenneTwister rand;
	
	public RandomGeneratorMersenne() {
		rand = new MersenneTwister();
	}

	@Override
	public void setSeed(long seed) {
		rand.setSeed(seed);
	}

	@Override
	public int randomInt() {
		return rand.nextInt();
	}

	@Override
	public int randomInt(int lowerBound, int upperBound) {
		if (upperBound == lowerBound)
			return lowerBound;
		if (lowerBound > upperBound) {
			Messages.error("RandomGeneratorJava: lower bound greater than upper bound");
			return 0;
		}
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
		if (upperBound == lowerBound)
			return lowerBound;
		if (lowerBound > upperBound) {
			Messages.error("RandomGeneratorJava: lower bound greater than upper bound");
			return 0;
		}
		return lowerBound + (upperBound-lowerBound)*randomDouble();
	}

}
