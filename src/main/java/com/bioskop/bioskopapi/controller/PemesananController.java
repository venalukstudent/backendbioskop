package com.bioskop.bioskopapi.controller;

import com.bioskop.bioskopapi.model.Pemesanan;
import com.bioskop.bioskopapi.model.Film;
import com.bioskop.bioskopapi.repository.PemesananRepository;
import com.bioskop.bioskopapi.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/pemesanan")
@CrossOrigin(origins = "*")
public class PemesananController {

    @Autowired
    private PemesananRepository pemesananRepository;

    @Autowired
    private FilmRepository filmRepository;

    // GET - Lihat semua pemesanan
    @GetMapping
    public ResponseEntity<List<Pemesanan>> getAllPemesanan() {
        List<Pemesanan> pemesanans = pemesananRepository.findAll();
        return ResponseEntity.ok(pemesanans);
    }

    // GET - Lihat pemesanan berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<Pemesanan> getPemesananById(@PathVariable Long id) {
        Optional<Pemesanan> pemesanan = pemesananRepository.findById(id);
        if (pemesanan.isPresent()) {
            return ResponseEntity.ok(pemesanan.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // POST - Buat pemesanan baru
    @PostMapping
    public ResponseEntity<?> createPemesanan(@RequestBody Map<String, Object> request) {
        try {
            Long filmId = ((Number) request.get("filmId")).longValue();
            String jenisHari = (String) request.get("jenisHari");
            List<String> kursiDipilih = (List<String>) request.get("kursiDipilih");

            // Validasi film
            Optional<Film> optionalFilm = filmRepository.findById(filmId);
            if (!optionalFilm.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Film tidak ditemukan"));
            }

            Film film = optionalFilm.get();

            // Buat pemesanan baru
            Pemesanan pemesanan = new Pemesanan();
            pemesanan.setFilm(film);
            pemesanan.setJenisHari(jenisHari);
            pemesanan.setKursiDipilih(kursiDipilih);
            pemesanan.hitungTotal();

            Pemesanan savedPemesanan = pemesananRepository.save(pemesanan);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPemesanan);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid request: " + e.getMessage()));
        }
    }

    // PUT - Update pemesanan
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePemesanan(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        Optional<Pemesanan> optionalPemesanan = pemesananRepository.findById(id);

        if (!optionalPemesanan.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Pemesanan tidak ditemukan"));
        }

        try {
            Pemesanan pemesanan = optionalPemesanan.get();

            // Update field jika ada
            if (request.containsKey("jenisHari")) {
                pemesanan.setJenisHari((String) request.get("jenisHari"));
            }

            if (request.containsKey("kursiDipilih")) {
                pemesanan.setKursiDipilih((List<String>) request.get("kursiDipilih"));
            }

            // Hitung ulang total
            pemesanan.hitungTotal();

            Pemesanan updatedPemesanan = pemesananRepository.save(pemesanan);
            return ResponseEntity.ok(updatedPemesanan);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid request: " + e.getMessage()));
        }
    }

    // DELETE - Hapus pemesanan
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePemesanan(@PathVariable Long id) {
        if (pemesananRepository.existsById(id)) {
            pemesananRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // POST - Tambah kursi ke pemesanan yang ada
    @PostMapping("/{id}/tambah-kursi")
    public ResponseEntity<?> tambahKursi(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Optional<Pemesanan> optionalPemesanan = pemesananRepository.findById(id);

        if (!optionalPemesanan.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Pemesanan tidak ditemukan"));
        }

        try {
            Pemesanan pemesanan = optionalPemesanan.get();
            String kursi = request.get("kursi");

            pemesanan.tambahKursi(kursi);
            pemesanan.hitungTotal();

            Pemesanan updatedPemesanan = pemesananRepository.save(pemesanan);
            return ResponseEntity.ok(updatedPemesanan);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid request: " + e.getMessage()));
        }
    }
}
