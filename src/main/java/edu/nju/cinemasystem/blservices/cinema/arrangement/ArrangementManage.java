package edu.nju.cinemasystem.blservices.cinema.arrangement;

import edu.nju.cinemasystem.data.po.Arrangement;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.ArrangementForm;

import java.util.Date;
import java.util.List;

public interface ArrangementManage {

    /**
     *  添加排片信息
     * @param arrangementForm 排片信息表单
     * @return 是否成功
     */
    Response addArrangement(ArrangementForm arrangementForm);

    /**
     * 修改排片信息
     * @param arrangementForm 排片信息表单
     * @return 是否成功
     */
    Response modifyArrangement(ArrangementForm arrangementForm, int ID);

    /**
     * 删除排片信息
     * @param ID 排片ID
     * @return 是否成功
     */
    Response removeArrangement(int ID);

    /**
     * 根据影厅查看排片信息
     * @param hallID 影厅ID
     * @return List<ArrangementVO>
     */
    Response getArrangementsByHallID(int hallID, Date startDate);

    /**
     * 批量修改排片可见时间
     * @param IDs 排片ID
     * @param date 日期
     * @return 是否成功
     */
    @Deprecated
    Response modifyVisibleDay(List<Integer> IDs, Date date);

    /**
     * 返回这两个日期内的所有排片
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 排片List
     */
    List<Arrangement> getArrangementsByDay(Date startDate, Date endDate);
}
