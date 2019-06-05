package edu.nju.cinemasystem.blservices.sale.promotion;

import edu.nju.cinemasystem.data.vo.Form.PromotionForm;
import edu.nju.cinemasystem.data.vo.Response;

public interface Promotion {

    /**
     * 发布优惠活动
     * 
     * @return
     */
    Response publishPromotion(PromotionForm promotionForm);

    /**
     * 获取所有的优惠活动
     * 
     * @return
     */
    Response getAllPromotions();


}
