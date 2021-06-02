package metaheuristics;

import control.Globals;
import control.Messages;
import problems.Problem;
import solutions.Solution;
import utils.Algorithms;
import utils.RandomGenerator;

public class PSO implements IMetaheuristic {
	private Solution [] sols;
	private Solution [] bestSols;
	private double [] v;
	private Solution gSol;
	private Problem targetProblem;
	private int popSize;
	
	public PSO(Problem targetProblem) {
		this.targetProblem = targetProblem;
	}

	public PSO(int popSize, Problem targetProblem) {
		this(targetProblem);
		this.popSize = popSize;
		sols = new Solution[popSize];
	}
	
	public PSO(Solution [] sols, Problem targetProblem) {
		this(targetProblem);
		this.sols = sols.clone();
		this.popSize = sols.length;
	}

	public PSO(PSO pso) {
		this(pso.sols, pso.targetProblem);
	}

	@Override
	public void initPop() {
		if (sols == null) {
			Messages.error("PSO: solutions are not initialized");
			return ;
		}
		// Random init
		for (Solution sol : sols) {
			sol.randomInit();
		}

		// Update optimums
		gSol = new Solution();
		bestSols = new Solution[popSize];
		gSol.setFitness(7777777.777);
		for (int i = 0; i < popSize; ++i) {
			sols[i].randomInit();
			// Update global optimum
			if (sols[i].getFitness() < gSol.getFitness()) {
				gSol = sols[i];
			}
			// Update local optimum for each particle
			bestSols[i] = sols[i];
		}
		
		// Init speed
		// TODO
		v = new double[popSize];
	    RandomGenerator rand = Globals.getRandomGenerator();
		for(int i = 0; i < popSize; ++i) {
			v[i] = rand.randomUniform(targetProblem.getLB()-targetProblem.getUB(),
									targetProblem.getUB()-targetProblem.getLB());
		}
		
	}

	@Override
	public void nextIter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNumIter() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Solution[] getSols() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Solution getGlobalOptimum() {
		return Algorithms.getGlobalOptimum(sols);
	}
	
}
