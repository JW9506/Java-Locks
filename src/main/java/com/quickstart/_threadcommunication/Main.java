package com.quickstart._threadcommunication;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

public class Main {
  public static void main(String[] args) {
    Account acc = new Account("1234567", new BigDecimal("10000"));
    new DrawThread("Alice", acc, new BigDecimal("10000")).start();
    new DrawThread("Bob", acc, new BigDecimal("10000")).start();
    new SaveThread("Dad_01", acc, new BigDecimal("10000")).start();
    new SaveThread("Dad_02", acc, new BigDecimal("10000")).start();
    new SaveThread("Dad_03", acc, new BigDecimal("10000")).start();
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
        Thread.sleep(3000);
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
        Thread.sleep(3000);
        account.draw(drawAmount);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
class Account {

  private String accountNo;
  private BigDecimal bal;

  public synchronized void draw(BigDecimal drawAmount) throws InterruptedException {
    if (bal.compareTo(drawAmount) >= 0) {
      bal = bal.subtract(drawAmount);
      log.info("{}: Money retrieval successful, amount: ${}. Account balance: ${}", Thread.currentThread().getName(), drawAmount, bal);
      this.notifyAll();
      this.wait();
    } else {
      log.info("{}: Money retrieval Failed, insufficient balance", Thread.currentThread().getName());
      this.notifyAll();
      this.wait();
    }
  }

  public synchronized void save(BigDecimal saveAmount) throws InterruptedException {
    if (bal.equals(BigDecimal.valueOf(0))) {
      bal = bal.add(saveAmount);
      log.info("{}: Money deposit successful, amount: ${}. Account balance: ${}", Thread.currentThread().getName(), saveAmount, bal);
      this.notifyAll();
      this.wait();
    } else {
      log.info("{}: There is already savings, no need to deposit", Thread.currentThread().getName());
      this.notifyAll();
      this.wait();
    }
  }
}
