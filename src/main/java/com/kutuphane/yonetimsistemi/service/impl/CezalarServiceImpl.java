package com.kutuphane.yonetimsistemi.service.impl;
import com.kutuphane.yonetimsistemi.entity.Cezalar;
import com.kutuphane.yonetimsistemi.repository.CezalarRepository;
import com.kutuphane.yonetimsistemi.service.CezalarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CezalarServiceImpl implements CezalarService {
    private final CezalarRepository cezalarRepository;

    @Override
    public List<Cezalar> findAll() {
        return cezalarRepository.findAll();
    }

    @Override
    public Cezalar getById(int id) {
        return cezalarRepository.findById(id).orElseThrow(()-> new RuntimeException("Ceza Kaydı Bulunamadı: "+ id));
    }

    @Override
    public Cezalar cezaOde(int id){
        Cezalar ceza = getById(id);
        ceza.setOdendiMi(true);
        return cezalarRepository.save(ceza);
    }
}
