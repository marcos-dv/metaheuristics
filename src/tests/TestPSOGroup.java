package tests;

import commombenchmarks.AckleyProblem;
import commombenchmarks.SphereProblem;
import mason.ContinuousMetaSimulationUI;
import metaheuristics.IMetaheuristic;
import metaheuristics.MultiSimulatedAnnealing;
import metaheuristics.PSO;
import metaheuristics.PSOAdaptativeGroups;
import metaheuristics.PSOGroups;
import misc.SolverInfo;
import problems.CircleProblem;
import problems.PolygonProblem;
import problems.Problem;
import solutions.Solution;
import solutions.SolutionGenerator;
import utils.Polygons;

public class TestPSOGroup {

	static long seed = 1;
//	static Problem targetProblem = 

	private static Problem generateProblem() {
		return new AckleyProblem(2, -50, 50);
//	 	return new PolygonProblem(Polygons.hexagon, false);
//		return new CircleProblem(2, 50);
	}
	
	private static IMetaheuristic generateMetaheuristic(Problem targetProblem, int popsize, Solution[] sols) {
		PSOAdaptativeGroups meta = new PSOAdaptativeGroups(sols, targetProblem);
		meta.initPop();
		meta.setGroupSize(2);
		meta.setIterPeriod(10);
		return meta;
	}

	private static Solution[] generateSols(Problem targetProblem, int popsize) {
		double [][] polygon = Polygons.A;
//		Solution[] sols = SolutionGenerator.overCircle(popsize, targetProblem, ratio);
		Solution[] sols = SolutionGenerator.overPolygon(popsize, polygon, true, targetProblem);
		return sols;
	}
	
	private static SolverInfo generateSolverInfo() {
		int popsize = 50;
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
