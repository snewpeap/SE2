package edu.nju.cinemasystem.blservices.impl.vip;

import edu.nju.cinemasystem.data.vo.Response;
import org.springframework.stereotype.Service;

@Service
public class VIPCardImpl implements edu.nju.cinemasystem.blservices.vip.VIPCard {
    @Override
    public Response getVIPCard(int userID) {
        return null;
    }

    @Override
    public Response buyVIPCard(int userID) {
        return null;
    }

    @Override
    public Response getRechargeReduction() {
        return null;
    }

    @Override
    public Response getRechargeHistory(int userID) {
        return null;
    }

    @Override
    public boolean reduceVIPBalance(int userID, float totalAmount) {
        return false;
    }

    @Override
    public void addVIPBalance(int userID, float amount) {

    }
}
