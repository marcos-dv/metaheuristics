package metaheuristics;

import java.util.ArrayList;
import java.util.Arrays;

import control.Globals;
import control.Messages;
import problems.Problem;
import solutions.Solution;

public abstract class PTAlgorithmVariant implements IMetaheuristic {
	private IMetaheuristic metaheuristic;
	private double [] prevFitness;
	private Problem problem;
	private int popsize;

	private boolean DEBUG = false;
	private double curAlfa; // current value for the parameter alfa
	
	private EW ew;
	
	public PTAlgorithmVariant(IMetaheuristic metaheuristic, double [] range) {
		this.metaheuristic = metaheuristic;
		popsize = metaheuristic.getSols().length;
		if (popsize <= 0) {
			Messages.warning("PTAlgorithm: metaheuristic has no population");
		}
		ew = new EW(popsize, range);
	}

	public double getCurAlfa() {
		return curAlfa;
	}

	public void setCurAlfa(double curAlfa) {
		this.curAlfa = curAlfa;
		setNewParam(curAlfa);
	}

	public IMetaheuristic getMetaheuristic() {
		return metaheuristic;
	}

	public void setMetaheuristic(IMetaheuristic metaheuristic) {
		this.metaheuristic = metaheuristic;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	
	public void printParamRange() {
		ew.printParamRange();
	}

	public void printEW() {
		ew.printEW();
	}

	public void printMeans() {
		ew.printMeans();
	}
	
	public void printFitness() {
		if (prevFitness == null) {
			System.out.println("Previous fitness is not updated yet");
			return ;
		}
		String decimalFormat = "%.2f";
		String coordsFormat = "%.3f";
		Solution[] sols = metaheuristic.getSols();
		
		System.out.println("Current fitness vs Old fitness:");
		for(int i = 0; i < popsize; ++i) {
			// Fitness vs
			System.out.print(i 
				+ ". Now " + String.format(decimalFormat, sols[i].getFitness())
				+ " vs Prev " + String.format(decimalFormat, prevFitness[i]));
			// Coords
			double [] coords = sols[i].getCoords();
			System.out.print(" \t ( ");
			for(int d = 0; d < coords.length; ++d) {
				System.out.print(String.format(coordsFormat, coords[d]) + " , ");
			}
			System.out.println(" )");
		}
	}

	public int getPopsize() {
		return popsize;
	}

	public void setPopsize(int popsize) {
		this.popsize = popsize;
	}
	
	@Override
	public void initPop() {
		metaheuristic.initPop();
		// Initialize previous fitness for each iteration
		prevFitness = null;
	}

	// Return the index of the best param
	public int selectParamIndex() {
		return ew.selectParamIndex();
	}
	
	// Update in the metaheuristic the proper parameter!
	public abstract void setNewParam(double alfa);
	
	public void updatePrevFitness() {
		Solution[] sols = metaheuristic.getSols();
		if(popsize != sols.length) {
			Messages.warning("PTAlgorithm: popsize != sols.length in metaheuristic!\n\tpopsize = " 
					+ popsize + " and sols.length = " + sols.length);
		}
		prevFitness = new double[popsize];
		for(int i = 0; i < popsize; ++i) {
			prevFitness[i] = sols[i].getFitness();
		}
	}

	public void updateEW(int row) {
		double [] deltaFitness = new double[popsize];
		Solution [] sols = metaheuristic.getSols();
		// Compute differences
		for(int i = 0; i < popsize; ++i) {
			// delta_i = f(x_i) - f(x_i')
			deltaFitness[i] = prevFitness[i] - sols[i].getFitness();
		}
		ew.updateRow(row, deltaFitness);
	}
	
	public void nextIter() {
		int idx = selectParamIndex();
		curAlfa = ew.getParamRange()[idx];
		setNewParam(curAlfa);
		metaheuristic.nextIter();
//		if (getNumIter() % 20 == 0)
//			printFitness();

		// first iteration
		if (prevFitness == null) {
			updatePrevFitness();
		}
		else { // other iterations
			updateEW(idx);
		}
		updatePrevFitness();
	}

	public void run(int numiter) {
		initPop();
		for(int i = 0; i < numiter; ++i) {
			nextIter();
		}
	}

	@Override
	public int getNumIter() {
		return metaheuristic.getNumIter();
	}

	@Override
	public Solution[] getSols() {
		return metaheuristic.getSols();
	}

	@Override
	public Solution getGlobalOptimum() {
		return metaheuristic.getGlobalOptimum();
	}

	public double getTemporalWeight() {
		return ew.getTemporalWeight();
	}

	public void setTemporalWeight(double temporalWeight) {
		ew.setTemporalWeight(temporalWeight);
	}
	
	
}
