package edu.nju.cinemasystem.blservices.impl.statistics;

import edu.nju.cinemasystem.blservices.movie.StatisticsInfo;
import edu.nju.cinemasystem.data.vo.statisticsVO.MovieTotalBoxOfficeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsInfoImpl implements StatisticsInfo {

   final
   StatisticsImpl statistics;

    @Autowired
    public StatisticsInfoImpl(StatisticsImpl statistics) {
        this.statistics = statistics;
    }

    @Override
    public double getHeatOf(int movieID) {
        List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeVOS = statistics.getDescendingMovieTotalBoxOffices();
        double movieBoxOffice = 0;
        for (MovieTotalBoxOfficeVO movieTotalBoxOfficeVO : movieTotalBoxOfficeVOS) {
            if (movieTotalBoxOfficeVO.getMovieId() == movieID) {
                movieBoxOffice = movieTotalBoxOfficeVO.getBoxOffice();
                break;
            }
        }
        if(movieTotalBoxOfficeVOS.size()!=0) {
            double maxBoxOffice = movieTotalBoxOfficeVOS.get(0).getBoxOffice() == 0 ? 1 : movieTotalBoxOfficeVOS.get(0).getBoxOffice();
            return movieBoxOffice / maxBoxOffice;
        }else {
            return 0;
        }
    }

}
