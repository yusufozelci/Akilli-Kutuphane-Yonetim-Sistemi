package com.kutuphane.yonetimsistemi.repository;

import com.kutuphane.yonetimsistemi.entity.Kategori;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KategoriRepository extends JpaRepository<Kategori, Integer> {

}