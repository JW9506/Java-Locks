package com.quickstart._threadcommunication;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Main {
  public static void main(String[] args) {
    Account acc = new Account("1234567", new BigDecimal("10000"));
    new DrawThread("甲", acc, new BigDecimal("8000")).start();
    new DrawThread("乙", acc, new BigDecimal("8000")).start();
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
    account.draw(drawAmount);
  }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Account {

  private String accountNo;
  private BigDecimal bal;

  public synchronized void draw(BigDecimal drawAmount) {
    if (bal.compareTo(drawAmount) >= 0) {
      System.out.println(Thread.currentThread().getName() + "取钱成功！吐出钞票：" + drawAmount);
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      bal = bal.subtract(drawAmount);
      this.notifyAll();
      try {
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("\t余额为：" + bal);
    } else {
      System.out.println(Thread.currentThread().getName() + "取钱失败！余额不足！");
    }
    this.notifyAll();
  }
}
