package edu.nju.cinemasystem.blservices.impl.statistics;

import edu.nju.cinemasystem.blservices.movie.StatisticsInfo;
import edu.nju.cinemasystem.blservices.statistics.Statistics;
import edu.nju.cinemasystem.data.vo.Response;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StatisticsImpl implements Statistics, StatisticsInfo {
    @Override
    public double getHeatOf(int movieID) {
        return 0;
    }

    @Override
    public Response getAudiencePrice(int days) {
        return null;
    }

    @Override
    public Response getMoviePlacingRateByDate(Date date) {
        return null;
    }

    @Override
    public Response getPopularMovies(int days, int movieNum) {
        return null;
    }

    @Override
    public Response getScheduleRateByDate(Date date) {
        return null;
    }

    @Override
    public Response getTotalBoxOffice() {
        return null;
    }
    
}