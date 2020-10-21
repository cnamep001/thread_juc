package com.m2.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {

    private final static int QPS = 10;

    private Integer times = 1;

    public SemaphoreTest() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleWithFixedDelay(() -> {
            System.out.println(times++ + "=================");
            semaphore.release(QPS - semaphore.availablePermits());		//释放
        }, 1000, 1000, TimeUnit.MILLISECONDS);
    }


    private Semaphore semaphore = new Semaphore(QPS);

    class Task implements Runnable {
        @Override
        public void run() {

            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }   //不释放
            System.out.println(Thread.currentThread().getName());
        }
    }

    public void qpsTest() {

        for (int i = 0; i < 100; i++) {
            new Thread(new Task(), "thread" + (i + 1)).start();
        }
    }

    public static void main(String[] args) {
        new SemaphoreTest().qpsTest();
    }
}
