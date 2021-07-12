package exec;

import commombenchmarks.AckleyProblem;
import commombenchmarks.EllipticProblem;
import commombenchmarks.GriewankProblem;
import commombenchmarks.RosebrockProblem;
import commombenchmarks.Schwefel222Problem;
import control.Globals;
import metaheuristics.GSA;
import metaheuristics.IMetaheuristic;
import metaheuristics.MultiSimulatedAnnealing;
import metaheuristics.PSO;
import metaheuristics.PSOAll;
import metaheuristics.PSOGroups;
import metaheuristics.PSOOne;
import metaheuristics.PTGSA;
import problems.MinSqSumProblem;
import problems.Problem;
import utils.SimpleClock;

public class Experiments {

	private static int numSimulations = 30;
	private static long [] seed;
	private static int numProblems = 4;
	private static Problem [] benchmark;

	private static int dimension = 30;
	private static int numIter = 10000;

	private static int numAlgorithms = 9;

	private static SimpleClock clock = new SimpleClock();
	
	private static void initBenchmark() {
		benchmark = new Problem[numProblems];
		benchmark[0] = new Schwefel222Problem(dimension);
		benchmark[1] = new EllipticProblem(dimension);
		benchmark[2] = new GriewankProblem(dimension);
		benchmark[3] = new AckleyProblem(dimension);
	}

	private static IMetaheuristic generateAlgorithm(long seed, Problem targetProblem, int popsize, int method) {
		if (method == 0) {
			PSO meta = new PSO(popsize, targetProblem);
			meta.setCoefGlobalBest(1./3);
			meta.setCoefLocalBest(1./3);
			meta.setCoefSpeed(1./3);
			meta.setLearningRate(0.9);
			meta.initPop();
			return meta;
		}
		else if (method == 1) {
			PSOGroups meta = new PSOGroups(popsize, targetProblem);
			meta.setCoefGlobalBest(1./3);
			meta.setCoefLocalBest(1./3);
			meta.setCoefSpeed(1./3);
			meta.setLearningRate(0.9);
			meta.initPop();
			meta.setGroupSize(5);
			return meta;
		}
		else if (method == 2) {
			GSA meta = new GSA(popsize, targetProblem);
			meta.setAlfa(20);
			meta.initPop();
			return meta;
		} 
		else if (method == 3) {
			MultiSimulatedAnnealing meta = new MultiSimulatedAnnealing(popsize, targetProblem);
			meta.setAlfa(0.9);
			meta.setTemp(50000);
			meta.setL(500);
			meta.setStep(1.0);
			meta.initPop();
			return meta;
		} // PARAMETER-TUNING ALGORITHMS
		else if (method == 4) {
			PSO meta = new PSO(popsize, targetProblem);
			meta.setLearningRate(0.9);
			meta.initPop();
			PSOAll meta2 = new PSOAll(meta);
			double [] coefs = new double[]{1./4, 1./3, 1./2};
			meta2.setNewParam("v", coefs);
			meta2.setNewParam("local", coefs);
			meta2.setNewParam("global", coefs);
			return meta2;
		}
		else if (method == 5) {
			PSO meta = new PSO(popsize, targetProblem);
			meta.setLearningRate(0.9);
			meta.initPop();
			PSOOne meta2 = new PSOOne(meta);
			double [] coefs = new double[]{1./4, 1./3, 1./2};
			meta2.setNewParam("v", coefs);
			meta2.setNewParam("local", coefs);
			meta2.setNewParam("global", coefs);
			return meta2;
		}
		else if (method == 6) {
			PSOGroups meta = new PSOGroups(popsize, targetProblem);
			meta.setLearningRate(0.9);
			meta.initPop();
			meta.setGroupSize(5);
			PSOAll meta2 = new PSOAll(meta);
			double [] coefs = new double[]{1./4, 1./3, 1./2};
			meta2.setNewParam("v", coefs);
			meta2.setNewParam("local", coefs);
			meta2.setNewParam("global", coefs);
			return meta2;
		}
		else if (method == 7) {
			PSOGroups meta = new PSOGroups(popsize, targetProblem);
			meta.setLearningRate(0.9);
			meta.initPop();
			meta.setGroupSize(5);
			PSOOne meta2 = new PSOOne(meta);
			double [] coefs = new double[]{1./4, 1./3, 1./2};
			meta2.setNewParam("v", coefs);
			meta2.setNewParam("local", coefs);
			meta2.setNewParam("global", coefs);
			return meta2;
		}
		else if (method == 8) {
			GSA meta = new GSA(popsize, targetProblem);
			meta.setAlfa(20);
			meta.initPop();
			double [] alphas = new double[] {15, 20, 25, 30};
			PTGSA meta2 = new PTGSA(meta, alphas);
			return meta2;
		} 
		
		System.out.println("Warning: method not found");
		return null;
	}

	private static double simulation(long seed, Problem targetProblem, int method) {
		Globals.getRandomGenerator().setSeed(seed);
		int popsize = 50;
		IMetaheuristic algorithm = generateAlgorithm(seed, targetProblem, popsize, method);
		return run(algorithm);
	}

	private static double run(IMetaheuristic algorithm) {
		for(int i = 0; i < numIter; ++i) {
			algorithm.nextIter();
		}
		return algorithm.getGlobalOptimum().getFitness();
	}

	public static void main(String[] args) {
		seed = new long[numSimulations];
		for(int i = 0; i < seed.length; ++i) {
			seed[i] = i*3+1;
		}
		double [][][] results = new double[numAlgorithms][numProblems][numSimulations];
		initBenchmark();

		for(int k = 0; k < numAlgorithms; ++k) {
			clock.start();
			for(int j = 0; j < numProblems; ++j) {
				for(int i = 0; i < seed.length; ++i) {
					results[k][j][i] = simulation(seed[i], benchmark[j], k);
				}
			}
			clock.end();
			clock.displayTime();
		}
		// TODO get media
		double [][] means = new double[numAlgorithms][numProblems];
		for(int k = 0; k < numAlgorithms; ++k) {
			for(int j = 0; j < numProblems; ++j) {
				for(int i = 0; i < numSimulations; ++i) {
					means[k][j] += results[k][j][i];
				}
				means[k][j] = means[k][j]/numSimulations;
			}
		}
		for(int k = 0; k < numAlgorithms; ++k) {
			for(int j = 0; j < numProblems; ++j) {
				System.out.println("Algo " + k + " Problem " + (j+1) + " = " + means[k][j]);
			}
		}
	}


	
	
}
