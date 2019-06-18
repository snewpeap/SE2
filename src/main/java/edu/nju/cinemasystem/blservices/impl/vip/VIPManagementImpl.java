package edu.nju.cinemasystem.blservices.impl.vip;

import edu.nju.cinemasystem.blservices.user.AccountService;
import edu.nju.cinemasystem.blservices.vip.SalesInfo;
import edu.nju.cinemasystem.blservices.vip.VIPCouponBusiness;
import edu.nju.cinemasystem.data.po.Vipcard;
import edu.nju.cinemasystem.data.po.VipcardRechargeReduction;
import edu.nju.cinemasystem.data.vo.RechargeReductionVO;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.UserVO;
import edu.nju.cinemasystem.data.vo.VipUserVO;
import edu.nju.cinemasystem.dataservices.vip.VipcardMapper;
import edu.nju.cinemasystem.dataservices.vip.VipcardRechargeReductionMapper;
import edu.nju.cinemasystem.util.exception.ServiceException;
import edu.nju.cinemasystem.util.properties.message.VIPMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VIPManagementImpl implements edu.nju.cinemasystem.blservices.vip.VIPManagement {
    private final VipcardMapper vipcardMapper;
    private final VipcardRechargeReductionMapper reductionMapper;
    private final SalesInfo salesInfo;
    private final AccountService accountService;
    private final VIPCouponBusiness vipCouponBusiness;
    private final VIPMsg vipMsg;

    @Autowired
    public VIPManagementImpl(VipcardMapper vipcardMapper, VipcardRechargeReductionMapper reductionMapper, SalesInfo salesInfo, AccountService accountService, VIPCouponBusiness vipCouponBusiness, VIPMsg vipMsg) {
        this.vipcardMapper = vipcardMapper;
        this.reductionMapper = reductionMapper;
        this.salesInfo = salesInfo;
        this.accountService = accountService;
        this.vipCouponBusiness = vipCouponBusiness;
        this.vipMsg = vipMsg;
    }

    @Override
    public Response getVIPs() {
        Response response = Response.success();
        List<Vipcard> cards = vipcardMapper.selectAll();
        List<VipUserVO> vips = new ArrayList<>(cards.size());
        cards.forEach(
                card -> {
                    int id = card.getUserId();
                    UserVO userVO = UserVO.assembleUserVO(accountService.getUserByID(id));
                    vips.add(new VipUserVO(userVO, id, salesInfo.getConsumption(id)));
                }
        );
        response.setContent(vips);
        return response;
    }

    @Override
    public Response getVIPs(double money) {
        return null;
    }

    @Override
    public Response presentCoupon(List<Integer> vips, List<Integer> couponIDs) {
        Response response = Response.success();
        List<Integer> notVIPList = new ArrayList<>();
        List<Integer> presentSuccess = new ArrayList<>();
        for (int id : vips) {
            if (vipcardMapper.selectByPrimaryKey(id) == null) {
                notVIPList.add(id);
            } else {
                try {
                    vipCouponBusiness.presentCouponTo(id, couponIDs);
                    presentSuccess.add(id);
                } catch (ServiceException e) {
                    response = Response.fail(e.getMessage());
                    response.setContent(presentSuccess);
                    return response;
                }
            }
        }
        response.setContent(notVIPList.isEmpty() ? null : notVIPList);
        return response;
    }

    @Override
    public Response addReduction(RechargeReductionVO reductionVO) {
        if (reductionVO.getTargetAmount() < 0) {
            return Response.fail(vipMsg.getWrongParam() + " : 目标金额不能<0");
        } else if (reductionVO.getDiscountAmount() <= 0) {
            return Response.fail(vipMsg.getWrongParam() + " : 优惠金额需>0");
        }
        VipcardRechargeReduction reduction = assembleReductionPO(reductionVO);
        if (reductionMapper.insertSelective(reduction)==0){
            return Response.fail(vipMsg.getOperationFailed());
        }
        return Response.success(vipMsg.getOperationSuccess());
    }

    @Override
    public Response modifyReduction(RechargeReductionVO reductionVO) {
        if (reductionVO.getTargetAmount() < 0) {
            return Response.fail(vipMsg.getWrongParam() + " : 目标金额不能<0");
        } else if (reductionVO.getDiscountAmount() <= 0) {
            return Response.fail(vipMsg.getWrongParam() + " : 优惠金额需>0");
        }
        VipcardRechargeReduction reduction = assembleReductionPO(reductionVO);
        if (reductionMapper.updateByPrimaryKeySelective(reduction)==0){
            return Response.fail(vipMsg.getOperationFailed());
        }
        return Response.success(vipMsg.getOperationSuccess());
    }

    @Override
    public Response removeReduction(int id) {
        if (reductionMapper.select(id)==null){
            return Response.fail(vipMsg.getReductionInexist());
        }
        reductionMapper.deleteByPrimaryKey(id);
        return Response.success(vipMsg.getOperationSuccess());
    }

    private VipcardRechargeReduction assembleReductionPO(RechargeReductionVO vo) {
        VipcardRechargeReduction po = new VipcardRechargeReduction();
        po.setTargetAmount(vo.getTargetAmount());
        po.setDiscountAmount(vo.getDiscountAmount());
        return po;
    }
}
