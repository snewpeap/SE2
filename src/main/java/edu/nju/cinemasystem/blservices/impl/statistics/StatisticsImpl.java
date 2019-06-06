package edu.nju.cinemasystem.blservices.impl.statistics;

import java.util.Date;

import org.springframework.stereotype.Service;

import edu.nju.cinemasystem.blservices.statistics.Statistics;
import edu.nju.cinemasystem.data.vo.Response;

@Service
public class StatisticsImpl implements Statistics {

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