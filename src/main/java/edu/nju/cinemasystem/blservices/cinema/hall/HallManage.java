package edu.nju.cinemasystem.blservices.cinema.hall;

import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.HallForm;

public interface HallManage {

    /**
     * 录入影厅信息
     * @param hallForm
     * @return
     */
    Response InputHallInfo(HallForm hallForm);

    /**
     * 修改影厅信息
     * @param hallForm
     * @return
     */
    Response modifyHallInfo(HallForm hallForm, int ID);

    /**
     * 获得所有的影厅信息
     * @return
     */
    Response getAllHallInfo();

    /**
     * 返回影院影厅的平均座位数
     * @deprecated
     * @return
     */
    double getAverageSeatNum();


    /**
     * 返回某个影厅的座位数
     * @param hallID
     * @return
     */
    int getSeatNumByHallID(int hallID);

    /**
     * 根据座位ID，返回排和列
     * @param seatID
     * @return
     */
    int[] getSeatBySeatID(int seatID);

    /**
     * 根据影厅ID返回影厅名字
     * @param ID
     * @return
     */
    String getHallNameByID(int ID);
}
