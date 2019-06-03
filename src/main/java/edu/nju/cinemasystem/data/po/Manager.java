package edu.nju.cinemasystem.data.po;

public class Manager {
    private Integer userId;
    private Byte root;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getRoot() {
        return root;
    }

    public void setRoot(Byte root) {
        this.root = root;
    }

    public void setRoot(boolean root) {
        this.root = root ? (byte) 1 : (byte) 0;
    }

    public boolean isRootManager() {
        return root == 1;
    }
}