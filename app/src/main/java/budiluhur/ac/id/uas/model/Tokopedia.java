package budiluhur.ac.id.uas.model;

import java.util.List;

public class Tokopedia {

    private List<Record> tokopedia;
    private String status;

    public Tokopedia(List<Record> tokopedia) {
        this.tokopedia = tokopedia;
    }

    public List<Record> getTokopedia() {
        return tokopedia;
    }

    public String getStatus() {
        return status;
    }
}
