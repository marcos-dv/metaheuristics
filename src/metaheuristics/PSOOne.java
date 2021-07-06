package metaheuristics;

/**
 * PSO with autoadjustment of parameters. Only one parameter per iteration
 * @author Marcos Dominguez Velad
 *
 */
public class PSOOne extends PTAlgorithmOne {
	private PSO pso;
	
	private boolean DEBUG = false;
	
	public PSOOne(PSO pso) {
		super(pso);
		this.pso = (PSO) getMetaheuristic();
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
