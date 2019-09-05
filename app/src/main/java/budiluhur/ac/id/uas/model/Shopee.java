package budiluhur.ac.id.uas.model;

import java.util.List;

public class Shopee {

    private List<Record> shopee;
    private String status;

    public Shopee(List<Record> shopee) {
        this.shopee = shopee;
    }

    public List<Record> getShopee() {
        return shopee;
    }

    public String getStatus() {
        return status;
    }
}
