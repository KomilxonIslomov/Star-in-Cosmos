package uz.isystem.surfacejava.engine;

import java.util.ArrayList;
import java.util.Iterator;

public class Model {
    public static float INITIAL_COORD = -3;
    static float MOTION_SPEED = 0.0000000015f;
    ArrayList<Point> points = new ArrayList<>();

    public void update(long elapsedTime) {
        for (int i = 0; i < 3; i++) {
            points.add(new Point(random(-1, 1), random(-1, 1), INITIAL_COORD,
                    0xff000000 | (int) random(180, 255) << 16 | (int) random(180, 255) << 8 | (int) random(180, 255)));
        }
        for (Point point : points) {
            point.z += elapsedTime * MOTION_SPEED;
        }

        Iterator<Point> iterator = points.iterator();
        while (iterator.hasNext()) {
            Point point = iterator.next();
            if (point.z >= 0) {
                iterator.remove();
            }
        }

    }

    private float random(float from, float to) {
        return (float) (from + (to - from) * Math.random());
    }

    public ArrayList<Point> getPoints() {
        return points;
    }
}
