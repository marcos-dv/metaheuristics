package problems.cec2015;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import control.Messages;

public class Cec2015Data {
	// Global array -> [dimension][func_number]
	public static Cec2015Data [][] data;

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
	// TODO check if these are OK!
	public int [] SS;
	
	private void initValues(int dim, int func_num) throws FileNotFoundException {
		int i;
		int j;
		int cf_num = cf_nums[func_num];
		int nx = dim;

		if (!(nx == 2 || nx == 10 || nx == 30 || nx == 50 || nx == 100)) {
			System.out.println("\nError: Test functions are only defined for D=2,10,30,50,100.");
		}

		if (nx == 2 && ((func_num >= 6 && func_num <= 8) || (func_num == 10) || (func_num == 13))) {
			System.out.println("\nError: hf01,hf02,hf03,cf02&cf05 are NOT defined for D=2.\n");
		}

		/* Load Matrix M *****************************************************/
		File fpt = new File("input_data/M_" + func_num + "_D" + nx + ".txt");// * Load M data *
		@SuppressWarnings("resource")
		Scanner input = new Scanner(fpt);
		if (!fpt.exists()) {
			System.out.println("\n Error: Cannot open input file for reading ");
		}

		M = new double[cf_num * nx * nx];

		for (i = 0; i < cf_num * nx * nx; i++) {
			M[i] = input.nextDouble();
		}

		input.close();

		/* Load Bias_value bias *************************************************/

		if (cf_num > 1) {
			fpt = new File("input_data/bias_" + func_num + ".txt");// * Load bias data *
			input = new Scanner(fpt);
			if (!fpt.exists()) {
				System.out.println("\n Error: Cannot open input file for reading ");
			}
			bias = new double[cf_num];
			for (i = 0; i < cf_num; i++) {
				bias[i] = input.nextDouble();
			}
			input.close();
		}

		/* Load shift_data ***************************************************/

		fpt = new File("input_data/shift_data_" + func_num + ".txt");
		input = new Scanner(fpt);
		if (!fpt.exists()) {
			System.out.println("\n Error: Cannot open input file for reading ");
		}

		OShift = new double[cf_num * nx];

		if (func_num < 9) {
			for (i = 0; i < nx * cf_nums[func_num]; i++) {

				OShift[i] = input.nextDouble();
			}
		} else {
			for (i = 0; i < cf_nums[func_num] - 1; i++) {
				for (j = 0; j < nx; j++) {
					OShift[i * nx + j] = input.nextDouble();
				}
				input.nextLine();
			}
			for (j = 0; j < nx; j++) {
				OShift[(cf_nums[func_num] - 1) * nx + j] = input.nextDouble();
			}

		}

		input.close();

		/* Load Shuffle_data *******************************************/

		if (bShuffle[func_num] == 1) {
			fpt = new File("input_data/shuffle_data_" + func_num + "_D" + nx + ".txt");
			input = new Scanner(fpt);
			if (!fpt.exists()) {
				System.out.println("\n Error: Cannot open input file for reading ");
			}
			SS = new int[cf_num * nx];

			for (i = 0; i < cf_num * nx; i++) {
				SS[i] = input.nextInt();
			}
			input.close();
		}

	}
	
	public Cec2015Data(int dim, int func_num) {
		try {
			initValues(dim, func_num);
		} catch (FileNotFoundException e) {
			Messages.error("Error-Cec2015Data: File not found for dimension" + dim + " function " + func_num);
		}
	}
	
}
