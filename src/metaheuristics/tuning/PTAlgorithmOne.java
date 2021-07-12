package metaheuristics.tuning;

import metaheuristics.IMetaheuristic;

public abstract class PTAlgorithmOne extends PTAlgorithmMulti {

	public PTAlgorithmOne(IMetaheuristic metaheuristic) {
		super(metaheuristic);
	}
	
	// Update in the metaheuristic the proper parameter!
	public abstract void setParam(String paramName, double alfa);
	
	public String selectBestParam() {
		double bestMean = Double.NEGATIVE_INFINITY;
		String bestParam = "";
		for (String paramName : getParamNames()) {
			getParameters().get(paramName).selectParamIndex();
			if (getParameters().get(paramName).getBestMean() > bestMean) {
				bestMean = getParameters().get(paramName).getBestMean();
				bestParam = paramName;
			}
		}
		return bestParam;
	}
	
	// Return the index of the best param
	public int selectParamIndex(String paramName) {
		return getParameters().get(paramName).getBestIndex();
	}
	
	@Override
	public void nextIter() {
		String paramName = selectBestParam();
		int idx = selectParamIndex(paramName);
		double curAlfa = getParameters().get(paramName).getParamRange()[idx];
		setParam(paramName, curAlfa);
		getMetaheuristic().nextIter();
//		if (getNumIter() % 20 == 0)
//			printFitness();

		// first iteration
		if (getPrevFitness() == null) {
			updatePrevFitness();
		}
		else { // other iterations
			updateEW(paramName, idx);
		}
		updatePrevFitness();
	}
	
}
