import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grids;
    private int numOfOpenSites = 0;
    private final WeightedQuickUnionUF wuf;
    private final WeightedQuickUnionUF fuf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.grids = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.grids[i][j] = false;
            }
        }

        this.wuf = new WeightedQuickUnionUF(n * n + 2);
        this.fuf = new WeightedQuickUnionUF(n * n + 1);
    }

    private void check(int row, int col) {
        if (row < 1 || row > this.grids.length) {
            throw new IllegalArgumentException("row -> " + String.valueOf(row));
        }

        if (col < 1 || col > this.grids.length) {
            throw new IllegalArgumentException("col -> " + String.valueOf(col));
        }
    }

    private int calculateId(int row, int col) {
        return (row - 1) * this.grids.length + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        this.check(row, col);
        if (this.isOpen(row, col)) return;

        int uid = this.calculateId(row, col);
        this.grids[row - 1][col - 1] = true;
        this.numOfOpenSites++;
        int n = this.grids.length;
        if (n == 1) {
            this.fuf.union(0, 1);
            this.wuf.union(0, 1);
            this.wuf.union(1, 2);
            return;
        }

        // union left and right first
        if (col > 1 && col <= n && this.isOpen(row, col - 1)) {
            this.fuf.union(uid, this.calculateId(row, col - 1));
            this.wuf.union(uid, this.calculateId(row, col - 1));
        }
        if (col >= 1 && col < n && this.isOpen(row, col + 1)) {
            this.fuf.union(uid, this.calculateId(row, col + 1));
            this.wuf.union(uid, this.calculateId(row, col + 1));
        }

        // then, union top
        if (row == 1) {
            this.fuf.union(uid, 0);
            this.wuf.union(uid, 0);
        }
        else {
            int top = this.calculateId(row - 1, col);
            if (this.isOpen(row - 1, col)) {
                this.fuf.union(uid, top);
                this.wuf.union(uid, top);
            }
        }

        // then, union bottom
        if (row < n) {
            int bot = this.calculateId(row + 1, col);
            if (this.isOpen(row + 1, col)) {
                this.wuf.union(uid, bot);
                this.fuf.union(uid, bot);
            }
        }
        else {
            this.wuf.union(uid, n * n + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        this.check(row, col);

        return this.grids[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        this.check(row, col);

        int uid = this.calculateId(row, col);
        return this.fuf.connected(uid, 0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        int n = this.grids.length;
        return this.wuf.connected(0, n * n + 1);
    }

    // public static void main(String[] args) {
    //
    // }
}
