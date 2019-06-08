package edu.nju.cinemasystem.data.vo;

public class VipUserVO {
    private UserVO user;
    private int VIPcardID;
    private float consumption;

    public VipUserVO(UserVO user, int VIPcardID, float consumption) {
        this.user = user;
        this.VIPcardID = VIPcardID;
        this.consumption = consumption;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public int getVIPcardID() {
        return VIPcardID;
    }

    public void setVIPcardID(int VIPcardID) {
        this.VIPcardID = VIPcardID;
    }

    public float getConsumption() {
        return consumption;
    }

    public void setConsumption(float consumption) {
        this.consumption = consumption;
    }
}
