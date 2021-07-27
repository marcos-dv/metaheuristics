package mason;

import metaheuristics.GSA;
import metaheuristics.IMetaheuristic;
import metaheuristics.MultiSimulatedAnnealing;
import metaheuristics.PSO;
import metaheuristics.PSORingGroups;
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
//		return new AckleyProblem(2);
//		return new Cec2015Problem(1, 2);
		double [][] polygon = Polygons.regularPolygon(3, 30);
		polygon = Polygons.polygonRotation(polygon, Math.PI/5);
		polygon = Polygons.polygonTranslation(polygon, new double[]{-30, 12});
	 	return new PolygonProblem(polygon, false);
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
		
		/*
		MultiSimulatedAnnealing meta = new MultiSimulatedAnnealing(popsize, targetProblem);
		meta.setSols(sols);
		meta.setTemp(500);
		meta.setL(100);
		meta.setAlfa(0.9);
		meta.setStep(0.3);
		return meta;
		*/
		
		PSORingGroups meta = new PSORingGroups(popsize, targetProblem);
		meta.setCoefSpeed(0.7);
		meta.setCoefLocalBest(0.2);
		meta.setCoefGlobalBest(0.3);
		meta.setLearningRate(0.9);
		meta.setRatio(1);
		meta.setSols(sols);
		meta.initPop();
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
		Solution[] sols = SolutionGenerator.insideCircle(popsize, targetProblem, 75);
//		Solution[] sols = SolutionGenerator.randomInit(popsize, targetProblem);
		return sols;
	}
	
	private static SolverInfo generateSolverInfo() {
		int popsize = 300;
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
