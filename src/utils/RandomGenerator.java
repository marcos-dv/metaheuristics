package utils;

public interface RandomGenerator {
	public void setSeed(long seed);
	public int randomInt();
	public int randomInt(int lowerBound, int upperBound);
	public double randomDouble();
	public double randomDouble(double lowerBound, double upperBound);
}
