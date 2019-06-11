package edu.nju.cinemasystem.data.vo.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class HallForm{

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Integer column;

    @NotNull
    @Positive
    private Integer row;

    @NotBlank
    private String size;    //大，中，小

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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
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