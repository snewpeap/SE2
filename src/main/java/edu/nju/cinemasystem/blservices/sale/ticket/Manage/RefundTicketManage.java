package edu.nju.cinemasystem.blservices.sale.ticket.Manage;

import edu.nju.cinemasystem.data.vo.Form.RefundStrategyForm;
import edu.nju.cinemasystem.data.vo.Response;

public interface RefundTicketManage {
    
    /**
     * TODO：新增退票策略
     * 
     * @param refundStrategyForm
     * @return
     */
    Response addRefundTicketManage(RefundStrategyForm refundStrategyForm);
    
    /**
     * TODO：修改退票策略
     * 
     * @param refundStrategyForm
     * @return
     */
    Response modifyRefundTicketManage(RefundStrategyForm refundStrategyForm);
}