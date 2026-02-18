import java.util.ArrayList;

public class Pemesanan {

    private Film film;
    private String jenisHari;
    private ArrayList<String> kursiDipilih = new ArrayList<>();

    public void setFilm(Film film) {
        this.film = film;
    }

    public void setJenisHari(String jenisHari) {
        this.jenisHari = jenisHari;
    }

    public void tambahKursi(String kursi) {
        kursiDipilih.add(kursi);
    }

    public int hitungTotal() {
        int harga = film.getHargaByHari(jenisHari);
        return kursiDipilih.size() * harga;
    }

    void info() {
        System.out.println(
            "Film : " + film.getNamaFilm() +
            " | Hari : " + jenisHari +
            " | Kursi : " + kursiDipilih +
            " | Jumlah : " + kursiDipilih.size() +
            " | Total : Rp " + hitungTotal()
        );
    }
}
