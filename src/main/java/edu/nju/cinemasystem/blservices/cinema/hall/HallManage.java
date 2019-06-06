package edu.nju.cinemasystem.blservices.cinema.hall;

import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.HallForm;

public interface HallManage {

    /**
     * 录入影厅信息
     * @param hallForm
     * @return
     */
    Response enterHallInfo(HallForm hallForm);

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
