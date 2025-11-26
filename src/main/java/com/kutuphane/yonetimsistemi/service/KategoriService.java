package com.kutuphane.yonetimsistemi.service;
import com.kutuphane.yonetimsistemi.entity.Kategori;
import java.util.List;

public interface KategoriService {
    List<Kategori> findAll();
    Kategori getById(int id);
    Kategori save(Kategori kategori);
    void deleteById(int id);
}
