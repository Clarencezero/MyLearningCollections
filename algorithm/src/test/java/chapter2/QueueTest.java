package chapter2;


import codeinterviews.chapter2.Queue;
import codeinterviews.chapter2.Stack;
import org.junit.Test;

import java.util.Iterator;

public class QueueTest {
    @Test
    public void testAdd() {
        Queue<String> queue = new Queue<String>();
        for (int i = 0; i < 10; i++) {
            queue.add("" + i);

        }

        System.out.println(queue.size());

        Stack<String> stack = new Stack<String>();
        for (Iterator<String> iterator = queue.iterator();iterator.hasNext();) {
            stack.addStack(iterator.next());
        }

        for (Iterator<String> iterator = stack.iterator();iterator.hasNext();) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void testStack() {
        Stack<String> stack = new Stack<String>();
        stack.addStack("a");
        stack.addStack("b");
        stack.addStack("c");
        stack.addStack("d");
        stack.addStack("e");

        for (Iterator<String> iterator = stack.iterator();iterator.hasNext();) {
            System.out.println(iterator.next());
        }
    }
}
