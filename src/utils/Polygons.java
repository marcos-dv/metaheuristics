package utils;

import control.Messages;

public class Polygons {
	public final static double [][] hexagon = new double[][]{
		{-20, -11}, {-20, 11}, {0, 21},
		{20, 11}, {20, -11}, {0, -21},
	};

	public final static double [][] M = new double[][]{
		{-15, -10}, {-15, 10}, {0, 0},
		{15, 10}, {15, -10}
	};

	public final static double [][] K = new double[][]{
		{-5, 10}, {-5, -10}, {-5, 0},
		{5, 10}, {-5, 0}, {5, -10}
	};

	public final static double [][] A = new double[][]{
		{-7, -10}, {0, 15}, {7, -10},
		{21/5, 0}, {-21/5, 0}
	};
	
	public static double[][] polygonTranslation(double [][] polygon, double [] direction) {
		if (polygon == null || polygon[0] == null || direction == null) {
			Messages.error("Polygons: polygonTranslation has null arguments");
			return polygon;
		}
		double [][] movedPolygon = new double[polygon.length][polygon[0].length];
		for(int p = 0; p < polygon.length; ++p) {
			movedPolygon[p] = Geometry.sum(polygon[p], direction);
		}
		return movedPolygon;
	}

	public static double[][] polygonScale(double [][] polygon, double factor) {
		if (polygon == null || polygon[0] == null) {
			Messages.error("Polygons: polygonScale has null arguments");
			return polygon;
		}
		double [][] movedPolygon = new double[polygon.length][polygon[0].length];
		for(int p = 0; p < polygon.length; ++p) {
			movedPolygon[p] = Geometry.mult(polygon[p], factor);
		}
		return movedPolygon;
	}
	
	public static double[][] polygonRotation(double [][] polygon, double angle) {
		if (polygon == null || polygon[0] == null) {
			Messages.error("Polygons: polygonRotation has null arguments");
			return polygon;
		}
		double [][] rotatedPolygon = new double[polygon.length][polygon[0].length];
		double [][] rotationMatrix = Matrices.rotationMatrix2D(angle);
		for(int p = 0; p < polygon.length; ++p) {
			rotatedPolygon[p] = Matrices.matrixProduct(rotationMatrix, polygon[p]);
		}
		return rotatedPolygon;
	}

	public static double[][] square(double side) {
		double halfSide = side*0.5;
		double[][] square = new double[4][2];
		square[0] = new double[]{-halfSide, halfSide};
		square[1] = new double[]{halfSide, halfSide};
		square[2] = new double[]{halfSide, -halfSide};
		square[3] = new double[]{-halfSide, -halfSide};
		return square;
	}
	
}
