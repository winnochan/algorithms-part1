import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // perform independent trials on an n-by-n grid
    private final double[] samples;
    private double confidence = 1.96;
    private double meanCache = -1;
    private double stddevCache = -1;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.samples = new double[trials];

        for (int i = 0; i < trials; i++) {
            int[] ids = new int[n * n];
            for (int j = 0; j < ids.length; j++) {
                ids[j] = j;
            }
            int num = 0;

            StdRandom.shuffle(ids);
            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                int id = ids[num++];
                int row = id / n + 1;
                int col = id % n + 1;
                p.open(row, col);
            }

            this.samples[i] = (double) p.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        if (this.meanCache < 0) {
            this.meanCache = StdStats.mean(this.samples);
        }
        return this.meanCache;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (this.stddevCache < 0) {
            this.stddevCache = StdStats.stddev(this.samples);
        }
        return this.stddevCache;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - this.confidence * this.stddev() / Math.sqrt(this.samples.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + this.confidence * this.stddev() / Math.sqrt(this.samples.length);
    }

    public static void main(String[] args) {
        if (args.length < 2) return;

        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);

        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        double lo = ps.confidenceLo();
        double hi = ps.confidenceHi();
        StdOut.printf("95%% confidence interval = [%f, %f]%n", lo, hi);
    }
}
