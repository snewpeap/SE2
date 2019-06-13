package edu.nju.cinemasystem.blservices.impl.vip;

import edu.nju.cinemasystem.data.po.TradeRecord;
import edu.nju.cinemasystem.data.po.Vipcard;
import edu.nju.cinemasystem.data.po.VipcardRechargeReduction;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.dataservices.vip.TradeRecordMapper;
import edu.nju.cinemasystem.dataservices.vip.VipcardMapper;
import edu.nju.cinemasystem.dataservices.vip.VipcardRechargeReductionMapper;
import edu.nju.cinemasystem.util.properties.message.VIPMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Service
public class VIPCardImpl implements edu.nju.cinemasystem.blservices.vip.VIPCard {
    private final VipcardMapper vipcardMapper;
    private final VIPMsg vipMsg;
    private final VipcardRechargeReductionMapper reductionMapper;
    private final TradeRecordMapper recordMapper;

    @Autowired
    public VIPCardImpl(VipcardMapper vipcardMapper, VIPMsg vipMsg, VipcardRechargeReductionMapper reductionMapper, TradeRecordMapper recordMapper) {
        this.vipcardMapper = vipcardMapper;
        this.vipMsg = vipMsg;
        this.reductionMapper = reductionMapper;
        this.recordMapper = recordMapper;
    }

    @Override
    public Response getVIPCard(@NotNull int userID) {
        Response response = Response.success();
        Vipcard vipcard = vipcardMapper.selectByPrimaryKey(userID);
        if (vipcard == null) {
            return Response.fail(vipMsg.getNoVIPCard());
        } else {
            response.setContent(vipcard);
        }
        return response;
    }

    @Override
    public Response addVIPCard(int userID) {
        Response response = Response.success();
        if (vipcardMapper.selectByPrimaryKey(userID) != null) {
            return Response.fail(vipMsg.getHasVIPCard());
        }
        if (vipcardMapper.insertSelective(new Vipcard(userID)) == 0) {
            response = Response.fail();
            response.setMessage(vipMsg.getAddFailed());
        } else {
            response.setContent(vipcardMapper.selectByPrimaryKey(userID));
        }
        return response;
    }

    @Override
    public Response getRechargeReduction() {
        Response response = Response.success();
        Integer allReduction = null;
        response.setContent(reductionMapper.selectByPrimaryKey(allReduction));
        //TODO
        return response;
    }

    @Override
    public Response getRechargeHistory(int userID) {
        Response response = Response.success();
        if (vipcardMapper.selectByPrimaryKey(userID) == null) {
            return Response.fail(vipMsg.getNoVIPCard());
        }
        response.setContent(recordMapper.selectByUserID(userID));
        return response;
    }

    @Override
    public Response deposit(int userID, float amount) {
        VipcardRechargeReduction reduction = reductionMapper.selectByAmount(amount);
        float discountAmount = amount - reduction.getDiscountAmount();
        Response response = addVIPBalance(userID, amount);
        if (response.isSuccess()){
            TradeRecord tradeRecord = new TradeRecord();
            tradeRecord.setDate(new Date());
            tradeRecord.setUserId(userID);
            tradeRecord.setOriginalAmount(amount);
            tradeRecord.setDiscountAmount(discountAmount);
            recordMapper.insertSelective(tradeRecord);
        }
        return response;
    }

    @Override
    public Response pay(int userID, float amount) {
        Response response = Response.success();
        Vipcard vipcard = vipcardMapper.selectByPrimaryKey(userID);
        if (vipcard == null) {
            return Response.fail(vipMsg.getNoVIPCard());
        }
        float balanceAfterPayment = vipcard.getBalance() - amount;
        if (balanceAfterPayment < 0) {
            response = Response.fail();
            response.setMessage(vipMsg.getOutOfBalance());
        } else {
            TradeRecord tradeRecord = new TradeRecord();
            tradeRecord.setDate(new Date());
            tradeRecord.setUserId(userID);
            tradeRecord.setOriginalAmount(vipcard.getBalance());
            tradeRecord.setDiscountAmount(amount);
            vipcard.setBalance(balanceAfterPayment);
            vipcardMapper.updateByPrimaryKeySelective(vipcard);
            recordMapper.insertSelective(tradeRecord);
        }
        response.setContent(vipcard);
        return response;
    }

    @Override
    public Response addVIPBalance(int userID, float amount) {
        Response response = Response.success();
        Vipcard vipcard = vipcardMapper.selectByPrimaryKey(userID);
        if (vipcard == null) {
            return Response.fail(vipMsg.getNoVIPCard());
        }
        vipcard.setBalance(vipcard.getBalance() + amount);
        if (vipcardMapper.updateByPrimaryKeySelective(vipcard) == 0) {
            response = Response.fail(vipMsg.getOperationFailed());
            response.setStatusCode(500);
        } else {
            response.setContent(vipcardMapper.selectByPrimaryKey(userID));
        }
        return response;
    }
}
