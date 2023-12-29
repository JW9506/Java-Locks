package com.quickstart._semaphore;

import java.util.concurrent.Semaphore;
import lombok.extern.slf4j.Slf4j;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    // At most one thread will be able to acquire lock
    Service service = new Service(1);
    ThreadA threadA = new ThreadA(service);
    ThreadB threadB = new ThreadB(service);
    threadA.start();
    threadB.start();
    threadA.join();
    threadB.join();
  }
}

@Slf4j
class Service {

  private Semaphore semaphore;

  public Service(int permits) {
    this.semaphore = new Semaphore(permits);
  }

  public void checkout() {
    try {
      semaphore.acquire();
      log.info("{} is going to checkout at {}", Thread.currentThread().getName(), System.currentTimeMillis());
      Thread.sleep(1000);
      log.info("{} checkout successful", Thread.currentThread().getName());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      e.printStackTrace();
    } finally {
      log.info("{} finished checking out at {}", Thread.currentThread().getName(), System.currentTimeMillis());
      semaphore.release();
    }
  }
}

class ThreadA extends Thread {

  private Service service;
  public ThreadA(Service service) {
    super("ThreadA");
    this.service = service;
  }

  @Override
  public void run() {
    this.service.checkout();
  }
}

class ThreadB extends Thread {

  private Service service;
  public ThreadB(Service service) {
    super("ThreadB");
    this.service = service;
  }

  @Override
  public void run() {
    this.service.checkout();
  }
}