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

    /**
     * 排片率
     * @param date 查询的日期
     * @return 排片率VO
     */
    @GetMapping("/arrangementRate")
    public Response getScheduleRateByDate(@RequestParam Date date){
        return statistics.getScheduleRateByDate(date);
    }

    /**
     * 票房
     * @return 票房voList
     */
    @GetMapping("/totalBoxOffice")
    public Response getTotalBoxOffice(){
        return statistics.getTotalBoxOffice();
    }

    /**
     * 客单价
     * @param days 天数
     * @return 天数内的客单价
     */
    @GetMapping("/audiencePrice")
    public Response getAudiencePrice(@RequestParam int days){
        return statistics.getAudiencePrice(days);
    }

    /**
     * 上座率
     * @param date 查询的日期
     * @return 当日的上座率
     */
    @GetMapping("/placingRate")
    public Response getMoviePlacingRateByDate(@RequestParam Date date){
        return statistics.getMoviePlacingRateByDate(date);
    }

    /**
     * 最受欢迎电影，用票房来衡量
     * @param days 天数
     * @param movieNum 查看的电影数目
     * @return 最受欢迎电影
     */
    @GetMapping("/popularMovies")
    public Response getPopularMovies(@RequestParam int days,@RequestParam int movieNum){
        return statistics.getPopularMovies(days,movieNum);
    }
}
