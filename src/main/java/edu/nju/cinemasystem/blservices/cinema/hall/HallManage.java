package edu.nju.cinemasystem.blservices.cinema.hall;

import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.HallForm;

public interface HallManage {

    /**
     * 录入影厅信息
     *
     * @param hallForm 影厅信息表单
     * @return 是否成功
     */
    Response inputHallInfo(HallForm hallForm);

    /**
     * 修改影厅信息
     *
     * @param hallForm 影厅信息表单
     * @return 是否成功
     */
    Response modifyHallInfo(HallForm hallForm, int ID);

    /**
     * 获得所有的影厅信息
     *
     * @return List<HallVO>
     */
    Response getAllHallInfo();

    /**
     * 返回影院影厅的平均座位数
     *
     * @return 平均座位数
     * @deprecated
     */
    double getAverageSeatNum();


    /**
     * 返回某个影厅的座位数
     *
     * @param hallID 影厅ID
     * @return 影厅座位数
     */
    int getSeatNumByHallID(int hallID);

    /**
     * 根据座位ID，返回排和列
     *
     * @param seatID 座位ID
     * @return 排和列
     */
    int[] getSeatBySeatID(int seatID);

    /**
     * 根据影厅ID返回影厅名字
     *
     * @param ID 影厅ID
     * @return 名字
     */
    String getHallNameByID(int ID);
}
