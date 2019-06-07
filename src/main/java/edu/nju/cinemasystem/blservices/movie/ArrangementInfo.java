package edu.nju.cinemasystem.blservices.movie;

public interface ArrangementInfo {
    /**
     * 查找电影此刻之后还有没有排片
     * @param movieID 电影
     * @return 此刻之后有没有排片
     */
    boolean movieHasArrangement(int movieID);
}
