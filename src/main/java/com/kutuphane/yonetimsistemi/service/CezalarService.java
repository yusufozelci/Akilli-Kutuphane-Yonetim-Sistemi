package com.kutuphane.yonetimsistemi.service;
import com.kutuphane.yonetimsistemi.entity.Cezalar;
import java.util.List;
public interface CezalarService {
    List<Cezalar> findAll();
    Cezalar getById(int id);
    Cezalar cezaOde(int id);

}
