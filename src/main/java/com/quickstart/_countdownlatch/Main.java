package com.quickstart._countdownlatch;

import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;

/**
 * CountDownLatch allows one or more threads to wait until other threads are finished, to get into
 * Running state.
 * 
 * Given: Thread A is going to print A and C Thread B is going to print B Make sure A, B, C are
 * logged in this order.
 * 
 */

public class Main {

  public static void main(String[] args) {
    CountDownLatch latchAB = new CountDownLatch(1);
    CountDownLatch latchBC = new CountDownLatch(1);

    new ThreadA(latchAB, latchBC).start();
    new ThreadB(latchAB, latchBC).start();
  }
}

@Slf4j
class ThreadA extends Thread {
  private CountDownLatch latchAB;
  private CountDownLatch latchBC;

  public ThreadA(CountDownLatch latchAB, CountDownLatch latchBC) {
    super("ThreadA");
    this.latchAB = latchAB;
    this.latchBC = latchBC;
  }

  @Override
  public void run() {
    log.info("A");
    latchAB.countDown(); // Signal B to print
    try {
      latchBC.await(); // Wait for B to finish
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("Thread interrupted", e);
    }
    log.info("C");
  }
}


@Slf4j
class ThreadB extends Thread {
  private CountDownLatch latchAB;
  private CountDownLatch latchBC;

  public ThreadB(CountDownLatch latchAB, CountDownLatch latchBC) {
    super("ThreadB");
    this.latchAB = latchAB;
    this.latchBC = latchBC;
  }

  @Override
  public void run() {
    try {
      latchAB.await(); // Wait for A to print
      log.info("B");
      latchBC.countDown(); // Signal C to print
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("Thread interrupted", e);
    }
  }
}
