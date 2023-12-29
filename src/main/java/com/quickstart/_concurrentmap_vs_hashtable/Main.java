package com.quickstart._concurrentmap_vs_hashtable;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  // Using HashMap for thread unsafety, for demo purposes.
  // public static Map<String, String> map = new HashMap<>();

  // Using HashTable for thread safety with mediocre performance.
  // public static Map<String, String> map = new Hashtable<>();

  // Using ConcurrentHashMap for thread safety with better performance.
  public static Map<String, String> map = new ConcurrentHashMap<>();

  public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(new MyRunnable(), "T1");
    Thread t2 = new Thread(new MyRunnable(), "T2");
    t1.start();
    t2.start();

    // Join to ensure both threads complete before calculating the map size
    t1.join();
    t2.join();

    log.info("Map size: {}", map.size());
  }
}


@Slf4j
class MyRunnable implements Runnable {

  @Override
  public void run() {
    long begin = System.currentTimeMillis();

    // Using thread name and loop index as the key to avoid overwriting in the map
    for (int i = 0; i < 500000; i++) {
      Main.map.put(Thread.currentThread().getName() + "-" + i, Integer.toString(i));
    }

    long end = System.currentTimeMillis();
    log.info("Time spent by {}: {} ms", Thread.currentThread().getName(), (end - begin));
  }
}
