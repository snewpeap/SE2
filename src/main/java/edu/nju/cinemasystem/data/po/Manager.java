package edu.nju.cinemasystem.data.po;

public class Manager extends User {
    private Integer isRoot = 0;

    public Integer getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(Integer isRoot) {
        this.isRoot = isRoot;
    }

    public void setRoot(boolean root) {
        this.isRoot = root ? 1 : 0;
    }

    public boolean isRootManager() {
        return isRoot == 1;
    }

}