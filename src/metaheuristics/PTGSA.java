package metaheuristics;

public class PTGSA extends PTAlgorithm {
	private GSA gsa;
	public PTGSA(GSA gsa_) {
		super(gsa_);
		gsa = (GSA) getMetaheuristic();
	}

	@Override
	public void setNewParam(double alfa) {
		gsa.setAlfa(alfa);
		setMetaheuristic(gsa);
	}
	
}
