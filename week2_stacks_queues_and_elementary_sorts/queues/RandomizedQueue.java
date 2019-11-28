import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int sz;
    private Item[] items;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.sz = 0;
        this.items = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.size() == 0;
    }

    private int cap() {
        return this.items.length;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.sz;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (this.size() == this.cap()) {
            this.resize(2 * this.cap());
        }
        this.items[this.sz++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item;
        int sz = this.size();
        if (sz == 1) {
            item = this.items[0];
            this.items[0] = null;
        }
        else {
            int r = StdRandom.uniform(sz);
            if (r != sz - 1) {
                Item tmp = this.items[r];
                this.items[r] = this.items[sz - 1];
                this.items[sz - 1] = tmp;
            }
            item = this.items[sz - 1];
            this.items[sz - 1] = null;
        }

        this.sz--;

        int cap = this.cap();
        if (this.size() * 4 <= cap && cap >= 2) {
            this.resize(cap / 2);
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        return this.items[StdRandom.uniform(this.size())];
    }

    private void resize(int n) {
        Item[] items = (Item[]) new Object[n];

        for (int i = 0; i < this.size(); i++) {
            items[i] = this.items[i];
            this.items[i] = null;
        }

        this.items = items;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrIterator<Item>(this);
    }

    private class ArrIterator<Item> implements Iterator<Item> {
        // RandomizedQueue<Item> raque;
        private int index;
        private Item[] items;

        public ArrIterator(RandomizedQueue<Item> raque) {
            // this.raque = raque;
            this.index = 0;
            this.items = (Item[]) new Object[raque.size()];
            for (int i = 0; i < raque.size(); i++) {
                this.items[i] = raque.items[i];
            }
            StdRandom.shuffle(this.items);
        }

        public boolean hasNext() {
            return this.index < this.items.length;
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.items[this.index++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> raque = new RandomizedQueue<Integer>();

        StdOut.println(raque.size());

        raque.enqueue(1);
        StdOut.println(raque.size());
        StdOut.println(raque.dequeue());
        StdOut.println(raque.size());

        for (int i = 1; i < 10; i++) {
            raque.enqueue(i);
        }

        for (int i : raque) {
            StdOut.println(i);
        }
    }

}
