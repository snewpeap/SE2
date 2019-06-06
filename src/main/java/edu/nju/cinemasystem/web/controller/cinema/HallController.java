package edu.nju.cinemasystem.web.controller.cinema;

import edu.nju.cinemasystem.blservices.cinema.hall.HallManage;
import edu.nju.cinemasystem.data.vo.Form.HallForm;
import edu.nju.cinemasystem.data.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hall")
public class HallController {
    @Autowired
    private HallManage hallManage;

    @PostMapping("/add")
    public Response addHall(@RequestBody HallForm hallForm){
        return hallManage.InputHallInfo(hallForm);
    }

    @PostMapping("/modify")
    public Response updateHall(@RequestBody HallForm hallForm){
        return hallManage.modifyHallInfo(hallForm);
    }

    @GetMapping("/get")
    public Response getAllHall(){
        return hallManage.getAllHallInfo();
    }
}
