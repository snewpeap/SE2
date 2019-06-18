package edu.nju.cinemasystem.blservices.impl.vip;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedTask implements Delayed {
    public static final long DELAY = 15L * 60 * 1000;
    private String ID;
    private int userID;
    private Date startTime;
    private float amount;
    private float realAmount;

    public DelayedTask(String ID, int userID, Date startTime, float amount, float realAmount) {
        this.ID = ID;
        this.userID = userID;
        this.startTime = startTime;
        this.amount = amount;
        this.realAmount = realAmount;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public static long getDELAY() {
        return DELAY;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(float realAmount) {
        this.realAmount = realAmount;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.startTime.getTime() + DELAY - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }
}
