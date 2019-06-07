package edu.nju.cinemasystem.blservices.impl.cinema;

import edu.nju.cinemasystem.blservices.movie.ArrangementInfo;
import edu.nju.cinemasystem.data.vo.Response;
import org.springframework.stereotype.Service;

@Service
public class ArrangementImpl implements
        edu.nju.cinemasystem.blservices.cinema.arrangement.Arrangement, ArrangementInfo {
    @Override
    public boolean movieHasArrangement(int movieID) {
        return false;
    }

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