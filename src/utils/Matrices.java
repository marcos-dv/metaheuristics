package utils;

import control.Messages;

public class Matrices {

	public static double[][] sum(double[][] A, double[][] B) {
		int rowsA = A.length, colsA = A[0].length;
		int rowsB = B.length, colsB = B[0].length;
		if (rowsA != rowsB || colsA != rowsB) {
			Messages.error("matrixSum: Dimensions are different. A = " 
					+ rowsA + "x" + colsA + " and B = " + rowsB + " x " + colsB);
			return A;
		}
		double[][] res = new double[rowsA][colsA];
		for (int i = 0; i < rowsA; i++) {
			for (int j = 0; j < colsB; j++) {
				res[i][j] = A[i][j] + B[i][j];
			}
		}
		return res;
	}

	public static double[][] product(double[][] A, double factor) {
		double[][] res = new double[A.length][A[0].length];
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res[i].length; j++) {
				res[i][j] = A[i][j]*factor;
			}
		}
		return res;
	}

	public static double[][] negate(double[][] A) {
		return product(A, -1);
	}

	public static double[][] diff(double[][] A, double [][] B) {
		return sum(A, negate(B));
	}

	public static double[][] identity(int dimension) {
		double [][] res = new double[dimension][dimension];
		for(int i = 0; i < dimension; ++i)
			res[i][i] = 1;
		return res;
	}

	public static double[][] zero(int dimension) {
		return new double[dimension][dimension];
	}

	public static double[][] matrixProduct(double[][] A, double[][] B) {
		int rowsA = A.length, colsA = A[0].length;
		int rowsB = B.length, colsB = B[0].length;
		if (rowsB != colsA) {
			Messages.error("matrixProduct: rows of B is not equal to cols of A (" 
					+ rowsB + ", " + colsA + ")");
			return A;
		}
		double[][] res = new double[rowsA][colsB];
		for (int i = 0; i < rowsA; i++) {
			for (int j = 0; j < colsB; j++) {
				for (int k = 0; k < colsA; k++)
					res[i][j] += A[i][k] * B[k][j];
			}
		}
		return res;
	}

	public static double[] matrixProduct(double[][] A, double[] v) {
		int rowsA = A.length, colsA = A[0].length;
		int rowsB = v.length;
		if (rowsB != colsA) {
			Messages.error("matrixProduct: rows of v is not equal to cols of A (" 
					+ rowsB + ", " + colsA + ")");
			return v;
		}
		double[] res = new double[rowsA];
		for (int i = 0; i < rowsA; i++) {
			for (int k = 0; k < colsA; k++)
				res[i] += A[i][k] * v[k];
		}
		return res;
	}

	public static double[][] rotationMatrix2D(double angle) {
		double[][] res = new double[2][2];
		res[0][0] = Math.cos(angle);
		res[1][0] = Math.sin(angle);
		res[0][1] = -res[1][0];
		res[1][1] = res[0][0];
		return res;
	}

	public static double[][] rotationMatrix3DX(double angle) {
		double[][] res = new double[3][3];
		double sin = Math.sin(angle);
		double cos = Math.sin(angle);
		res[0][0] = 1;
		res[0][1] = 0;
		res[0][2] = 0;
		res[1][0] = 0;
		res[1][1] = cos;
		res[1][2] = -sin;
		res[2][0] = 0;
		res[2][1] = sin;
		res[2][2] = cos;
		return res;
	}

	public static double[][] rotationMatrix3DY(double angle) {
		double[][] res = new double[3][3];
		double sin = Math.sin(angle);
		double cos = Math.sin(angle);
		res[0][0] = cos;
		res[0][1] = 0;
		res[0][2] = sin;
		res[1][0] = 0;
		res[1][1] = 1;
		res[1][2] = 0;
		res[2][0] = -sin;
		res[2][1] = 0;
		res[2][2] = cos;
		return res;
	}

	public static double[][] rotationMatrix3DZ(double angle) {
		double[][] res = new double[3][3];
		double sin = Math.sin(angle);
		double cos = Math.sin(angle);
		res[0][0] = cos;
		res[0][1] = -sin;
		res[0][2] = 0;
		res[1][0] = sin;
		res[1][1] = cos;
		res[1][2] = 0;
		res[2][0] = 0;
		res[2][1] = 0;
		res[2][2] = 1;
		return res;
	}

}
