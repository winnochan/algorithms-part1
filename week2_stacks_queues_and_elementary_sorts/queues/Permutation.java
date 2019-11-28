import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class Permutation {
    public static void main(String[] args) {
        if (args.length <= 0) return;

        // StdOut.println(StdIn.readString());
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> raque = new RandomizedQueue<String>();
        // for (int i = 0; i < k; i++) {
        //     String e = StdIn.readString();
        //     // StdOut.println(e);
        //     raque.enqueue(e);
        // }

        while (true) {
            try {
                String e = StdIn.readString();
                raque.enqueue(e);
            }
            catch (NoSuchElementException e) {
                break;
            }
        }

        // StdOut.println("=================");

        int i = 0;
        for (String e : raque) {
            if (i++ >= k) break;
            StdOut.println(e);
        }
        // StdOut.println(raque.size());
    }
}
