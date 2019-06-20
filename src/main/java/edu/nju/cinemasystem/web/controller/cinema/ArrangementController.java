package edu.nju.cinemasystem.web.controller.cinema;

import edu.nju.cinemasystem.blservices.cinema.arrangement.ArrangementManage;
import edu.nju.cinemasystem.blservices.cinema.arrangement.ArrangementService;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.ArrangementForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 排片相关接口
 *
 */
@RestController
public class ArrangementController {
    @Autowired
    private ArrangementService arrangementService;
    @Autowired
    private ArrangementManage arrangementManage;

    /**
     * 根据电影ID给用户返回此电影全部的排片
     * @param movieId 电影ID
     * @return 一个Map，key是日期，value是相应的排片list
     */
    @GetMapping("/user/arrangement/get")
    public Response getArrangement(@RequestParam int movieId){
        return arrangementService.getByMovieID(movieId);
    }

    /**
     * 获得对应排片下的座位情况
     * @param arrangementId 排片号
     * @return 一个ArrangementSeatVO的列表
     */
    @GetMapping("/user/seat/get")
    public Response getSeatMap(@RequestParam int arrangementId){
        return arrangementService.getSeatMap(arrangementId);
    }

    /**
     * 管理员添加排片
     * @param arrangementForm 此排片的相关信息 包括：开始/结束时间 场次费用 影厅号 电影ID 可见日期
     * @return 是否成功
     */
    @PostMapping("/manage/arrangement/add")
    public Response addManageArrangement(@RequestBody ArrangementForm arrangementForm){
        return arrangementManage.addArrangement(arrangementForm);
    }

    /**
     * 管理员修改排片
     * @param arrangementForm 排片相关信息
     * @param arrangementId 排片号
     * @return 是否成功
     */
    @PostMapping("/manage/arrangement/modify")
    public Response updateManageArrangement(@RequestBody ArrangementForm arrangementForm,@RequestParam int arrangementId){
        return arrangementManage.modifyArrangement(arrangementForm,arrangementId);
    }

    @PostMapping("/manage/arrangement/remove")
    public Response removeManageArrangement(@RequestParam int arrangementId){
        return arrangementManage.removeArrangement(arrangementId);
    }

    /**
     * 管理员按影厅查看排片情况
     * @param hallId 影厅号
     * @param startDate 开始日期
     * @return 一个ArrangementVO的list
     */
    @GetMapping("/manage/arrangement/get")
    public Response getManageArrangement(@RequestParam int hallId, @RequestParam Date startDate){
        return arrangementManage.getArrangementsByHallID(hallId,startDate);
    }

    /**
     * 批量修改排片可见日期
     * @param day 要改成的日期
     * @param arrangementIds 排片号的list
     * @return 是否成功
     */
    @Deprecated
    @PostMapping("/manage/arrangement/visibleDay")
    public Response modifyVisibleDay(@RequestParam Date day, @RequestBody List<Integer> arrangementIds){
        return arrangementManage.modifyVisibleDay(arrangementIds,day);
    }
}
