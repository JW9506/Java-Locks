package com.quickstart._countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch allows one or more threads to wait until other threads are finished, to get into Running state.
 * 
 * Given:
 *    Thread A is going to print A and C
 *    Thread B is going to print B
 * Make sure A, B, C are logged in this order.
 * 
 */

public class Main {
  public static void main(String[] args) {
    CountDownLatch countDownLatch = new CountDownLatch(1);

    new ThreadA(countDownLatch).start();
    new ThreadB(countDownLatch).start();
  }
}

class ThreadA extends Thread {

  private CountDownLatch countDownLatch;
  public ThreadA(CountDownLatch countDownLatch) {
    this.countDownLatch = countDownLatch;
  }

  @Override
  public void run() {
    System.out.println("A");
    System.out.println("C");
  }
}

class ThreadB extends Thread {

  private CountDownLatch countDownLatch;
  public ThreadB(CountDownLatch countDownLatch) {
    this.countDownLatch = countDownLatch;
  }

  @Override
  public void run() {
    System.out.println("B");
  }
}