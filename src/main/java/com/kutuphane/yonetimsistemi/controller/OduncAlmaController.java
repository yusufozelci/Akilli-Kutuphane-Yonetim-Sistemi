package com.kutuphane.yonetimsistemi.controller;

import com.kutuphane.yonetimsistemi.entity.OduncAlma;
import com.kutuphane.yonetimsistemi.service.OduncAlmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/odunc")
@RequiredArgsConstructor
public class OduncAlmaController {

    private final OduncAlmaService oduncAlmaService;

    @GetMapping
    public ResponseEntity<List<OduncAlma>> tumKayitlariGetir() {
        return ResponseEntity.ok(oduncAlmaService.findAll());
    }

    @PostMapping("/ver")
    public ResponseEntity<OduncAlma> oduncVer(@RequestBody OduncAlma oduncAlma) {
        OduncAlma yeniKayit = oduncAlmaService.oduncVer(oduncAlma);
        return new ResponseEntity<>(yeniKayit, HttpStatus.CREATED);
    }

    @PutMapping("/iade/{id}")
    public ResponseEntity<OduncAlma> kitapIadeEt(@PathVariable int id) {
        OduncAlma iadeEdilen = oduncAlmaService.kitapIadeEt(id);
        return ResponseEntity.ok(iadeEdilen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> kayitSil(@PathVariable int id) {
        oduncAlmaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}