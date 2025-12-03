package com.kutuphane.yonetimsistemi.service.impl;

import com.kutuphane.yonetimsistemi.entity.IslemLoglari;
import com.kutuphane.yonetimsistemi.repository.IslemLoglariRepository;
import com.kutuphane.yonetimsistemi.service.IslemLoglariService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IslemLoglariServiceImpl implements IslemLoglariService {

    private final IslemLoglariRepository islemLoglariRepository;

    @Override
    public List<IslemLoglari> findAll() {
        return islemLoglariRepository.findAll(Sort.by(Sort.Direction.DESC, "tarih"));
    }
}