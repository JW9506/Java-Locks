package com.quickstart._threadcommunication;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Main {
  public static void main(String[] args) {
    Account acc = new Account("1234567", new BigDecimal("10000"));
    new DrawThread("甲", acc, new BigDecimal("10000")).start();
    new DrawThread("乙", acc, new BigDecimal("10000")).start();
    new SaveThread("父1", acc, new BigDecimal("10000")).start();
    new SaveThread("父2", acc, new BigDecimal("10000")).start();
    new SaveThread("父3", acc, new BigDecimal("10000")).start();
  }
}

class SaveThread extends Thread {

  private Account account;
  private BigDecimal drawAmount;

  public SaveThread(String name, Account account, BigDecimal drawAmount) {
    super(name);
    this.account = account;
    this.drawAmount = drawAmount;
  }

  @Override
  public void run() {
    try {
      while (true) {
        account.save(drawAmount);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

class DrawThread extends Thread {

  private Account account;
  private BigDecimal drawAmount;

  public DrawThread(String name, Account account, BigDecimal drawAmount) {
    super(name);
    this.account = account;
    this.drawAmount = drawAmount;
  }

  @Override
  public void run() {
    try {
      while (true) {
        account.draw(drawAmount);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Account {

  private String accountNo;
  private BigDecimal bal;

  public synchronized void draw(BigDecimal drawAmount) throws InterruptedException {
    if (bal.compareTo(drawAmount) >= 0) {
      bal = bal.subtract(drawAmount);
      Thread.sleep(1000);
      System.out.println(Thread.currentThread().getName() + "取钱成功！吐出钞票：" + drawAmount);
      this.notifyAll();
      this.wait();
      System.out.println("\t余额为：" + bal);
    } else {
      this.notifyAll();
      this.wait();
      System.out.println(Thread.currentThread().getName() + "取钱失败！余额不足！");
    }
  }

  public synchronized void save(BigDecimal saveAmount) throws InterruptedException {
    if (bal.equals(BigDecimal.valueOf(0))) {
      bal = bal.add(saveAmount);
      Thread.sleep(1000);
      System.out.println(Thread.currentThread().getName() + "存钱成功：" + saveAmount);
      this.notifyAll();
      this.wait();
      System.out.println("\t余额为：" + bal);
    } else {
      this.notifyAll();
      this.wait();
      System.out.println(Thread.currentThread().getName() + "发现已有存款，无需存钱！");
    }
  }
}
