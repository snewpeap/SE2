package edu.nju.cinemasystem.blservices.impl.cinema;

import edu.nju.cinemasystem.blservices.cinema.hall.HallManage;
import edu.nju.cinemasystem.data.po.Hall;
import edu.nju.cinemasystem.data.po.Seat;
import edu.nju.cinemasystem.data.vo.HallVO;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.HallForm;
import edu.nju.cinemasystem.dataservices.cinema.hall.HallMapper;
import edu.nju.cinemasystem.dataservices.cinema.hall.SeatMapper;
import edu.nju.cinemasystem.util.properties.message.GlobalMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HallManageImpl implements HallManage{

    @Autowired
    HallMapper hallMapper;
    @Autowired
    GlobalMsg globalMsg;
    @Autowired
    SeatMapper seatMapper;

    @Override
    public Response InputHallInfo(HallForm hallForm) {
        Response response;
        String name = hallForm.getName();
        int column = hallForm.getColumn();
        int row = hallForm.getRow();
        byte size = 0;
        switch (hallForm.getSize()) {
            case "大":
                break;
            case "中":
                size = 1;
                break;
            case "小":
                size = 2;
                break;
            default:
                response = Response.fail();
                response.setMessage(globalMsg.getWrongParam());
                return response;
        }

        byte isImax = (byte) (hallForm.getIsImax() ? 1 : 0);
        byte is3d = (byte) (hallForm.getIs3d() ? 1 : 0);
        Hall hall = new Hall(name, column, row, size, isImax, is3d);
        try {
            //TODO 这个id不确定
            hallMapper.insertSelective(hall);
            int id = hall.getId();
            for(int i = 1; i <= hallForm.getRow(); i++){
                for(int j = 1; j <= hallForm.getColumn(); j++){
                    Seat seat = new Seat(j,i,id);
                    seatMapper.insertSelective(seat);
                }
            }
            response = Response.success();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.fail();
            response.setMessage(globalMsg.getWrongParam());
            return response;
        }

    }

    @Override
    public Response getAllHallInfo() {
        Response response;
        List<Hall> hallList = hallMapper.selectAll();
        List<HallVO> hallVOs = new ArrayList<HallVO>();
        for (Hall hall : hallList) {
            hallVOs.add(new HallVO(hall));
        }
        response = Response.success();
        return response;
    }

    @Override
    public Response modifyHallInfo(HallForm hallForm, int ID) {
        Response response;
        if(!censorHallForm(hallForm)){
            response = Response.fail();
            response.setMessage(globalMsg.getWrongParam());
            return response;
        }
        Hall hall = assembleHall(hallForm);
        hall.setId(ID);
        try {
            hallMapper.updateByPrimaryKeySelective(hall);
            response = Response.success();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.fail();
            response.setMessage(globalMsg.getWrongParam());
            return response;
        }
    }

    /**
     * 检查HallForm里的数据是否合法
     * @param hallForm
     * @return
     */
    private boolean censorHallForm(HallForm hallForm){
        if(!(hallForm.getSize().equals("大")||hallForm.getSize().equals("小")||hallForm.getSize().equals("中"))){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 装配一个HallPO
     * @param hallForm
     * @return
     */
    private Hall assembleHall(HallForm hallForm){
        String name = hallForm.getName();
        int column = hallForm.getColumn();
        int row = hallForm.getRow();
        byte size = 0;
        switch(hallForm.getSize()){
            case "中":
                size = 1;
                break;
            case "小":
                size = 2;
                break;
            default:
                break;
        }

        byte isImax = (byte) (hallForm.getIsImax() ? 1 : 0);
        byte is3d = (byte)(hallForm.getIs3d()? 1 : 0);
        Hall hall = new Hall(name,column,row,size,isImax,is3d);
        return hall;
    }
}