public class CoordinatePair {
    private Coordinate coordinate1;
    private Coordinate coordinate2;
    private double distance;

    public CoordinatePair(Coordinate coordinate1, Coordinate coordinate2, double distance) {
        this.coordinate1 = coordinate1;
        this.coordinate2 = coordinate2;
        this.distance = distance;
    }

    public Coordinate getCoordinate1() {
        return coordinate1;
    }

    public Coordinate getCoordinate2() {
        return coordinate2;
    }

    public double getDistance() {
        return distance;
    }
}
