package io.github._7isenko;

import io.github._7isenko.point.Point;

import java.util.ArrayList;

/**
 * @author 7isenko
 */
public class RungeKuttaException extends ArithmeticException{
    private final ArrayList<Point> points;
    private final ArrayList<Point> diffPoints;
    private final double badX1;
    private final double badX2;

    public RungeKuttaException(ArrayList<Point> points, ArrayList<Point> diffPoints, double badX1, double badX2) {
        this.points = points;
        this.diffPoints = diffPoints;
        this.badX1 = badX1;
        this.badX2 = badX2;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public ArrayList<Point> getDiffPoints() {
        return diffPoints;
    }

    public double getBadX1() {
        return badX1;
    }

    public double getBadX2() {
        return badX2;
    }
}
