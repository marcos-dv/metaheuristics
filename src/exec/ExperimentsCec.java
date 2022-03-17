package exec;

import control.Globals;
import metaheuristics.GSA;
import metaheuristics.IMetaheuristic;
import metaheuristics.PSORingAll;
import metaheuristics.PSORingGroups;
import metaheuristics.PSORingOne;
import metaheuristics.PTGSA;
import problems.Cec2015Problem;
import problems.Problem;
import solutions.Solution;
import utils.Algorithms;
import utils.SimpleClock;

public class ExperimentsCec {
	
	private static long [] seed;
	private static Problem [] benchmark;
	
	private static int dimension = 30;

	private static int numAlgorithms;

	private static boolean time_flag = false;

	private static boolean parsedArgs;
	
	private static SimpleClock clock = new SimpleClock();
	
	// All these numeric globals should be specified via args[]	
	private static int numSimulations = 30;
	private static int numSimulationsStart = 0;
	private static int numSimulationsEnd = numSimulationsStart+numSimulations;
	private static long startingSeed = 1;
	private static int numProblems; // see initBenchmark()
	private static int problem_id = 1;
	private static int method_id = 1;
//	private static int numIter = dimension*10000; // 300 000
	private static int numIter = 20; // 300 000

	
	private static int [] initMethods() {
		int [] methods;
		if (!parsedArgs) {
			methods = new int[]{1, 2, 3, 4};
			numAlgorithms = methods.length;
		}
		else {
			methods = new int[numAlgorithms];
			methods[0] = method_id;
		}
		return methods;		
	}

	private static Problem[] initBenchmark() {
		Problem [] benchmark;
		if (!parsedArgs) {
			int [] funcnumber1 = new int[] {1, 2, 3, 4, 5, 6, 8};
			int [] funcnumber2 = new int[] {7, 9, 10, 12, 13, 14};
			int [] funcnumber3 = new int[] {11, 15};
			int [] funcnumberAll = new int[] {1, 2, 3, 4, 5,
											  6, 7, 8, 9, 10,
											  11, 12, 13, 14, 15};
			
			int [] funcnumber = new int[] {1};
			numProblems = funcnumber.length;
			benchmark = new Problem[numProblems];
			for(int i = 0; i < numProblems; ++i) {
				benchmark[i] = new Cec2015Problem(funcnumber[i], dimension);
			}
		}
		else {
			benchmark = new Problem[numProblems];
			benchmark[0] = new Cec2015Problem(problem_id, dimension);
		}
		return benchmark;
	}

	private static IMetaheuristic generateAlgorithm(long seed, Problem targetProblem, int popsize, int method) {
		
		// double [] alphas = Algorithms.uniformSample(10, 50, 9);
		// Alphas for PTGSA
		double [] alphas = Algorithms.uniformSample(1, 101, 11);

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
			PTGSA meta2 = new PTGSA(meta, alphas);
			return meta2;
		}
		else if (method == 3) { // Temporal weight 0.1
			double temporalWeight = 0.1;
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter);
			meta.initPop();
			PTGSA meta2 = new PTGSA(meta, alphas);
			meta2.setTemporalWeight(temporalWeight);
			return meta2;
		}
		else if (method == 4) { // Temporal weight 0.25
			double temporalWeight = 0.25;
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter);
			meta.initPop();
			PTGSA meta2 = new PTGSA(meta, alphas);
			meta2.setTemporalWeight(temporalWeight);
			return meta2;
		}
		else if (method == 5) { // Consec iter 10
			int consecIter = 10;
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter*consecIter);
			meta.initPop();
			PTGSA meta2 = new PTGSA(meta, alphas);
			meta2.setConsecutiveIterations(consecIter);
			return meta2;
		}
		else if (method == 6) { // Consec iter 20
			int consecIter = 20;
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter*consecIter);
			meta.initPop();
			PTGSA meta2 = new PTGSA(meta, alphas);
			meta2.setConsecutiveIterations(consecIter);
			return meta2;
		}
		else if (method == 7) { // PTGSA - sigmoid
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter);
			meta.initPop();
			PTGSA meta2 = new PTGSA(meta, alphas);
			return meta2;
		}
		else if (method == 8) { // PTGSA - mix 0.25, 10
			int consecIter = 10;
			double temporalWeight = 0.25;
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter*consecIter);
			meta.initPop();
			PTGSA meta2 = new PTGSA(meta, alphas);
			meta2.setTemporalWeight(temporalWeight);
			meta2.setConsecutiveIterations(consecIter);
			return meta2;
		}
		
		////// MORE TESTING
		else if (method == 20) { // Temporal weight 0.05
			double temporalWeight = 0.05;
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter);
			meta.initPop();
			PTGSA meta2 = new PTGSA(meta, alphas);
			meta2.setTemporalWeight(temporalWeight);
			return meta2;
		}
		else if (method == 21) { // Temporal weight 0.125
			double temporalWeight = 0.125;
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter);
			meta.initPop();
			PTGSA meta2 = new PTGSA(meta, alphas);
			meta2.setTemporalWeight(temporalWeight);
			return meta2;
		}
		else if (method == 22) { // Temporal weight 0.15
			double temporalWeight = 0.15;
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter);
			meta.initPop();
			PTGSA meta2 = new PTGSA(meta, alphas);
			meta2.setTemporalWeight(temporalWeight);
			return meta2;
		}
		else if (method == 23) { // Consec iter 50
			int consecIter = 50;
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter*consecIter);
			meta.initPop();
			PTGSA meta2 = new PTGSA(meta, alphas);
			meta2.setConsecutiveIterations(consecIter);
			return meta2;
		}
		else if (method == 24) { // Consec iter 75
			int consecIter = 75;
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter*consecIter);
			meta.initPop();
			PTGSA meta2 = new PTGSA(meta, alphas);
			meta2.setConsecutiveIterations(consecIter);
			return meta2;
		}
		else if (method == 25) { // Consec iter 100
			int consecIter = 100;
			GSA meta = new GSA(popsize, targetProblem);
			meta.setMAX_ITER(numIter*consecIter);
			meta.initPop();
			PTGSA meta2 = new PTGSA(meta, alphas);
			meta2.setConsecutiveIterations(consecIter);
			return meta2;
		}
		
		///// PSO
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

		/*
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
			PSOIndependantGroups meta = new PSOIndependantGroups(popsize, targetProblem);
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
			PSOIndependantGroups meta = new PSOIndependantGroups(popsize, targetProblem);
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
		*/
		System.out.println("Warning: method not found");
		return null;
	}

	private static Solution simulation(long seed, Problem targetProblem, int method) {
		Globals.getRandomGenerator().setSeed(seed);
		int popsize = 50;
		IMetaheuristic algorithm = generateAlgorithm(seed, targetProblem, popsize, method);
		// RUN
		for(int i = 0; i < numIter; ++i) {
			algorithm.nextIter();
		}
		return algorithm.getGlobalOptimum();
	}

	private static boolean parseArgs(String[] args) {
		if (args.length <= 0)
			return false;
		if (args[0].equals("-h")) {
			System.out.println("./exec NUM_ITER ID_PROBLEM METHOD NUM_SIMUL SEED");
			System.out.println("\tNUM_ITER: number of iterations. Default: " + numIter);
			System.out.println("\tID_PROBLEM: id of the problem to solve. Range of ids: from 1 to 15. Default: 1");
			System.out.println("\tMETHOD: solving method number. Default: 1");
			System.out.println("\tNUM_SIMUL: number of simulations. Default: " + numSimulations);
			System.out.println("\tSTARTING_SIM_NUM ENDING_SIM_NUM: interval of simulations. Default: 0 " + numSimulations);
			System.out.println("\tSEED: starting seed for simulations. Default: " + startingSeed);
			return false;
		}
		if (args.length <= 4) {
			System.out.println("Not enough arguments. Exit (" + args.length + " < 5)");
			return false;
		}
		numIter = Integer.parseInt(args[0]);
		problem_id = Integer.parseInt(args[1]);
		method_id = Integer.parseInt(args[2]);
		numSimulations = Integer.parseInt(args[3]);
		numSimulationsStart = Integer.parseInt(args[4]);
		numSimulationsEnd = Integer.parseInt(args[5]);
		startingSeed = Long.parseLong(args[6]);
		return true;
	}
	
	public static void main(String[] args) {
		// Parse arguments
		parsedArgs = parseArgs(args);
		if (parsedArgs) {
			numAlgorithms = 1;
			numProblems = 1;
		}
		// Setup simulation
		// Problems
		benchmark = initBenchmark();
		int [] methods = initMethods();

		// Setup seeds
		seed = new long[numSimulations];
		for(int i = numSimulationsStart; i < numSimulationsEnd; ++i) {
			// seed[i] = startingSeed xor i^2
			seed[i] = startingSeed^(i*i);
		}
		double [][][] results = new double[numAlgorithms][numProblems][numSimulations];
		Solution [][][] globalOptimums = new Solution[numAlgorithms][numProblems][numSimulations];
		for(int k = 0; k < methods.length; ++k) {
			if (time_flag)
				clock.start();
			for(int j = 0; j < numProblems; ++j) {
				for(int i = numSimulationsStart; i < numSimulationsEnd; ++i) {
					globalOptimums[k][j][i] = simulation(seed[i], benchmark[j], methods[k]);
					results[k][j][i] = globalOptimums[k][j][i].getFitness();
				}
			}
			if (time_flag) {
				clock.end();
				clock.displayTime();
			}
		}
		
		// Display results
		for(int k = 0; k < methods.length; ++k) {
			for(int j = 0; j < numProblems; ++j) {
				for(int i = numSimulationsStart; i < numSimulationsEnd; ++i) {
					System.out.print(results[k][j][i] + ";");
				}
				System.out.println();
			}
		}
		// Display results
		for(int k = 0; k < methods.length; ++k) {
			for(int j = 0; j < numProblems; ++j) {
				for(int i = numSimulationsStart; i < numSimulationsEnd; ++i) {
					System.out.println(globalOptimums[k][j][i].coords2string());
				}
			}
		}
		// Compute means
		double [][] means = new double[numAlgorithms][numProblems];
		for(int k = 0; k < methods.length; ++k) {
			for(int j = 0; j < numProblems; ++j) {
				for(int i = numSimulationsStart; i < numSimulationsEnd; ++i) {
					means[k][j] += results[k][j][i];
				}
				means[k][j] = means[k][j]/numSimulations;
			}
		}

		// Compute sd
		double [][] sd = new double[numAlgorithms][numProblems];
		for(int k = 0; k < methods.length; ++k) {
			for(int j = 0; j < numProblems; ++j) {
				for(int i = 0; i < numSimulations; ++i) {
					sd[k][j] += (results[k][j][i]-means[k][j])*(results[k][j][i]-means[k][j]);
				}
				sd[k][j] = Math.sqrt(sd[k][j]/numSimulations);
			}
		}
		// Display stats
		/*
		for(int k = 0; k < methods.length; ++k) {
			for(int j = 0; j < numProblems; ++j) {
				System.out.println("Algo " + methods[k] + " Problem " + (problem_id) + " means " + means[k][j] + " standard deviations " + sd[k][j]);
			}
		}
		*/
	}

	
	
}
