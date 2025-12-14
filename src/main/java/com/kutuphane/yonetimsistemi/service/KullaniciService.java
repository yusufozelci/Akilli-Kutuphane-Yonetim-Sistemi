package com.kutuphane.yonetimsistemi.service;
import java.util.List;
import com.kutuphane.yonetimsistemi.entity.Kullanici;

public interface KullaniciService {

    List<Kullanici> findAll();
    Kullanici getById(int id);
    Kullanici save(Kullanici kullanici);
    Kullanici update(Kullanici kullanici);
    void sifreSifirlamaKoduGonder(String email);
    void sifreyiGuncelle(String token, String yeniSifre);
    boolean tokenGecerliMi(String token);
    void adminSifreGuncelle(int id, String yeniSifre);
    boolean kullaniciKendiSifresiniGuncelle(String email, String eskiSifre, String yeniSifre);
    void deleteById(int id);
}
