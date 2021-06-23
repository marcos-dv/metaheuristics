package metaheuristics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import control.Globals;
import control.Messages;
import problems.Problem;
import solutions.Solution;

public abstract class PTAlgorithmOne implements IMetaheuristic {
	private IMetaheuristic metaheuristic;
	private double [] prevFitness;
	private Problem problem;
	private int popsize;

	private boolean DEBUG = false;
	private double curAlfa; // current value for the parameter alfa
	
	private Set<String> paramNames;
	private HashMap<String, EW> parameters;
	
	public PTAlgorithmOne(IMetaheuristic metaheuristic) {
		this.metaheuristic = metaheuristic;
		popsize = metaheuristic.getSols().length;
		if (popsize <= 0) {
			Messages.warning("PTAlgorithm: metaheuristic has no population");
		}
		paramNames = new HashSet<>();
		parameters = new HashMap<>();
	}
	
	public void setNewParam(String paramName, double [] paramRange) {
		paramNames.add(paramName);
		EW ew = new EW(paramName, popsize, paramRange);
		parameters.put(paramName, ew);
	}
	
	public double getCurAlfa(String paramName) {
		return parameters.get(paramName).getCurAlfa();
	}
	
	public void setCurAlfa(String paramName, double curAlfa) {
		parameters.get(paramName).setCurAlfa(curAlfa);
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
	
	public void printParamRange(String paramName) {
		parameters.get(paramName).printParamRange();
	}

	public void printEW(String paramName) {
		parameters.get(paramName).printEW();
	}

	public void printMeans(String paramName) {
		parameters.get(paramName).printMeans();
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

	// Update in the metaheuristic the proper parameter!
	public abstract void setNewParam(String paramName, double alfa);
	
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

	public void updateEW(String paramName, int row) {
		double [] deltaFitness = new double[popsize];
		Solution [] sols = metaheuristic.getSols();
		// Compute differences
		for(int i = 0; i < popsize; ++i) {
			// delta_i = f(x_i) - f(x_i')
			deltaFitness[i] = prevFitness[i] - sols[i].getFitness();
		}
		parameters.get(paramName).updateRow(row, deltaFitness);
	}
	
	public String selectBestParam() {
		double bestMean = Double.NEGATIVE_INFINITY;
		String bestParam = "";
		for (String paramName : paramNames) {
			parameters.get(paramName).selectParamValue();
			if (parameters.get(paramName).getBestMean() > bestMean) {
				bestMean = parameters.get(paramName).getBestMean();
				bestParam = paramName;
			}
		}
		return bestParam;
	}
	
	// Return the index of the best param
	public int selectParamValue(String paramName) {
		return parameters.get(paramName).getBestIndex();
	}
	
	public void nextIter() {
		String paramName = selectBestParam();
		int idx = selectParamValue(paramName);
		curAlfa = parameters.get(paramName).getParamRange()[idx];
		setNewParam(paramName, curAlfa);
		metaheuristic.nextIter();
		if (getNumIter() % 20 == 0)
			printFitness();

		// first iteration
		if (prevFitness == null) {
			updatePrevFitness();
		}
		else { // other iterations
			updateEW(paramName, idx);
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
		for (String paramName : paramNames) {
			return parameters.get(paramName).getTemporalWeight();
		}
		Messages.error("PTAlgorithmMulti: getTemporalWeight() no parameter found");
		return -1;
	}

	public void setTemporalWeight(double temporalWeight) {
		for (String paramName : paramNames) {
			parameters.get(paramName).setTemporalWeight(temporalWeight);
		}
	}
	
	
}
