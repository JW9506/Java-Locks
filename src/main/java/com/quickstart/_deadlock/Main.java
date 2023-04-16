package com.quickstart._deadlock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
  public static void main(String[] args) {
    Object threadA = new Object();
    Object threadB = new Object();
    new Thread(() -> {
        synchronized (threadA) {
          log.info("ThreadA acquired lock of threadA, waiting for lock of threadB");
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          synchronized (threadB) {
            log.info("ThreadA acquired lock of threadB");
          }
        }
    }).start();
    new Thread(new Runnable() {
      @Override
      public void run() {
        synchronized (threadB) {
          log.info("ThreadB acquired lock of threadB, waiting for lock of threadA");
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          synchronized (threadA) {
            log.info("ThreadB acquired lock of threadA");
          }
        }
      }
    }).start();
  }
}
