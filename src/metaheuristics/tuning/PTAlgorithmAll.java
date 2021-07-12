package metaheuristics.tuning;

import metaheuristics.IMetaheuristic;

public abstract class PTAlgorithmAll extends PTAlgorithmMulti {

	public PTAlgorithmAll(IMetaheuristic metaheuristic) {
		super(metaheuristic);
	}

	// Update in the metaheuristic the proper parameter!
	public abstract void setParam(String paramName, double alfa);

	// Return the index of the best param
	public int selectParamIndex(String paramName) {
		return getParameters().get(paramName).selectParamIndex();
	}

	@Override
	public void nextIter() {
		// Select the best parameters
		for (String paramName : getParamNames()) {
			int idx = selectParamIndex(paramName);
			double curAlfa = getParameters().get(paramName).getParamRange()[idx];
			setParam(paramName, curAlfa);
		}
		for(int it = 0; it < getConsecutiveIterations(); it++)
			getMetaheuristic().nextIter();
//		if (getNumIter() % 20 == 0)
//			printFitness();

		// first iteration
		if (getPrevFitness() == null) {
			updatePrevFitness();
		}
		else { // other iterations
			// Update EW
			for (String paramName : getParamNames()) {
				int idx = getParameters().get(paramName).getBestIndex();
				updateEW(paramName, idx);
			}
		}
		updatePrevFitness();
	}

}
