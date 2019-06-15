package edu.nju.cinemasystem.web.controller.cinema;

import edu.nju.cinemasystem.blservices.cinema.hall.HallManage;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.HallForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HallController {
    @Autowired
    private HallManage hallManage;

    @PostMapping("/admin/hall/add")
    public Response addHall(@RequestBody HallForm hallForm){
        return hallManage.inputHallInfo(hallForm);
    }

    @PostMapping("/admin/hall/modify")
    public Response updateHall(@RequestBody HallForm hallForm, @RequestParam int hallId){
        return hallManage.modifyHallInfo(hallForm,hallId);
    }

    @GetMapping("/manage/hall/get")
    public Response getAllHall(){
        return hallManage.getAllHallInfo();
    }
}
