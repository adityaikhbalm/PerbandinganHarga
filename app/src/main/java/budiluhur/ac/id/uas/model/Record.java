package budiluhur.ac.id.uas.model;

public class Record {

    private String nama;
    private int harga;
    private float rating;
    private int view;
    private String url;
    private String gambar;

    public Record(String nama, int harga, float rating, int view, String url, String gambar) {
        this.nama = nama;
        this.harga = harga;
        this.rating = rating;
        this.view = view;
        this.url = url;
        this.gambar = gambar;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return harga;
    }

    public float getRating() {
        return rating;
    }

    public int getView() {
        return view;
    }

    public String getUrl() {
        return url;
    }

    public String getGambar() {
        return gambar;
    }
}
