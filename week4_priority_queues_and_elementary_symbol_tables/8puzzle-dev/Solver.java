import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {
    private class Node implements Comparable<Node> {
        private Board board;
        private Node prev;

        Node(Board b, Node p) {
            this.board = b;
            this.prev = p;
        }

        Board getBoard() {
            return this.board;
        }

        Node getPrev() {
            return this.prev;
        }

        public int compareTo(Node that) {
            return this.board.manhattan() - that.board.manhattan();
        }
    }

    private int n = 0;
    private Node last;
    private MinPQ<Node> hpq = new MinPQ<Node>();
    private MinPQ<Node> mpq = new MinPQ<Node>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        this.mpq.insert(new Node(initial, null));

        while (!this.mpq.isEmpty()) {
            this.n++;
            Node temp = this.mpq.delMin();
            for (Board b : temp.getBoard().neighbors()) {
                if (b.equals(temp.getPrev().getBoard())) continue;
                this.mpq.insert(new Node(b, temp));
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.hpq.min().getBoard().isGoal();
    }

    // min number of moves to solve initial board
    public int moves() {
        return this.n;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        ArrayList<Board> arr = new ArrayList<Board>();
        Node temp = this.last;
        while (temp != null) {
            arr.add(temp.getBoard());
            temp = temp.getPrev();
        }
        return arr;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
