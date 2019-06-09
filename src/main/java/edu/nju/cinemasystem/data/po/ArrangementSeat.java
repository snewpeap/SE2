package edu.nju.cinemasystem.data.po;

public class ArrangementSeat {
    private Byte isLocked;

    private Integer arrangementId;

    private Integer seatId;

    public Byte getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Byte isLocked) {
        this.isLocked = isLocked;
    }

    public Integer getArrangementId() {
        return arrangementId;
    }

    public void setArrangementId(Integer arrangementId) {
        this.arrangementId = arrangementId;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public ArrangementSeat(Byte isLocked, Integer arrangementId, Integer seatId) {
        this.isLocked = isLocked;
        this.arrangementId = arrangementId;
        this.seatId = seatId;
    }
}