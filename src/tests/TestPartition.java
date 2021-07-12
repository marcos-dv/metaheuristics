package tests;

import utils.Algorithms;

public class TestPartition {
	public static void main(String[] args) {
		double[] range = Algorithms.uniformSample(10, 30, 9);
		for (double d : range) {
			System.out.println(d);
		}
		range = Algorithms.forwardSample(10, 3, 3);
		for (double d : range) {
			System.out.println(d);
		}
	}
}
