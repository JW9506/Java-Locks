package com.quickstart._callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Main {
    public static void main(String[] args) {
      FutureTask<String> futureTask = new FutureTask<>(new MyCallable());
      new Thread(futureTask).start();
      for (int i = 0; i < 10; i++) {
        System.out.println(Thread.currentThread().getName() + " " + i);
      }
      try {
        String num = futureTask.get();
        System.out.println(num);
    } catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    }
    }
}

class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        int num = 0;
        for (int i = 1; i <= 100; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
            num += i;
        }
        return "" + num;
    }
}
