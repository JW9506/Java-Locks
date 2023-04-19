package com.quickstart._volatile;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
  public static void main(String[] args) {
    MyRunnable r = new MyRunnable();
    new Thread(r).start();
    while (true) {
      // Fix 1
      // Lock clears workspace memory and reloads from main memory
      synchronized (Main.class) {
        if (r.isFlag()) {
          log.info("main thread is over");
          break;
        }
      }
    }
  }
}

@Slf4j
class MyRunnable implements Runnable {
  
  // Fix 2
  // volatile private boolean flag = false;
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
      e.printStackTrace();
    }
    
    setFlag(true);
    log.info("flag: {}", flag);
  }
}
