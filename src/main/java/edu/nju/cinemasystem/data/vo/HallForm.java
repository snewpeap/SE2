package edu.nju.cinemasystem.data.vo;

public class HallForm{

    private String name;

    private Integer column;

    private Integer row;

    private Byte size;

    private Boolean isImax;

    private Boolean is3d;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Byte getSize() {
        return size;
    }

    public void setSize(Byte size) {
        this.size = size;
    }

    public Boolean getIsImax() {
        return isImax;
    }

    public void setIsImax(Boolean isImax) {
        this.isImax = isImax;
    }

    public Boolean getIs3d() {
        return is3d;
    }

    public void setIs3d(Boolean is3d) {
        this.is3d = is3d;
    }

}