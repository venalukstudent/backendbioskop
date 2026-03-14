package com.bioskop.bioskopapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pemesanan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pemesanan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    private String jenisHari;

    @ElementCollection
    @CollectionTable(name = "pemesanan_kursi", joinColumns = @JoinColumn(name = "pemesanan_id"))
    @Column(name = "kursi")
    private List<String> kursiDipilih = new ArrayList<>();

    private int total;

    public int hitungTotal() {
        if (film != null && !kursiDipilih.isEmpty()) {
            int harga = film.getHargaByHari(jenisHari);
            this.total = kursiDipilih.size() * harga;
            return this.total;
        }
        return 0;
    }

    public void tambahKursi(String kursi) {
        kursiDipilih.add(kursi);
    }

    @Override
    public String toString() {
        return "Pemesanan{" +
                "id=" + id +
                ", film=" + film +
                ", jenisHari='" + jenisHari + '\'' +
                ", kursiDipilih=" + kursiDipilih +
                ", total=" + total +
                '}';
    }
}
