package edu.nju.cinemasystem.blservices.impl.sale.promotion;

import edu.nju.cinemasystem.blservices.movie.PromotionInfo;
import edu.nju.cinemasystem.data.po.PromotionHasMovie;
import edu.nju.cinemasystem.dataservices.sale.promotion.PromotionHasMovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionInfoImpl implements PromotionInfo {

    final
    private PromotionHasMovieMapper promotionHasMovieMapper;

    @Autowired
    public PromotionInfoImpl(PromotionHasMovieMapper promotionHasMovieMapper) {
        this.promotionHasMovieMapper = promotionHasMovieMapper;
    }

    @Override
    public List<Integer> getJoinedPromotionOf(int movieID) {
        List<PromotionHasMovie> promotionHasMovies = promotionHasMovieMapper.selectByMovieID(movieID);
        List<Integer> promotionIDs = new ArrayList<>();
        promotionHasMovies.forEach(promotionHasMovie -> promotionIDs.add(promotionHasMovie.getPromotionId()));
        return promotionIDs;
    }
}
