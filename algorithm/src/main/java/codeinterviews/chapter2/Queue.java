package codeinterviews.chapter2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int n;



    private class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public void add(Item item) {
        Node<Item> temp = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;

        if (first == null) first = last;
        else temp.next = last;
        n++;
    }

    public Item pop() {
        if (first == null) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        n--;
        if (first == null) last = null;
        return item;
    }
    public int size() {
        return n;
    }
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;
        public ListIterator(Node<Item> first) {
            this.current = first;
        }
        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
