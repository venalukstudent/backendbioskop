package com.bioskop.bioskopapi.controller;

import com.bioskop.bioskopapi.model.Film;
import com.bioskop.bioskopapi.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/films")
@CrossOrigin(origins = "*")
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    // GET - Lihat semua film
    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        List<Film> films = filmRepository.findAll();
        return ResponseEntity.ok(films);
    }

    // GET - Lihat film berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable Long id) {
        Optional<Film> film = filmRepository.findById(id);
        if (film.isPresent()) {
            return ResponseEntity.ok(film.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // POST - Tambah film baru
    @PostMapping
    public ResponseEntity<Film> addFilm(@RequestBody Film film) {
        Film savedFilm = filmRepository.save(film);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFilm);
    }

    // PUT - Update film
    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable Long id, @RequestBody Film filmDetails) {
        Optional<Film> optionalFilm = filmRepository.findById(id);

        if (optionalFilm.isPresent()) {
            Film film = optionalFilm.get();
            film.setNamaFilm(filmDetails.getNamaFilm());
            film.setHargaTiket(filmDetails.getHargaTiket());

            Film updatedFilm = filmRepository.save(film);
            return ResponseEntity.ok(updatedFilm);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // DELETE - Hapus film
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {
        if (filmRepository.existsById(id)) {
            filmRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
