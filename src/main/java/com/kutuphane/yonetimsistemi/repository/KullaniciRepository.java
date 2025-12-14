package com.kutuphane.yonetimsistemi.repository;

import com.kutuphane.yonetimsistemi.entity.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KullaniciRepository extends JpaRepository<Kullanici, Integer> {
    java.util.Optional<com.kutuphane.yonetimsistemi.entity.Kullanici> findByEmail(String email);
    Kullanici findByResetToken(String resetToken);
}