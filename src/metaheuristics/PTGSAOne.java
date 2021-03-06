package metaheuristics;

import metaheuristics.tuning.PTAlgorithmOne;

public class PTGSAOne extends PTAlgorithmOne {
	private GSA gsa;
	
	private boolean DEBUG = true;
	
	public PTGSAOne(GSA gsa) {
		super(gsa);
		this.gsa = (GSA) getMetaheuristic();
	}

	@Override
	public void setParam(String paramName, double alfa) {
		if (paramName.equals("alfa")) {
			gsa.setAlfa(alfa);
		}
		else if (paramName.equals("G0")) {
			gsa.setG0(alfa);
		}
		if (DEBUG) {
			GSA meta = (GSA) this.getMetaheuristic();
			System.out.println("Alfa = " + meta.getAlfa());
			System.out.println("G0 = " + meta.getG0());
		}
		
	}

}
