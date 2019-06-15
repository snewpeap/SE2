package edu.nju.cinemasystem.data.po;

public class Seat {
    private Integer id;

    private Integer column;

    private Integer row;

    private Integer hallId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getHallId() {
        return hallId;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }

    public static Seat assembleSeatPO(Integer column, Integer row, Integer hallId){
        Seat seat = new Seat();
        seat.setColumn(column);
        seat.setRow(row);
        seat.setHallId(hallId);
        return seat;
    }
}