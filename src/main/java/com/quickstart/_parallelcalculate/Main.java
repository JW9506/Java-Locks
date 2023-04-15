package com.quickstart._parallelcalculate;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    ExecutorService pool = Executors.newFixedThreadPool(3);
    Future<Integer> t1 = pool.submit(new MyCallable(100));
    Future<Integer> t2 = pool.submit(new MyCallable(200));
    Future<Integer> t3 = pool.submit(new MyCallable(300));
    log.info("hi");
    Integer t1i = t1.get();
    Integer t2i = t2.get();
    Integer t3i = t3.get();
    log.info("{}, {}, {}", t1i, t2i, t3i);
    pool.shutdown();
  }
}

@Slf4j
class MyCallable implements Callable<Integer> {
  
  private int n;
  public MyCallable(int n) {
    this.n = n;
  }

  @Override
  public Integer call() throws Exception {
    int num = 0;
    for (int i = 0; i < n; i++) {
      log.info(Thread.currentThread().getName() + ": " + i);
      num += i;
    }
    return num;
  }
}
