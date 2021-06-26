package mason;

import commombenchmarks.AckleyProblem;
import commombenchmarks.SphereProblem;
import metaheuristics.IMetaheuristic;
import metaheuristics.MultiSimulatedAnnealing;
import misc.SolverInfo;
import problems.Cec2015Problem;
import problems.CircleProblem;
import problems.PolygonProblem;
import problems.Problem;
import solutions.Solution;
import solutions.SolutionGenerator;
import utils.Polygons;

public class TestMasonMeta {

	static long seed = 1;
//	static Problem targetProblem = new CircleProblem(2, 50);

	private static Problem generateProblem() {
	 	return new PolygonProblem(Polygons.hexagon, false);
	}
	
	private static IMetaheuristic generateMetaheuristic(Problem targetProblem, int popsize, Solution[] sols) {
		MultiSimulatedAnnealing meta = new MultiSimulatedAnnealing(popsize, targetProblem);
		meta.setSols(sols);
		meta.setL(50);
		meta.setStep(0.3);
		return meta;

		/*
		GSA gsa = new GSA(popsize, targetProblem);
		gsa.setMAX_ITER(maxiter);
		gsa.initPop();
		setAlgorithm(gsa);
		*/

	}

	private static Solution[] generateSols(Problem targetProblem, int popsize) {
		double [][] polygon = Polygons.A;
//		Solution[] sols = SolutionGenerator.overCircle(popsize, targetProblem, ratio);
		Solution[] sols = SolutionGenerator.overPolygon(popsize, polygon, true, targetProblem);
		return sols;
	}
	
	private static SolverInfo generateSolverInfo() {
		int popsize = 500;
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
