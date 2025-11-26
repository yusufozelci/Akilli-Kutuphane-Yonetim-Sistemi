package com.kutuphane.yonetimsistemi.service;
import java.util.List;
import com.kutuphane.yonetimsistemi.entity.OduncAlma;

public interface OduncAlmaService {
    List<OduncAlma> findAll();
    OduncAlma getById(int id);
    OduncAlma oduncVer(OduncAlma oduncAlma);
    OduncAlma kitapIadeEt(int oduncAlmaId);
}
