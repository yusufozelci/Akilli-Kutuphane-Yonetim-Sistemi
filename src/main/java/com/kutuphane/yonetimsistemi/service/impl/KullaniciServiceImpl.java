package com.kutuphane.yonetimsistemi.service.impl;

import com.kutuphane.yonetimsistemi.entity.Kullanici;
import com.kutuphane.yonetimsistemi.repository.KullaniciRepository;
import com.kutuphane.yonetimsistemi.service.KullaniciService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KullaniciServiceImpl implements KullaniciService {

    private final KullaniciRepository kullaniciRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Kullanici> findAll() {
        return kullaniciRepository.findAll();
    }

    @Override
    public Kullanici getById(int id) {
        return kullaniciRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + id));
    }

    @Override
    public Kullanici save(Kullanici kullanici) {
        kullanici.setSifre(passwordEncoder.encode(kullanici.getSifre()));
        return kullaniciRepository.save(kullanici);
    }

    @Override
    public Kullanici update(Kullanici kullanici) {
        getById(kullanici.getId());
        kullanici.setSifre(passwordEncoder.encode(kullanici.getSifre()));
        return kullaniciRepository.save(kullanici);
    }

    @Override
    public void deleteById(int id) {
        Kullanici kullanici = getById(id);
        kullaniciRepository.delete(kullanici);
    }
}