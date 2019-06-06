package edu.nju.cinemasystem.blservices.cinema.hall;

import edu.nju.cinemasystem.data.vo.Form.HallForm;
import edu.nju.cinemasystem.data.vo.Response;

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
    Response modifyHallInfo(HallForm hallForm);

    /**
     * 获得所有的影厅信息
     * @return
     */
    Response getAllHallInfo();
}
