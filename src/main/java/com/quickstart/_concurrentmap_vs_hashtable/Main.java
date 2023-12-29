package com.quickstart._concurrentmap_vs_hashtable;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
  public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(new MyRunnable(), "T1");
    Thread t2 = new Thread(new MyRunnable(), "T2");
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    log.info("Map size: {}", map.size());
  }

  // not Thread safe, but fast.
  // public static Map<String, String> map = new HashMap<>();
  // Thread safe, but slow. All instance methods are synchronized.
  // public static Map<String, String> map = new Hashtable<>();
  // Thread safe. Compare and swap, locally synchronized.
  public static Map<String, String> map = new ConcurrentHashMap<>();
}

@Slf4j
class MyRunnable implements Runnable {

  @Override
  public void run() {
    long begin = System.currentTimeMillis();
    for (int i = 0; i < 500000; i++) {
      Main.map.put(Thread.currentThread().getName() + i, "" + i);
    }
    long end = System.currentTimeMillis();
    log.info("Time spent: {}", (end - begin));
  }
}
