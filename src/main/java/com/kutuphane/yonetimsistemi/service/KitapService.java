package com.kutuphane.yonetimsistemi.service;
import java.util.List;
import com.kutuphane.yonetimsistemi.entity.Kitap;

public interface KitapService {
    List<Kitap> findAll();
    Kitap getById(int id);
    Kitap save(Kitap kitap);
    void deleteById(int id);
}
