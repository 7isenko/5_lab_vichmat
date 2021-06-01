package io.github._7isenko;

import io.github._7isenko.point.Point;
import io.github._7isenko.point.PointFunction;

import java.util.ArrayList;

/**
 * @author 7isenko
 */
public class RungeKuttaMethod {
    private final double METHOD_PRECISION = 4;
    private final double METHOD_DENOMINATOR = Math.pow(2, METHOD_PRECISION) - 1;

    private final PointFunction ode;
    private final double accuracy;

    private ArrayList<Point> points;
    private ArrayList<Point> diffPoints;
    private int iterations;

    public RungeKuttaMethod(PointFunction ode, double accuracy) {
        this.ode = ode;
        this.accuracy = accuracy;
    }

    public void solve(Point startingPoint, double endX) throws RungeKuttaException {
        iterations = 0;
        points = new ArrayList<>();
        diffPoints = new ArrayList<>();

        double h = accuracy;
        points.add(startingPoint);

        Point current = startingPoint;
        while (current.x < endX) {
            iterations++;

            current = points.get(points.size() - 1);
            double x = current.x + h;
            double x_h2 = current.x + h / 2;

            double k1 = h * ode.solve(current);
            double k2 = h * ode.solve(new Point(x_h2, current.y + k1 / 2));
            double k3 = h * ode.solve(new Point(x_h2, current.y + k2 / 2));
            double k4 = h * ode.solve(new Point(x, current.y + k3));

            double y = current.y + (k1 + 2 * k2 + 2 * k3 + k4) / 6;
            if (!Double.isFinite(y)) throw new RungeKuttaException(points, diffPoints, current.x, x);
            Point newPoint = new Point(x, y);
            points.add(newPoint);
            diffPoints.add(new Point(newPoint.y, ode.solve(newPoint)));

            //  if (points.size() > 2) {
            //      double inaccuracy = (newPoint.y - points.get(points.size() - 3).y) / RUNGE_RULE_DENOMINATOR;
            //  }
        }
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public ArrayList<Point> getDiffPoints() {
        return diffPoints;
    }

    public int getIterations() {
        return iterations;
    }
}
