package edu.nju.cinemasystem.blservices.impl.vip;

import edu.nju.cinemasystem.blservices.vip.VIPCardService;
import edu.nju.cinemasystem.data.po.TradeRecord;
import edu.nju.cinemasystem.data.po.Vipcard;
import edu.nju.cinemasystem.data.po.VipcardRechargeReduction;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.dataservices.vip.TradeRecordMapper;
import edu.nju.cinemasystem.dataservices.vip.VipcardMapper;
import edu.nju.cinemasystem.dataservices.vip.VipcardRechargeReductionMapper;
import edu.nju.cinemasystem.util.properties.message.VIPMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;

@Service
public class VIPCardImpl implements VIPCardService {
    private final VipcardMapper vipcardMapper;
    private final VIPMsg vipMsg;
    private final VipcardRechargeReductionMapper reductionMapper;
    private final TradeRecordMapper recordMapper;
    private OrderHolder orderHolder;

    @Autowired
    public VIPCardImpl(VipcardMapper vipcardMapper, VIPMsg vipMsg, VipcardRechargeReductionMapper reductionMapper, TradeRecordMapper recordMapper) {
        this.vipcardMapper = vipcardMapper;
        this.vipMsg = vipMsg;
        this.reductionMapper = reductionMapper;
        this.recordMapper = recordMapper;
        this.orderHolder = new OrderHolder();
    }

    @PostConstruct
    public void startDQ() {
        orderHolder.run();
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
    public Response buyable(int userID) {
        if (vipcardMapper.selectByPrimaryKey(userID) != null) {
            return Response.fail(vipMsg.getHasVIPCard());
        }
        DelayedTask delayedTask = orderHolder.getTask(userID, false);
        if (delayedTask != null) {
            Response response = Response.fail();
            response.setStatusCode(777);
            response.setContent(delayedTask);
            return response;
        }
        Date now = new Date();
        delayedTask = new DelayedTask(
                "B" + now.getTime(),
                userID,
                now,
                20,
                20
        );
        orderHolder.addTask(delayedTask);
        Response response = Response.success();
        response.setContent(delayedTask);
        return response;
    }

    @Override
    public Response addVIPCard(int userID, String orderID) {
        Response response = Response.success();
        if (vipcardMapper.selectByPrimaryKey(userID) != null) {
            return Response.fail(vipMsg.getHasVIPCard());
        }
        Vipcard vipcard = new Vipcard();
        vipcard.setUserId(userID);
        if (vipcardMapper.insertSelective(vipcard) == 0) {
            response = Response.fail();
            response.setMessage(vipMsg.getAddFailed());
        } else {
            orderHolder.removeTask(orderID);
            response.setContent(vipcardMapper.selectByPrimaryKey(userID));
        }
        return response;
    }

    @Override
    public Response getRechargeReduction() {
        Response response = Response.success();
        Integer allReduction = null;
        response.setContent(reductionMapper.select(allReduction));
        return response;
    }

    @Override
    public Response getRechargeHistory(int userID) {
        Response response = Response.success();
        if (vipcardMapper.selectByPrimaryKey(userID) == null) {
            return Response.fail(vipMsg.getNoVIPCard());
        }
        List<TradeRecord> tradeRecords = recordMapper.selectByUserID(userID);
        if (tradeRecords != null) {
            tradeRecords.removeIf(tradeRecord -> tradeRecord.getDelta() <= 0);
        }
        response.setContent(tradeRecords);
        return response;
    }

    @Override
    public Response depositable(int userID, float realAmount) {
        if (vipcardMapper.selectByPrimaryKey(userID) == null) {
            return Response.fail(vipMsg.getNoVIPCard());
        } else if (realAmount <= 0) {
            return Response.fail(vipMsg.getWrongParam() + "金额需大于等于0");
        }
        DelayedTask delayedTask = orderHolder.getTask(userID, true);
        if (delayedTask != null) {
            Response response = Response.fail();
            response.setStatusCode(777);
            response.setContent(delayedTask);
            return response;
        }
        VipcardRechargeReduction reduction = reductionMapper.selectByAmount(realAmount);
        float amount = realAmount;
        if (reduction != null) {
            amount -= reduction.getDiscountAmount();
        }
        Date now = new Date();
        delayedTask = new DelayedTask(
                "D" + now.getTime(),
                userID,
                now,
                amount,
                realAmount);
        orderHolder.addTask(delayedTask);
        Response response = Response.success();
        response.setContent(delayedTask);
        return response;
    }

    @Override
    public Response deposit(int userID, String orderID) {
        if (vipcardMapper.selectByPrimaryKey(userID) == null) {
            return Response.fail(vipMsg.getNoVIPCard());
        }
        DelayedTask delayedTask = orderHolder.getTask(userID, true);
        if (delayedTask == null || !delayedTask.getID().equals(orderID)) {
            return Response.fail("订单不存在");
        }
        float realAmount = delayedTask.getRealAmount();
        float balanceBeforeDeposit = vipcardMapper.selectByPrimaryKey(userID).getBalance();
        Response response = addVIPBalance(userID, realAmount);
        if (response.isSuccess()) {
            TradeRecord tradeRecord = new TradeRecord();
            tradeRecord.setDate(new Date());
            tradeRecord.setUserId(userID);
            tradeRecord.setOriginalAmount(balanceBeforeDeposit);
            tradeRecord.setDelta(realAmount);
            recordMapper.insertSelective(tradeRecord);
            orderHolder.removeTask(orderID);
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
            tradeRecord.setDelta(0 - amount);
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

    private class OrderHolder implements Runnable {

        private final Logger LOG = LoggerFactory.getLogger(OrderHolder.class);
        DelayQueue<DelayedTask> delayQueue;

        OrderHolder() {
            this.delayQueue = new DelayQueue<>();
        }

        @Override
        public void run() {
            Executors.newSingleThreadExecutor().execute(() -> {
                DelayedTask delayedTask;
                while (true) {
                    try {
                        delayedTask = delayQueue.take();
                        LOG.info("将超时订单" + delayedTask.getID() + "移除");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        void addTask(DelayedTask task) {
            delayQueue.add(task);
        }

        void removeTask(String id) {
            for (DelayedTask task : delayQueue) {
                if (task.getID().equals(id)) {
                    delayQueue.remove(task);
                    LOG.info("将订单" + id + "主动移除");
                }
            }
        }

        DelayedTask getTask(int userID, boolean isDeposit) {
            for (DelayedTask task : delayQueue) {
                if (task.getUserID() == userID) {
                    if (task.getID().startsWith("D") && isDeposit) {
                        return task;
                    } else if (task.getID().startsWith("B") && !isDeposit) {
                        return task;
                    }
                }
            }
            return null;
        }
    }
}
