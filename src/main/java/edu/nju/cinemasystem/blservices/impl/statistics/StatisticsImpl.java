package edu.nju.cinemasystem.blservices.impl.statistics;

import edu.nju.cinemasystem.blservices.cinema.arrangement.ArrangementManage;
import edu.nju.cinemasystem.blservices.cinema.hall.HallManage;
import edu.nju.cinemasystem.blservices.movie.MovieManagement;
import edu.nju.cinemasystem.blservices.movie.StatisticsInfo;
import edu.nju.cinemasystem.blservices.sale.ticket.TicketStatistics;
import edu.nju.cinemasystem.blservices.statistics.Statistics;
import edu.nju.cinemasystem.data.po.Arrangement;
import edu.nju.cinemasystem.data.po.Movie;
import edu.nju.cinemasystem.data.vo.*;
import edu.nju.cinemasystem.util.properties.message.GlobalMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StatisticsImpl implements Statistics, StatisticsInfo {

    private final
    TicketStatistics ticketStatistics;
    private final
    GlobalMsg globalMsg;
    private final
    ArrangementManage arrangementManage;
    private final
    MovieManagement movieManagement;
    private final
    HallManage hallManage;

    @Autowired
    public StatisticsImpl(TicketStatistics ticketStatistics, GlobalMsg globalMsg, ArrangementManage arrangementManage, MovieManagement movieManagement, HallManage hallManage) {
        this.ticketStatistics = ticketStatistics;
        this.globalMsg = globalMsg;
        this.arrangementManage = arrangementManage;
        this.movieManagement = movieManagement;
        this.hallManage = hallManage;
    }

    @Override
    public double getHeatOf(int movieID) {
        return 0;
    }

    @Override
    public Response getAudiencePrice(int days) {
        Response response;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Date startDate = getNumDayAfterDate(today, -(days - 1));
            List<AudiencePriceVO> audiencePriceVOList = new ArrayList<>();
            for (int i = 0; i < days; i++) {
                AudiencePriceVO audiencePriceVO = new AudiencePriceVO();
                Date date = getNumDayAfterDate(startDate, i);
                audiencePriceVO.setDate(date);
                audiencePriceVO.setPrice(ticketStatistics.getAudiencePriceByDay(date, getNumDayAfterDate(date, 1)));
                audiencePriceVOList.add(audiencePriceVO);
            }
            response = Response.success();
            response.setContent(audiencePriceVOList);
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.fail(globalMsg.getOperationFailed());
        }
        return response;
    }

    @Override
    public Response getMoviePlacingRateByDate(Date date) {
        Response response;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date formatDate = simpleDateFormat.parse(simpleDateFormat.format(date));
            List<Arrangement> arrangements = arrangementManage.getArrangementsByDay(formatDate, getNumDayAfterDate(formatDate, 1));
            List<Movie> allMovie = movieManagement.getReleasedMovies();
            Map<Integer, List> arrangementsByMovie = new HashMap<>();
            List<MoviePlacingRateVO> moviePlacingRateVOS = new ArrayList<>();
            for (Movie movie : allMovie) {
                List arrangementAndMovieName = new ArrayList();
                arrangementAndMovieName.add(movie.getName());
                arrangementAndMovieName.add(new ArrayList<Arrangement>());
                arrangementsByMovie.put(movie.getId(), arrangementAndMovieName);
            }
            arrangements.stream().forEach(arrangement -> {
                List arr = (List) arrangementsByMovie.get(arrangement.getMovieId()).get(1);
                arr.add(arrangement);
                arrangementsByMovie.get(arrangement.getMovieId()).set(1, arr);
            });
            for (Map.Entry<Integer, List> entry : arrangementsByMovie.entrySet()) {
                int movieID = entry.getKey();
                List value = entry.getValue();
                String movieName = (String) value.get(0);
                int audienceNum = 0;
                List<Arrangement> oneArrangementList = (List<Arrangement>) value.get(1);
                int seatNum = 0;
                for (Arrangement arrangement : oneArrangementList) {
                    int aID = arrangement.getId();
                    audienceNum += ticketStatistics.getNumOfTicketsByArrangement(aID);
                    seatNum += hallManage.getSeatNumByHallID(arrangement.getHallId());
                }
                double averageSeatNum = (double) seatNum / (double) oneArrangementList.size();
                //Double rate = audienceNum/oneArrangementList.size()/hallManage.getAverageSeatNum();
                Double rate = audienceNum / oneArrangementList.size() / averageSeatNum;
                moviePlacingRateVOS.add(new MoviePlacingRateVO(date, movieID, rate, movieName));
            }
            response = Response.success();
            response.setContent(moviePlacingRateVOS);
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.fail();
            response.setMessage(e.getMessage());
        }
        return null;
    }

    @Override
    public Response getPopularMovies(int days, int movieNum) {
        Response response;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Date startDate = getNumDayAfterDate(today, -(days - 1));
            List<Movie> movies = movieManagement.getReleasedMovies();
            List<MoviePopularityVO> moviePopularityVOS = new ArrayList<>();
            for (Movie movie : movies) {
                BaseMovieVO baseMovieVO = BaseMovieVO.assembleMovieVO(movie);
                float boxOffice = ticketStatistics.getBoxOfficeByMovieIDAndDay(movie.getId(), startDate, today);
                moviePopularityVOS.add(new MoviePopularityVO(baseMovieVO, boxOffice));
            }
            moviePopularityVOS.sort((MoviePopularityVO m1, MoviePopularityVO m2) -> (int) m2.getBoxOffice() - (int) m1.getBoxOffice());
            List<MoviePopularityVO> resultVOs = new ArrayList<>();
            for (int i = 0; i < movieNum; i++) {
                resultVOs.add(moviePopularityVOS.get(i));
            }
            response = Response.success();
            response.setContent(resultVOs);
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.fail(e.getMessage());
        }
        return response;
    }

    @Override
    public Response getScheduleRateByDate(Date date) {
        Response response;
        try {
            Date startDate = date;
            if (startDate == null) {
                startDate = new Date();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            startDate = simpleDateFormat.parse(simpleDateFormat.format(startDate));
            Date endDate = getNumDayAfterDate(startDate, 1);
            List<Movie> movies = movieManagement.getReleasedMovies();
            List<Arrangement> arrangements = arrangementManage.getArrangementsByDay(startDate, endDate);
            Map<Integer, List> scheduleMapByMovie = new HashMap<>();
            movies.forEach(movie -> {
                List movieNameAndTimes = new ArrayList();
                movieNameAndTimes.add(movie.getName());
                movieNameAndTimes.add(0);
                scheduleMapByMovie.put(movie.getId(), movieNameAndTimes);
            });
            arrangements.forEach(arrangement -> {
                int t = (int) scheduleMapByMovie.get(arrangement.getMovieId()).get(1);
                t++;
                scheduleMapByMovie.get(arrangement.getMovieId()).set(1, t);
            });
            List<MovieScheduleTimesVO> movieScheduleTimesVOS = new ArrayList<>();
            for (Map.Entry<Integer, List> entry : scheduleMapByMovie.entrySet()) {
                int movieID = entry.getKey();
                int times = (int) entry.getValue().get(1);
                String movieName = (String) entry.getValue().get(0);
                movieScheduleTimesVOS.add(new MovieScheduleTimesVO(movieID, times, movieName));
            }
            response = Response.success();
            response.setContent(movieScheduleTimesVOS);
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.fail(e.getMessage());
        }
        return response;
    }

    @Override
    public Response getTotalBoxOffice() {
        List<Movie> movies = movieManagement.getReleasedMovies();
        List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeVOS = new ArrayList<>();
        for (Movie movie : movies) {
            int movieID = movie.getId();
            String name = movie.getName();
            float totalBoxOffice = ticketStatistics.getTotalBoxOfficeByMovieID(movieID);
            movieTotalBoxOfficeVOS.add(new MovieTotalBoxOfficeVO(movieID, totalBoxOffice, name));
        }
        movieTotalBoxOfficeVOS.sort((MovieTotalBoxOfficeVO m1, MovieTotalBoxOfficeVO m2) -> (int) m2.getBoxOffice() - (int) m1.getBoxOffice());
        Response response = Response.success();
        response.setContent(movieTotalBoxOfficeVOS);
        return response;
    }

    /**
     * 获得num天后的日期
     *
     * @param oldDate 之前的日期
     * @param num     天数
     * @return 之后的日期
     */
    private Date getNumDayAfterDate(Date oldDate, int num) {
        Calendar calendarTime = Calendar.getInstance();
        calendarTime.setTime(oldDate);
        calendarTime.add(Calendar.DAY_OF_YEAR, num);
        return calendarTime.getTime();
    }

}