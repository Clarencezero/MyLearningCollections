package com.zhangwenfeng.basicjavalearning.concurrency.casdemo;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class MyLockTest {
    private static int count = 0;

    @Test
    public void testGetUnsafe() throws InterruptedException {
        MyLock lock = new MyLock();
        int thredCount = 100;
        CountDownLatch countDownLatch = new CountDownLatch(thredCount);
        IntStream.range(0, thredCount).forEach(i -> new Thread(() -> {
            lock.lock();;

            try {
                IntStream.range(0, 10000).forEach(j -> {
                    count++;
                });
            } finally {
                lock.unlock();
                countDownLatch.countDown();
            }

        }, "tt-" +i).start());

        countDownLatch.await();

        System.out.println(count);
    }

}