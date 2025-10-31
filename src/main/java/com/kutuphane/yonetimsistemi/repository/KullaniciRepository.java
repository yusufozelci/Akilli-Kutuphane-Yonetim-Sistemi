package com.kutuphane.yonetimsistemi.repository;

import com.kutuphane.yonetimsistemi.entity.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KullaniciRepository extends JpaRepository<Kullanici, Integer> {

}