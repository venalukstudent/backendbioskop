public class Film {

    private String namaFilm;
    private int hargaTiket;

    public String getNamaFilm() {
        return namaFilm;
    }

    public void setNamaFilm(String namaFilm) {
        this.namaFilm = namaFilm;
    }

    public int getHargaTiket() {
        return hargaTiket;
    }

    public void setHargaTiket(int hargaTiket) {
        this.hargaTiket = hargaTiket;
    }

    public int getHargaByHari(String jenisHari) {
        if (jenisHari.equalsIgnoreCase("weekend")) {
            return hargaTiket + 10000; 
        } else {
            return hargaTiket; 
        }
    }

    void info() {
        System.out.println(
            "Film : " + namaFilm +
            " | Harga Weekday : Rp " + hargaTiket +
            " | Harga Weekend : Rp " + (hargaTiket + 10000)
        );
    }
}
