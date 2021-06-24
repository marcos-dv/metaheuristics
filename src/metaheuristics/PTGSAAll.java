package metaheuristics;

public class PTGSAAll extends PTAlgorithmAll {
	private GSA gsa;
	
	private boolean DEBUG = true;
	
	public PTGSAAll(GSA gsa) {
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
