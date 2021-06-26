package metaheuristics;

import control.Globals;
import control.Messages;
import problems.Problem;
import solutions.Solution;
import solutions.SolutionGenerator;
import utils.Algorithms;

public class MultiSimulatedAnnealing implements IMetaheuristic {

	// TODO implement termination criteria + tuning initial temperature!
	
	private boolean DEBUG = false;
	
	private Solution [] sols;
	private Solution globalBest;
	private Problem targetProblem;
	private int numIter;

	private double alfa;
	private double L;
	private double Temp;
	private double step; // for neighbours!

	public MultiSimulatedAnnealing(Problem targetProblem) {
		this.targetProblem = targetProblem;
		if (targetProblem.getDim() <= 0) {
			Messages.warning("GSA: Target problem dimension equals 0.");
		}
		numIter = 0;
		// Default Hyperparameters
		alfa = 0.9;
		L = 500;
		Temp = 50000;
		step = 1;
	}

	public MultiSimulatedAnnealing(int popSize, Problem targetProblem) {
		this(targetProblem);
		sols = new Solution[popSize];
	}
	
	public MultiSimulatedAnnealing(Solution [] sols, Problem targetProblem) {
		this(targetProblem);
		this.sols = sols.clone();
	}

	@Override
	public void initPop() {
		// Init population
		if (sols == null) {
			Messages.error("GSA: solutions are not initialized");
			return ;
		}
		// Random init
		for (int i = 0; i < sols.length; ++i) {
			sols[i] = new Solution(targetProblem);
			sols[i].randomInit();
		}
		// Update best solution
		updateGlobalBest();
	}

	public void updateGlobalBest() {
		Solution curIterationBest = Algorithms.getGlobalOptimum(sols);
		if ((globalBest == null)
				|| (curIterationBest.getFitness() < globalBest.getFitness())) {
			globalBest = new Solution(curIterationBest);
		}
	}
	
	// Should jump from s to ss?
	public boolean diffFeasible(Solution s, Solution ss) {
		double delta = ss.getFitness()-s.getFitness();
		double prob = Globals.getRandomGenerator().randomUniform(0, 1);
		if (DEBUG) {
			System.out.println(Math.exp(-delta/Temp) + " vs " + prob);
			System.out.println(Temp);
		}
		return Math.exp(-delta/Temp) > prob;
	}
	
	public void update(int idx) {
		Solution sol_ = SolutionGenerator.getNeighbour(sols[idx], step);
		if (diffFeasible(sols[idx], sol_)) {
			sols[idx] = new Solution(sol_);
		}
	}
	
	@Override
	public void nextIter() {
		for(int i = 0; i < sols.length; ++i) {
			update(i);
		}
		
		// Decrease the temperature in multiples of L
		if (numIter % L == 0) {
			Temp *= alfa;
		}
		
		numIter++;
	}

	@Override
	public int getNumIter() {
		return numIter;
	}

	public void setNumIter(int numIter) {
		this.numIter = numIter;
	}

	public double getAlfa() {
		return alfa;
	}

	public void setAlfa(double alfa) {
		this.alfa = alfa;
	}

	public double getL() {
		return L;
	}

	public void setL(double l) {
		L = l;
	}

	public double getTemp() {
		return Temp;
	}

	public void setTemp(double temp) {
		Temp = temp;
	}

	public double getStep() {
		return step;
	}

	public void setStep(double step) {
		this.step = step;
	}

	public void setSols(Solution[] sols) {
		this.sols = sols;
	}

	@Override
	public Solution[] getSols() {
		return sols;
	}

	@Override
	public Solution getGlobalOptimum() {
		updateGlobalBest();
		return globalBest;
	}

}
