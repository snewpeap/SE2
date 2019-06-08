package edu.nju.cinemasystem.blservices.impl.vip;

import edu.nju.cinemasystem.blservices.user.Account;
import edu.nju.cinemasystem.blservices.vip.SalesInfo;
import edu.nju.cinemasystem.blservices.vip.VIPCouponBusiness;
import edu.nju.cinemasystem.data.po.Vipcard;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.UserVO;
import edu.nju.cinemasystem.data.vo.VipUserVO;
import edu.nju.cinemasystem.dataservices.vip.VipcardMapper;
import edu.nju.cinemasystem.util.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VIPManagementImpl implements edu.nju.cinemasystem.blservices.vip.VIPManagement {
    private final VipcardMapper vipcardMapper;
    private final SalesInfo salesInfo;
    private final Account account;
    private final VIPCouponBusiness vipCouponBusiness;

    @Autowired
    public VIPManagementImpl(VipcardMapper vipcardMapper, SalesInfo salesInfo, Account account, VIPCouponBusiness vipCouponBusiness) {
        this.vipcardMapper = vipcardMapper;
        this.salesInfo = salesInfo;
        this.account = account;
        this.vipCouponBusiness = vipCouponBusiness;
    }

    @Override
    public Response getVIPs() {
        Response response = Response.success();
        List<Vipcard> cards = vipcardMapper.selectAll();
        List<VipUserVO> vips = new ArrayList<>(cards.size());
        cards.forEach(
                card -> {
                    int id = card.getUserId();
                    UserVO userVO = UserVO.assembleUserVO(account.getUserByID(id));
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
}
