package com.quickstart._cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Main {
  public static void main(String[] args) {
    final int numberOfEmployees = 5;
    CyclicBarrier barrier = new CyclicBarrier(numberOfEmployees, new Meeting());

    for (int i = 1; i <= numberOfEmployees; i++) {
      new Employee("Employee" + i, barrier).start();
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
    try {
      System.out.println(getName() + " is arrived at the meeting.");
      barrier.await();
    } catch (InterruptedException e) {
      // Interrupt status should be set again when InterruptedException is caught
      Thread.currentThread().interrupt();
      System.out.println(getName() + " was interrupted.");
    } catch (BrokenBarrierException e) {
      System.out.println("Barrier was broken.");
    }
  }
}


class Meeting implements Runnable {
  @Override
  public void run() {
    System.out.println("All employees have arrived. Let's start the meeting.");
  }
}
