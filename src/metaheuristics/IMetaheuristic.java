package metaheuristics;

import solutions.Solution;

public interface IMetaheuristic {

	public void initPop();
	
	public void nextIter();
	
	public int getNumIter();

	public Solution[] getSols();

	public void setSols(Solution[] sols);

	public Solution getGlobalOptimum();

}
