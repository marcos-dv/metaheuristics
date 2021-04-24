package utils;

import java.util.Arrays;

import problems.Solution;

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
	
	public static Pair[] getKMinimum(Pair[] pairs) {
		Pair[] kMin = pairs.clone();
		Arrays.sort(kMin);
		return kMin;
	}

	public static Pair[] getKBest(Solution[] sols) {
		Pair kbest[] = new Pair[sols.length];
		for(int i = 0; i < sols.length; ++i) {
			kbest[i] = new Pair(i, sols[i].getFitness());
		}
		Arrays.sort(kbest);
		return kbest;
	}

}
