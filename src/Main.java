import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javafx.util.*;

public class Main {
    public static void main(String args[]) {
        List<String> input = readInput();
        List<Point> points = new ArrayList<>();
        input.forEach(str -> {
            String[] parsedInput = str.split(", ");
            points.add(new Point(Integer.valueOf(parsedInput[0]), Integer.valueOf(parsedInput[1])));
        });
        double maxDistance = 0;
        for (int i = 0; i < points.size() - 1; ++i) {
            for (int j = i + 1; j < points.size(); ++j) {
                double currentDistance = points.get(i).distance(points.get(j));
                if (maxDistance < currentDistance) {
                    maxDistance = currentDistance;
                }
            }
        }
        int maxDistanceRoundedUp = (int) Math.ceil(maxDistance);
        Pair<Point, Double>[][] table = new Pair[maxDistanceRoundedUp][maxDistanceRoundedUp];
        for (int i = 0; i < maxDistanceRoundedUp; ++i) {
            for (int j = 0; j < maxDistanceRoundedUp; ++j) {
                for (Point point : points) {
                    double currentDistance = getDistance(point, new Point(i, j));
                    if (table[i][j] == null || table[i][j].getValue() > currentDistance) {
                        table[i][j] = new Pair(point, currentDistance);
                    } else {
                        if (table[i][j] != null && Math.abs(table[i][j].getValue() - currentDistance) < 0.003) {
                            table[i][j] = new Pair(null, currentDistance);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < maxDistanceRoundedUp; ++i) {
            for (int j = 0; j < maxDistanceRoundedUp; ++j) {
                if (i == 0 || i == maxDistanceRoundedUp - 1 || j == 0 || j == maxDistanceRoundedUp - 1) {
                    points.remove(table[i][j].getKey());
                    table[i][j] = null;
                }
            }
        }

        Map<Point, Integer> occurences = new HashMap<>();
        for (int i = 0; i < maxDistanceRoundedUp; ++i) {
            for (int j = 0; j < maxDistanceRoundedUp; ++j) {
                if (table[i][j] != null) {
                    Point point = table[i][j].getKey();
                    if (points.contains(point)) {
                        int count = occurences.getOrDefault(point, 0);
                        occurences.put(point, ++count);
                    }
                }
            }
        }
        System.out.println(occurences.entrySet().stream()
        .max(Comparator.comparingInt(Map.Entry::getValue))
        .orElse(null));
    }

    private static double getDistance(Point point, Point otherPoint) {
        return Math.abs(point.getX() - otherPoint.getX()) + Math.abs(point.getY() - otherPoint.getY());
    }

    public static List<String> readInput() {
        List<String> ret = new ArrayList<>();
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader("input");
            br = new BufferedReader(fr);

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                ret.add(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return ret;
        }
    }
}
