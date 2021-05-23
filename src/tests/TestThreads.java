package tests;

import problems.Cec2015Problem;
import problems.Problem;
import problems.utils.FitnessCalculator;
import solutions.Solution;
import utils.Globals;

public class TestThreads {

	public static void print(double [] f) {
		for(int i = 0; i < f.length; ++i) {
			System.out.println(i + ". " + f[i]);
		}
		System.out.println();
	}
	
	/**
	 * Checksum at the end of the test
	 */
	private static void showSum(double[] f) {
		double x = 0;
		for(int i = 0; i < f.length; ++i) {
			x += f[i];
		}
		System.out.println("Suma: " + x);
	}

	public static Solution[] genRandomSols(Problem p, int popsize, int dim) {
		Solution [] sols = new Solution[popsize];
		for(int i = 0; i < sols.length; ++i) {
			sols[i] = new Solution(new Cec2015Problem(1, dim));
			sols[i].randomInit();
		}
		return sols;
	}
	
	public static void test(int popsize, int dim) {
		Problem p = new Cec2015Problem(1, dim);
		Solution [] sols = genRandomSols(p, popsize, dim);
		double [] f = new double[popsize];
		
		int nthreads = 8;
		FitnessCalculator [] calcu = new FitnessCalculator[nthreads];
		int rate = popsize/nthreads;
		System.out.println("Rate " + rate);
		for(int i = 0; i < nthreads-1; ++i) {
			calcu[i] = new FitnessCalculator(i*rate, rate, sols, p, f);
		}
		calcu[nthreads-1] = new FitnessCalculator((nthreads-1)*rate, rate+(popsize%nthreads), sols, p, f);
		for (FitnessCalculator fitnessCalculator : calcu) {
			fitnessCalculator.start();
		}
		for (FitnessCalculator fitnessCalculator : calcu) {
			try {
				fitnessCalculator.join();
			} catch (InterruptedException e) {
				System.out.println("Error-TestThreads: Error while waiting threads");
			}
		}

		print(f);
		showSum(f);
	}
	

	public static void main(String[] args) {
		Globals.getRandomGenerator().setSeed(1);;
		int popsize = 50;
		int dim = 30;
		for(int i = 0; i < 5; ++i)
			test(popsize, dim);
	}
	
}
