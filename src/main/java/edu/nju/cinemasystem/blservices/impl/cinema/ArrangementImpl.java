package edu.nju.cinemasystem.blservices.impl.cinema;

import org.springframework.stereotype.Service;

import edu.nju.cinemasystem.blservices.cinema.arrangement.Arrangement;
import edu.nju.cinemasystem.data.vo.Response;

@Service
public class ArrangementImpl implements Arrangement {

    @Override
    public Response getArrangement(int aID) {
        return null;
    }

    @Override
    public Response getByMovieID(int movieID) {
        return null;
    }

    @Override
    public Response getSeatMap(int aID) {
        return null;
    }
    
}