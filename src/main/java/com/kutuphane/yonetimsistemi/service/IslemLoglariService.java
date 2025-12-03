package com.kutuphane.yonetimsistemi.service;

import com.kutuphane.yonetimsistemi.entity.IslemLoglari;
import java.util.List;

public interface IslemLoglariService {
    List<IslemLoglari> findAll();
}