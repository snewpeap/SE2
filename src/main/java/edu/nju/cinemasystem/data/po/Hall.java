package edu.nju.cinemasystem.data.po;

public class Hall {
    private Integer id;

    private String name;

    private Integer column;

    private Integer row;

    private Byte size;  //0,1,2分别是大中小

    private Byte isImax;

    private Byte is3d;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public Byte getIsImax() {
        return isImax;
    }

    public void setIsImax(Byte isImax) {
        this.isImax = isImax;
    }

    public Byte getIs3d() {
        return is3d;
    }

    public void setIs3d(Byte is3d) {
        this.is3d = is3d;
    }

    public static  Hall assembleHallPO(String name, Integer column, Integer row, Byte size, Byte isImax, Byte is3d) {
        Hall hall = new Hall();
        hall.setName(name);
        hall.setColumn(column);
        hall.setRow(row);
        hall.setSize(size);
        hall.setIsImax(isImax);
        hall.setIs3d(is3d);
        return hall;
    }
}