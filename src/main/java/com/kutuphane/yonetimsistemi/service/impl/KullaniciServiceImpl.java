package com.kutuphane.yonetimsistemi.service.impl;

import com.kutuphane.yonetimsistemi.email.EmailService;
import com.kutuphane.yonetimsistemi.entity.Kullanici;
import com.kutuphane.yonetimsistemi.repository.KullaniciRepository;
import com.kutuphane.yonetimsistemi.service.KullaniciService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class KullaniciServiceImpl implements KullaniciService {

    private final KullaniciRepository kullaniciRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

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
        if (kullanici.getSifre() != null) {
            sifreFormatKontrolu(kullanici.getSifre());
            kullanici.setSifre(passwordEncoder.encode(kullanici.getSifre()));
        }
        return kullaniciRepository.save(kullanici);
    }

    @Override
    public Kullanici update(Kullanici gelenKullaniciVerisi) {
        Kullanici veritabanindakiKullanici = getById(gelenKullaniciVerisi.getId());
        veritabanindakiKullanici.setAd(gelenKullaniciVerisi.getAd());
        veritabanindakiKullanici.setSoyad(gelenKullaniciVerisi.getSoyad());
        veritabanindakiKullanici.setEmail(gelenKullaniciVerisi.getEmail());

        return kullaniciRepository.save(veritabanindakiKullanici);
    }

    @Override
    public void deleteById(int id) {
        Kullanici kullanici = getById(id);
        kullaniciRepository.delete(kullanici);
    }

    @Override
    public void sifreSifirlamaKoduGonder(String email) {
        Kullanici kullanici = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Bu mail adresiyle kayıtlı kullanıcı yok!"));

        String token = String.valueOf(new Random().nextInt(900000) + 100000);
        kullanici.setResetToken(token);
        kullaniciRepository.save(kullanici);

        emailService.sifreSifirlamaMailiGonder(email, kullanici.getAd(), token);
    }

    @Override
    public boolean tokenGecerliMi(String token) {
        Kullanici kullanici = kullaniciRepository.findByResetToken(token);
        return kullanici != null;
    }

    @Override
    public void sifreyiGuncelle(String token, String yeniSifre) {
        Kullanici kullanici = kullaniciRepository.findByResetToken(token);

        if (kullanici == null) {
            throw new RuntimeException("Girdiğiniz kod hatalı veya geçersiz!");
        }

        sifreKurallariniDogrula(yeniSifre, kullanici.getSifre());

        kullanici.setSifre(passwordEncoder.encode(yeniSifre));
        kullanici.setResetToken(null);
        kullaniciRepository.save(kullanici);

        emailService.sifreDegistiBilgilendirmesi(kullanici.getEmail(), kullanici.getAd());
    }

    @Override
    public void adminSifreGuncelle(int id, String yeniSifre) {
        Kullanici kullanici = getById(id);

        sifreKurallariniDogrula(yeniSifre, kullanici.getSifre());

        kullanici.setSifre(passwordEncoder.encode(yeniSifre));
        kullaniciRepository.save(kullanici);

        emailService.sifreDegistiBilgilendirmesi(kullanici.getEmail(), kullanici.getAd());
    }

    @Override
    public boolean kullaniciKendiSifresiniGuncelle(String email, String eskiSifre, String yeniSifre) {
        Kullanici kullanici = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        if (!passwordEncoder.matches(eskiSifre, kullanici.getSifre())) {
            return false;
        }

        sifreKurallariniDogrula(yeniSifre, kullanici.getSifre());

        kullanici.setSifre(passwordEncoder.encode(yeniSifre));
        kullaniciRepository.save(kullanici);

        emailService.sifreDegistiBilgilendirmesi(kullanici.getEmail(), kullanici.getAd());

        return true;
    }

    private void sifreKurallariniDogrula(String yeniSifre, String mevcutSifreHash) {
        sifreFormatKontrolu(yeniSifre);

        if (passwordEncoder.matches(yeniSifre, mevcutSifreHash)) {
            throw new RuntimeException("Yeni şifreniz eski şifrenizle aynı olamaz!");
        }
    }

    private void sifreFormatKontrolu(String sifre) {
        if (sifre.length() < 8) {
            throw new RuntimeException("Şifre en az 8 karakterden oluşmalıdır!");
        }
        if (!sifre.matches(".*[A-Z].*")) {
            throw new RuntimeException("Şifre en az 1 büyük harf içermelidir!");
        }
    }
}