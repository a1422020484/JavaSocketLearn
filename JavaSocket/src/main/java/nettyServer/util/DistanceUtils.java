package nettyServer.util;

import java.util.TreeSet;

public class DistanceUtils {

	private static boolean inArea(Point playerPoint, Point[] areaPoints) {
		int count = 0;
		int length = areaPoints.length;
		for (int i = 0; i < length; ++i) {
			Point point1 = areaPoints[i];
			Point point2 = areaPoints[(i + 1) % length];
			if ((point1.y - point2.y) / (point1.x - point2.x) == (point1.y - playerPoint.y) / (point1.x - playerPoint.x)
					&& between(playerPoint.x, point1.x, point2.x) && between(playerPoint.y, point1.y, point2.y)) {
				return true;
			}
			if (point1.y == point2.y) {
				continue;
			}

			if (playerPoint.y < point1.y) {
				continue;
			}
			if (playerPoint.y > point2.y) {
				continue;
			}
			double x = (playerPoint.y - point1.y) * (point2.x - point1.x) / (point2.y - point1.y) + point1.x;
			if (x > playerPoint.x) {
				++count;
			}
		}
		return count % 2 != 0;
	}

	private static boolean between(double d0, double d1, double d2) {
		double min = d1;
		double max = d2;
		if (d1 > d2) {
			min = d2;
			max = d1;
		}
		return d0 >= min && d0 <= max;
	}

	public static double distance(Point playerPoint, TreeSet<Point> areaPoints) {
		if (areaPoints == null || areaPoints.size() <= 2) {
			throw new IllegalArgumentException();
		}
		
		if (areaPoints.contains(playerPoint)) {
			return 0;
		}

		Point[] _areaPoints = areaPoints.toArray(new Point[areaPoints.size()]);
		if (inArea(playerPoint, _areaPoints)) {
			return 0;
		}

		Point minPoint1 = _areaPoints[0];
		Point minPoint2 = _areaPoints[1];
		double minDistanceSquare1 = distanceSquare(_areaPoints[0], playerPoint);
		double minDistanceSquare2 = distanceSquare(_areaPoints[1], playerPoint);
		for (int i = 2; i < _areaPoints.length; ++i) {
			double distanceSquare = distanceSquare(_areaPoints[i], playerPoint);
			if (distanceSquare < minDistanceSquare1) {
				minPoint1 = _areaPoints[i];
				minDistanceSquare1 = distanceSquare;
			} else if (distanceSquare < minDistanceSquare2) {
				minPoint2 = _areaPoints[i];
				minDistanceSquare2 = distanceSquare;
			}
		}

		Vector2 vectorA = new Vector2(playerPoint, minPoint1);
		Vector2 vectorB = new Vector2(playerPoint, minPoint2);
		Vector2 vectorC = new Vector2(minPoint1, minPoint2);
		double consine = cosine(vectorB, vectorA, vectorC);
		if (consine <= 0) {
			return Math.sqrt(minDistanceSquare1);
		}
		consine = cosine(vectorA, vectorB, vectorC);
		if (consine <= 0) {
			return Math.sqrt(minDistanceSquare2);
		}

		double coefficientA = minPoint2.y - minPoint1.y;
		double coefficientB = minPoint1.x - minPoint2.x;
		double coefficientC = minPoint2.x * minPoint1.y - minPoint1.x * minPoint2.y;

		return Math.abs((coefficientA * playerPoint.x + coefficientB * playerPoint.y + coefficientC)
				/ Math.sqrt(Math.pow(coefficientA, 2) + Math.pow(coefficientB, 2)));
	}

	private static double cosine(Vector2 a, Vector2 b, Vector2 c) {
		return (b.getNormSquare() + c.getNormSquare() - a.getNormSquare()) / (2 * b.getNorm() * c.getNorm());
	}

	private static double distanceSquare(Point point1, Point point2) {
		return Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2);
	}

	public static class Point {

		public double x;

		public double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (this == obj) {
				return true;
			}
			Point point = (Point) obj;
			if (x == point.x && y == point.y) {
				return true;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return (int) (31 * x + y);
		}
	}

	public static class Vector2 {

		public double x;

		public double y;

		private double normSquare;

		private double norm;

		public Vector2(Point point1, Point point2) {
			this.x = point1.x - point2.x;
			this.y = point1.y - point2.y;
			normSquare = Math.pow(x, 2) + Math.pow(y, 2);
			norm = Math.sqrt(normSquare);
		}

		public double getNormSquare() {
			return normSquare;
		}

		public double getNorm() {
			return norm;
		}
	}
}
