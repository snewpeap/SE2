package edu.nju.cinemasystem.blservices.vip;

import edu.nju.cinemasystem.data.vo.Response;

public interface VIPManagement {
    Response getVIPs();

    Response getVIPs(double money);
}
