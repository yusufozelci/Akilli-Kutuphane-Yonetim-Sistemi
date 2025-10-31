package com.kutuphane.yonetimsistemi.repository;

import com.kutuphane.yonetimsistemi.entity.Kitap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitapRepository extends JpaRepository<Kitap, Integer> {

}