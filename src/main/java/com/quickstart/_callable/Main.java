package com.quickstart._callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Main {
    public static void main(String[] args) {
        // Create a FutureTask that wraps a MyCallable instance
        FutureTask<String> futureTask = new FutureTask<>(new MyCallable());

        // Start a new thread with the FutureTask
        new Thread(futureTask).start();

        // Printing numbers in the main thread
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }

        try {
            // Wait and get the result from the futureTask
            // The main thread will block here until the result is available
            String sum = futureTask.get();
            System.out.println("Sum from Callable: " + sum);
        } catch (InterruptedException e) {
            // It's good practice to re-interrupt the thread when an InterruptedException is caught
            Thread.currentThread().interrupt();
            System.out.println("Thread was interrupted.");
        } catch (ExecutionException e) {
            System.out.println("Exception occurred in the callable task.");
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
        // Convert the sum to a string and return it
        return String.valueOf(num);
    }
}
