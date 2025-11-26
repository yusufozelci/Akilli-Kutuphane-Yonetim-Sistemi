package com.kutuphane.yonetimsistemi.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kutuphane.yonetimsistemi.entity.Kullanici;
import com.kutuphane.yonetimsistemi.repository.KullaniciRepository;
import com.kutuphane.yonetimsistemi.service.KullaniciService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KullaniciServiceImpl implements KullaniciService {
    private final KullaniciRepository kullaniciRepository;

    @Override
    public List<Kullanici> findAll() {
        return kullaniciRepository.findAll();
    }

    @Override
    public Kullanici getById(int id) {
        return kullaniciRepository.findById(id).orElseThrow(()-> new RuntimeException("Kullanıcı bulunamadı: "+ id));
    }

    @Override
    public Kullanici save(Kullanici kullanici) {
        return kullaniciRepository.save(kullanici);
    }

    @Override
    public Kullanici update(Kullanici kullanici) {
        getById(kullanici.getId());
        return kullaniciRepository.save(kullanici);
    }

    @Override
    public void deleteById(int id) {
        Kullanici kullanici = getById(id);
        kullaniciRepository.deleteById(id);
    }

}
