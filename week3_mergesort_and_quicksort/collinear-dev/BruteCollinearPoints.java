import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segs = new ArrayList<LineSegment>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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

        for (int p = 0; p < points.length - 3; p++) {
            for (int q = p + 1; q < points.length - 2; q++) {
                for (int r = q + 1; r < points.length - 1; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        Point pp = points[p];
                        Point pq = points[q];
                        Point pr = points[r];
                        Point ps = points[s];
                        double pqSlope = pp.slopeTo(pq);
                        double rsSlope = pr.slopeTo(ps);
                        if (pqSlope != rsSlope) continue;
                        this.segs.add(new LineSegment(pp, ps));
                    }
                }
            }
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
        BruteCollinearPoints bcp = new BruteCollinearPoints(ps);

        StdOut.println(bcp.numberOfSegments());
        LineSegment[] ls = bcp.segments();
        for (int i = 0; i < ls.length; i++) {
            StdOut.println(ls[i]);
            // ls[i].draw();
        }
    }
}
