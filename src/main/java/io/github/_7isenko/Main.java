package io.github._7isenko;

import io.github._7isenko.point.Point;
import io.github._7isenko.point.PointFunction;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import static java.lang.Math.*;

/**
 * @author 7isenko
 */
public class Main {

    public static void main(String[] args) {
        blockErrorStream();

        System.out.println("Выберите ОДУ: ");            // pi/2 = 1.56       sqrt(2) = 1.4142
        System.out.println("1: y' + 2xy = 2x^3 * y^3 "); //  y(0) = sqrt(2)    y = 1/sqrt(x^2 + 0.5)     уравнение Бернулли
        System.out.println("2: y' - y/x = x*sin(x) ");   //  y(pi/2) = 1       y = x(2/pi - cos(x))      классный график диффура
        System.out.println("3: y' = y^2 + 1");           //  y(0) = 0          y = tg(x)                 от -pi/2 до pi/2
        System.out.println("4: (13y^3 - x)y' = 4y ");    //  y(5) = 1          x = y^3 + 4 / 4root(y)
        int chosenAlgorithm = InputReader.readIntFromConsole();

        if (chosenAlgorithm > 4 || chosenAlgorithm <= 0) {
            System.out.println("Таких я не знаю!");
            return;
        }

        PointFunction chosenFunction;

        switch (chosenAlgorithm) {
            case 1:
                chosenFunction = point -> 2 * pow(point.x, 3) * pow(point.y, 3) - 2 * point.x * point.y;
                break;
            case 2:
                chosenFunction = point -> point.x * sin(point.x) + point.y / point.x;
                break;
            case 3:
                chosenFunction = point -> pow(point.y, 2) + 1;
                break;
            case 4:
                chosenFunction = point -> 4 * point.y / (13 * pow(point.y, 3) - point.x);
                break;
            default:
                return;
        }

        System.out.println("Введите начальное приближение: ");
        System.out.print("x = ");
        double x = InputReader.readDoubleFromConsole();
        System.out.print("y = ");
        double y = InputReader.readDoubleFromConsole();
        Point starting = new Point(x, y);

        System.out.println("Введите конец интервала: ");
        double endX = InputReader.readDoubleFromConsole();

        System.out.println("Введите точность: ");
        double accuracy = InputReader.readDoubleFromConsole();

        RungeKuttaMethod method = new RungeKuttaMethod(chosenFunction, accuracy);
        ArrayList<Point> points;
        ArrayList<Point> diffPoints;
        try {
            method.solve(starting, endX);
        } catch (RungeKuttaException e) {
            System.out.printf("На отрезке (%f; %f) функция претерпевает разрыв%n", e.getBadX1(), e.getBadX2());
            System.out.println("На графике выведены все данные, которые удалось получить");
        }
        points = method.getPoints();
        diffPoints = method.getDiffPoints();
        GraphBuilder.drawPoints(points, "y=f(x)", "x", "y");
        GraphBuilder.drawPoints(diffPoints, "y'=f(x,y)", "y", "y'");

        //   for (Point point : points) {
        //       System.out.printf("x=%f, y=%f\n", point.x, point.y);
        //   }
    }

    private static void blockErrorStream() {
        try {
            System.setErr(new PrintStream("errors"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
