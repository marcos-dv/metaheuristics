package utils;

public interface RandomGenerator {
	public void setSeed(long seed);
	public int randomInt();
	public int randomInt(int lowerBound, int upperBound);
	public double randomUniform();
	public double randomUniform(double lowerBound, double upperBound);
	public double randomNormal();
	public double randomNormal(double mean, double variance);
}
