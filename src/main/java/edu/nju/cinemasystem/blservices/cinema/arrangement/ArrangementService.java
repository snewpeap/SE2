package edu.nju.cinemasystem.blservices.cinema.arrangement;

import edu.nju.cinemasystem.data.vo.Response;

import java.util.Date;

public interface ArrangementService {
    /**
     * 用排片ID查找排片，排片是不可见状态的时候返回一个错误码401
     * 不用我先留着备用
     *
     * @param aID 排片ID
     * @return 包含ArrangementVO
     */
    Response getArrangement(int aID);

    /**
     * 用电影ID查找电影的所有排片
     *
     * @param movieID 电影ID
     * @return 一个Map，key是日期，value是相应的排片list
     */
    Response getByMovieID(int movieID);

    /**
     * 获得座位分布，排片不可见的时候返回response.fail,包含一个错误码401
     *
     * @param aID 排片ID
     * @return 返回ArrangementSeatVO的列表
     */
    Response getSeatMap(int aID);

    /**
     * 根据ID获得当场排片的价格
     *
     * @param arrangementID 排片ID
     * @return 价格
     */
    float getFareByID(int arrangementID);

    /**
     * 改变座位的状态
     * @param arrangementID 排片ID
     * @param seatID        座位ID
     * @param locked        状态
     */
    void changeArrangementSeatStatus(int arrangementID, int seatID, boolean locked);

    /**
     * 检查排片是否开始放映
     *
     * @param ID 排片ID
     * @return boolean值
     */
    boolean isArrangementStart(int ID);

    /**
     * 根据排片返回电影id
     *
     * @param ID 排片ID
     * @return 电影ID
     */
    int getMovieIDbyID(int ID);

    /**
     * 获取排片的开始时间和结束时间
     *
     * @param ID 排片ID
     * @return 开始时间结束时间
     */
    Date[] getStartDateAndEndDate(int ID);

    /**
     * 返回排片的影厅名称
     *
     * @param ID 排片ID
     * @return 影厅名称
     */
    String getHallNameByArrangementID(int ID);

    /**
     * 判断当前时间以后该影厅是否还有排片
     *
     * @param hallID      影厅ID
     * @param currentTime 当前时间
     * @return boolean值
     */
    boolean haveArrangementAfterCurrentTime(int hallID, Date currentTime);

    boolean isSeatBeenLocked(int arrangementID, int seatID);
}
