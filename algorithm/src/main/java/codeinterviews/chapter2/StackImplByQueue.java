package codeinterviews.chapter2;

class StackImpl<Item> {
    private Queue<Item> queue1 = new Queue<Item>();
    private Queue<Item> queue2 = new Queue<Item>();

    public void addStack(Item item) {
        if (queue1.size() == 0  && queue2.size() == 0) {
            queue1.add(item);
        }
        else if (queue1.size() != 0 && queue2.size() == 0) {
            queue1.add(item);
        }
        else if (queue2.size() != 0 && queue1.size() == 0) {
            queue2.add(item);
        }
        //System.out.println("queue1 size:" + queue1.size());
        //System.out.println("queue2 size:" + queue2.size());
    }

    public Item pop() {
        Item item = null;
        if (queue1.size() == 0 && queue2.size() == 0) {
            return  null;
        }
        if (queue2.size() == 0) {
            while (queue1.size() > 0) {
                item = queue1.pop();
                if (queue1.size() != 0) {
                    queue2.add(item);
                }
            }
        } else if (queue1.size() == 0) {
            while (queue2.size() > 0) {
                item = queue2.pop();
                if (queue2.size() != 0) {
                    queue1.add(item);
                }
            }
        }
        return item;
    }



}


public class StackImplByQueue {
    public static void main(String[] args) {
        StackImpl<Integer> stack = new StackImpl<Integer>();
        stack.addStack(1);
        stack.addStack(2);
        stack.addStack(3);
        stack.addStack(4);
        stack.addStack(5);

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());

    }
}
