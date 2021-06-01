package io.github._7isenko;

import io.github._7isenko.point.Point;
import org.knowm.xchart.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author 7isenko
 */
public class GraphBuilder {

    public static SwingWrapper<XYChart> currentWrapper;

    public static void drawPoints(ArrayList<Point> points, String title, String xAxis, String yAxis) {
        ArrayList<ArrayList<Double>> split = excludePoints(points);
        ArrayList<Double> xp = split.get(0);
        ArrayList<Double> yp = split.get(1);

        XYChart chart = new XYChartBuilder().width(600).height(400).title(title).xAxisTitle(xAxis).yAxisTitle(yAxis).build();
        chart.getStyler().setMarkerSize(5);
        chart.getStyler().setZoomEnabled(true);
        chart.addSeries("points", xp, yp).setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line).setMarkerColor(Color.GREEN);
        currentWrapper = new SwingWrapper<>(chart);
        currentWrapper.displayChart().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private static ArrayList<ArrayList<Double>> excludePoints(List<Point> points) {
        ArrayList<ArrayList<Double>> rez = new ArrayList<>();
        rez.add(new ArrayList<>());
        rez.add(new ArrayList<>());

        for (Point point : points) {
            rez.get(0).add(point.x);
            rez.get(1).add(point.y);
        }
        return rez;
    }
}
