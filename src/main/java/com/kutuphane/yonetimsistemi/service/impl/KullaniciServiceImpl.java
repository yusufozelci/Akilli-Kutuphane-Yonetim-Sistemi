package com.kutuphane.yonetimsistemi.service.impl;

import com.kutuphane.yonetimsistemi.entity.Kullanici;
import com.kutuphane.yonetimsistemi.repository.KullaniciRepository;
import com.kutuphane.yonetimsistemi.service.KullaniciService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class KullaniciServiceImpl implements KullaniciService {

    private final KullaniciRepository kullaniciRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

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
    public Kullanici update(Kullanici gelenKullaniciVerisi) {
        // 1. Veritabanındaki asıl kullanıcıyı (eski bilgileriyle) buluyoruz
        Kullanici veritabanindakiKullanici = getById(gelenKullaniciVerisi.getId());
        veritabanindakiKullanici.setAd(gelenKullaniciVerisi.getAd());
        veritabanindakiKullanici.setSoyad(gelenKullaniciVerisi.getSoyad());
        veritabanindakiKullanici.setEmail(gelenKullaniciVerisi.getEmail());
        return kullaniciRepository.save(veritabanindakiKullanici);
    }

    @Override
    public void sifreSifirlamaKoduGonder(String email) {
        Kullanici kullanici = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Bu mail adresiyle kayıtlı kullanıcı yok!"));

        String token = String.valueOf(new Random().nextInt(900000) + 100000);
        kullanici.setResetToken(token);
        kullaniciRepository.save(kullanici);

        SimpleMailMessage mesaj = new SimpleMailMessage();
        mesaj.setFrom("yusufozelci2005@gmail.com");
        mesaj.setTo(email);
        mesaj.setSubject("Şifre Sıfırlama Kodu");
        mesaj.setText("Merhaba, şifrenizi sıfırlamak için kodunuz: " + token);

        mailSender.send(mesaj);
    }

    @Override
    public void sifreyiGuncelle(String token, String yeniSifre) {
        Kullanici kullanici = kullaniciRepository.findByResetToken(token);

        if (kullanici == null) {
            throw new RuntimeException("Girdiğiniz kod hatalı veya geçersiz!");
        }

        kullanici.setSifre(passwordEncoder.encode(yeniSifre));
        kullanici.setResetToken(null);
        kullaniciRepository.save(kullanici);
    }

    @Override
    public boolean tokenGecerliMi(String token) {
        Kullanici kullanici = kullaniciRepository.findByResetToken(token);
        return kullanici != null;
    }

    @Override
    public void adminSifreGuncelle(int id, String yeniSifre) {
        Kullanici kullanici = getById(id);
        kullanici.setSifre(passwordEncoder.encode(yeniSifre));
        kullaniciRepository.save(kullanici);
    }

    @Override
    public boolean kullaniciKendiSifresiniGuncelle(String email, String eskiSifre, String yeniSifre) {
        Kullanici kullanici = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        if (!passwordEncoder.matches(eskiSifre, kullanici.getSifre())) {
            return false;
        }

        kullanici.setSifre(passwordEncoder.encode(yeniSifre));
        kullaniciRepository.save(kullanici);
        return true;
    }

    @Override
    public void deleteById(int id) {
        Kullanici kullanici = getById(id);
        kullaniciRepository.delete(kullanici);
    }
}