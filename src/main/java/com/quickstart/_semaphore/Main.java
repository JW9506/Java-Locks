package com.quickstart._semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import lombok.extern.slf4j.Slf4j;

/**
 * Semaphore permits control at most how many threads can acquire lock at the same time.
 */
public class Main {
  public static void main(String[] args) throws InterruptedException {
    // At most one thread will be able to acquire lock
    final int permits = 1;
    final int threadCounts = 2;
    Service service = new Service(permits);
    List<Thread> threads = new ArrayList<>();
    for (int i = 0; i < threadCounts; i++) {
      threads.add(new MyThread(service));
    }
    for (Thread t : threads) {
      t.start();
    }
    for (Thread t : threads) {
      t.join();
    }
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

class MyThread extends Thread {

  private Service service;
  public MyThread(Service service) {
    this.service = service;
  }

  @Override
  public void run() {
    this.service.checkout();
  }
}