package com.kutuphane.yonetimsistemi.controller;

import com.kutuphane.yonetimsistemi.entity.Kategori;
import com.kutuphane.yonetimsistemi.service.KategoriService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/kategoriler")
@RequiredArgsConstructor
public class KategoriController {

    private final KategoriService kategoriService;

    @GetMapping
    public ResponseEntity<List<Kategori>> tumKategorileriGetir() {
        return ResponseEntity.ok(kategoriService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kategori> idIleKategoriGetir(@PathVariable int id) {
        return ResponseEntity.ok(kategoriService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Kategori> kategoriKaydet(@RequestBody Kategori kategori) {
        return new ResponseEntity<>(kategoriService.save(kategori), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> kategoriSil(@PathVariable int id) {
        kategoriService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}