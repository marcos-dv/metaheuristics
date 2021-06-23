package metaheuristics;

import java.util.ArrayList;
import java.util.Arrays;

import control.Globals;
import control.Messages;
import problems.Problem;
import solutions.Solution;

public class EW {
	private double[] paramRange;
	private double[] meansEW;
	private double[][] EW;
	private Problem problem;
	private int popsize;

	private double temporalWeight = 0;

	private boolean DEBUG = false;
	private int bestIndex;
	private double bestMean;
	private double curAlfa; // current value for the parameter alfa
	private String paramName; // current value for the parameter alfa

	private double SIGN_TOLERANCE = 0;
	private double DELTA_THRESHOLD = 1e-7;

	public EW(String paramName, int popsize, double[] range) {
		setParamName(paramName);
		if (popsize <= 0) {
			Messages.warning("EW: metaheuristic has no population");
		}
		setPopsize(popsize);
		setParamRange(range);
		initEWandMeans();
	}

	private void initEWandMeans() {
		// Initialize EW matrix
		EW = new double[paramRange.length][popsize];
		// Initialize means
		meansEW = new double[paramRange.length];
		for (int i = 0; i < paramRange.length; ++i) {
			for (int j = 0; j < popsize; ++j)
				EW[i][j] = 1;
			meansEW[i] = calcMean(i);
		}
	}

	public EW(int popsize, double[] range) {
		this("Unknown parameter", popsize, range);
	}

	public double[] getParamRange() {
		return paramRange;
	}

	public void setParamRange(double[] paramRange) {
		this.paramRange = paramRange.clone();
		Arrays.sort(this.paramRange);
	}

	public double getCurAlfa() {
		return curAlfa;
	}

	public void setCurAlfa(double curAlfa) {
		this.curAlfa = curAlfa;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public void printParamRange() {
		System.out.println("Parameter domain:");
		for (int i = 0; i < paramRange.length; ++i) {
			System.out.print(paramRange[i] + ' ');
		}
		System.out.println();
	}

	public void printEW() {
		String decimalFormat = "%.2f";
		System.out.println("Elementary Weights Matrix:");
		for (int i = 0; i < EW.length; ++i) {
			for (int j = 0; j < EW[i].length; ++j) {
				System.out.print(String.format(decimalFormat, EW[i][j]) + "  ");
			}
			System.out.println();
		}
	}

	public void printFitness(Solution[] sols, double[] prevFitness) {
		if (prevFitness == null) {
			System.out.println("Previous fitness is not updated yet");
			return;
		}
		String decimalFormat = "%.2f";
		String coordsFormat = "%.3f";

		System.out.println("Current fitness vs Old fitness:");
		for (int i = 0; i < popsize; ++i) {
			// Fitness vs
			System.out.print(i + ". Now " + String.format(decimalFormat, sols[i].getFitness()) + " vs Prev "
					+ String.format(decimalFormat, prevFitness[i]));
			// Coords
			double[] coords = sols[i].getCoords();
			System.out.print(" \t ( ");
			for (int d = 0; d < coords.length; ++d) {
				System.out.print(String.format(coordsFormat, coords[d]) + " , ");
			}
			System.out.println(" )");
		}
	}

	public void printMeans() {
		System.out.println("Means:");
		for (int i = 0; i < meansEW.length; ++i) {
			System.out.println(i + ". Mean = " + meansEW[i]);
		}
	}

	public double[][] getEW() {
		return EW;
	}

	public void setEW(double[][] eW) {
		EW = eW.clone();
	}

	public int getPopsize() {
		return popsize;
	}

	public void setPopsize(int popsize) {
		this.popsize = popsize;
	}

	// Return the index of the best param
	public int selectParamValue() {
		setBestMean(Double.NEGATIVE_INFINITY);
		ArrayList<Integer> bestIndexes = new ArrayList<>();
		for (int i = 0; i < paramRange.length; ++i) {
			// better value
			if (meansEW[i] > getBestMean()) {
				bestIndexes.clear();
				bestIndexes.add(i);
				setBestMean(meansEW[i]);
			}
			// new candidate
			else if (meansEW[i] == getBestMean()) {
				bestIndexes.add(i);
			}
		}
		if (bestIndexes.size() == 0) {
			Messages.warning("EW: not best parameter value found");
		}
		/*
		 * if (DEBUG) { System.out.println("# Best indexes and values:"); for (Integer
		 * index: bestIndexes) { System.out.println(index + " -> " + paramRange[index]);
		 * } }
		 */
		int idx = 0;
		if (bestIndexes.size() > 1) // Avoid random sample almost always!
			idx = Globals.getRandomGenerator().randomInt(0, bestIndexes.size());
		bestIndex = bestIndexes.get(idx);
		return bestIndex;
	}

	public double calcMean(int row) {
		double mean = 0;
		for (int j = 0; j < popsize; ++j) {
			mean += EW[row][j];
		}
		return mean / (double) popsize;
	}

	public void updateCaseAllGood(int row, double[] delta) {
		if (DEBUG)
			System.out.println("# Update case good");
		for (int j = 0; j < popsize; ++j) {
			EW[row][j] = temporalWeight * EW[row][j] + (1 - temporalWeight) * Math.tanh(delta[j] / popsize);
		}
	}

	public void updateCaseAllBad(int row, double[] delta) {
		if (DEBUG)
			System.out.println("# Update case bad");
		// Negative weights
		for (int j = 0; j < popsize; ++j) {
			EW[row][j] = temporalWeight * EW[row][j] + (1 - temporalWeight) * Math.tanh(delta[j] / popsize);
		}
	}

	public void updateCaseMixed(int row, double[] delta) {
		if (DEBUG)
			System.out.println("# Update case mix");
		for (int j = 0; j < popsize; ++j) {
			EW[row][j] = temporalWeight * EW[row][j] + (1 - temporalWeight) * Math.abs(Math.tanh(delta[j] / popsize));
		}
	}

	public void updateCaseStagnition(int row, double[] delta) {
		if (DEBUG)
			System.out.println("# Update case stagnition");
		for (int j = 0; j < popsize; ++j) {
			EW[row][j] = temporalWeight * EW[row][j] + (1 - temporalWeight) * (-2);
		}
	}

	public void updateRow(int row, double[] delta) {
		assert (popsize == delta.length);
		int cntPlus = 0;
		int cntMinus = 0;
		int cntZero = 0;
		int cntStagnition = 0;

		// SIGN_TOLERANCE ~ 0
		// Analysis of the landscape
		for (int i = 0; i < popsize; ++i) {
			// Magnitude
			if (Math.abs(delta[i]) <= DELTA_THRESHOLD) {
				cntStagnition++;
			}
			// Sign
			if (delta[i] > SIGN_TOLERANCE) {
				cntPlus++;
			} else if (delta[i] < -SIGN_TOLERANCE) {
				cntMinus++;
			} else if (Math.abs(delta[i]) <= SIGN_TOLERANCE) {
				cntZero++;
			}
		}
		// Update
		// Case 6 -> Stagnition
		if (cntStagnition == popsize) {
			updateCaseStagnition(row, delta);
		}
		// Cases 1 and 2, improvement
		else if (cntMinus == 0) {
			updateCaseAllGood(row, delta);
		}
		// Cases 3 and 4, bad improvement
		else if (cntPlus + cntZero == 0) {
			updateCaseAllBad(row, delta);
		}
		// Cases 5, exploration
		else if ((cntPlus + cntZero > 0) && (cntMinus > 0)) {
			updateCaseMixed(row, delta);
		} else {
			Messages.warning("EW: Unknown landscape case");
		}
		// Update mean
		meansEW[row] = calcMean(row);
	}

	public double getTemporalWeight() {
		return temporalWeight;
	}

	public void setTemporalWeight(double temporalWeight) {
		this.temporalWeight = temporalWeight;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public int getBestIndex() {
		return bestIndex;
	}

	public void setBestIndex(int bestIndex) {
		this.bestIndex = bestIndex;
	}

	public double getBestMean() {
		return bestMean;
	}

	public void setBestMean(double bestMean) {
		this.bestMean = bestMean;
	}

}
