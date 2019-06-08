package edu.nju.cinemasystem.blservices.impl.vip;

import edu.nju.cinemasystem.data.po.Vipcard;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.dataservices.vip.RechargeRecordMapper;
import edu.nju.cinemasystem.dataservices.vip.VipcardMapper;
import edu.nju.cinemasystem.dataservices.vip.VipcardRechargeReductionMapper;
import edu.nju.cinemasystem.util.properties.message.VIPMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class VIPCardImpl implements edu.nju.cinemasystem.blservices.vip.VIPCard {
    private final VipcardMapper vipcardMapper;
    private final VIPMsg vipMsg;
    private final VipcardRechargeReductionMapper reductionMapper;
    private final RechargeRecordMapper recordMapper;

    @Autowired
    public VIPCardImpl(VipcardMapper vipcardMapper, VIPMsg vipMsg, VipcardRechargeReductionMapper reductionMapper, RechargeRecordMapper recordMapper) {
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
    public Response deposit(int userID, double amount) {
        Response response = Response.success();
        Vipcard vipcard = vipcardMapper.selectByPrimaryKey(userID);
        if (vipcard == null) {
            return Response.fail(vipMsg.getNoVIPCard());
        }
        vipcard.setBalance(vipcard.getBalance() + (float) amount);
        if (vipcardMapper.updateByPrimaryKeySelective(vipcard) == 0) {
            response = Response.fail(vipMsg.getOperationFailed());
            response.setStatusCode(500);
        } else {
            response.setContent(vipcardMapper.selectByPrimaryKey(userID));
        }
        return response;
    }

    @Override
    public Response pay(int userID, double amount) {
        Response response = Response.success();
        Vipcard vipcard = vipcardMapper.selectByPrimaryKey(userID);
        if (vipcard == null) {
            return Response.fail(vipMsg.getNoVIPCard());
        }
        float balanceAfterPayment = vipcard.getBalance() - (float) amount;
        if (balanceAfterPayment < 0) {
            response = Response.fail();
            response.setMessage(vipMsg.getOutOfBalance());
            response.setContent(vipcard);
        } else {
            vipcard.setBalance(balanceAfterPayment);
            vipcardMapper.updateByPrimaryKeySelective(vipcard);
        }
        return response;
    }
}
