package edu.nju.cinemasystem.blservices.vip;

import edu.nju.cinemasystem.data.vo.Response;

public interface VIPCard {
    Response getVIPCard(int userID);

    Response buyVIPCard(int userID);

    Response getRechargeReduction();

    Response getRechargeHistory(int userID);
}
