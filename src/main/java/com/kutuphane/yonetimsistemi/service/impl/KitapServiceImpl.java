package com.kutuphane.yonetimsistemi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import com.kutuphane.yonetimsistemi.entity.Kitap;
import com.kutuphane.yonetimsistemi.repository.KitapRepository;
import com.kutuphane.yonetimsistemi.service.KitapService;

@Service
@RequiredArgsConstructor

public class KitapServiceImpl implements KitapService {
    private final KitapRepository kitapRepository;

    @Override
    public List<Kitap> findAll() {
        return kitapRepository.findAll();
    }

    @Override
    public Kitap getById(int id) {
        return kitapRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Kitap bulunamadı:" +id));
    }

    @Override
    public Kitap save(Kitap kitap) {
        return kitapRepository.save(kitap);
    }

    @Override
    public void deleteById(int id) {
        Kitap kitap = getById(id);
        kitapRepository.deleteById(id);
    }

    @Override
    public List<Kitap> search(String keyword) {
        if (keyword != null) {
            return kitapRepository.aramaYap(keyword);
        }
        return kitapRepository.findAll();
    }
}
