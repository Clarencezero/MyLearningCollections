package codeinterviews.chapter2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 栈
 * @param <Item>
 */
public class Stack<Item> implements Iterable<Item>{
    private Node<Item> first;
    private int N;

    private class Node<Item> {
        private Item item ;
        private Node<Item> next;
    }

    public void addStack(Item item) {
        Node<Item> temp = first;
        first = new Node<Item>();
        first.item = item;
        first.next = temp;
        N++;
    }

    public int size() {
        return N;
    }



    public Item pop() {
        if (first == null) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        N--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    private class ListIterator<Item> implements Iterator<Item>{
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
