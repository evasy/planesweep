import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;


public class Main {

    static double[] test1 = Data.test1;
    static double[] test2 = Data.test2;

    /**
     * return the squared distance between two coordinates
     * using latitude and longitude
     */
    public static double distanceLatLngSq(double lat1, double lat2, double lon1, double lon2) {
        return Math.pow(((lat1-lat2)*111139),2) + Math.pow(((lon1 - lon2)*111139),2);
    }

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
     * find the main line equation y=ax+b and return a and b from
     * return an Array that the first element is a and second element is b
     */
    public double[] mainLine(Coordinate c1, Coordinate c2) {

    }


    /**
     * check if a equals to zero, and find the equation of the end point from test A
     * and its perpendicular line to the main line
     * return the line segment of end point and intersection on the perpendicular line
     */
    public double[] perpendicularLine(Coordinate A, double[] mainline) {

    }

    /**
     * find the line segment which has an intersection with the perpendicular line
     * return the line segment object
     */
    public LineSegment intersectLineSegment() {

    }


    /**
     * find the intersection point
     * return the Coordinate
     */
    public Coordinate[] findIntersection(LineSegment l1, LineSegment l2) {

    }

    /**
     * return the max distance of all the distances from test A endpoints to test B line segments
     */
    public static double maxDistance(double[] testA, double[] testB) {
        double maxdist;
        return Math.sqrt(maxdist);
    }

    public static void main(String[] args) {
        return maxDistance(test1, test2);
    }


}