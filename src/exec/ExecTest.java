package exec;

import problems.*;

public class ExecTest {

	public static void dummyTest1() {
		int dim = 30;
		MinSumProblem problem = new MinSumProblem();
		Solution sol = new Solution(dim, problem);
		sol.randomInit();
		System.out.println(sol);
	}

	public static void dummyTest2() {
		int dim = 5;
		NearestPointProblem problem = new NearestPointProblem();
		Solution [] sols = new Solution[5];
		int i = 1;
		for (Solution solution : sols) {
			solution = new Solution(dim, problem);
			solution.randomInit();
			System.out.println("Solution " + i + " fitness = " + solution.getFitness());
			i++;
		}
		
	}

	public static void main(String[] args) {
		dummyTest2();
	
	}

}
