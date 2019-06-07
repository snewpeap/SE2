package edu.nju.cinemasystem.web.controller.cinema;

import edu.nju.cinemasystem.blservices.cinema.arrangement.Arrangement;
import edu.nju.cinemasystem.data.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArrangementController {
    @Autowired
    private Arrangement arrangement;

    @GetMapping("/arrangement/get")
    public Response getArrangement(@RequestParam int movieId){
        return arrangement.getArrangement(movieId);
    }

    @GetMapping("/Seat/get")
    public Response getSeatMap(@RequestParam int arrangementId){
        return arrangement.getSeatMap(arrangementId);
    }
}
