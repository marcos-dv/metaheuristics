package tests;

import control.Globals;
import metaheuristics.GSA;
import metaheuristics.IMetaheuristic;
import metaheuristics.PSORingAll;
import metaheuristics.PSORingGroups;
import metaheuristics.PSORingOne;
import metaheuristics.PTGSA;
import problems.Cec2015Problem;
import problems.Problem;
import problems.commombenchmarks.AckleyProblem;
import problems.commombenchmarks.RosebrockProblem;
import solutions.Solution;
import utils.Algorithms;
import utils.Geometry;
import utils.SimpleClock;

public class TestAlgorithm {
	
	static int dim = 30;
	static int numIter = 10000*dim;
	static int method = -1;
	
	private static IMetaheuristic generateAlgorithm(long seed, Problem targetProblem, int popsize) {
		if (method == 1) { // GSA - ori
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter);
			meta.initPop();
			meta.setAlfa(20);
			return meta;
		}
		else if (method == 2) { // PTGSA - ori
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter);
			meta.initPop();
			double [] alphas = Algorithms.uniformSample(10, 50, 9);
			PTGSA meta2 = new PTGSA(meta, alphas);
			return meta2;
		}
		else if (method == 3) { // Temporal weight 0.1
			double temporalWeight = 0.1;
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter);
			meta.initPop();
			double [] alphas = Algorithms.uniformSample(10, 50, 9);
			PTGSA meta2 = new PTGSA(meta, alphas);
			meta2.setTemporalWeight(temporalWeight);
			return meta2;
		}
		else if (method == 4) { // Temporal weight 0.25
			double temporalWeight = 0.25;
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter);
			meta.initPop();
			double [] alphas = Algorithms.uniformSample(10, 50, 9);
			PTGSA meta2 = new PTGSA(meta, alphas);
			meta2.setTemporalWeight(temporalWeight);
			return meta2;
		}
		else if (method == 5) { // Consec iter 10
			int consecIter = 10;
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter*consecIter);
			meta.initPop();
			double [] alphas = Algorithms.uniformSample(10, 50, 9);
			PTGSA meta2 = new PTGSA(meta, alphas);
			meta2.setConsecutiveIterations(consecIter);
			return meta2;
		}
		else if (method == 6) { // Consec iter 20
			int consecIter = 20;
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter*consecIter);
			meta.initPop();
			double [] alphas = Algorithms.uniformSample(10, 50, 9);
			PTGSA meta2 = new PTGSA(meta, alphas);
			meta2.setConsecutiveIterations(consecIter);
			return meta2;
		}
		else if (method == 7) { // PTGSA - sigmoid
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter);
			meta.initPop();
			double [] alphas = Algorithms.uniformSample(10, 50, 9);
			PTGSA meta2 = new PTGSA(meta, alphas);
			return meta2;
		}
		else if (method == 8) { // PTGSA - mix 0.1, 10
			int consecIter = 10;
			double temporalWeight = 0.25;
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter*consecIter);
			meta.initPop();
			double [] alphas = Algorithms.uniformSample(10, 50, 9);
			PTGSA meta2 = new PTGSA(meta, alphas);
			meta2.setTemporalWeight(temporalWeight);
			meta2.setConsecutiveIterations(consecIter);
			return meta2;
		}
		
		else if (method == 10) { // PSORing ratio 1
			PSORingGroups meta = new PSORingGroups(popsize, targetProblem);
			meta.setCoefSpeed(0.7);
			meta.setCoefLocalBest(0.2);
			meta.setCoefGlobalBest(0.3);
			meta.setLearningRate(0.99);
			meta.setRatio(1);
			meta.initPop();

			return meta;
		}
		
		else if (method == 11) { // PSORing ratio 2
			PSORingGroups meta = new PSORingGroups(popsize, targetProblem);
			meta.setCoefSpeed(0.7);
			meta.setCoefLocalBest(0.2);
			meta.setCoefGlobalBest(0.3);
			meta.setLearningRate(0.99);
			meta.setRatio(2);
			meta.initPop();

			return meta;
		}

		else if (method == 12) { // PSORing ratio 1 one
			PSORingGroups meta = new PSORingGroups(popsize, targetProblem);
			meta.setCoefSpeed(0.7);
			meta.setCoefLocalBest(0.2);
			meta.setCoefGlobalBest(0.3);
			meta.setLearningRate(0.99);
			meta.setRatio(1);
			meta.initPop();

			PSORingOne meta2 = new PSORingOne(meta);
			double [] unif = Algorithms.uniformSample(0.1, 0.5, 6);
			double [] unif2 = Algorithms.uniformSample(0.5, 1, 6);
			meta2.setNewParam("v", unif2);
			meta2.setNewParam("local", unif);
			meta2.setNewParam("global", unif);
			return meta2;
		}

		else if (method == 13) { // PSORing ratio 2 one
			PSORingGroups meta = new PSORingGroups(popsize, targetProblem);
			meta.setCoefSpeed(0.7);
			meta.setCoefLocalBest(0.2);
			meta.setCoefGlobalBest(0.3);
			meta.setLearningRate(0.99);
			meta.setRatio(2);
			meta.initPop();

			PSORingOne meta2 = new PSORingOne(meta);
			double [] unif = Algorithms.uniformSample(0.1, 0.5, 6);
			double [] unif2 = Algorithms.uniformSample(0.5, 1, 6);
			meta2.setNewParam("v", unif2);
			meta2.setNewParam("local", unif);
			meta2.setNewParam("global", unif);
			return meta2;
		}

		else if (method == 14) { // PSORing ratio 1 all
			PSORingGroups meta = new PSORingGroups(popsize, targetProblem);
			meta.setCoefSpeed(0.7);
			meta.setCoefLocalBest(0.2);
			meta.setCoefGlobalBest(0.3);
			meta.setLearningRate(0.99);
			meta.setRatio(1);
			meta.initPop();

			PSORingAll meta2 = new PSORingAll(meta);
			double [] unif = Algorithms.uniformSample(0.1, 0.5, 6);
			double [] unif2 = Algorithms.uniformSample(0.5, 1, 6);
			meta2.setNewParam("v", unif2);
			meta2.setNewParam("local", unif);
			meta2.setNewParam("global", unif);
			return meta2;
		}

		else if (method == 15) { // PSORing ratio 2 all
			PSORingGroups meta = new PSORingGroups(popsize, targetProblem);
			meta.setCoefSpeed(0.7);
			meta.setCoefLocalBest(0.2);
			meta.setCoefGlobalBest(0.3);
			meta.setLearningRate(0.99);
			meta.setRatio(2);
			meta.initPop();

			PSORingAll meta2 = new PSORingAll(meta);
			double [] unif = Algorithms.uniformSample(0.1, 0.5, 6);
			double [] unif2 = Algorithms.uniformSample(0.5, 1, 6);
			meta2.setNewParam("v", unif2);
			meta2.setNewParam("local", unif);
			meta2.setNewParam("global", unif);
			return meta2;
		}
		return null;

	}
	
	private static Problem generateProblem(int num, int dim) {
		// return new RosebrockProblem(dim);
		return new Cec2015Problem(num, dim);
	}

	public static Solution simulation(int num, long seed) {
		Globals.getRandomGenerator().setSeed(seed);
		int popsize = 50;
		Problem p = generateProblem(num, dim);
		IMetaheuristic algorithm = generateAlgorithm(seed, p, popsize);
		// RUN
		for(int i = 0; i < numIter; ++i) {
			algorithm.nextIter();
			Solution[] sols = algorithm.getSols();
			Solution best = Algorithms.getGlobalOptimum(sols);
			System.out.print(best.getFitness() + " ");
		}
		return algorithm.getGlobalOptimum();
	}
	
	public static void main(String[] args) {
		method = Integer.parseInt(args[0]);
		int num = Integer.parseInt(args[1]);
		long seed = System.currentTimeMillis();
		Solution sol = simulation(num, seed);
	}

}
