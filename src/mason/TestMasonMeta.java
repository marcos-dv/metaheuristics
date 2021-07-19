package mason;

import metaheuristics.GSA;
import metaheuristics.IMetaheuristic;
import metaheuristics.MultiSimulatedAnnealing;
import metaheuristics.PSO;
import misc.SolverInfo;
import problems.Cec2015Problem;
import problems.CircleProblem;
import problems.PolygonProblem;
import problems.Problem;
import problems.commombenchmarks.AckleyProblem;
import problems.commombenchmarks.SphereProblem;
import solutions.Solution;
import solutions.SolutionGenerator;
import utils.Polygons;

public class TestMasonMeta {

	static long seed = 1;
//	static Problem targetProblem = new CircleProblem(2, 50);

	private static Problem generateProblem() {
		return new AckleyProblem(2);
//		return new Cec2015Problem(2, 2);
//	 	return new PolygonProblem(Polygons.regularPolygon(5, 25), false);
	}
	
	private static IMetaheuristic generateMetaheuristic(Problem targetProblem, int popsize, Solution[] sols) {
		/*
		PSO meta = new PSO(sols, targetProblem);
		meta.initPop();
		meta.setLearningRate(1);
		meta.setCoefSpeed(.5);
		meta.setCoefLocalBest(.3);
		meta.setCoefGlobalBest(.2);
		return meta;
		*/
		
		
		MultiSimulatedAnnealing meta = new MultiSimulatedAnnealing(popsize, targetProblem);
		meta.setSols(sols);
		meta.setTemp(9000);
		meta.setL(50);
		meta.setAlfa(0.9);
		meta.setStep(0.3);
		return meta;
		
		
		/*
		GSA meta = new GSA(popsize, targetProblem);
		meta.setMAX_ITER(10000);
		meta.setAlfa(20);
		meta.initPop();
		return meta;
		*/
	}

	private static Solution[] generateSols(Problem targetProblem, int popsize) {
//		double [][] polygon = Polygons.A;
//		Solution[] sols = SolutionGenerator.overCircle(popsize, targetProblem, ratio);
		Solution[] sols = SolutionGenerator.randomInit(popsize, targetProblem);
		return sols;
	}
	
	private static SolverInfo generateSolverInfo() {
		int popsize = 200;
		Problem targetProblem = generateProblem();
		Solution [] sols = generateSols(targetProblem, popsize);
		IMetaheuristic algorithm = generateMetaheuristic(targetProblem, popsize, sols);
		return new SolverInfo(algorithm, targetProblem, popsize);
	}
	
	public static void startContinuousSimulationUI() {
		int w = 250;
		int h = 160;
		double discretization = 1.0;
		SolverInfo solverInfo = generateSolverInfo();
		ContinuousMetaSimulationUI simulation = new ContinuousMetaSimulationUI(w, h, discretization, seed, solverInfo);
		simulation.createController();
	}
	
	public static void main(String[] args) {
		startContinuousSimulationUI();
	}

}
