package metaheuristics;

import metaheuristics.tuning.PTAlgorithm;

public class PTGSA extends PTAlgorithm {
	private GSA gsa;
	
	private boolean DEBUG = false;
	
	public PTGSA(GSA gsa, double [] range) {
		super(gsa, range);
		this.gsa = (GSA) getMetaheuristic();
	}
	
	@Override
	public void setNewParam(double alfa) {
		gsa.setAlfa(alfa);
	//	setMetaheuristic(gsa);
		if (DEBUG) {
			GSA meta = (GSA) this.getMetaheuristic();
			System.out.println("Alfa = " + meta.getAlfa());
		}
	}

}
