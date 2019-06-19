package edu.nju.cinemasystem.blservices.impl.statistics;

import edu.nju.cinemasystem.blservices.cinema.arrangement.ArrangementManage;
import edu.nju.cinemasystem.blservices.cinema.hall.HallManage;
import edu.nju.cinemasystem.blservices.movie.MovieManagement;
import edu.nju.cinemasystem.blservices.movie.StatisticsInfo;
import edu.nju.cinemasystem.blservices.sale.ticket.TicketStatistics;
import edu.nju.cinemasystem.blservices.statistics.Statistics;
import edu.nju.cinemasystem.data.po.Arrangement;
import edu.nju.cinemasystem.data.po.Movie;
import edu.nju.cinemasystem.data.vo.BaseMovieVO;
import edu.nju.cinemasystem.data.vo.ManagerMovieVO;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.statisticsVO.*;
import edu.nju.cinemasystem.util.properties.message.GlobalMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StatisticsImpl implements Statistics, StatisticsInfo {

    private final TicketStatistics ticketStatistics;
    private GlobalMsg globalMsg;
    private final ArrangementManage arrangementManage;
    private final MovieManagement movieManagement;
    private final HallManage hallManage;

    @Autowired
    public StatisticsImpl(TicketStatistics ticketStatistics, ArrangementManage arrangementManage, MovieManagement movieManagement, HallManage hallManage) {
        this.ticketStatistics = ticketStatistics;
        this.arrangementManage = arrangementManage;
        this.movieManagement = movieManagement;
        this.hallManage = hallManage;
    }

    @Qualifier(value = "globalMsg")
    @Autowired
    public void setGlobalMsg(GlobalMsg globalMsg) {
        this.globalMsg = globalMsg;
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date formatDate;
        try {
            formatDate = simpleDateFormat.parse(simpleDateFormat.format(date));
        } catch (ParseException e) {
            return Response.fail(globalMsg.getWrongParam() + " : 时间格式错误");
        }

        List<Arrangement> arrangements = arrangementManage.getArrangementsByDay(formatDate, getNumDayAfterDate(formatDate, 1));
        Map<Integer, List<Arrangement>> arrangementsByMovie = new HashMap<>();
        arrangements.forEach(arrangement -> {
            Integer movieID = arrangement.getMovieId();
            if (!arrangementsByMovie.containsKey(movieID)) {
                arrangementsByMovie.put(movieID, new ArrayList<>());
            }
            arrangementsByMovie.get(movieID).add(arrangement);
        });

        List<MoviePlacingRateVO> moviePlacingRateVOS = new ArrayList<>();
        for (Map.Entry<Integer, List<Arrangement>> entry : arrangementsByMovie.entrySet()) {
            int movieID = entry.getKey();
            List<Arrangement> arrangementList = entry.getValue();
            int audienceNum = 0;
            int seatNum = 0;
            for (Arrangement arrangement : arrangementList) {
                int aID = arrangement.getId();
                audienceNum += ticketStatistics.getNumOfTicketsByArrangement(aID);
                seatNum += hallManage.getSeatNumByHallID(arrangement.getHallId());
            }
            double averageSeatNum
                    = new BigDecimal(seatNum).divide(new BigDecimal(arrangementList.size()), 2,BigDecimal.ROUND_HALF_UP).doubleValue();
            double rate = 0.0;
            if (arrangementList.size() != 0 && averageSeatNum != 0) {
                BigDecimal relativeSeatNum = new BigDecimal(arrangementList.size()).multiply(new BigDecimal(averageSeatNum));
                rate = new BigDecimal(audienceNum).divide(relativeSeatNum,2,BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            Movie movie = movieManagement.getMovieByID(movieID);
            String movieName = movie.getName();
            moviePlacingRateVOS.add(new MoviePlacingRateVO(date, movieID, rate, movieName));
        }
        Response response = Response.success();
        response.setContent(moviePlacingRateVOS);
        return response;
    }

    @Override
    public Response getPopularMovies(int days, int movieNum) {
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
        } catch (ParseException ignored) {
        }
        Date startDate = getNumDayAfterDate(today, -(days - 1));
        List<Movie> movies = movieManagement.getReleasedMovies();
        List<MoviePopularityVO> moviePopularityVOS = new ArrayList<>();
        for (Movie movie : movies) {
            ManagerMovieVO movieVO = new ManagerMovieVO();
            BaseMovieVO.assembleMovieVO(movie, movieVO);
            float boxOffice = ticketStatistics.getBoxOfficeByMovieIDAndDay(movie.getId(), startDate, today);
            moviePopularityVOS.add(new MoviePopularityVO(movieVO, boxOffice));
        }
        moviePopularityVOS.sort((MoviePopularityVO m1, MoviePopularityVO m2) -> (int) m2.getBoxOffice() - (int) m1.getBoxOffice());
        List<MoviePopularityVO> resultVOs = new ArrayList<>();
        if (moviePopularityVOS.size() > movieNum) {
            for (int i = 0; i < movieNum; i++) {
                resultVOs.add(moviePopularityVOS.get(i));
            }
        } else {
            resultVOs.addAll(moviePopularityVOS);
        }
        Response response = Response.success();
        response.setContent(resultVOs);
        return response;
    }

    @Override
    public Response getScheduleRateByDate(Date date) {
        Date startDate = (date == null) ? new Date() : date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate = simpleDateFormat.parse(simpleDateFormat.format(startDate));
        } catch (ParseException e) {
            return Response.fail(globalMsg.getWrongParam() + " : 时间格式错误");
        }
        Date endDate = getNumDayAfterDate(startDate, 1);
        List<Arrangement> arrangements = arrangementManage.getArrangementsByDay(startDate, endDate);
        Map<Integer, List<Arrangement>> scheduleMapByMovie = new HashMap<>();

        arrangements.forEach(arrangement -> {
            Integer movieID = arrangement.getMovieId();
            if (!scheduleMapByMovie.containsKey(arrangement.getMovieId())) {
                scheduleMapByMovie.put(movieID, new ArrayList<>());
            }
            scheduleMapByMovie.get(movieID).add(arrangement);
        });
        List<MovieScheduleTimesVO> movieScheduleTimesVOS = new ArrayList<>();
        for (Map.Entry<Integer, List<Arrangement>> entry : scheduleMapByMovie.entrySet()) {
            int movieID = entry.getKey();
            int times = entry.getValue().size();
            String movieName = movieManagement.getMovieByID(movieID).getName();
            movieScheduleTimesVOS.add(new MovieScheduleTimesVO(movieID, times, movieName));
        }

        Response response = Response.success();
        response.setContent(movieScheduleTimesVOS);
        return response;
    }

    @Override
    public Response getTotalBoxOffice() {
        List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeVOS = getDescendingMovieTotalBoxOffices();
        Response response = Response.success();
        response.setContent(movieTotalBoxOfficeVOS);
        return response;
    }

    @Override
    public double getHeatOf(int movieID) {
        List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeVOS = getDescendingMovieTotalBoxOffices();
        double movieBoxOffice = 0;
        for (MovieTotalBoxOfficeVO movieTotalBoxOfficeVO : movieTotalBoxOfficeVOS) {
            if (movieTotalBoxOfficeVO.getMovieId() == movieID) {
                movieBoxOffice = movieTotalBoxOfficeVO.getBoxOffice();
                break;
            }
        }
        if (movieTotalBoxOfficeVOS.size() != 0) {
            double maxBoxOffice = movieTotalBoxOfficeVOS.get(0).getBoxOffice() == 0 ? 1 : movieTotalBoxOfficeVOS.get(0).getBoxOffice();
            return movieBoxOffice / maxBoxOffice;
        } else {
            return 0;
        }
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

    /**
     * 获得降序排列的电影票房VOs
     *
     * @return List<MovieTotalBoxOfficeVO>
     */
    private List<MovieTotalBoxOfficeVO> getDescendingMovieTotalBoxOffices() {
        List<Movie> movies = movieManagement.getReleasedMovies();
        List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeVOS = new ArrayList<>();
        for (Movie movie : movies) {
            int movieID = movie.getId();
            String name = movie.getName();
            float totalBoxOffice = ticketStatistics.getTotalBoxOfficeByMovieID(movieID);
            movieTotalBoxOfficeVOS.add(new MovieTotalBoxOfficeVO(movieID, totalBoxOffice, name));
        }
        movieTotalBoxOfficeVOS.sort((MovieTotalBoxOfficeVO m1, MovieTotalBoxOfficeVO m2) -> (int) m2.getBoxOffice() - (int) m1.getBoxOffice());
        return movieTotalBoxOfficeVOS;
    }


}