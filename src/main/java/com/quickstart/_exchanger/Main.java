package com.quickstart._exchanger;

import java.util.concurrent.Exchanger;

public class Main {
  public static void main(String[] args) {
    // Create an Exchanger object for String type
    Exchanger<String> exchanger = new Exchanger<>();

    // Thread 1: Sends a message
    Thread thread1 = new Thread(() -> {
      try {
        // Original message by thread1
        String message1 = "Hello from Thread 1!";
        System.out.println("Thread 1 sending message: " + message1);

        // Exchange the message with thread2
        String response1 = exchanger.exchange(message1);

        // Print the message received from thread2
        System.out.println("Thread 1 received message: " + response1);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.println("Thread 1 interrupted.");
      }
    });

    // Thread 2: Receives the message and responds
    Thread thread2 = new Thread(() -> {
      try {
        // Original message by thread2
        String message2 = "Hello from Thread 2!";
        System.out.println("Thread 2 sending message: " + message2);

        // Exchange the message with thread1
        String response2 = exchanger.exchange(message2);

        // Print the message received from thread1
        System.out.println("Thread 2 received message: " + response2);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.println("Thread 2 interrupted.");
      }
    });

    // Start both threads
    thread1.start();
    thread2.start();
  }
}

