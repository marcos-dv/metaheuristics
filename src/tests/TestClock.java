package tests;

import utils.SimpleClock;

public class TestClock {
	public static void main(String[] args) {
		SimpleClock clock = new SimpleClock();
		clock.start();
		double sum = 0;
		for(int i = 0; i < 112233445; ++i) {
			int j = (2*i+1) % 17;
			double k = Math.cos(j);
			sum += k*k;
		}
		System.out.println(sum);
		clock.end();
		clock.displayTime();
	}
}
