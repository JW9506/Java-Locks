package com.quickstart._volatile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
  public static void main(String[] args) {
    MyRunnable r = new MyRunnable();
    new Thread(r).start();
    while (true) {
      synchronized (Main.class) {
        // The synchronized block ensures that the reading of the flag variable
        // happens with a happens-before relationship, providing visibility guarantees.
        // This forces the main thread to see the most recent change made to the flag variable
        // by any other thread (in this case, the thread running MyRunnable).
        if (r.isFlag()) {
          log.info("Main thread is over");
          break;
        }
      }
    }
  }
}


@Slf4j
class MyRunnable implements Runnable {

  // The volatile keyword could have fixed the issue but it is omitted here.
  // The synchronized block in the main method ensures visibility of changes
  // to this flag variable across threads.
  private boolean flag = false;

  public boolean isFlag() {
    return flag;
  }

  public void setFlag(boolean flag) {
    this.flag = flag;
  }

  @Override
  public void run() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("Thread interrupted", e);
    }

    setFlag(true);
    log.info("Flag set to: {}", flag);
  }
}
