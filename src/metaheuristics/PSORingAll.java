package metaheuristics;

import metaheuristics.tuning.PTAlgorithmAll;

public class PSORingAll extends PTAlgorithmAll {
	private PSORingGroups pso;
	
	private boolean DEBUG = false;
	
	public PSORingAll(PSORingGroups pso) {
		super(pso);
		this.pso = (PSORingGroups) getMetaheuristic();
	}

	@Override
	public void setParam(String paramName, double alfa) {
		if (paramName.equals("v")) {
			pso.setCoefSpeed(alfa);
		}
		else if (paramName.equals("local")) {
			pso.setCoefLocalBest(alfa);
		}
		else if (paramName.equals("global")) {
			pso.setCoefGlobalBest(alfa);
		}
		else if (paramName.equals("lr")) {
			pso.setLearningRate(alfa);
		}
		if (DEBUG) {
			PSO meta = (PSO) this.getMetaheuristic();
			System.out.println("v = " + meta.getCoefSpeed());
			System.out.println("local = " + meta.getCoefLocalBest());
			System.out.println("global = " + meta.getCoefGlobalBest());
			System.out.println("lr = " + meta.getLearningRate());
		}
		
	}

}
