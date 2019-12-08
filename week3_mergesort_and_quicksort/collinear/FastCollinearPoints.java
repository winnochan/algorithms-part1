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
        if (points.length < 4) {
            throw new IllegalArgumentException();
        }

        // O(n^2)
        Point[] safe = new Point[points.length];
        Point[] copy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null) {
                    throw new IllegalArgumentException();
                }
                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }
            }
            safe[i] = points[i];
            copy[i] = points[i];
        }

        points = safe;
        for (int i = 0; i < copy.length; i++) {
            Point base = copy[i];
            Arrays.sort(points, base.slopeOrder());

            int left = 1, right = 2;
            for (; right < points.length; right++) {
                if (base.slopeTo(points[right]) == base.slopeTo(points[right - 1])) {
                    continue;
                }
                if (right - left < 3) {
                    left = right;
                    continue;
                }

                Point[] line = new Point[right - left + 1];
                line[0] = base;
                for (int k = left; k < right; k++) {
                    line[k - left + 1] = points[k];
                }
                Arrays.sort(line);

                LineSegment seg = new LineSegment(line[0], line[right - left]);
                // StdOut.println(seg.toString());
                left = right;
                if (!strs.contains(seg.toString())) {
                    segs.add(seg);
                    strs.add(seg.toString());
                }
            }
            if (right - left >= 3) {
                Point[] line = new Point[right - left + 1];
                line[0] = base;
                for (int k = left; k < right; k++) {
                    line[k - left + 1] = points[k];
                }
                Arrays.sort(line);

                LineSegment seg = new LineSegment(line[0], line[right - left]);
                // StdOut.println(seg.toString());
                left = right;
                if (!strs.contains(seg.toString())) {
                    segs.add(seg);
                    strs.add(seg.toString());
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
