package edu.nju.cinemasystem.blservices.cinema.arrangement;

import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.ArrangementForm;

import java.util.Date;
import java.util.List;

public interface ArrangementManage {

    /**
     *  添加排片信息
     * @param arrangementForm
     * @return
     */
    Response addArrangement(ArrangementForm arrangementForm);

    /**
     * 修改排片信息
     * @param arrangementForm
     * @return
     */
    Response modifyArrangement(ArrangementForm arrangementForm, int ID);

    /**
     * 删除排片信息
     * @param ID
     * @return
     */
    Response removeArrangement(int ID);

    /**
     * 根据影厅查看排片信息
     * @param hallID
     * @return
     */
    Response getArrangementsByHallID(int hallID, Date startDate);

    /**
     * 批量修改排片可见时间
     * @param IDs
     * @param date
     * @return
     */
    Response modifyVisibleDay(List<Integer> IDs, Date date);

    /**
     * 返回这两个日期内的所有排片
     * @param startDate
     * @param endDate
     * @return
     */
    List<edu.nju.cinemasystem.data.po.Arrangement> getArrangementsByDay(Date startDate, Date endDate);
}
