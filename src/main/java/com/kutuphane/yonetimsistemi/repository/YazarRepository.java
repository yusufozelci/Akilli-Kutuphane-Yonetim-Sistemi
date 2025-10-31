package com.kutuphane.yonetimsistemi.repository;

import com.kutuphane.yonetimsistemi.entity.Yazar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YazarRepository extends JpaRepository<Yazar, Integer> {

}