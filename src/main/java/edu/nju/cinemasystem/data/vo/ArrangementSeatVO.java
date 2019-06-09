package edu.nju.cinemasystem.data.vo;

import edu.nju.cinemasystem.data.po.ArrangementSeat;

public class ArrangementSeatVO{
    
    private boolean isLocked;

    private Integer seatId;

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

    public ArrangementSeatVO(ArrangementSeat arrangementSeat) {
        this.isLocked = arrangementSeat.getIsLocked() == 0? false:true;
        this.seatId = arrangementSeat.getSeatId();
    }

    
}