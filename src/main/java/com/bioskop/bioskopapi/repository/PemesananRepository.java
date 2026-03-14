package com.bioskop.bioskopapi.repository;

import com.bioskop.bioskopapi.model.Pemesanan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PemesananRepository extends JpaRepository<Pemesanan, Long> {
}
