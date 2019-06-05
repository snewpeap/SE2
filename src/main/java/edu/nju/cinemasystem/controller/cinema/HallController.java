package edu.nju.cinemasystem.controller.cinema;

import edu.nju.cinemasystem.blservices.cinema.hall.HallManage;
import edu.nju.cinemasystem.data.vo.HallForm;
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
        return hallManage.enterHallInfo(hallForm);
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
