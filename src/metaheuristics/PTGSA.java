package metaheuristics;

public class PTGSA extends PTAlgorithm {
	private GSA gsa;
	
	private boolean DEBUG = false;
	
	public PTGSA(GSA gsa) {
		super(gsa);
		this.gsa = (GSA) getMetaheuristic();
	}

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
