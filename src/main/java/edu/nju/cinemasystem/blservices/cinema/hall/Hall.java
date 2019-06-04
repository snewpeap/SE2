package edu.nju.cinemasystem.blservices.cinema.hall;

import edu.nju.cinemasystem.data.vo.HallForm;
import edu.nju.cinemasystem.data.vo.Response;

public interface Hall {

    /**
     * TODO：录入影厅信息
     * @param HallForm
     * @return
     */
    Response enterCinemaInfo(HallForm hallForm);

    /**
     * TODO：修改影厅信息
     * @param HallForm
     * @return
     */
    Response modifyCinemaInfo(HallForm hallForm);
}
