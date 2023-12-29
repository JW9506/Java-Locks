package com.quickstart._countdownlatch;

/**
 * CountDownLatch allows one or more threads to wait until other threads are finished, to get into Running state.
 * 
 * Given:
 *    Thread A is going to print A and C
 *    Thread B is going to print B
 * Make sure A, B, C are logged.
 * 
 */

public class Main {
  public static void main(String[] args) {
    new ThreadA().start();
    new ThreadB().start();
  }
}

class ThreadA extends Thread {

  @Override
  public void run() {
    System.out.println("A");
    System.out.println("C");
  }
}

class ThreadB extends Thread {

  @Override
  public void run() {
    System.out.println("B");
  }
}