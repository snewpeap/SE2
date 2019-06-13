package edu.nju.cinemasystem.blservices.impl.cinema;

import edu.nju.cinemasystem.blservices.cinema.arrangement.ArrangementManage;
import edu.nju.cinemasystem.blservices.movie.ArrangementInfo;
import edu.nju.cinemasystem.blservices.movie.Movie;
import edu.nju.cinemasystem.data.po.Arrangement;
import edu.nju.cinemasystem.data.po.ArrangementSeat;
import edu.nju.cinemasystem.data.po.Seat;
import edu.nju.cinemasystem.data.vo.ArrangementSeatVO;
import edu.nju.cinemasystem.data.vo.ArrangementVO;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.ArrangementForm;
import edu.nju.cinemasystem.dataservices.cinema.arrangement.ArrangementMapper;
import edu.nju.cinemasystem.dataservices.cinema.arrangement.ArrangementSeatMapper;
import edu.nju.cinemasystem.dataservices.cinema.hall.HallMapper;
import edu.nju.cinemasystem.dataservices.cinema.hall.SeatMapper;
import edu.nju.cinemasystem.dataservices.movie.MovieMapper;
import edu.nju.cinemasystem.util.properties.message.ArrangementMsg;
import edu.nju.cinemasystem.util.properties.message.GlobalMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArrangementImpl
        implements edu.nju.cinemasystem.blservices.cinema.arrangement.Arrangement,
        ArrangementInfo, ArrangementManage {
    private final
    ArrangementMapper arrangementMapper;
    private final
    GlobalMsg globalMsg;
    private final
    ArrangementSeatMapper arrangementSeatMapper;
    private final
    SeatMapper seatMapper;
    private final
    ArrangementMsg arrangementMsg;
    private final
    HallMapper hallMapper;
    private final MovieMapper movieMapper;

    @Autowired
    public ArrangementImpl(ArrangementMapper arrangementMapper, GlobalMsg globalMsg, ArrangementSeatMapper arrangementSeatMapper, SeatMapper seatMapper, ArrangementMsg arrangementMsg, HallMapper hallMapper, MovieMapper movieMapper) {
        this.arrangementMapper = arrangementMapper;
        this.globalMsg = globalMsg;
        this.arrangementSeatMapper = arrangementSeatMapper;
        this.seatMapper = seatMapper;
        this.arrangementMsg = arrangementMsg;
        this.hallMapper = hallMapper;
        this.movieMapper = movieMapper;
    }

    @Override
    public boolean movieHasArrangement(int movieID) {
        List<Arrangement> arrangementList = arrangementMapper.selectByMovieID(movieID);
        return arrangementList.size()!=0;
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
            response.setMessage(globalMsg.getWrongParam());
            return response;
        }
    }

    @Override
    public Response getByMovieID(int movieID) {
        Response response;
        List<Arrangement> arrangements = arrangementMapper.selectByMovieID(movieID);
        if (arrangements == null) {
            response = Response.fail();
            response.setMessage(globalMsg.getWrongParam());
            return response;
        } else {
            List<ArrangementVO> arrangementVOs = new ArrayList<>();
            arrangements.forEach(arrangement -> arrangementVOs.add(new ArrangementVO(arrangement)));
            response = Response.success();
            response.setContent(arrangementVOs);
            return response;
        }
    }

    @Override
    public Response getSeatMap(int aID) {
        Response response;
        Arrangement arrangement = arrangementMapper.selectByPrimaryKey(aID);
        if (arrangement == null) {
            response = Response.fail();
            response.setMessage(globalMsg.getWrongParam());
            return response;
        }
        Date date = new Date();
        if (date.compareTo(arrangement.getVisibleDate()) < 0) {
            response = Response.fail();
            response.setStatusCode(401);
            return response;
        }
        List<ArrangementSeat> arrangementSeats = arrangementSeatMapper.selectByArrangementID(aID);
        List<ArrangementSeatVO> arrangementSeatVOs = new ArrayList<>();
        arrangementSeats.forEach(arrangementSeat -> arrangementSeatVOs.add(new ArrangementSeatVO(arrangementSeat)));
        response = Response.success();
        response.setContent(arrangementSeatVOs);
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
        Response response;
        if (unCensorArrangementForm(arrangementForm)) {
            response = Response.fail();
            response.setMessage(globalMsg.getWrongParam());
            return response;
        }
        response = censorTimeConflict(arrangementForm,0);
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
        response.setMessage(globalMsg.getOperationSuccess());
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
        if (unCensorArrangementForm(arrangementForm)) {
            response = Response.fail();
            response.setMessage(globalMsg.getWrongParam());
            return response;
        }
        response = censorTimeConflict(arrangementForm,ID);
        if (!response.isSuccess()) {
            return response;
        }
        Arrangement arrangement = assembleArrangementByForm(arrangementForm);
        arrangement.setId(ID);
        arrangementMapper.updateByPrimaryKeySelective(arrangement);
        response = Response.success();
        response.setMessage(globalMsg.getOperationSuccess());
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
        response.setMessage(globalMsg.getOperationSuccess());
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
        response.setMessage(globalMsg.getOperationSuccess());
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

    /**
     * 检查ArrangementForm里的参数是否不合法
     *
     * @param arrangementForm 排片表单
     * @return boolean值
     */
    private boolean unCensorArrangementForm(ArrangementForm arrangementForm) {
        Date date = new Date();
        Date completeTime = new Date(arrangementForm.getStartTime().getTime()+ (movieMapper.selectByPrimaryKey(arrangementForm.getMovieId()).getDuration() * 60 * 1000));
        return (arrangementForm.getStartTime().compareTo(arrangementForm.getEndTime()) >= 0)
                ||(arrangementForm.getFare() <= 0) || (arrangementForm.getStartTime().compareTo(date) < 0) || (arrangementForm.getEndTime().compareTo(date) < 0)
                || arrangementForm.getEndTime().before(completeTime);
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
    private Response censorTimeConflict(ArrangementForm arrangementForm,int ID) {
        int hallID = arrangementForm.getHallId();
        List<Arrangement> arrangements = arrangementMapper.selectByDay(arrangementForm.getStartTime(), arrangementForm.getEndTime());
        for (Arrangement arrangement : arrangements) {
            if (arrangement.getHallId() == hallID && ((ID != 0 && arrangement.getId() != ID)||ID == 0)) {
                return Response.fail(arrangementMsg.getIsAlreadyHaveArrangement());
            }
        }
        return Response.success();
    }

    /**
     * 通过参数组装一个ArrangementPO
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param fare 票价
     * @param hallId 影厅ID
     * @param movieId 电影ID
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
}