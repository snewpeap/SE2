package edu.nju.cinemasystem.util.properties.message;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "vip-msg")
public class VIPMsg extends GlobalMsg{
    private String addFailed;
    private String rechargeFailed;
    private String noVIPCard;
    private String hasVIPCard;
    private String outOfBalance;
    private String reductionInexist;

    public String getHasVIPCard() {
        return hasVIPCard;
    }

    public void setHasVIPCard(String hasVIPCard) {
        this.hasVIPCard = hasVIPCard;
    }

    public String getOutOfBalance() {
        return outOfBalance;
    }

    public void setOutOfBalance(String outOfBalance) {
        this.outOfBalance = outOfBalance;
    }

    public String getNoVIPCard() {
        return noVIPCard;
    }

    public void setNoVIPCard(String noVIPCard) {
        this.noVIPCard = noVIPCard;
    }

    public String getAddFailed() {
        return addFailed;
    }

    public void setAddFailed(String addFailed) {
        this.addFailed = addFailed;
    }

    public String getRechargeFailed() {
        return rechargeFailed;
    }

    public void setRechargeFailed(String rechargeFailed) {
        this.rechargeFailed = rechargeFailed;
    }

    public String getReductionInexist() {
        return reductionInexist;
    }

    public void setReductionInexist(String reductionInexist) {
        this.reductionInexist = reductionInexist;
    }
}
