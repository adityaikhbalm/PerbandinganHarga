package budiluhur.ac.id.uas.model;

import java.util.List;

public class Bukalapak {

    private List<Record> bukalapak;
    private String status;

    public Bukalapak(List<Record> bukalapak) {
        this.bukalapak = bukalapak;
    }

    public List<Record> getBukalapak() {
        return bukalapak;
    }

    public String getStatus() {
        return status;
    }
}
