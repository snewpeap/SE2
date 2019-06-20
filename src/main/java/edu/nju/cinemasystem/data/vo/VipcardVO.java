package edu.nju.cinemasystem.data.vo;

/**
 * VIP卡的信息 包括：
 * id 会员号
 * balance 余额
 */
public class VipcardVO {
    private int id;
    private float balance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
