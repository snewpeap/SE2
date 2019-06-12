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

    public static ArrangementSeat assembleArrangementSeat(Byte isLocked, Integer arrangementId, Integer seatId){
        ArrangementSeat arrangementSeat = new ArrangementSeat();
        arrangementSeat.setIsLocked(isLocked);
        arrangementSeat.setArrangementId(arrangementId);
        arrangementSeat.setSeatId(seatId);
        return arrangementSeat;
    }
}