package edu.nju.cinemasystem.blservices.impl.cinema;

import edu.nju.cinemasystem.blservices.cinema.arrangement.ArrangementManage;
import edu.nju.cinemasystem.blservices.movie.ArrangementInfo;
import edu.nju.cinemasystem.data.po.*;
import edu.nju.cinemasystem.data.vo.ArrangementDetailVO;
import edu.nju.cinemasystem.data.vo.ArrangementSeatVO;
import edu.nju.cinemasystem.data.vo.ArrangementVO;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.ArrangementForm;
import edu.nju.cinemasystem.dataservices.cinema.arrangement.ArrangementMapper;
import edu.nju.cinemasystem.dataservices.cinema.arrangement.ArrangementSeatMapper;
import edu.nju.cinemasystem.dataservices.cinema.hall.HallMapper;
import edu.nju.cinemasystem.dataservices.cinema.hall.SeatMapper;
import edu.nju.cinemasystem.util.properties.message.ArrangementMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ArrangementImpl
        implements edu.nju.cinemasystem.blservices.cinema.arrangement.Arrangement,
        ArrangementManage, ArrangementInfo {
    private final ArrangementMapper arrangementMapper;
    private final ArrangementSeatMapper arrangementSeatMapper;
    private final SeatMapper seatMapper;
    private final ArrangementMsg arrangementMsg;
    private final HallMapper hallMapper;
    private edu.nju.cinemasystem.blservices.movie.Movie movieService;

    @Autowired
    public ArrangementImpl(ArrangementMapper arrangementMapper, ArrangementSeatMapper arrangementSeatMapper, SeatMapper seatMapper, ArrangementMsg arrangementMsg, HallMapper hallMapper) {
        this.arrangementMapper = arrangementMapper;
        this.arrangementSeatMapper = arrangementSeatMapper;
        this.seatMapper = seatMapper;
        this.arrangementMsg = arrangementMsg;
        this.hallMapper = hallMapper;
    }

    @Autowired
    public void setMovieService(edu.nju.cinemasystem.blservices.movie.Movie movieService) {
        this.movieService = movieService;
    }

    @Override
    public Response getArrangement(int aID) {
        Response response;
        Arrangement arrangement = arrangementMapper.selectByPrimaryKey(aID);
        if (arrangement != null) {
            ArrangementVO arrangementVO = new ArrangementVO(arrangement);
            response = Response.success();
            response.setContent(arrangementVO);
            return response;
        } else {
            response = Response.fail();
            response.setMessage(arrangementMsg.getWrongParam());
            return response;
        }
    }

    @Override
    public Response getByMovieID(int movieID) {
        Response response;
        List<Arrangement> arrangements = arrangementMapper.selectByMovieID(movieID);
        if (arrangements == null) {
            response = Response.fail();
            response.setMessage(arrangementMsg.getWrongParam());
            return response;
        } else {
            List<ArrangementVO> arrangementVOs = new ArrayList<>();
            arrangements.forEach(arrangement -> arrangementVOs.add(new ArrangementVO(arrangement)));
            Map<Date, List<ArrangementVO>> map = new HashMap<>();
            for (ArrangementVO arrangementVO : arrangementVOs) {
                Date date = convertDateToDay(arrangementVO.getStartTime());
                if (!map.containsKey(date)) {
                    map.put(date, new ArrayList<>());
                }
                map.get(date).add(arrangementVO);
            }
            Map<Date, List<ArrangementVO>> reMap = new HashMap<>();
            Object[] objects = map.keySet().toArray();
            Arrays.sort(objects);
            for (Object o : objects) {
                List<ArrangementVO> as = map.get(o);
                as.sort((ArrangementVO a1, ArrangementVO a2) -> (int) ((a1.getStartTime().getTime() - a2.getStartTime().getTime()) / (1000 * 60)));
                reMap.put((Date) o, map.get(o));
            }
            List<List<ArrangementVO>> reList = new ArrayList<>();
            for (Map.Entry<Date, List<ArrangementVO>> entry : reMap.entrySet()) {
                reList.add(entry.getValue());
            }
            response = Response.success();
            response.setContent(reList);
            return response;
        }
    }

    @Override
    public Response getSeatMap(int aID) {
        Response response;
        Arrangement arrangement = arrangementMapper.selectByPrimaryKey(aID);
        if (arrangement == null) {
            return Response.fail(arrangementMsg.getWrongParam());
        }
        Date date = new Date();
        if (date.before(arrangement.getVisibleDate())) {
            response = Response.fail();
            response.setStatusCode(401);
            return response;
        }
        List<ArrangementSeat> arrangementSeats = arrangementSeatMapper.selectByArrangementID(aID);
        Hall hall = hallMapper.selectByPrimaryKey(arrangement.getHallId());
        List<ArrangementSeatVO[]> seatMap = new ArrayList<>(hall.getRow());
        for (int row = 0; row < hall.getRow(); row++) {
            seatMap.add(new ArrangementSeatVO[hall.getColumn()]);
        }
        for (ArrangementSeat as : arrangementSeats) {
            Seat seat = seatMapper.selectByPrimaryKey(as.getSeatId());
            seatMap.get(seat.getRow()-1)[seat.getColumn()-1] = new ArrangementSeatVO(as);
        }
        ArrangementDetailVO detailVO = new ArrangementDetailVO();
        detailVO.setSeatMap(seatMap);
        detailVO.setId(aID);
        detailVO.setStartTime(arrangement.getStartTime());
        detailVO.setEndTime(arrangement.getEndTime());
        detailVO.setFare(arrangement.getFare());
        detailVO.setHall(hallMapper.selectByPrimaryKey(arrangement.getHallId()).getName());
        detailVO.setMovie(movieService.getMovieNameByID(arrangement.getMovieId()));
        response = Response.success();
        response.setContent(detailVO);
        return response;
    }

    @Override
    public float getFareByID(int arrangementID) {
        return arrangementMapper.selectByPrimaryKey(arrangementID).getFare();
    }

    @Override
    @Transactional
    public void changeArrangementSeatStatus(int arrangementID, int seatID, boolean locked) {
        ArrangementSeat arrangementSeat
                = ArrangementSeat.assembleArrangementSeat(locked ? (byte) 1 : (byte) 0, arrangementID, seatID);
        arrangementSeatMapper.updateSeatStatus(arrangementSeat);
    }

    @Override
    @Transactional
    public Response addArrangement(ArrangementForm arrangementForm) {
        Response response = unCensorArrangementForm(arrangementForm);
        if (!response.isSuccess()) {
            return response;
        }
        response = censorTimeConflict(arrangementForm, 0);
        if (!response.isSuccess()) {
            return response;
        }
        Arrangement arrangement = assembleArrangementByForm(arrangementForm);
        arrangementMapper.insertSelective(arrangement);
        int id = arrangement.getId();
        List<Seat> seats = seatMapper.selectByHallID(arrangementForm.getHallId());
        seats.forEach(seat -> {
            ArrangementSeat arrangementSeat = ArrangementSeat.assembleArrangementSeat((byte) 0, id, seat.getId());
            arrangementSeatMapper.insertSelective(arrangementSeat);
        });
        response = Response.success();
        response.setMessage(arrangementMsg.getOperationSuccess());
        return response;
    }

    @Override
    @Transactional
    public Response modifyArrangement(ArrangementForm arrangementForm, int ID) {
        Response response;
        if (arrangementMapper.selectByPrimaryKey(ID).getVisibleDate().compareTo(new Date()) <= 0) {
            response = Response.fail();
            response.setMessage(arrangementMsg.getVisibleToAudience());
            return response;
        }
        response = unCensorArrangementForm(arrangementForm);
        if (!response.isSuccess()) {
            return response;
        }
        response = censorTimeConflict(arrangementForm, ID);
        if (!response.isSuccess()) {
            return response;
        }
        Arrangement arrangement = assembleArrangementByForm(arrangementForm);
        arrangement.setId(ID);
        arrangementMapper.updateByPrimaryKeySelective(arrangement);
        response = Response.success();
        response.setMessage(arrangementMsg.getOperationSuccess());
        return response;
    }

    @Override
    @Transactional
    public Response removeArrangement(int ID) {
        Response response;
        if (arrangementMapper.selectByPrimaryKey(ID).getVisibleDate().compareTo(new Date()) <= 0) {
            response = Response.fail();
            response.setMessage(arrangementMsg.getVisibleToAudience());
            return response;
        }
        List<ArrangementSeat> arrangementSeats = arrangementSeatMapper.selectByArrangementID(ID);
        arrangementSeats.forEach(arrangementSeatMapper::delete);
        arrangementMapper.deleteByPrimaryKey(ID);
        response = Response.success();
        response.setMessage(arrangementMsg.getOperationSuccess());
        return response;
    }

    @Override
    @Transactional
    public Response modifyVisibleDay(List<Integer> IDs, Date date) {
        List<Arrangement> arrangements = new ArrayList<>();
        IDs.forEach(ID -> arrangements.add(arrangementMapper.selectByPrimaryKey(ID)));
        Response response;
        arrangements.forEach(arrangement -> {
            arrangement.setVisibleDate(date);
            arrangementMapper.updateByPrimaryKeySelective(arrangement);
        });
        response = Response.success();
        response.setMessage(arrangementMsg.getOperationSuccess());
        return response;
    }

    @Override
    public Response getArrangementsByHallID(int hallID, Date startDate) {
        int duration = 7; //TODO 数据层接口可能会变
        List<Arrangement> arrangements = arrangementMapper.selectByHallIDAndStartDate(hallID, startDate, duration);
        List<List<ArrangementVO>> daySeparatedVOs = new ArrayList<>(7);
        for (int i = 0; i < duration; i++) {
            daySeparatedVOs.add(new ArrayList<>());
        }
        for (Arrangement arrangement : arrangements) {
            int day = (int) ((arrangement.getStartTime().getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
            daySeparatedVOs.get(day).add(new ArrangementVO(arrangement));
        }
        Response response = Response.success();
        response.setContent(daySeparatedVOs);
        return response;
    }

    @Override
    public boolean isArrangementStart(int ID) {
        Arrangement arrangement = arrangementMapper.selectByPrimaryKey(ID);
        return (new Date()).compareTo(arrangement.getStartTime()) >= 0;
    }

    @Override
    public int getMovieIDbyID(int ID) {
        return arrangementMapper.selectByPrimaryKey(ID).getMovieId();
    }

    @Override
    public List<Arrangement> getArrangementsByDay(Date startDate, Date endDate) {
        return arrangementMapper.selectByDay(startDate, endDate);
    }

    @Override
    public Date[] getStartDateAndEndDate(int ID) {
        Arrangement arrangement = arrangementMapper.selectByPrimaryKey(ID);
        Date[] dates = new Date[2];
        dates[0] = arrangement.getStartTime();
        dates[1] = arrangement.getEndTime();
        return dates;
    }

    @Override
    public String getHallNameByArrangementID(int ID) {
        Arrangement arrangement = arrangementMapper.selectByPrimaryKey(ID);
        int hallID = arrangement.getHallId();
        return hallMapper.selectByPrimaryKey(hallID).getName();
    }

    @Override
    public boolean haveArrangementAfterCurrentTime(int hallID, Date currentTime) {
        return arrangementMapper.selectByHallIDAndCurrentTime(hallID, currentTime).size() != 0;
    }

    @Override
    public boolean isSeatBeenLocked(int arrangementID, int seatID) {
        return arrangementSeatMapper.select(
                ArrangementSeat.assembleArrangementSeat((byte) 1, arrangementID, seatID)
        ).getIsLocked() == 1;
    }

    @Override
    public boolean movieHasArrangement(int movieID) {
        List<Arrangement> arrangementList = arrangementMapper.selectByMovieID(movieID);
        return arrangementList.size() != 0;
    }

    /**
     * 检查ArrangementForm里的参数是否不合法
     *
     * @param arrangementForm 排片表单
     * @return boolean值
     */
    private Response unCensorArrangementForm(ArrangementForm arrangementForm) {
        Response response = Response.fail();
        Date date = new Date();
        Movie movie = movieService.getMoviePOByID(arrangementForm.getMovieId());
        Date completeTime = new Date(arrangementForm.getStartTime().getTime() + (movieService.getDurationTimeByID(arrangementForm.getMovieId()) * 60 * 1000));
        if (arrangementForm.getStartTime().compareTo(arrangementForm.getEndTime()) >= 0 || (arrangementForm.getStartTime().compareTo(date) < 0) || (arrangementForm.getEndTime().compareTo(date) < 0) || (convertDateToDay(arrangementForm.getStartTime()).compareTo(convertDateToDay(arrangementForm.getEndTime())) != 0)) {
            response.setMessage(arrangementMsg.getTimeConflict());
        } else if ((arrangementForm.getFare() <= 0)) {
            response.setMessage(arrangementMsg.getFareCannotBeNegative());
        } else if (arrangementForm.getEndTime().before(completeTime)) {
            response.setMessage(arrangementMsg.getDurationIsShort());
        } else if (arrangementForm.getStartTime().before(movie.getStartDate()) || movie.getStatus() == (byte) 2 || movie.getStatus() == (byte) 3) {
            response.setMessage(arrangementMsg.getMovieUnReleased());
        } else {
            response = Response.success();
        }
        return response;
    }

    /**
     * 通过表单VO装配一个ArrangementPO
     *
     * @param arrangementForm 排片表单
     * @return arrangement
     */
    private Arrangement assembleArrangementByForm(ArrangementForm arrangementForm) {
        Date startTime = arrangementForm.getStartTime();
        Date endTime = arrangementForm.getEndTime();
        Float fare = arrangementForm.getFare();
        Integer hallId = arrangementForm.getHallId();
        Integer movieId = arrangementForm.getMovieId();
        Date visibleDate = arrangementForm.getVisibleDate();
        return assembleArrangement(startTime, endTime, fare, hallId, movieId, visibleDate);
    }

    /**
     * 检查要添加的排片和之前的是否有时间段冲突
     *
     * @param arrangementForm 排片表单
     * @return 没有返回success
     */
    private Response censorTimeConflict(ArrangementForm arrangementForm, int ID) {
        int hallID = arrangementForm.getHallId();
        List<Arrangement> arrangements = arrangementMapper.selectByDay(arrangementForm.getStartTime(), arrangementForm.getEndTime());
        for (Arrangement arrangement : arrangements) {
            if (arrangement.getHallId() == hallID && ((ID != 0 && arrangement.getId() != ID) || ID == 0)) {
                return Response.fail(arrangementMsg.getIsAlreadyHaveArrangement());
            }
        }
        return Response.success();
    }

    /**
     * 通过参数组装一个ArrangementPO
     *
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @param fare        票价
     * @param hallId      影厅ID
     * @param movieId     电影ID
     * @param visibleDate 可见时间
     * @return ArrangementPO
     */
    private Arrangement assembleArrangement(Date startTime, Date endTime, Float fare, Integer hallId, Integer movieId, Date visibleDate) {
        Arrangement arrangement = new Arrangement();
        arrangement.setStartTime(startTime);
        arrangement.setEndTime(endTime);
        arrangement.setFare(fare);
        arrangement.setHallId(hallId);
        arrangement.setMovieId(movieId);
        arrangement.setVisibleDate(visibleDate);
        return arrangement;
    }

    private Date convertDateToDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

}