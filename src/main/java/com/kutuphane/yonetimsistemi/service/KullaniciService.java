package com.kutuphane.yonetimsistemi.service;
import java.util.List;
import com.kutuphane.yonetimsistemi.entity.Kullanici;

public interface KullaniciService {

    List<Kullanici> findAll();
    Kullanici getById(int id);
    Kullanici save(Kullanici kullanici);
    Kullanici update(Kullanici kullanici);
    void deleteById(int id);
}
