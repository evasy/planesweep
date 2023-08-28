import javax.sound.sampled.Line;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;


public class Main {

    static ArrayList<Coordinate> test1 = convertToCoordinates(Data.test1);
    static ArrayList<Coordinate> test2 = convertToCoordinates(Data.test2);
    static double[] mainline;

    /**
     * convert the numbers from the data list into coordinate object
     * return an arraylist of Coordinates
     */
    public static ArrayList<Coordinate> convertToCoordinates(double[] list) {
        ArrayList<Coordinate> l = new ArrayList<>();
        for(int i = 0; i < list.length-1; i+=2) {
            Coordinate c = new Coordinate(list[i], list[i+1]);
            l.add(c);
        }
        return l;
    }

    /**
     * convert the Coordinates from the data list into line segment object
     * return an arraylist of LineSegment
     */
    public static ArrayList<LineSegment> convertToLineSegments(ArrayList<Coordinate> list) {
        ArrayList<LineSegment> lineSegments = new ArrayList<>();
        for (int i = 0; i < list.size() - 2; i += 1) {
            LineSegment segment = new LineSegment(list.get(i), list.get(i + 1));
            lineSegments.add(segment);
        }
        return lineSegments;
    }

    /**
     * return the squared distance between two coordinates
     * using latitude and longitude
     */
    public static double distanceLatLngSq(Coordinate c1, Coordinate c2) {
        double lat1 = c1.getLatitude();
        double lat2 = c2.getLatitude();
        double lon1 = c1.getLongitude();
        double lon2 = c2.getLongitude();
        return Math.pow(((lat1-lat2)*111139),2) + Math.pow(((lon1 - lon2)*111139),2);
    }


    /**
     * find the main line equation y=ax+b
     * return an Array that the first element is slope 'a'
     * and second element is y-intercept 'b'.
     */
    public static double[] findLine(Coordinate c1, Coordinate c2) {
        double[] res = new double[2];
        res[0] = (c1.getLongitude() - c2.getLongitude())/(c1.getLatitude() - c2.getLatitude());
        res[1] = c1.getLongitude() - res[0] * c1.getLatitude();
        return res;
    }

    /**
     * find the intersection of two lines
     * return the Coordinate of the intersection
     */
    public static Coordinate findIntersection(double[] line1, double[] line2) {
        double lat = (line2[1] - line1[1])/(line1[0] - line2[0]);
        double lon = line1[0] * lat + line1[1];
        Coordinate intersection = new Coordinate(lat, lon);
        return intersection;
    }


    /**
     * find the equation of the end point from test A
     * and its perpendicular line to the main line
     * return the line segment of end point and intersection on the perpendicular line
     */
    public static double[] perpendicularLine(Coordinate A) {
        double[] res = new double[2];
        res[0] = -1/mainline[0];
        res[1] = A.getLongitude() + A.getLatitude()/mainline[0];
        return res;
    }


    /**
     * find the line segment from testB that has an intersection with the perpendicular line
     * and find the intersection,
     * return the coordinate of the intersection
     */
    public static Coordinate intersectionLineSegment(Coordinate A, ArrayList<LineSegment> testB) {
        double x;
        LineSegment lineb = null;
        Coordinate res = null;
        // check if slope equals to zero
        if (mainline[0] == 0) {
            x = A.getLatitude();
            for (LineSegment l : testB) {
                if (l.start.getLatitude() <= x && x <= l.end.getLatitude()) {
                    lineb = l;
                }
            }
            double[] b = findLine(lineb.end, lineb.start);
            res = new Coordinate(x, x*b[0] + b[1]);
        }
        else {
            double[] perpendicular = perpendicularLine(A);
            for (LineSegment l : testB) {
                double bound1 = perpendicular[0] * l.start.getLatitude() + perpendicular[1];
                double bound2 = perpendicular[0] * l.end.getLatitude() + perpendicular[1];
                double segement_y_start = l.start.getLongitude();
                double segement_y_end = l.end.getLongitude();
                if (Math.max(bound1, bound2) >= Math.max(segement_y_start, segement_y_end) &&
                        Math.min(bound1, bound2) <= Math.min(segement_y_start, segement_y_end)) {
                    lineb = l;
                }
            }
            if(lineb == null) {
                return null;
            }
            double[] b = findLine(lineb.end, lineb.start);
            res = findIntersection(b, perpendicular);
        }

        return res;
    }

    /**
     * return the max distance of all the distances from A endpoints to test B line segments
     */
    public static double maxDistance(ArrayList<Coordinate> coordinatesA, ArrayList<Coordinate> testB) {
        double maxdist = 0;
        ArrayList<LineSegment> linesegmentsB = convertToLineSegments(testB);
        for (Coordinate a : coordinatesA) {
            Coordinate b = intersectionLineSegment(a, linesegmentsB);
            if (b != null) {
                double temp = distanceLatLngSq(a,b);
                if (temp > maxdist) {
                    maxdist = temp;
                }
            }
        }
        return Math.sqrt(maxdist);
    }

    public static void main(String[] args) {
        mainline = findLine(test2.get(0), test1.get(test2.size()-1));
        System.out.println(maxDistance(test1, test2));
        System.out.println(maxDistance(test2, test1));
    }


}