package com.quickstart._cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Main {
  public static void main(String[] args) {
    CyclicBarrier barrier = new CyclicBarrier(5, new Meeting());
    for (int i = 1; i <= 5; i++) {
      new Employee("Employee"+i, barrier).start();
    }
  }
}

class Employee extends Thread {
  
  private CyclicBarrier barrier;
  Employee(String name, CyclicBarrier barrier) {
    super(name);
    this.barrier = barrier;
  }
  
  @Override
  public void run() {
    System.out.println(Thread.currentThread().getName() + " is arrived");
    try {
      barrier.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (BrokenBarrierException e) {
      e.printStackTrace();
    }
  }
}

class Meeting implements Runnable {

  @Override
  public void run() {
    System.out.println("Let's start the meeting");
  }
}