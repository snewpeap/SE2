package edu.nju.cinemasystem.blservices.sale.ticket.Manage;

import edu.nju.cinemasystem.data.vo.RefundStrategyForm;
import edu.nju.cinemasystem.data.vo.Response;

public interface RefundTicketManage {
    
    /**
     * 新增退票策略
     * 
     * @param refundStrategyForm
     * @return
     */
    Response addRefundTicketManage(RefundStrategyForm refundStrategyForm);
    
    /**
     * 修改退票策略
     * 
     * @param refundStrategyForm
     * @return
     */
    Response modifyRefundTicketManage(RefundStrategyForm refundStrategyForm);
}