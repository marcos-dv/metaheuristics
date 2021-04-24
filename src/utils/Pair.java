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
	
	public static Pair[] sortIdxPairs(Pair[] pairs) {
		if (pairs == null) {
			System.out.println("Warning-Pair: getKMinimum pairs are not initialized");
		}
		Pair[] kMin = pairs.clone();
		Arrays.sort(kMin);
		return kMin;
	}

	public static Pair[] sortIdxSolutions(Solution[] sols) {
		if (sols == null) {
			System.out.println("Warning-Pair: getKBest solutions are not initialized");
		}
			
		Pair kbest[] = new Pair[sols.length];
		for(int i = 0; i < sols.length; ++i) {
			if (sols[i] == null)
				continue;
			kbest[i] = new Pair(i, sols[i].getFitness());
		}
		return sortIdxPairs(kbest);
	}
	
	public static Solution[] getKbest(Solution [] sols, int k) {
		Pair[] idx = sortIdxSolutions(sols);
		Solution[] kbest = new Solution[k];
		for(int i = 0; i < k; ++i) {
			int kIndex = idx[i].index;
			kbest[i] = new Solution(sols[kIndex]);
		}
		return kbest;
	}


}
