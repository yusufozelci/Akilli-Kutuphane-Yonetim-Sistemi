package com.kutuphane.yonetimsistemi.repository;

import com.kutuphane.yonetimsistemi.entity.IslemLoglari;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IslemLoglariRepository extends JpaRepository<IslemLoglari, Integer> {
}