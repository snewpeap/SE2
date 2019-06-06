package edu.nju.cinemasystem.blservices.impl.cinema;

import org.springframework.stereotype.Service;

import edu.nju.cinemasystem.blservices.cinema.hall.HallManage;
import edu.nju.cinemasystem.data.vo.HallForm;
import edu.nju.cinemasystem.data.vo.Response;

@Service
public class HallManageImpl implements HallManage {

    @Override
    public Response enterHallInfo(HallForm hallForm) {
        return null;
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