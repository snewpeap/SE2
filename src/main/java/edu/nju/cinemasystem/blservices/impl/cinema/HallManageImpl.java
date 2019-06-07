package edu.nju.cinemasystem.blservices.impl.cinema;

import edu.nju.cinemasystem.blservices.cinema.hall.HallManage;
import edu.nju.cinemasystem.data.po.Hall;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.HallForm;
import edu.nju.cinemasystem.dataservices.cinema.hall.HallMapper;
import edu.nju.cinemasystem.util.properties.message.GlobalMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HallManageImpl implements HallManage {

    @Autowired
    HallMapper hallMapper;
    @Autowired
    GlobalMsg globalMsg;

    @Override
    public Response InputHallInfo(HallForm hallForm) {
        Response response;
        String name = hallForm.getName();
        int column = hallForm.getColumn();
        int row = hallForm.getRow();
        byte size = 0;
        switch(hallForm.getSize()){
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
        byte is3d = (byte)(hallForm.getIs3d()? 1 : 0);
        Hall hall = new Hall(name,column,row,size,isImax,is3d);
        //TODO:没有捕获异常
        hallMapper.insert(hall);
        response = Response.success();
        return response;
    }

    @Override
    public Response getAllHallInfo() {
        return null;
    }

    @Override
    public Response modifyHallInfo(HallForm hallForm) {
        return null;
    }
    
}