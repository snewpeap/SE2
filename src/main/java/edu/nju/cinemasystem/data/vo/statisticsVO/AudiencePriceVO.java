package edu.nju.cinemasystem.data.vo.statisticsVO;

import java.util.Date;

/**
 * 客单价VO 包括：
 * date 日期
 * price 客单价
 */
public class AudiencePriceVO {

    private Date date;
    /**
     * 客单价
     */
    private float price;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
