package utils;

// Keep track of indexes when sorting an array
public class Pair implements Comparable<Pair> {
    public int index;
    public double value;
    
    public Pair(int index, double value) {
       this.index = index;
       this.value = value;
    }
    
	@Override
	// (a, b) < (c, d) <=> b < d
	public int compareTo(Pair other) {
	    return Double.valueOf(this.value).compareTo(other.value);
	}
}
