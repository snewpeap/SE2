package edu.nju.cinemasystem.web.controller.cinema;

import edu.nju.cinemasystem.blservices.cinema.arrangement.ArrangementManage;
import edu.nju.cinemasystem.blservices.cinema.arrangement.ArrangementService;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.ArrangementForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class ArrangementController {
    @Autowired
    private ArrangementService arrangementService;
    @Autowired
    private ArrangementManage arrangementManage;

    @GetMapping("/user/arrangement/get")
    public Response getArrangement(@RequestParam int movieId){
        return arrangementService.getByMovieID(movieId);
    }

    @GetMapping("/user/seat/get")
    public Response getSeatMap(@RequestParam int arrangementId){
        return arrangementService.getSeatMap(arrangementId);
    }

    @PostMapping("/manage/arrangement/add")
    public Response addManageArrangement(@RequestBody ArrangementForm arrangementForm){
        return arrangementManage.addArrangement(arrangementForm);
    }

    @PostMapping("/manage/arrangement/modify")
    public Response updateManageArrangement(@RequestBody ArrangementForm arrangementForm,@RequestParam int arrangementId){
        return arrangementManage.modifyArrangement(arrangementForm,arrangementId);
    }

    @PostMapping("/manage/arrangement/remove")
    public Response removeManageArrangement(@RequestParam int arrangementId){
        return arrangementManage.removeArrangement(arrangementId);
    }

    @GetMapping("/manage/arrangement/get")
    public Response getManageArrangement(@RequestParam int hallId, @RequestParam Date startDate){
        return arrangementManage.getArrangementsByHallID(hallId,startDate);
    }

    @PostMapping("/manage/arrangement/visibleDay")
    public Response ModifyVisibleDay(@RequestParam Date day, @RequestBody List<Integer> arrangementIds){
        return arrangementManage.modifyVisibleDay(arrangementIds,day);
    }
}
