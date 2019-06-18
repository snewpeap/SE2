package edu.nju.cinemasystem.blservices.impl.sale.ticket;

import edu.nju.cinemasystem.blservices.sale.ticket.RefundTicketManage;
import edu.nju.cinemasystem.data.po.RefundStrategy;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.RefundStrategyForm;
import edu.nju.cinemasystem.data.vo.statisticsVO.RefundStrategyVO;
import edu.nju.cinemasystem.dataservices.sale.ticket.RefundStrategyMapper;
import edu.nju.cinemasystem.util.properties.message.GlobalMsg;
import edu.nju.cinemasystem.util.properties.message.TicketMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RefundTicketManageImpl implements RefundTicketManage {

    private final RefundStrategyMapper refundStrategyMapper;
    private final TicketMsg ticketMsg;
    private final GlobalMsg globalMsg;

    @Autowired
    public RefundTicketManageImpl(RefundStrategyMapper refundStrategyMapper, TicketMsg ticketMsg, GlobalMsg globalMsg) {
        this.refundStrategyMapper = refundStrategyMapper;
        this.ticketMsg = ticketMsg;
        this.globalMsg = globalMsg;
    }

    @Override
    @Transactional
    public Response addRefundTicketManage(RefundStrategyForm refundStrategyForm) {
        Response response;
        int days = refundStrategyForm.getDay();
        if (refundStrategyMapper.selectByPrimaryKey(days) != null) {
            response = Response.fail();
            response.setMessage(ticketMsg.getRefundStrategyExist());
            return response;
        }
        byte refundable = refundStrategyForm.getRefundable() ? (byte) 1 : (byte) 0;
        float percentage = refundStrategyForm.getPercentage();
        refundStrategyMapper.insert(RefundStrategy.assembleRefundStrategyPO(days, refundable, percentage));
        response = Response.success();
        response.setMessage(globalMsg.getOperationSuccess());
        return response;
    }

    @Override
    @Transactional
    public Response modifyRefundTicketManage(RefundStrategyForm refundStrategyForm) {
        Response response;
        int days = refundStrategyForm.getDay();
        byte refundable = refundStrategyForm.getRefundable() ? (byte) 1 : (byte) 0;
        float percentage = refundStrategyForm.getPercentage();
        refundStrategyMapper.updateByPrimaryKeySelective(RefundStrategy.assembleRefundStrategyPO(days, refundable, percentage));
        response = Response.success();
        response.setMessage(globalMsg.getOperationSuccess());
        return response;
    }

    @Override
    public Response getAllRefunds(){
        List<RefundStrategy> refundStrategies = refundStrategyMapper.selectAll();
        List<RefundStrategyVO> refundStrategyVOS = new ArrayList<>();
        refundStrategies.forEach(refundStrategy -> refundStrategyVOS.add(new RefundStrategyVO(refundStrategy)));
        Response response = Response.success();
        response.setContent(refundStrategyVOS);
        return response;
    }
}
