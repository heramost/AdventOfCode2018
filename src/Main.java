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
        Double[][] table = new Double[maxDistanceRoundedUp][maxDistanceRoundedUp];
        for (int i = 0; i < maxDistanceRoundedUp; ++i) {
            for (int j = 0; j < maxDistanceRoundedUp; ++j) {
                for (Point point : points) {
                    double currentDistance = getDistance(point, new Point(i, j));
                    Double sumOfDistance = Optional.ofNullable(table[i][j]).orElse(0d) + currentDistance;
                    table[i][j] = sumOfDistance;
                    if (sumOfDistance > 10000) {
                        continue;
                    }
                }
            }
        }

        int c = 0;
        for (int i = 0; i < maxDistanceRoundedUp; ++i) {
            for (int j = 0; j < maxDistanceRoundedUp; ++j) {
                if (table[i][j] < 10000) {
                    ++c;
                }
            }
        }
        System.out.println(c);
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
