package edu.nju.cinemasystem.blservices.movie;

public interface StatisticsInfo {

    /**
     * 获取电影的热度，返回值是该电影票房与最高票房的比值
     * @param movieID 电影ID
     * @return 热度
     */
    double getHeatOf(int movieID);
}
