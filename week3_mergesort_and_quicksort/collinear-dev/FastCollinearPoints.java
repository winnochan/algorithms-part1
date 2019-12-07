import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    // finds all line segments containing 4 or more points
    // private SET<String> sets = new SET<String>();
    final private ArrayList<String> strs = new ArrayList<String>();
    final private ArrayList<LineSegment> segs = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        // O(n^2)
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }
            }
        }

        Point[] copy = points.clone();
        for (int i = 0; i < copy.length; i++) {
            Point base = copy[i];
            Arrays.sort(points, base.slopeOrder());

            // StdOut.println("====================");
            int last = 1;
            for (int j = 2; j < points.length; j++) {
                // StdOut.print(base.slopeTo(points[j - 1]) + " ");
                // StdOut.println(base.slopeTo(points[j]));
                if (base.slopeTo(points[j]) == base.slopeTo(points[j - 1])) {
                    continue;
                }
                if (j - last < 3) {
                    last = j;
                    continue;
                }

                Point[] line = new Point[j - last + 1];
                line[0] = base;
                for (int k = last; k < j; k++) {
                    line[k - last + 1] = points[k];
                }
                Arrays.sort(line);

                LineSegment seg = new LineSegment(line[0], line[j - last]);
                last = j;
                if (strs.contains(seg.toString())) continue;
                segs.add(seg);
                strs.add(seg.toString());
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
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        // StdOut.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
