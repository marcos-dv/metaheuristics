package metaheuristics;

public class PTGSAVariant extends PTAlgorithmVariant {
	private GSA gsa;
	
	private boolean DEBUG = false;
	
	public PTGSAVariant(GSA gsa, double [] range) {
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
