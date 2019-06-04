package edu.nju.cinemasystem.blservices.cinema.hall;

import edu.nju.cinemasystem.data.vo.HallForm;
import edu.nju.cinemasystem.data.vo.Response;

public interface HallManage {

    /**
     * TODO：录入影厅信息
     * @param HallForm
     * @return
     */
    Response enterHallInfo(HallForm hallForm);

    /**
     * TODO：修改影厅信息
     * @param HallForm
     * @return
     */
    Response modifyHallInfo(HallForm hallForm);

    /**
     * 获得所有的影厅信息
     * @return
     */
    Response getAllHallInfo();
}
