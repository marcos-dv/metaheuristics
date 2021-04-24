package metaheuristics;

import problems.Solution;

public interface IMetaheuristic {

	public void initPop();
	
	public void nextIter();
	
	public int getNumIter();

	public Solution[] getSols();
	
	public Solution getGlobalOptimum();

}
