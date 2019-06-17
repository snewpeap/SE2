package edu.nju.cinemasystem.blservices.impl.cinema;

import edu.nju.cinemasystem.blservices.movie.ArrangementInfo;
import edu.nju.cinemasystem.data.po.Arrangement;
import edu.nju.cinemasystem.dataservices.cinema.arrangement.ArrangementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArrangementInfoImpl implements ArrangementInfo {

    private final ArrangementMapper arrangementMapper;

    @Autowired
    public ArrangementInfoImpl(ArrangementMapper arrangementMapper) {
        this.arrangementMapper = arrangementMapper;
    }

    @Override
    public boolean movieHasArrangement(int movieID) {
        List<Arrangement> arrangementList = arrangementMapper.selectByMovieID(movieID);
        return arrangementList.size()!=0;
    }
}
