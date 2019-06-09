package edu.nju.cinemasystem.util.exception;

import java.util.List;

public class ServiceException extends Exception {

    private List<Integer> successList;
    private int fail;

    public List<Integer> getSuccessList() {
        return successList;
    }

    public void setSuccessList(List<Integer> successList) {
        this.successList = successList;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }
}
