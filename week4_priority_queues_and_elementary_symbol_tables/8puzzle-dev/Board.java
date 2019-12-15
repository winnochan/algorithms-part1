import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private int n;
    private int row;
    private int col;
    private int[][] tiles;
    private int h = 0;
    private int m = 0;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    // done
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[this.n][this.n];
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                this.tiles[i][j] = tiles[i][j];

                if (this.tiles[i][j] == 0) {
                    this.row = i;
                    this.col = j;
                }
            }
        }

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (i != this.n - 1 || j != this.n - 1) {
                    if (this.tiles[i][j] != i * this.n + j + 1) {
                        this.h++;
                    }
                }

                if (this.tiles[i][j] == 0) continue;
                int r = (this.tiles[i][j] - 1) / this.n;
                int c = (this.tiles[i][j] - 1) % this.n;
                this.m += Math.abs(r - i) + Math.abs(c - j);
            }
        }
    }

    // string representation of this board
    // done
    public String toString() {
        String result = "";
        for (int i = 0; i < this.n; i++) {
            result += Arrays.toString(this.tiles[i]);
        }
        return result;
    }

    // board dimension n
    // done
    public int dimension() {
        return this.n;
    }

    // done
    // number of tiles out of place
    public int hamming() {
        return this.h;
    }

    // sum of Manhattan distances between tiles and goal
    // done
    public int manhattan() {
        return this.m;
    }

    // is this board the goal board?
    // done
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // does this board equal y?
    // done
    public boolean equals(Object y) {
        if (y == null) return false;
        if (!(y instanceof Board)) return false;
        Board that = (Board) y;

        if (this.n != that.n) return false;
        if (this.h != that.h) return false;
        if (this.m != that.m) return false;

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    // done
    public Iterable<Board> neighbors() {
        int[][] cb = new int[this.n][this.n];
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                cb[i][j] = this.tiles[i][j];
            }
        }
        ArrayList<Board> cp = new ArrayList<Board>();
        if (this.row > 0) {
            cb[row][col - 1] = this.tiles[row][col];
            cb[row][col] = this.tiles[row][col - 1];
            cp.add(new Board(cb));
            cb[row][col - 1] = this.tiles[row][col - 1];
            cb[row][col] = this.tiles[row][col];
        }
        if (this.row < this.n - 1) {
            cb[row][col + 1] = this.tiles[row][col];
            cb[row][col] = this.tiles[row][col + 1];
            cp.add(new Board(cb));
            cb[row][col + 1] = this.tiles[row][col + 1];
            cb[row][col] = this.tiles[row][col];
        }
        if (this.col > 0) {
            cb[row - 1][col] = this.tiles[row][col];
            cb[row][col] = this.tiles[row - 1][col];
            cp.add(new Board(cb));
            cb[row - 1][col] = this.tiles[row - 1][col];
            cb[row][col] = this.tiles[row][col];
        }
        if (this.col < this.n - 1) {
            cb[row + 1][col] = this.tiles[row][col];
            cb[row][col] = this.tiles[row + 1][col];
            cp.add(new Board(cb));
            cb[row + 1][col] = this.tiles[row + 1][col];
            cb[row][col] = this.tiles[row][col];
        }
        return cp;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        Board second = new Board(tiles);
        StdOut.println(initial.equals(second));
    }

}
