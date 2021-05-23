package utils;

public interface Parallelizable {
	public boolean isParallel();
	public void setNumThreads(int n);
	public int getNumThreads();
}
