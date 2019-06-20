package edu.nju.cinemasystem.data.vo;

import edu.nju.cinemasystem.data.po.Hall;

/**
 * 影厅信息 包含：
 * id 序号
 * name 名字
 * column 行数
 * size 0-大 1-中 2-小
 * isImax 是否Imax
 * is3d 是否3D
 *
 */
public class HallVO{
    private Integer id;

    private String name;

    private Integer column;

    private Integer row;

    private String size;  //0,1,2分别是大中小

    private boolean isImax;

    private boolean is3d;


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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isImax() {
        return isImax;
    }

    public void setImax(boolean isImax) {
        this.isImax = isImax;
    }

    public boolean isIs3d() {
        return is3d;
    }

    public void setIs3d(boolean is3d) {
        this.is3d = is3d;
    }

    public HallVO(Hall hall) {
        this.id = hall.getId();
        this.name = hall.getName();
        this.column = hall.getColumn();
        this.row = hall.getRow();
        switch(hall.getSize()){
            case 0:
                this.size = "大";
                break;
            case 1:
                this.size = "中";
                break;
            case 2:
                this.size = "小";
                break;
        }
        this.isImax = hall.getIsImax() == 1?true:false;
        this.is3d = hall.getIs3d() == 1? true:false;
    }

}