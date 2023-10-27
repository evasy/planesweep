import java.util.ArrayList;

// precondition, the person does not walk backwards and walking on the line
public class Main {

    static ArrayList<Point> test7 = convertToCoordinates(Data.test7);
    static ArrayList<Point> test8 = convertToCoordinates(Data.test8);
    static ArrayList<Point> statuetosuzzallo1 = convertToCoordinates(Data.statuetosuzzallo1);
    static ArrayList<Point> statuetosuzzallo2 = convertToCoordinates(Data.statuetosuzzallo2);
    static ArrayList<Point> test6 = convertToCoordinates(Data.test6);
    static ArrayList<Point> test5 = convertToCoordinates(Data.test5);
    static double[] mainline;
    static double REAL_NUMBER = Double.MIN_VALUE; // explain what it is
    static double CONSTANTOFDISTANCE = 111139.0;

    /**
     * convert the numbers from the data list into coordinate object
     * return an arraylist of Coordinates
     */
    public static ArrayList<Point> convertToCoordinates(double[] list) {
        ArrayList<Point> l = new ArrayList<>();
        for(int i = 0; i < list.length-1; i+=2) {
            Point c = new Point(list[i], list[i+1]);
            l.add(c);
        }
        return l;
    }

    /**
     * convert the Coordinates from the data list into line segment object
     * return an arraylist of LineSegment
     */
    public static ArrayList<LineSegment> convertToLineSegments(ArrayList<Point> list) {
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
     * TODO: find the reference of this equation
     */
    public static double distanceLatLngSq(Point c1, Point c2) {
        double lat1 = c1.getLatitude();
        double lat2 = c2.getLatitude();
        double lon1 = c1.getLongitude();
        double lon2 = c2.getLongitude();
        return Math.pow(((lat1-lat2)*CONSTANTOFDISTANCE),2) + Math.pow(((lon1 - lon2)*CONSTANTOFDISTANCE),2);
    }


    /**
     * find the main line equation y=ax+b
     * return an Array that the first element is slope 'a'
     * and second element is y-intercept 'b'.
     */
    public static double[] findLine(Point c1, Point c2) {
        double[] res = new double[2];
        res[0] = (c1.getLongitude() - c2.getLongitude())/(c1.getLatitude() - c2.getLatitude());
        res[1] = c1.getLongitude() - res[0] * c1.getLatitude();
        return res;
    }

    /**
     * find the intersection of two lines
     * pre-condition is to make sure denominator is not zero
     * return the Coordinate of the intersection
     */
    public static Point findIntersection(double[] line1, double[] line2) {
        double lat = (line2[1] - line1[1])/(line1[0] - line2[0]);
        double lon = line1[0] * lat + line1[1];
        Point intersection = new Point(lat, lon);
        return intersection;
    }


    /**
     * find the perpendicular line of the end point from test A
     * return the line segment of end point and intersection on the perpendicular line
     */
    public static double[] perpendicularLine(Point A, double[] line) {
        double[] res = new double[2];
        res[0] = -1/line[0];
        res[1] = A.getLongitude() + A.getLatitude()/line[0];
        return res;
    }


    /**
     * find the line segment from testB that has an intersection with the perpendicular line
     * and find the intersection, assume there is only one line segment
     * return the coordinates of the intersection
     */
    public static Point intersectionLineSegment(Point A, ArrayList<LineSegment> testB) {
        double x;
        LineSegment lineb = null;
        Point res = null;
        // check if slope equals to zero
        if (mainline[0] <= REAL_NUMBER && mainline[0] >= -REAL_NUMBER) {
            x = A.getLatitude();
            for (LineSegment l : testB) {
                if (l.start.getLatitude() <= x && x <= l.end.getLatitude()) {
                    lineb = l;
                }
            }
            double[] b = findLine(lineb.end, lineb.start);
            res = new Point(x, x*b[0] + b[1]);
        }
        else {
            double[] perpendicular = perpendicularLine(A, mainline);
            for (LineSegment l : testB) {
                double bound1 = perpendicular[0] * l.start.getLatitude() + perpendicular[1];
                double bound2 = perpendicular[0] * l.end.getLatitude() + perpendicular[1];
                double segement_y_start = l.start.getLongitude();
                double segement_y_end = l.end.getLongitude(); // check if the intersection falls between the range
                if (Math.max(bound1, bound2) >= Math.max(segement_y_start, segement_y_end) &&
                        Math.min(bound1, bound2) <= Math.min(segement_y_start, segement_y_end)) {
                    lineb = l;
                } // TODO: add a new check for y values and x values
                // what if there's no precondition, keep the larger one
            }
            if (lineb == null) {
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
    public static double maxDistance(ArrayList<Point> coordinatesA, ArrayList<Point> testB) {
        double maxdist = 0;
        ArrayList<LineSegment> linesegmentsB = convertToLineSegments(testB);
        for (Point a : coordinatesA) {
            Point b = intersectionLineSegment(a, linesegmentsB);
            if (b != null) {
                double temp = distanceLatLngSq(a,b);
                if (temp > maxdist) {
                    maxdist = temp;
                }
            }
        }
        return Math.sqrt(maxdist);
    }

    /**
     * return the average distance of all the distances from A endpoints to test B line segments
     */
    public static double avgDistance(ArrayList<Point> coordinatesA, ArrayList<Point> testB) {
        double meandist = 0;
        ArrayList<LineSegment> linesegmentsB = convertToLineSegments(testB);
        for (Point a : coordinatesA) {
            Point b = intersectionLineSegment(a, linesegmentsB);
            if (b != null) {
                meandist += Math.sqrt(distanceLatLngSq(a,b));
            }
        }
        return meandist/coordinatesA.size();
    }

    public static void main(String[] args) {
        mainline = findLine(test8.get(0), test8.get(test8.size()-1));
        // System.out.println(maxDistance(test5, test6));
        System.out.println(maxDistance(test8, test7));
        System.out.println(avgDistance(test8, test7));
        // System.out.println(avgDistance(statuetosuzzallo1, statuetosuzzallo2));
    }


}