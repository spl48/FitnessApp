package seng202.team6.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Route is responsible for the Map Routes.
 * Created by brad on 9/09/16.
 */
public class Route {
    private List<Position> route = new ArrayList<>();

    /**
     * A constructor for the function Route that gets all the points of the activity.
     * @param points A Position parameter that represents route points.
     */
    public Route(ArrayList<Position> points) {
        for (Position point : points) {
            route.add(point);
        }
    }

    /**
     * A function that adds all the route points.
     * @param points A Position parameter that represents route points.
     */
    public Route(Position ...points) {
        Collections.addAll(route, points);
    }

    /**
     * A function that formats the points that the maps uses.
     * @return Returns a String that represents the points of latitude and longitudes.
     */
    public String toJSONArray() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        route.forEach(pos -> stringBuilder.append(
                String.format("{lat: %f, lng: %f}, ", pos.lat, pos.lng)));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}