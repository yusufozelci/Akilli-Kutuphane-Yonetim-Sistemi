package com.kutuphane.yonetimsistemi.service;
import java.util.List;
import com.kutuphane.yonetimsistemi.entity.Yazar;

public interface YazarService {
    List<Yazar> findAll();
    Yazar getById(int id);
    Yazar save(Yazar yazar);
    Yazar update(Yazar yazar);
    void deleteById(int id) ;
}
