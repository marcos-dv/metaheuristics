package tests;

import problems.RandomProblem;
import problems.Solution;
import utils.Algorithms;
import utils.Globals;
import utils.Pair;

public class TestAlgorithms {
	
	public static void KbestTest(int pop, int k) {
		Globals.getRandomGenerator().setSeed(1);
		Solution[] sols = new Solution[pop];
		for (int i = 0; i < sols.length; ++i) {
			sols[i] = new Solution();
			sols[i].setTargetProblem(new RandomProblem());
			sols[i].randomInit();
		}
		System.out.println("Fitness:");
		for (int i = 0; i < sols.length; ++i) {
			System.out.println(i + ". " + sols[i].getFitness());
		}
		Pair[] orderedPairs = Pair.sortIdxSolutions(sols);
		System.out.println("\nSorted sols by fitness:");
		for (int i = 0; i < sols.length; ++i) {
			System.out.println(orderedPairs[i].index + ". " + orderedPairs[i].value);
		}

		Solution[] kbest = Pair.getKbest(sols, k);
		System.out.println("\nBest " + k + " sols by fitness:");
		for (int i = 0; i < kbest.length; ++i) {
			System.out.println(i + ". " + kbest[i].getFitness());
		}
}
	
	public static void main(String[] args) {
		System.out.println("Start test");
		KbestTest(10, 2);
		System.out.println("End testing");
	}


}
