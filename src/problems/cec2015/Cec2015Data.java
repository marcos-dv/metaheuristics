package problems.cec2015;

public class Cec2015Data {
	// Global array
	public static Cec2015Data [] data;

	// Private atributes
	private static final double INF = 1.0e99;
	private static final double EPS = 1.0e-14;
	private static final double E  = 2.7182818284590452353602874713526625;
	private static final double PI = 3.1415926535897932384626433832795029;
	//                   1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
	private static int[] cf_nums =  {0, 1,1,1,1,1,1,1,1,3,3, 5, 5, 5, 7, 10};
	private static int[] bShuffle = {0, 0,0,0,0,0,1,1,1,0,1, 0, 0, 1, 0, 0};

	// Values to read
	public double [] M;
	public double [] bias;
	public double [] OShift;
	public int [] SS;
	
	private void initValues(int func_num) {
		int cf_num,i,j;
		cf_num=cf_nums[func_num];
		// TODO
	}
	
	public Cec2015Data(int func_num) {
		initValues(func_num);
	}
	
}
