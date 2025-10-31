package com.kutuphane.yonetimsistemi.repository;

import com.kutuphane.yonetimsistemi.entity.OduncAlma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OduncAlmaRepository extends JpaRepository<OduncAlma, Integer> {

}