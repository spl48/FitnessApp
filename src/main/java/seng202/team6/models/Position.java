package seng202.team6.models;

/**
 * Stores the latitude and longitude of a point
 * of an activity
 */
public class Position {
    /**
     * Value representing the latitude of a point
     */
    public double lat;
    /**
     * Value representing the longitude of a point
     */
    public double lng;

    /**
     * Sets a point in an activities longitude
     * and latitude
     * @param lat the points latitude
     * @param lng the points longitude
     */
    public Position(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}