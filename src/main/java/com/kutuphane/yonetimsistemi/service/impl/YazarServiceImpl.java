package com.kutuphane.yonetimsistemi.service.impl;
import java.util.List;
import com.kutuphane.yonetimsistemi.entity.Yazar;
import com.kutuphane.yonetimsistemi.repository.YazarRepository;
import com.kutuphane.yonetimsistemi.service.YazarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class YazarServiceImpl implements YazarService {
    private final YazarRepository yazarRepository;

    @Override
    public List<Yazar> findAll() {
        return yazarRepository.findAll();
    }

    @Override
    public Yazar getById(int id){
        return yazarRepository.findById(id).orElseThrow(()-> new RuntimeException("Yazar bulunamadı: "+ id));
    }

    @Override
    public Yazar save(Yazar yazar) {
        return yazarRepository.save(yazar);
    }

    @Override
    public Yazar update(Yazar yazar) {
        getById(yazar.getId());
        return yazarRepository.save(yazar);
    }

    @Override
    public void deleteById(int id) {
        Yazar yazar = getById(id);
        yazarRepository.deleteById(id);
    }
}

