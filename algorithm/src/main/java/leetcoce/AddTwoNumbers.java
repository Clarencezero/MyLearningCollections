package leetcoce;
/**
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * <p>
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
 */

/**
 * 这个题目简单考查了节点的问题。主要思想是建立一个cur 指向当前的节点,类似一个指针的作用。添加的时候不断向下移动。获取元素的时候,也需要移动指针
 *
 */

class ListNode {
    int      val;
    ListNode next;

    ListNode(int x) { val = x; }

    @Override
    public String toString() {
        return "value: " + val + " next: " + next;
    }


}

public class AddTwoNumbers {
    public static void main(String[] args) {
        ListNode two   = new ListNode(1);
        ListNode four  = new ListNode(8);
        // leetcoce.ListNode three = new leetcoce.ListNode(7);
        two.next = four;
        // four.next = three;

        ListNode five  = new ListNode(0);
        // leetcoce.ListNode six   = new leetcoce.ListNode(6);
        // leetcoce.ListNode four2 = new leetcoce.ListNode(4);
        // five.next = six;
        // six.next = four2;

        ListNode result = addTwoNumbers(two, five);
        System.out.println(result);

    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode p      = l1, q = l2, cur = result;

        int carry = 0;
        while (p != null || q != null) {
            int a = p != null ? p.val : 0;
            int b = q != null ? q.val : 0;

            int sum = a + b + carry;
            carry = (sum)/10;
            cur.next = new ListNode(sum % 10);
            if (p != null) {
                p = p.next;
            }
            if (q != null) {
                q = q.next;
            }
            cur = cur.next;
        }

        if (carry > 0) {
            cur.next = new ListNode(carry);
        }

        return result.next;
    }

}
