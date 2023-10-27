public class CoordinatePair {
    private Point coordinate1;
    private Point coordinate2;
    private double distance;

    public CoordinatePair(Point coordinate1, Point coordinate2, double distance) {
        this.coordinate1 = coordinate1;
        this.coordinate2 = coordinate2;
        this.distance = distance;
    }

    public Point getCoordinate1() {
        return coordinate1;
    }

    public Point getCoordinate2() {
        return coordinate2;
    }

    public double getDistance() {
        return distance;
    }
}
