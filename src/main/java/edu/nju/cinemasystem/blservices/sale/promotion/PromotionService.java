package edu.nju.cinemasystem.blservices.sale.promotion;

import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.PromotionForm;

public interface PromotionService {

    /**
     * 发布优惠活动
     * 
     * @return 是否成功
     */
    Response publishPromotion(PromotionForm promotionForm);

    /**
     * 获取所有的优惠活动
     * 
     * @return List<PromotionVO>
     */
    Response getAllPromotions();




}
