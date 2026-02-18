import java.util.Scanner;

public class MainBioskop {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        Film film1 = new Film();
        film1.setNamaFilm("Avengers");
        film1.setHargaTiket(50000);

        Film film2 = new Film();
        film2.setNamaFilm("Batman");
        film2.setHargaTiket(50000);

        System.out.println("===== DAFTAR FILM =====");
        film1.info();
        film2.info();

        System.out.print("\nPilih Film (1-2): ");
        int pilih = input.nextInt();

        Film filmDipilih;
        if (pilih == 1) {
            filmDipilih = film1;
        } else {
            filmDipilih = film2;
        }

        System.out.print("Masukkan jenis hari (weekday/weekend): ");
        String hari = input.next();

        Pemesanan pesanan = new Pemesanan();
        pesanan.setFilm(filmDipilih);
        pesanan.setJenisHari(hari);

        System.out.print("Berapa kursi ingin dipesan? ");
        int jumlah = input.nextInt();

        for (int i = 1; i <= jumlah; i++) {
            System.out.print("Masukkan kursi ke-" + i + ": ");
            String kursi = input.next();
            pesanan.tambahKursi(kursi);
        }

        System.out.println("\n===== STRUK =====");
        pesanan.info();
    }
}
