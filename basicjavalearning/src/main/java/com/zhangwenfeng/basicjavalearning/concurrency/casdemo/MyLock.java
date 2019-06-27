package com.zhangwenfeng.basicjavalearning.concurrency.casdemo;

import lombok.ToString;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. Unsafe操作使用详解
 */
public class MyLock {

    /**
     * 内部类: 双向链表
     */
    private static class Node {
        // 存储的元素为线程
        Thread thread;
        // 前一个节点
        Node prev;
        // 后一个节点
        Node next;
        public Node() {}

        public Node(Thread thread, Node prev) {
            this.thread = thread;
            this.prev = prev;
        }
    }

    // 标识是否被锁定
    private volatile int state;

    // state的偏移量
    private static long stateOffset;

    // tail的偏移量
    private static long tailOffset;

    // unsafe 实例
    private static Unsafe unsafe;

    static final Node EMPTY = new Node();

    // 链表头
    private volatile Node head;
    // 链表尾
    private volatile Node tail;


    // 构造方法
    public MyLock() {
        head = tail = EMPTY;
    }

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
            stateOffset = unsafe.objectFieldOffset(MyLock.class.getDeclaredField("state"));
            // 获取tail的偏移量
            tailOffset = unsafe.objectFieldOffset(MyLock.class.getDeclaredField("tail"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * CAS 操作
     * @param expect
     * @param update
     * @return
     */
    private boolean compareAndSetstate(int expect, int update) {
        /**
         * 比较obj的offset处内存位置中的值和期望的值，如果相同则更新。此更新是不可中断的。
         *
         * @param obj 需要更新的对象
         * @param offset obj中整型field的偏移量
         * @param expect 希望field中存在的值
         * @param update 如果期望值expect与field的当前值相同，设置filed的值为这个新值
         * @return 如果field的值被更改返回true
         */
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

    // 原子更新tail字段
    private boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }


    public void unlock() {
        // 把state更新成0。这里不需要原子更新, 因为同时只有一个线程访问到这里
        state = 0;

        // 下一个待唤醒的节点
        Node next = head.next;

        // 下一个节点不为空,则唤醒它
        if (next != null)
            unsafe.unpark(next.thread);
        System.out.println("当前线程: " + Thread.currentThread().getName() + "解锁成功");
    }


    /**
     * 加锁
     * 管理 *head 指针
     */
    public void lock() {
        // 尝试更新 state 字段, 更新成功说明占有了锁
        if (compareAndSetstate(0, 1)) {
            System.out.println("当前线程: " + Thread.currentThread().getName() + "获取锁成功");
            return;
        }

        // 未更新成功则入队列, 返回最后添加的节点
        Node newTail = enqueue();
        Node prev = newTail.prev;

        // 再次尝试获取锁, 需要检测上一个节点是不是head, 按入队顺序加锁
        //
        while (newTail.prev != head || !compareAndSetstate(0, 1)) {
            // 未获取到锁, 阻塞。调用该方法后, 线程会一直阻塞直到超时或者中断等条件出现
            // public native void park(boolean isAbsolute, long time);
            unsafe.park(false, 0L);
        }
        System.out.println("当前线程: " + Thread.currentThread().getName() + "获取锁成功");


        // 如果成功了，就把头节点后移一位，并清空当前节点的内容，且与上一个节点断绝关系
        // 下面不需要原子更新, 因为同时只有一个线程访问到这里
        // 获取到锁了且上一个节点是head
        // head 后移一位
        head = newTail;
        // 清空当前节点的内容, 协助GC
        newTail.thread = null;
        // 将上一个节点从链表中剔除,协助GC
        newTail.prev = null;
        prev.next = null;

    }


    /**
     * 入队列
     * @return
     */
    private Node enqueue() {
        for (;;) {
            // 1. 获取尾节点
            Node t = tail;

            // 2. 构造新节点
            Node newNode = new Node(Thread.currentThread(), t);

            // 3. 不断尝试原子更新尾结点
            if (compareAndSetTail(t, newNode)) {
                // 更新尾结点成功了, 让尾结点的prev指向原来的尾结点
                t.next = newNode;

                // 返回尾结点
                return newNode;
            }
        }
    }
}
