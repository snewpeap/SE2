package edu.nju.cinemasystem.data.vo;

import edu.nju.cinemasystem.data.po.ArrangementSeat;

public class ArrangementSeatVO{
    
    private boolean isLocked;

    private Integer seatId;

    private int row;

    private int column;

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public ArrangementSeatVO(ArrangementSeat arrangementSeat) {
        this.isLocked = arrangementSeat.getIsLocked() != 0;
        this.seatId = arrangementSeat.getSeatId();
    }
}