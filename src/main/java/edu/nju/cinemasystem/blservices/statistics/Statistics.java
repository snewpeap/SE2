package edu.nju.cinemasystem.blservices.statistics;

import java.util.Date;

import edu.nju.cinemasystem.data.vo.Response;

public interface Statistics {

    /**
     * 获取某日各影片排片率统计数据
     * @param date 时间
     * @return List<MovieScheduleTimesVO>
     */
    Response getScheduleRateByDate(Date date);

    /**
     * 获取所有电影的累计票房(降序排序，且包含已下架的电影)
     * @return List<MovieTotalBoxOfficeVO>
     */
    Response getTotalBoxOffice();

    /**
     * 客单价：（某天的客单价=当天观众购票所花金额/购票人次数）
     * 返回值为过去7天内每天客单价
     * @return List<AudiencePriceVO>
     */
    Response getAudiencePrice(int days);


    /**
     * 获取所有电影某天的上座率
     * 上座率参考公式：假设某影城设有n 个电影厅、m 个座位数，相对上座率=观众人次÷放映场次÷m÷n×100%
     * 此方法中运用到的相应的操作数据库的接口和实现请自行定义和实现，相应的结果需要自己定义一个VO类返回给前端
     * @param date 日期
     * @return List<MoviePlacingRateVO>
     */
    Response getMoviePlacingRateByDate(Date date);

    /**
     * 获取最近days天内，最受欢迎的movieNum个电影(可以简单理解为最近days内票房越高的电影越受欢迎)
     * 此方法中运用到的相应的操作数据库的接口和实现请自行定义和实现，相应的结果需要自己定义一个VO类返回给前端
     * @param days 天数
     * @param movieNum 电影数量
     * @return List<MoviePopularityVO>
     */
    Response getPopularMovies(int days, int movieNum);
}
