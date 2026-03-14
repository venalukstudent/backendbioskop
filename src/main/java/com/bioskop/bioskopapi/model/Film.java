package com.bioskop.bioskopapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "film")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namaFilm;
    private int hargaTiket;

    public int getHargaByHari(String jenisHari) {
        if (jenisHari.equalsIgnoreCase("weekend")) {
            return hargaTiket + 10000;
        } else {
            return hargaTiket;
        }
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", namaFilm='" + namaFilm + '\'' +
                ", hargaTiket=" + hargaTiket +
                '}';
    }
}
