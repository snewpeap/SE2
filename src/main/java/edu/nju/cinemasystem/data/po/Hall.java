package edu.nju.cinemasystem.data.po;

public class Hall {
    private Integer id;

    private String name;

    private Integer column;

    private Integer row;

    private Byte size;

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
}