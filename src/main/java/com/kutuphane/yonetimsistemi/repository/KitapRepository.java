package com.kutuphane.yonetimsistemi.repository;

import com.kutuphane.yonetimsistemi.entity.Kitap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KitapRepository extends JpaRepository<Kitap, Integer> {
    @Query("SELECT k FROM Kitap k WHERE " +
            "LOWER(k.kitapAdi) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.isbn) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.yazar.ad) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(k.yazar.soyad) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    java.util.List<Kitap> aramaYap(@org.springframework.data.repository.query.Param("keyword") String keyword);
    boolean existsByIsbn(String isbn);
}