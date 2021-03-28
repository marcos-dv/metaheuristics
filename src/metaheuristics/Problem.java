package metaheuristics;

public interface Problem {
	public double fitness(Solution sol);
	public double getUB();
	public double getLB();
	
}
