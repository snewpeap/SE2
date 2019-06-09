package edu.nju.cinemasystem.blservices.impl.cinema;

import edu.nju.cinemasystem.blservices.cinema.arrangement.ArrangementManage;
import edu.nju.cinemasystem.blservices.cinema.hall.HallManage;
import edu.nju.cinemasystem.blservices.movie.ArrangementInfo;
import edu.nju.cinemasystem.data.po.Arrangement;
import edu.nju.cinemasystem.data.po.ArrangementSeat;
import edu.nju.cinemasystem.data.po.Seat;
import edu.nju.cinemasystem.data.vo.ArrangementSeatVO;
import edu.nju.cinemasystem.data.vo.ArrangementVO;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.ArrangementForm;
import edu.nju.cinemasystem.dataservices.cinema.arrangement.ArrangementMapper;
import edu.nju.cinemasystem.dataservices.cinema.arrangement.ArrangementSeatMapper;
import edu.nju.cinemasystem.dataservices.cinema.hall.SeatMapper;
import edu.nju.cinemasystem.util.properties.message.ArrangementMsg;
import edu.nju.cinemasystem.util.properties.message.GlobalMsg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    HallManage hallManage;

    @Autowired
    public ArrangementImpl(ArrangementMapper arrangementMapper, GlobalMsg globalMsg, ArrangementSeatMapper arrangementSeatMapper, SeatMapper seatMapper, ArrangementMsg arrangementMsg, HallManage hallManage) {
        this.arrangementMapper = arrangementMapper;
        this.globalMsg = globalMsg;
        this.arrangementSeatMapper = arrangementSeatMapper;
        this.seatMapper = seatMapper;
        this.arrangementMsg = arrangementMsg;
        this.hallManage = hallManage;
    }

    @Override
    public boolean movieHasArrangement(int movieID) {
        List<Arrangement> arrangementList = arrangementMapper.selectByMovieID(movieID);
        return arrangementList != null;
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
    public void changeArrangementSeatStatus(int arrangementID, int seatID, Byte status) {
        ArrangementSeat arrangementSeat = new ArrangementSeat(status, arrangementID, seatID);
        arrangementSeatMapper.updateSeatStatus(arrangementSeat);
    }

    @Override
    public Response addArrangement(ArrangementForm arrangementForm) {
        Response response;
        if (censorArrangementForm(arrangementForm)) {
            response = Response.fail();
            response.setMessage(globalMsg.getWrongParam());
            return response;
        }
        Arrangement arrangement = assembleArrangement(arrangementForm);
        //TODO 这个ID不确定
        arrangementMapper.insertSelective(arrangement);
        int id = arrangement.getId();
        List<Seat> seats = seatMapper.selectByHallID(arrangementForm.getHallId());
        seats.forEach(seat -> {
            ArrangementSeat arrangementSeat = new ArrangementSeat((byte) 0, id, seat.getId());
            arrangementSeatMapper.insertSelective(arrangementSeat);
        });
        response = Response.success();
        response.setMessage(globalMsg.getOperationSuccess());
        return response;
    }

    @Override
    public Response modifyArrangement(ArrangementForm arrangementForm, int ID) {
        Response response;
        if (arrangementMapper.selectByPrimaryKey(ID).getVisibleDate().compareTo(new Date()) <= 0) {
            response = Response.fail();
            response.setMessage(arrangementMsg.getVisibleToAudience());
            return response;
        }
        if (censorArrangementForm(arrangementForm)) {
            response = Response.fail();
            response.setMessage(globalMsg.getWrongParam());
            return response;
        }
        Arrangement arrangement = assembleArrangement(arrangementForm);
        arrangement.setId(ID);
        arrangementMapper.updateByPrimaryKeySelective(arrangement);
        response = Response.success();
        response.setMessage(globalMsg.getOperationSuccess());
        return response;
    }

    @Override
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
        int duration = 0; //TODO 数据层接口可能会变 
        List<Arrangement> arrangements = arrangementMapper.selectByHallIDAndStartDate(hallID, startDate, duration);
        List<ArrangementVO> arrangementVOS = new ArrayList<>();
        arrangements.forEach(arrangement -> arrangementVOS.add(new ArrangementVO(arrangement)));
        Response response = Response.success();
        response.setContent(arrangementVOS);
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
        return hallManage.getHallNameByID(hallID);
    }

    /**
     * 检查ArrangementForm里的参数是否合法
     *
     * @param arrangementForm 排片表单
     * @return boolean值
     */
    private boolean censorArrangementForm(ArrangementForm arrangementForm) {
        return (arrangementForm.getStartTime().compareTo(arrangementForm.getEndTime()) >= 0)
                || (arrangementForm.getVisibleDate().compareTo(new Date()) < 0) || (arrangementForm.getFare() <= 0);
    }

    /**
     * 装配一个ArrangementPO
     *
     * @param arrangementForm 排片表单
     * @return arrangement
     */
    private Arrangement assembleArrangement(ArrangementForm arrangementForm) {
        Date startTime = arrangementForm.getStartTime();
        Date endTime = arrangementForm.getEndTime();
        Float fare = arrangementForm.getFare();
        Integer hallId = arrangementForm.getHallId();
        Integer movieId = arrangementForm.getMovieId();
        Date visibleDate = arrangementForm.getVisibleDate();
        return new Arrangement(startTime, endTime, fare, hallId, movieId, visibleDate);
    }
}