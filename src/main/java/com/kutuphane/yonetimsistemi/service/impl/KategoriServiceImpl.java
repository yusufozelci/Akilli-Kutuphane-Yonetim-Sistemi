package com.kutuphane.yonetimsistemi.service.impl;

import com.kutuphane.yonetimsistemi.entity.Kategori;
import com.kutuphane.yonetimsistemi.repository.KategoriRepository;
import com.kutuphane.yonetimsistemi.service.KategoriService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KategoriServiceImpl implements KategoriService {

    private final KategoriRepository kategoriRepository;
    public KategoriServiceImpl(KategoriRepository kategoriRepository) {
        this.kategoriRepository = kategoriRepository;
    }

    @Override
    public List<Kategori> findAll() {
        return kategoriRepository.findAll();
    }

    @Override
    public Kategori getById(int id) {
        return kategoriRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı: " + id));
    }

    @Override
    public Kategori save(Kategori kategori) {
        return kategoriRepository.save(kategori);
    }

    @Override
    public void deleteById(int id) {
        Kategori kategori = getById(id);
        kategoriRepository.delete(kategori);
    }
}