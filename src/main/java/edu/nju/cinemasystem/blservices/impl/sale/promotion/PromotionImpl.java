package edu.nju.cinemasystem.blservices.impl.sale.promotion;

import edu.nju.cinemasystem.blservices.movie.PromotionInfo;
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
import edu.nju.cinemasystem.util.properties.message.GlobalMsg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PromotionImpl implements
        edu.nju.cinemasystem.blservices.sale.promotion.Promotion,
        edu.nju.cinemasystem.blservices.sale.promotion.Coupon, PromotionInfo {

    @Autowired
    PromotionMapper promotionMapper;
    @Autowired
    PromotionHasMovieMapper promotionHasMovieMapper;
    @Autowired
    GlobalMsg globalMsg;
    @Autowired
    CouponMapper couponMapper;

    @Override
    public Response getAllPromotions() {
        List<Promotion> promotions = promotionMapper.selectAll();
        Response response;
        List<PromotionVO> promotionVOs = new ArrayList<PromotionVO>();
        for (Promotion promotion : promotions) {
            PromotionVO promotionVO = new PromotionVO(promotion);
            if (promotion.getSpecifyMovies() == 1) {
                List<PromotionHasMovie> promotionHasMovies = promotionHasMovieMapper
                        .selectByPromotionID(promotion.getId());
                List<Integer> movieList = new ArrayList<Integer>();
                promotionHasMovies.stream().forEach(promotionHasMovie -> {
                    movieList.add(promotionHasMovie.getMovieId());
                });
                promotionVO.setMovieList(movieList);
            }
            promotionVOs.add(promotionVO);
        }
        response = Response.success();
        response.setContent(promotionVOs);
        return response;
    }

    @Override
    public List<Integer> getJoinedPromotionOf(int movieID) {
        List<PromotionHasMovie> promotionHasMovies = promotionHasMovieMapper.selectByMovieID(movieID);
        List<Integer> promotionIDs = new ArrayList<Integer>();
        promotionHasMovies.stream().forEach(promotionHasMovie -> {
            promotionIDs.add(promotionHasMovie.getPromotionId());
        });
        return promotionIDs;
    }

    @Override
    public Response publishPromotion(PromotionForm promotionForm) {
        String name = promotionForm.getName();
        Date startTime = promotionForm.getStartTime();
        Date endTime = promotionForm.getEndTime();
        Byte specifyMovies = (byte) (promotionForm.getSpecifyMovies() ? 1 : 0);
        Float targetAmount = promotionForm.getTargetAmount();
        Float couponAmount = promotionForm.getCouponAmount();
        Integer couponExpiration = promotionForm.getCouponExpiration();
        String description = promotionForm.getDescription();
        Promotion promotion = new Promotion(name, startTime, endTime, specifyMovies, targetAmount, couponAmount,
                couponExpiration, description);
        Response response;
        try {
            promotionMapper.insert(promotion);
            int promotionID = promotion.getId();
            if(promotionForm.getSpecifyMovies()){
                List<Integer> movieIDs = promotionForm.getMovieIDs();
                movieIDs.stream().forEach(movieID->{
                    promotionHasMovieMapper.insertSelective(new PromotionHasMovie(promotionID, movieID));
                });
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
        Iterator<CouponVO> it = couponVOs.iterator();
        while(it.hasNext()){
            CouponVO couponVO = it.next();
            if(couponVO.getTargetAmount()>totalAmount){
                it.remove();
            }
        }
        return couponVOs;
    }

    /**
     * 获取用户所有能用的优惠券
     * @param userId
     * @return List<CouponVO>
     */
    private List<CouponVO> getAllAvailableCouponsByUser(int userId){
        List<Coupon> coupons = couponMapper.selectByUserID(userId);
        List<CouponVO> couponVOs = new ArrayList<CouponVO>();
        Date date = new Date();
        for (Coupon coupon : coupons) {
            if (date.compareTo(coupon.getEndTime()) < 0) {
                CouponVO couponVO = new CouponVO(coupon.getId());
                Promotion promotion = promotionMapper.selectByPrimaryKey(coupon.getPromotionId());
                couponVO.setTargetAmount(promotion.getTargetAmount());
                couponVO.setDiscountAmount(promotion.getCouponAmount());
                couponVOs.add(couponVO);
            }
        }
        return couponVOs;
    }

    @Override
    public void removeCouponByID(int ID) {
        try {
            couponMapper.deleteByPrimaryKey(ID);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendCouponsToUser(int userID, int movieID) {
        List<Promotion> allPromotions = promotionMapper.selectAll();
        List<Promotion> availablePromotions = new ArrayList<Promotion>();
        Date date = new Date();
        for (Promotion promotion: allPromotions){
            if(promotion.getSpecifyMovies()==(byte)0 &&
                    date.compareTo(promotion.getStartTime()) >= 0 && date.compareTo(promotion.getEndTime()) <=0){
                availablePromotions.add(promotion);
            }
        }
        List<PromotionHasMovie> promotionHasMovies = promotionHasMovieMapper.selectByMovieID(movieID);
        List<Integer> promotionIDs = new ArrayList<Integer>();
        promotionHasMovies.stream().forEach(promotionHasMovie ->{
            promotionIDs.add(promotionHasMovie.getPromotionId());
        });
        for(int ID:promotionIDs){
            Promotion promotion = promotionMapper.selectByPrimaryKey(ID);
            if(date.compareTo(promotion.getStartTime())>=0&&date.compareTo(promotion.getEndTime())<=0){
                availablePromotions.add(promotion);
            }
        }
        for(Promotion promotion:availablePromotions){
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, promotion.getCouponExpiration());
            Date endTime = c.getTime();
            Coupon coupon = new Coupon(endTime,promotion.getId(),userID);
            couponMapper.insertSelective(coupon);
        }
    }

    @Override
    public float getCouponAmountByID(int ID) {
        return promotionMapper.selectByPrimaryKey(couponMapper.selectByPrimaryKey(ID).getPromotionId()).getCouponAmount();
    }
}