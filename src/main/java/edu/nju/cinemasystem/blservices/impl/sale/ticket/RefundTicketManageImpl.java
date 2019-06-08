package edu.nju.cinemasystem.blservices.impl.sale.ticket;

import edu.nju.cinemasystem.data.po.RefundStrategy;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.RefundStrategyForm;
import edu.nju.cinemasystem.dataservices.sale.ticket.RefundStrategyMapper;
import edu.nju.cinemasystem.util.properties.message.TicketMsg;
import edu.nju.cinemasystem.util.properties.message.GlobalMsg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefundTicketManageImpl implements edu.nju.cinemasystem.blservices.sale.ticket.Manage.RefundTicketManage {

    @Autowired
    RefundStrategyMapper refundStrategyMapper;
    @Autowired
    TicketMsg ticketMsg;
    @Autowired
    GlobalMsg globalMsg;

    @Override
    public Response addRefundTicketManage(RefundStrategyForm refundStrategyForm) {
        Response response;
        int days = refundStrategyForm.getDay();
        if(refundStrategyMapper.selectByPrimaryKey(days) != null){
            response = Response.fail();
            response.setMessage(ticketMsg.getRefundStrategyExist());
            return response;
        }
        byte refundable = refundStrategyForm.getRefundable()? (byte)1 : (byte)0;
        float percentage = refundStrategyForm.getPercentage();
        try {
            refundStrategyMapper.insert(new RefundStrategy(days,refundable,percentage));
            response = Response.success();
            response.setMessage(globalMsg.getOperationSuccess());
        }catch (Exception e){
            e.printStackTrace();
            response = Response.fail();
            response.setMessage(globalMsg.getOperationFailed());
        }
        return response;
    }

    @Override
    public Response modifyRefundTicketManage(RefundStrategyForm refundStrategyForm) {
        Response response;
        int days = refundStrategyForm.getDay();
        byte refundable = refundStrategyForm.getRefundable()? (byte)1 : (byte)0;
        float percentage = refundStrategyForm.getPercentage();
        try {
            refundStrategyMapper.updateByPrimaryKeySelective(new RefundStrategy(days,refundable,percentage));
            response = Response.success();
            response.setMessage(globalMsg.getOperationSuccess());
        }catch (Exception e){
            e.printStackTrace();
            response = Response.fail();
            response.setMessage(globalMsg.getWrongParam());
        }
        return response;
    }
}
