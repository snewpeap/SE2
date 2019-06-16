package edu.nju.cinemasystem.web.controller.statistics;

import edu.nju.cinemasystem.blservices.statistics.Statistics;
import edu.nju.cinemasystem.data.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/manage/statistics")
public class StatisticsController {
    @Autowired
    private Statistics statistics;

    @GetMapping("/arrangementRate")
    public Response getScheduleRateByDate(@RequestParam Date date){
        return statistics.getScheduleRateByDate(date);
    }

    @GetMapping("/totalBoxOffice")
    public Response getTotalBoxOffice(){
        return statistics.getTotalBoxOffice();
    }

    @GetMapping("/audiencePrice")
    public Response getAudiencePrice(@RequestParam int days){
        return statistics.getAudiencePrice(days);
    }

    @GetMapping("/placingRate")
    public Response getMoviePlacingRateByDate(@RequestParam Date date){
        return statistics.getMoviePlacingRateByDate(date);
    }

    @GetMapping("/popularMovies")
    public Response getPopularMovies(@RequestParam int days,@RequestParam int movieNum){
        return statistics.getPopularMovies(days,movieNum);
    }
}
