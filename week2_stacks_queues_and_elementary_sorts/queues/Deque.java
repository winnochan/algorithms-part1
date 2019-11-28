import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node<Item> head;
    private Node<Item> tail;

    // construct an empty deque
    public Deque() {
        this.size = 0;
        this.head = this.tail = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.tail == null;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> node = new Node<Item>(item);

        if (this.size == 0) {
            this.head = this.tail = node;
        }
        else {
            this.head.prev = node;
            node.next = this.head;
            this.head = node;
        }
        this.size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> node = new Node<Item>(item);

        if (this.size == 0) {
            this.head = this.tail = node;
        }
        else {
            this.tail.next = node;
            node.prev = this.tail;
            this.tail = node;
        }
        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = this.head.item;
        if (size == 1) {
            this.head = this.tail = null;
        }
        else {
            this.head = this.head.next;
            this.head.prev.next = null;
            this.head.prev = null;
        }
        this.size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = this.tail.item;
        if (size == 1) {
            this.head = this.tail = null;
        }
        else {
            this.tail = this.tail.prev;
            this.tail.next.prev = null;
            this.tail.next = null;
        }
        this.size--;
        return item;
    }

    private Node<Item> getHead() {
        return this.head;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DLLIterator<Item>(this);
    }

    private class Node<Item> {
        private Item item;
        private Node<Item> prev;
        private Node<Item> next;

        Node(Item item) {
            this.item = item;
            this.prev = null;
            this.next = null;
        }

        Node(Item item, Node<Item> prev, Node<Item> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

        public void setItem(Item item) {
            this.item = item;
        }

        public Item getItem() {
            return this.item;
        }

        public void setNext(Node<Item> next) {
            this.next = next;
        }

        public Node<Item> getNext() {
            return this.next;
        }
    }

    private class DLLIterator<Item> implements Iterator<Item> {
        private Deque<Item>.Node<Item> curr;

        public DLLIterator(Deque<Item> deque) {
            this.curr = deque.head;
        }

        public boolean hasNext() {
            return this.curr != null;
        }

        public Item next() {
            if (this.curr == null) {
                throw new NoSuchElementException();
            }

            Item item = this.curr.getItem();
            this.curr = this.curr.getNext();
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        StdOut.println(deque.size());

        deque.addFirst(1);
        StdOut.println(deque.size());
        deque.removeLast();
        StdOut.println(deque.size());
        deque.addLast(2);
        StdOut.println(deque.size());
        deque.removeFirst();
        StdOut.println(deque.size());

        StdOut.println("==================");

        for (int i = 1; i < 10; i++) {
            if (i % 2 == 0) {
                deque.addFirst(i);
            }
            else {
                deque.addLast(i);
            }
        }

        for (int i : deque) {
            StdOut.println(i);
        }

        for (int i = 1; i < 10; i++) {
            deque.removeFirst();
        }

        for (int i : deque) {
            StdOut.println(i);
        }
    }
}
