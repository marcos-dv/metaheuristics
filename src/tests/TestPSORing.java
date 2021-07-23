package tests;

import control.Globals;
import metaheuristics.IMetaheuristic;
import metaheuristics.PSORingAll;
import metaheuristics.PSORingGroups;
import metaheuristics.PSORingOne;
import problems.Cec2015Problem;
import problems.Problem;
import problems.commombenchmarks.AckleyProblem;
import problems.commombenchmarks.RosebrockProblem;
import solutions.Solution;
import utils.Algorithms;
import utils.Geometry;
import utils.SimpleClock;

public class TestPSORing {
	
	static int dim = 30;
	static int numIter = 1000*dim;
	
	private static IMetaheuristic generateAlgorithm(long seed, Problem targetProblem, int popsize) {
		
		PSORingGroups meta = new PSORingGroups(popsize, targetProblem);
		meta.setCoefSpeed(0.7);
		meta.setCoefLocalBest(0.2);
		meta.setCoefGlobalBest(0.3);
		meta.setLearningRate(0.99);
		meta.setRatio(2);
		meta.initPop();

		// return meta;

//		PSORingAll meta2 = new PSORingAll(meta);
		PSORingOne meta2 = new PSORingOne(meta);
		meta2.setConsecutiveIterations(11);
		double [] acoef = new double[]{0.5, 0.7, 0.9, 1.1};
		double [] bccoef = new double[]{0.1, 0.2, 0.3};
		double [] lrcoef = new double[]{.9};
		double [] unif = Algorithms.uniformSample(0.1, 0.5, 6);
		double [] unif2 = Algorithms.uniformSample(0.5, 1, 6);
		meta2.setNewParam("v", unif2);
		meta2.setNewParam("local", unif);
		meta2.setNewParam("global", unif);
		meta2.setNewParam("lr", lrcoef);
		return meta2;
		
		
		
	}
	
	private static Problem generateProblem(int num, int dim) {
		return new RosebrockProblem(dim);
		//		return new Cec2015Problem(num, dim);
	}

	public static Solution simulation(int num, long seed) {
		Globals.getRandomGenerator().setSeed(seed);
		int popsize = 50;
		Problem p = generateProblem(num, dim);
		IMetaheuristic algorithm = generateAlgorithm(seed, p, popsize);
		// RUN
		for(int i = 0; i < numIter; ++i) {
			algorithm.nextIter();
			Solution best = algorithm.getGlobalOptimum();
			double [] coo = new double[4];
			if (i % 20 == 0) {
				for (int kk = 0; kk < 4; ++kk)
					coo[kk] = best.coords[kk];
				System.out.println(i);
				Geometry.display(coo);
				System.out.println(best.getFitness());
			}
		}
		return algorithm.getGlobalOptimum();
	}
	
	public static void main(String[] args) {
		long seed = 31112;
		SimpleClock clock = new SimpleClock();
		clock.start();
		Solution sol = simulation(3, seed);
		clock.end();
		clock.displayTime();
		
		
	}

}
