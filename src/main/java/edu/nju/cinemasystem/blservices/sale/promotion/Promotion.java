package edu.nju.cinemasystem.blservices.sale.promotion;

import edu.nju.cinemasystem.data.vo.Form.PromotionForm;
import edu.nju.cinemasystem.data.vo.Response;

public interface Promotion {

    /**
     * TODO: 发布优惠活动
     * 
     * @return
     */
    Response publishPromotion(PromotionForm promotionForm);

    /**
     * TODO: 获取所有的优惠获得
     * 
     * @return
     */
    Response getAllPromotions();


}
