package edu.nju.cinemasystem.blservices.impl.sale.promotion;

import edu.nju.cinemasystem.blservices.movie.MovieService;
import edu.nju.cinemasystem.blservices.movie.PromotionInfo;
import edu.nju.cinemasystem.blservices.sale.promotion.CouponService;
import edu.nju.cinemasystem.blservices.sale.promotion.PromotionService;
import edu.nju.cinemasystem.blservices.vip.VIPCouponBusiness;
import edu.nju.cinemasystem.data.po.Coupon;
import edu.nju.cinemasystem.data.po.Promotion;
import edu.nju.cinemasystem.data.po.PromotionHasMovie;
import edu.nju.cinemasystem.data.vo.CouponVO;
import edu.nju.cinemasystem.data.vo.PromotionVO;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.PromotionForm;
import edu.nju.cinemasystem.dataservices.sale.promotion.CouponMapper;
import edu.nju.cinemasystem.dataservices.sale.promotion.PromotionHasMovieMapper;
import edu.nju.cinemasystem.dataservices.sale.promotion.PromotionMapper;
import edu.nju.cinemasystem.util.exception.ServiceException;
import edu.nju.cinemasystem.util.properties.message.GlobalMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PromotionImpl
        implements PromotionService, CouponService, VIPCouponBusiness, PromotionInfo {

    private final PromotionMapper promotionMapper;
    private final PromotionHasMovieMapper promotionHasMovieMapper;
    private final GlobalMsg globalMsg;
    private final CouponMapper couponMapper;
    private MovieService movieService;

    @Autowired
    public PromotionImpl(PromotionMapper promotionMapper, PromotionHasMovieMapper promotionHasMovieMapper, GlobalMsg globalMsg, CouponMapper couponMapper) {
        this.promotionMapper = promotionMapper;
        this.promotionHasMovieMapper = promotionHasMovieMapper;
        this.globalMsg = globalMsg;
        this.couponMapper = couponMapper;
    }

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public Response getAllPromotions() {
        List<Promotion> promotions = promotionMapper.selectAll();
        Response response;
        List<PromotionVO> promotionVOs = new ArrayList<>();
        for (Promotion promotion : promotions) {
            PromotionVO promotionVO = new PromotionVO(promotion);
            List<String> movieList = new ArrayList<>();
            if (promotion.getSpecifyMovies() == 1) {
                List<PromotionHasMovie> promotionHasMovies = promotionHasMovieMapper
                        .selectByPromotionID(promotion.getId());
                promotionHasMovies.forEach(promotionHasMovie -> movieList.add(movieService.getMovieNameByID(promotionHasMovie.getMovieId())));
            }
            promotionVO.setMovieList(movieList);
            promotionVOs.add(promotionVO);
        }
        response = Response.success();
        response.setContent(promotionVOs);
        return response;
    }

    @Override
    @Transactional
    public Response publishPromotion(PromotionForm promotionForm) {
        String name = promotionForm.getName();
        Date startTime = promotionForm.getStartTime();
        Date endTime = promotionForm.getEndTime();
        Byte specifyMovies = (byte) (promotionForm.getSpecifyMovies() ? 1 : 0);
        Float targetAmount = promotionForm.getTargetAmount();
        Float couponAmount = promotionForm.getCouponAmount();
        Integer couponExpiration = promotionForm.getCouponExpiration();
        String description = promotionForm.getDescription();
        Promotion promotion = Promotion.assemblePromotionPO(name, startTime, endTime, specifyMovies, targetAmount, couponAmount,
                couponExpiration, description);
        Response response;
        try {
            promotionMapper.insert(promotion);
            int promotionID = promotion.getId();
            if (promotionForm.getSpecifyMovies()) {
                List<Integer> movieIDs = promotionForm.getMovieIDs();
                movieIDs.forEach(movieID -> promotionHasMovieMapper.insertSelective(PromotionHasMovie.assemblePromotionHasMoviePO(promotionID, movieID)));
            }
            response = Response.success();
            response.setMessage(globalMsg.getOperationSuccess());
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.fail();
            response.setMessage(globalMsg.getWrongParam());
        }
        return response;
    }

    @Override
    public Response getAvailableCouponsByUser(int userId) {
        Response response = Response.success();
        response.setContent(getAllAvailableCouponsByUser(userId));
        return response;
    }

    @Override
    public List<CouponVO> getAvailableCouponsByUserAndTickets(int userId, float totalAmount) {
        List<CouponVO> couponVOs = getAllAvailableCouponsByUser(userId);
        couponVOs.removeIf(couponVO -> couponVO.getTargetAmount() > totalAmount);
        return couponVOs;
    }

    /**
     * 获取用户所有能用的优惠券
     *
     * @param userId 用户ID
     * @return List<CouponVO>
     */
    private List<CouponVO> getAllAvailableCouponsByUser(int userId) {
        List<Coupon> coupons = couponMapper.selectByUserID(userId);
        List<CouponVO> couponVOs = new ArrayList<>();
        Date date = new Date();
        for (Coupon coupon : coupons) {
            if (date.compareTo(coupon.getEndTime()) < 0) {
                CouponVO couponVO = new CouponVO(coupon);
                Promotion promotion = promotionMapper.selectByPrimaryKey(coupon.getPromotionId());
                couponVO.setTargetAmount(promotion.getTargetAmount());
                couponVO.setDiscountAmount(promotion.getCouponAmount());
                couponVO.setStartDay(new Date(coupon.getEndTime().getTime() - (promotion.getCouponExpiration() * 24 * 60 * 60 * 1000)));
                couponVO.setPromotionDescription(promotion.getDescription());
                couponVO.setPromotionName(promotion.getName());
                couponVOs.add(couponVO);
            }
        }
        return couponVOs;
    }

    @Override
    @Transactional
    public void removeCouponByID(int ID) {
        couponMapper.deleteByPrimaryKey(ID);
    }

    @Override
    @Transactional
    public void sendCouponsToUser(int userID, int movieID) {
        List<Promotion> allPromotions = promotionMapper.selectAll();
        List<Promotion> availablePromotions = new ArrayList<>();
        Date date = new Date();
        for (Promotion promotion : allPromotions) {
            if (promotion.getSpecifyMovies() == (byte) 0 &&
                    date.after(promotion.getStartTime()) && date.before(promotion.getEndTime())) {
                availablePromotions.add(promotion);
            }
        }
        List<PromotionHasMovie> promotionHasMovies = promotionHasMovieMapper.selectByMovieID(movieID);
        List<Integer> promotionIDs = new ArrayList<>();
        promotionHasMovies.forEach(promotionHasMovie -> promotionIDs.add(promotionHasMovie.getPromotionId()));
        for (int ID : promotionIDs) {
            Promotion promotion = promotionMapper.selectByPrimaryKey(ID);
            if (date.after(promotion.getStartTime()) && date.before(promotion.getEndTime())) {
                availablePromotions.add(promotion);
            }
        }
        for (Promotion promotion : availablePromotions) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, promotion.getCouponExpiration());
            Date endTime = c.getTime();
            Coupon coupon = Coupon.assembleCouponPO(endTime, promotion.getId(), userID);
            couponMapper.insertSelective(coupon);
        }
    }

    @Override
    public float getCouponAmountByID(int ID) {
        return promotionMapper.selectByPrimaryKey(couponMapper.selectByPrimaryKey(ID).getPromotionId()).getCouponAmount();
    }

    @Override
    @Transactional
    public void presentCouponTo(int userID, List<Integer> promotionIDs) throws ServiceException {
        List<Integer> success = new ArrayList<>();
        for (int promotionID : promotionIDs) {
            Promotion promotion = promotionMapper.selectByPrimaryKey(promotionID);
            Date endTime = addDate(new Date(), promotion.getCouponExpiration());
            Coupon coupon = Coupon.assembleCouponPO(endTime, promotionID, userID);
            int i = couponMapper.insertSelective(coupon);
            if (i != 0) {
                success.add(promotionID);
            } else {
                ServiceException serviceException = new ServiceException();
                serviceException.setFail(promotionID);
                serviceException.setSuccessList(success);
                throw serviceException;
            }
        }
    }

    @Override
    public List<String> getJoinedPromotionOf(int movieID) {
        List<PromotionHasMovie> promotionHasMovies = promotionHasMovieMapper.selectByMovieID(movieID);
        List<Promotion> allPromotions = promotionMapper.selectAll();
        List<String> promotionNames = new ArrayList<>();
        for (Promotion promotion : allPromotions) {
            if (promotion.getSpecifyMovies() == (byte) 0 && promotion.getEndTime().after(movieService.getReleaseTimeByID(movieID))) {
                promotionNames.add(promotion.getName());
            }
        }
        promotionHasMovies.forEach(promotionHasMovie -> promotionNames.add(promotionMapper.selectByPrimaryKey(promotionHasMovie.getPromotionId()).getName()));
        return promotionNames;
    }

    /**
     * 给指定的日期加上天数
     *
     * @param date 指定的日期
     * @param day  天数
     * @return 增加天数后的日期
     */
    private Date addDate(Date date, int day) {
        long time = date.getTime();
        long delayTime = day * 24 * 60 * 60 * 1000L;
        return new Date(time + delayTime);
    }
}