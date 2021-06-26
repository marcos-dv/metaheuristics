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

public abstract class PTAlgorithmMulti implements IMetaheuristic {
	private IMetaheuristic metaheuristic;
	private double [] prevFitness;
	private Problem problem;
	private int popsize;

	private boolean DEBUG = false;
	
	private Set<String> paramNames;
	private HashMap<String, EW> parameters;
	
	public PTAlgorithmMulti(IMetaheuristic metaheuristic) {
		this.metaheuristic = metaheuristic;
		popsize = metaheuristic.getSols().length;
		if (popsize <= 0) {
			Messages.warning("PTAlgorithm: metaheuristic has no population");
		}
		setParamNames(new HashSet<>());
		setParameters(new HashMap<>());
	}
	
	public void setNewParam(String paramName, double [] paramRange) {
		getParamNames().add(paramName);
		EW ew = new EW(paramName, popsize, paramRange);
		getParameters().put(paramName, ew);
	}
	
	public double getCurAlfa(String paramName) {
		return getParameters().get(paramName).getCurAlfa();
	}
	
	public void setCurAlfa(String paramName, double curAlfa) {
		getParameters().get(paramName).setCurAlfa(curAlfa);
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
		getParameters().get(paramName).printParamRange();
	}

	public void printEWs() {
		for (String paramName : getParamNames()) {
			System.out.println("Parameter: " + paramName);
			printEW(paramName);
		}
	}

	public void printEW(String paramName) {
		getParameters().get(paramName).printEW();
	}

	public void printMeans() {
		for (String paramName : getParamNames()) {
			System.out.println("Parameter: " + paramName);
			printMeans(paramName);
		}
	}

	public void printMeans(String paramName) {
		getParameters().get(paramName).printMeans();
	}
	
	public void printFitness() {
		if (getPrevFitness() == null) {
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
				+ " vs Prev " + String.format(decimalFormat, getPrevFitness()[i]));
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
		setPrevFitness(null);
	}

	// Update in the metaheuristic the proper parameter!
	public abstract void setParam(String paramName, double alfa);
	
	public void updatePrevFitness() {
		Solution[] sols = metaheuristic.getSols();
		if(popsize != sols.length) {
			Messages.warning("PTAlgorithm: popsize != sols.length in metaheuristic!\n\tpopsize = " 
					+ popsize + " and sols.length = " + sols.length);
		}
		setPrevFitness(new double[popsize]);
		for(int i = 0; i < popsize; ++i) {
			getPrevFitness()[i] = sols[i].getFitness();
		}
	}

	public void updateEW(String paramName, int row) {
		double [] deltaFitness = new double[popsize];
		Solution [] sols = metaheuristic.getSols();
		// Compute differences
		for(int i = 0; i < popsize; ++i) {
			// delta_i = f(x_i) - f(x_i')
			deltaFitness[i] = getPrevFitness()[i] - sols[i].getFitness();
		}
		getParameters().get(paramName).updateRow(row, deltaFitness);
	}
	
	// Return the index of the best param
	public int selectParamIndex(String paramName) {
		return getParameters().get(paramName).getBestIndex();
	}
	
	@Override
	public abstract void nextIter();

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
	public void setSols(Solution[] sols) {
		metaheuristic.setSols(sols);
	}

	@Override
	public Solution getGlobalOptimum() {
		return metaheuristic.getGlobalOptimum();
	}

	public double getTemporalWeight() {
		for (String paramName : getParamNames()) {
			return getParameters().get(paramName).getTemporalWeight();
		}
		Messages.error("PTAlgorithmMulti: getTemporalWeight() no parameter found");
		return -1;
	}

	public void setTemporalWeight(double temporalWeight) {
		for (String paramName : getParamNames()) {
			getParameters().get(paramName).setTemporalWeight(temporalWeight);
		}
	}

	public Set<String> getParamNames() {
		return paramNames;
	}

	public void setParamNames(Set<String> paramNames) {
		this.paramNames = paramNames;
	}

	public HashMap<String, EW> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<String, EW> parameters) {
		this.parameters = parameters;
	}

	public double [] getPrevFitness() {
		return prevFitness;
	}

	public void setPrevFitness(double [] prevFitness) {
		this.prevFitness = prevFitness;
	}
	
	
}
