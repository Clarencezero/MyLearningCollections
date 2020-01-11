package codeinterviews.chapter2;

import java.util.NoSuchElementException;

class QueueImpl <Item>{
    Stack<Item> stackIn = new Stack<Item>();
    Stack<Item> stackOut= new Stack<Item>();

    public void addQueue(Item item) {
        stackIn.addStack(item);
    }

    public Item getQueue() {
        //System.out.println(stackIn.size() + ":" + stackOut.size());
        if (stackIn.size() == 0 && stackOut.size() == 0) {
            //throw  new NoSuchElementException();

        }

        if (stackOut.size() == 0) {
            while (stackIn.size() > 0) {
                stackOut.addStack(stackIn.pop());
            }
        }
        return stackOut.pop();
    }
}

public class QueueImplByStack {
    public static void main(String[] args) {
        QueueImpl<String> queue = new QueueImpl<String>();
        queue.addQueue("a");
        queue.addQueue("b");
        queue.addQueue("c");
        queue.addQueue("d");
        queue.addQueue("e");
        queue.addQueue("f");
        queue.addQueue("g");
        queue.addQueue("h");

        System.out.println(queue.getQueue());
        System.out.println(queue.getQueue());
        System.out.println(queue.getQueue());
        System.out.println(queue.getQueue());
        System.out.println(queue.getQueue());
        System.out.println(queue.getQueue());
        System.out.println(queue.getQueue());
        System.out.println(queue.getQueue());
        //System.out.println(queue.getQueue());

    }
}
