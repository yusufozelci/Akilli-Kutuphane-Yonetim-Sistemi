package com.kutuphane.yonetimsistemi.service.impl;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import com.kutuphane.yonetimsistemi.email.EmailService;
import com.kutuphane.yonetimsistemi.entity.OduncAlma;
import com.kutuphane.yonetimsistemi.repository.OduncAlmaRepository;
import com.kutuphane.yonetimsistemi.service.OduncAlmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OduncAlmaServiceImpl implements OduncAlmaService {

    private final OduncAlmaRepository oduncAlmaRepository;
    private final EmailService emailService;
    private static final BigDecimal GUNLUK_CEZA = BigDecimal.valueOf(20.00);

    @Override
    public List<OduncAlma> findAll() {
        return oduncAlmaRepository.findAll();
    }

    @Override
    public OduncAlma getById(int id) {
        return oduncAlmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ödünç alma kaydı bulunamadı: " + id));
    }

    @Override
    public OduncAlma oduncVer(OduncAlma oduncAlma) {
        if (oduncAlma.getSonIadeTarihi().isBefore(LocalDate.now())) {
            throw new RuntimeException("Hata: Son iade tarihi bugünden önce olamaz!");
        }

        oduncAlma.setOduncAlmaTarihi(LocalDateTime.now());
        return oduncAlmaRepository.save(oduncAlma);
    }

    @Override
    public OduncAlma kitapIadeEt(int oduncAlmaId) {
        OduncAlma oduncAlma = getById(oduncAlmaId);
        oduncAlma.setGercekIadeTarihi(LocalDate.now());

        if (oduncAlma.getGercekIadeTarihi().isAfter(oduncAlma.getSonIadeTarihi())) {
            long gecikmeGunu = ChronoUnit.DAYS.between(
                    oduncAlma.getSonIadeTarihi(),
                    oduncAlma.getGercekIadeTarihi()
            );
            BigDecimal cezaTutari = GUNLUK_CEZA.multiply(BigDecimal.valueOf(gecikmeGunu));

            emailService.gecikmeUyarisiGonder(
                    oduncAlma.getKullanici().getEmail(),
                    oduncAlma.getKitap().getKitapAdi(),
                    oduncAlma.getSonIadeTarihi().toString(),
                    cezaTutari,
                    gecikmeGunu
            );
            System.out.println("GEÇ İADE TESPİT EDİLDİ: Mail tetiklendi.");
        }
        return oduncAlmaRepository.save(oduncAlma);
    }

    @Override
    public void deleteById(int id) {
        OduncAlma oduncAlma = getById(id);
        oduncAlmaRepository.delete(oduncAlma);
    }

    @Override
    public java.util.List<com.kutuphane.yonetimsistemi.entity.OduncAlma> getKullaniciGecmisi(Integer kullaniciId) {
        return oduncAlmaRepository.findAllByKullanici_Id(kullaniciId);
    }
}