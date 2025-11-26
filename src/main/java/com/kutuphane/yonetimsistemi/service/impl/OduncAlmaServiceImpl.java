package com.kutuphane.yonetimsistemi.service.impl;
import com.kutuphane.yonetimsistemi.entity.OduncAlma;
import com.kutuphane.yonetimsistemi.repository.KategoriRepository;
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

    @Override
    public List<OduncAlma> findAll() {
        return oduncAlmaRepository.findAll();
    }

    @Override
    public OduncAlma getById(int id) {
        return oduncAlmaRepository.findById(id).orElseThrow(() -> new RuntimeException("Odunç alma kaydı bulunamadı: "+id));
    }

    @Override
    public OduncAlma oduncVer(OduncAlma oduncAlma) {
        oduncAlma.setOduncAlmaTarihi(LocalDateTime.now());
        return oduncAlmaRepository.save(oduncAlma);
    }

    @Override
    public OduncAlma kitapIadeEt(int oduncAlmaId){
        OduncAlma oduncAlma = getById(oduncAlmaId);
        oduncAlma.setGercekIadeTarihi(LocalDate.now());

        return oduncAlmaRepository.save(oduncAlma);
    }
    @Override
    public void deleteById(int id) {
        OduncAlma oduncAlma = getById(id);
        oduncAlmaRepository.delete(oduncAlma);
    }
}
