import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        if (args.length <= 0) return;

        // StdOut.println(StdIn.readString());
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> raque = new RandomizedQueue<String>();
        for (int i = 0; i < k; i++) {
            String e = StdIn.readString();
            // StdOut.println(e);
            raque.enqueue(e);
        }

        for (String e : raque) {
            StdOut.println(e);
        }
    }
}
