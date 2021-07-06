package demo;

import commombenchmarks.AckleyProblem;
import commombenchmarks.SphereProblem;
import mason.ContinuousMetaSimulationUI;
import metaheuristics.GSA;
import metaheuristics.IMetaheuristic;
import metaheuristics.MultiSimulatedAnnealing;
import metaheuristics.PSO;
import metaheuristics.PSOAll;
import metaheuristics.PSOGroups;
import metaheuristics.PSOOne;
import misc.SolverInfo;
import problems.CircleProblem;
import problems.PolygonProblem;
import problems.Problem;
import solutions.Solution;
import solutions.SolutionGenerator;
import utils.Geometry;
import utils.Polygons;

public class MasonSimulation {

	static long seed = 1;

	private static Problem generateProblem() {
//		return new AckleyProblem(2);
//	 	return new PolygonProblem(Polygons.hexagon, false);
		double[][] regularPolygon = Polygons.regularPolygon(3, 30);
		regularPolygon = Polygons.polygonTranslation(regularPolygon, new double[] {10, 10});
	 	return new PolygonProblem(regularPolygon, false);
//	 	return new PolygonProblem(Polygons.segment, false);
//		return new CircleProblem(2, 30);
	}
	
	private static IMetaheuristic generateMetaheuristic(Problem targetProblem, int popsize, Solution[] sols) {
		/*
		PSO meta = new PSO(sols, targetProblem);
		meta.setLearningRate(0.9);
		meta.setCoefSpeed(0.3);
		meta.setCoefLocalBest(0.3);
		meta.setCoefGlobalBest(0.3);
		meta.initPop();
		return meta;
		*/

		/*
		PSOGroups meta = new PSOGroups(sols, targetProblem);
		meta.setLearningRate(0.9);
		meta.setCoefSpeed(0.3);
		meta.setCoefLocalBest(0.3);
		meta.setCoefGlobalBest(0.3);
		meta.setGroupSize(5);
		meta.initPop();
		return meta;
		*/

		PSOGroups meta = new PSOGroups(sols, targetProblem);
		meta.setLearningRate(0.9);
		meta.setCoefSpeed(0.3);
		meta.setCoefLocalBest(0.3);
		meta.setCoefGlobalBest(0.3);
		meta.setGroupSize(5);
		meta.initPop();
		
		PSOOne meta2 = new PSOOne(meta);
		double [] coefs = new double[]{0.2, 0.3, 0.4, 0.5};
		double [] lrcoefs = new double[]{0.5, 0.9, 1, 1.2};
		meta2.setNewParam("lr", lrcoefs);
		return meta2;
		
		/*
		MultiSimulatedAnnealing meta = new MultiSimulatedAnnealing(popsize, targetProblem);
		meta.setSols(sols);
		meta.setL(50);
		meta.setStep(0.3);
		return meta;
		*/
		/*
		GSA meta = new GSA(popsize, targetProblem);
		meta.setMAX_ITER(10000);
		meta.initPop();
		return meta;
		*/
	}

	private static Solution[] generateSols(Problem targetProblem, int popsize) {
		double [][] polygon = Polygons.A;
//		Solution[] sols = SolutionGenerator.overCircle(popsize, targetProblem, ratio);
		Solution[] sols = SolutionGenerator.overPolygon(popsize, polygon, true, targetProblem);
		return sols;
	}
	
	private static SolverInfo generateSolverInfo() {
		int popsize = 400;
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
