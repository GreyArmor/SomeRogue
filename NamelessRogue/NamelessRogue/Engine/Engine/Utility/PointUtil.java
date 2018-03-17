package Engine.Utility;

import com.jogamp.nativewindow.util.Point;

import java.util.ArrayList;

public class PointUtil {

   static int diagonalDistance(Point p0, Point p1) {
        int dx = p1.getX() - p0.getX(), dy = p1.getY() - p0.getY();
        return Math.max(Math.abs(dx), Math.abs(dy));
    }

    static Point roundPoint(Point p) {
        return new Point(Math.round(p.getX()), Math.round(p.getY()));
    }

   public static ArrayList<Point> getLine(Point p0, Point p1) {
        ArrayList<Point> points =  new ArrayList<>();
        int N = diagonalDistance(p0, p1);
        for (int step = 0; step <= N; step++) {
            float t = N == 0? (float) 0.0 : (float)step / N;
            points.add(lerp_point(p0, p1, t));
        }
        return points;
    }

    static Point lerp_point(Point p0, Point p1, float t) {
        float x = lerp(p0.getX(), p1.getX(), t);
        float y = lerp(p0.getY(), p1.getY(), t);
        return new Point(Math.round(x), Math.round(y));
    }

    static float lerp(float start, float end, float t) {
        return start + t * (end-start);
    }
}
