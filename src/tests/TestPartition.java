package tests;

import utils.Algorithms;
import utils.Geometry;

public class TestPartition {
	public static void main(String[] args) {
		
		Geometry.display(Algorithms.uniformSample(10, 50, 9));
		if (true)
			return;
		
		
		double[] range = Algorithms.uniformSample(10, 30, 9);
		for (double d : range) {
			System.out.println(d);
		}
		range = Algorithms.forwardSample(10, 3, 3);
		for (double d : range) {
			System.out.println(d);
		}

		range = Algorithms.uniformSample(10, 50, 9);
		for (double d : range) {
			System.out.println(d);
		}

	}
}
