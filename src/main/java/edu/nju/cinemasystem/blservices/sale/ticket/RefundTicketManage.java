package edu.nju.cinemasystem.blservices.sale.ticket;

import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.RefundStrategyForm;

public interface RefundTicketManage {
    
    /**
     * 新增退票策略
     * 
     * @param refundStrategyForm 退票策略表单
     * @return 是否成功
     */
    Response addRefundTicketManage(RefundStrategyForm refundStrategyForm);
    
    /**
     * 修改退票策略
     * 
     * @param refundStrategyForm 退票策略表单
     * @return 是否成功
     */
    Response modifyRefundTicketManage(RefundStrategyForm refundStrategyForm);

    /**
     * 获取所有退票策略
     *
     * @return 所有退票策略
     */
    Response getAllRefunds();
}