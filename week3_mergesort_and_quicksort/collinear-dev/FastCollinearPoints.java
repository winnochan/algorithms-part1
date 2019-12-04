import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    // finds all line segments containing 4 or more points
    ArrayList<LineSegment> segs = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }
            }
        }

        for (int i = 0; i < points.length; i++) {
            Arrays.sort(points, points[i].slopeOrder());
            StdOut.println(Arrays.toString(points));
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return this.segs.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] ls = new LineSegment[this.numberOfSegments()];
        return this.segs.toArray(ls);
    }

    public static void main(String[] args) {
        Point[] ps = new Point[4];
        ps[0] = new Point(1, 1);
        ps[1] = new Point(2, 2);
        ps[2] = new Point(3, 3);
        ps[3] = new Point(4, 4);
        FastCollinearPoints fcp = new FastCollinearPoints(ps);

        StdOut.println(fcp.numberOfSegments());
    }
}
