package edu.nju.cinemasystem.blservices.impl.cinema;

import edu.nju.cinemasystem.blservices.cinema.arrangement.ArrangementManage;
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
        ArrangementInfo ,ArrangementManage {
    @Autowired
    ArrangementMapper arrangementMapper;
    @Autowired
    GlobalMsg globalMsg;
    @Autowired
    ArrangementSeatMapper arrangementSeatMapper;
    @Autowired
    SeatMapper seatMapper;
    @Autowired
    ArrangementMsg arrangementMsg;

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
            List<ArrangementVO> arrangementVOs = new ArrayList<ArrangementVO>();
            arrangements.stream().forEach(arrangement -> {
                arrangementVOs.add(new ArrangementVO(arrangement));
            });
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
        List<ArrangementSeatVO> arrangementSeatVOs = new ArrayList<ArrangementSeatVO>();
        arrangementSeats.stream().forEach(arrangementSeat -> {
            arrangementSeatVOs.add(new ArrangementSeatVO(arrangementSeat));
        });
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
        ArrangementSeat arrangementSeat = new ArrangementSeat(status,arrangementID,seatID);
        try {
            arrangementSeatMapper.updateSeatStatus(arrangementSeat);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Response addArrangement(ArrangementForm arrangementForm) {
        Response response;
        if(!censorArrangementForm(arrangementForm)){
            response = Response.fail();
            response.setMessage(globalMsg.getWrongParam());
            return response;
        }
        Arrangement arrangement = assembleArrangement(arrangementForm);
        try {
            //TODO 这个ID不确定
            arrangementMapper.insertSelective(arrangement);
            int id = arrangement.getId();
            List<Seat> seats = seatMapper.selectByHallID(arrangementForm.getHallId());
            seats.stream().forEach(seat->{
                ArrangementSeat arrangementSeat = new ArrangementSeat((byte)0,id,seat.getId());
                arrangementSeatMapper.insertSelective(arrangementSeat);
            });
            response = Response.success();
            response.setMessage(globalMsg.getOperationSuccess());
        }catch (Exception e){
            e.printStackTrace();
            response = Response.fail();
            response.setMessage(globalMsg.getOperationFailed());
        }
        return response;
    }

    @Override
    public Response modifyArrangement(ArrangementForm arrangementForm, int ID) {
        Response response;
        if(arrangementMapper.selectByPrimaryKey(ID).getVisibleDate().compareTo(new Date()) <= 0){
            response = Response.fail();
            response.setMessage(arrangementMsg.getVisibleToAudience());
            return  response;
        }
        if(!censorArrangementForm(arrangementForm)){
            response = Response.fail();
            response.setMessage(globalMsg.getWrongParam());
            return  response;
        }
        Arrangement arrangement = assembleArrangement(arrangementForm);
        try {
            arrangement.setId(ID);
            arrangementMapper.updateByPrimaryKeySelective(arrangement);
            response = Response.success();
            response.setMessage(globalMsg.getOperationSuccess());
        }catch (Exception e){
            e.printStackTrace();
            response = Response.fail();
            response.setMessage(globalMsg.getOperationFailed());
        }
        return response;
    }

    @Override
    public Response removeArrangement(int ID) {
        Response response;
        if(arrangementMapper.selectByPrimaryKey(ID).getVisibleDate().compareTo(new Date()) <= 0){
            response = Response.fail();
            response.setMessage(arrangementMsg.getVisibleToAudience());
            return  response;
        }
        try {
            List<ArrangementSeat> arrangementSeats = arrangementSeatMapper.selectByArrangementID(ID);
            arrangementSeats.stream().forEach(arrangementSeat ->{
                arrangementSeatMapper.delete(arrangementSeat);
            });
            arrangementMapper.deleteByPrimaryKey(ID);
            response = Response.success();
            response.setMessage(globalMsg.getOperationSuccess());
        }catch (Exception e){
            e.printStackTrace();
            response = Response.fail();
            response.setMessage(globalMsg.getOperationFailed());
        }
        return response;
    }

    @Override
    public Response modifyVisibleDay(List<Integer> IDs, Date date) {
        List<Arrangement> arrangements = new ArrayList<>();
        IDs.stream().forEach(ID->{
            arrangements.add(arrangementMapper.selectByPrimaryKey(ID));
        });
        Response response;
        try {
            arrangements.stream().forEach(arrangement -> {
                arrangement.setVisibleDate(date);
                arrangementMapper.updateByPrimaryKeySelective(arrangement);
            });
            response = Response.success();
            response.setMessage(globalMsg.getOperationSuccess());
        }catch (Exception e){
            e.printStackTrace();
            response = Response.fail();
            response.setMessage(globalMsg.getOperationFailed());
        }
        return response;
    }

    @Override
    public Response getArrangementsByHallID(int hallID, Date startDate) {
        int duration = 0; //TODO 数据层接口可能会变 
        List<Arrangement> arrangements = arrangementMapper.selectByHallIDAndStartDate(hallID, startDate, duration);
        List<ArrangementVO> arrangementVOS = new ArrayList<ArrangementVO>();
        arrangements.stream().forEach(arrangement -> {
            arrangementVOS.add(new ArrangementVO(arrangement));
        });
        Response response = Response.success();
        response.setContent(arrangementVOS);
        return response;
    }

    @Override
    public boolean isArrangementStart(int ID) {
        Arrangement arrangement = arrangementMapper.selectByPrimaryKey(ID);
        return (new Date()).compareTo(arrangement.getStartTime())>=0;
    }

    @Override
    public int getMovieIDbyID(int ID) {
        return arrangementMapper.selectByPrimaryKey(ID).getMovieId();
    }

    /**
     * 检查ArrangementForm里的参数是否合法
     * @param arrangementForm
     * @return
     */
    private boolean censorArrangementForm(ArrangementForm arrangementForm){
        return (arrangementForm.getStartTime().compareTo(arrangementForm.getEndTime()) < 0)
                && (arrangementForm.getVisibleDate().compareTo(new Date()) >= 0) && (arrangementForm.getFare() > 0);
    }

    /**
     * 装配一个ArrangementPO
     * @param arrangementForm
     * @return arrangement
     */
    private Arrangement assembleArrangement(ArrangementForm arrangementForm){
        Date startTime = arrangementForm.getStartTime();
        Date endTime = arrangementForm.getEndTime();
        Float fare = arrangementForm.getFare();
        Integer hallId = arrangementForm.getHallId();
        Integer movieId = arrangementForm.getMovieId();
        Date visibleDate = arrangementForm.getVisibleDate();
        return new Arrangement(startTime,endTime,fare,hallId,movieId,visibleDate);
    }
}