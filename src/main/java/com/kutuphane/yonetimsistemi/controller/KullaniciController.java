package com.kutuphane.yonetimsistemi.controller;

import com.kutuphane.yonetimsistemi.entity.Kullanici;
import com.kutuphane.yonetimsistemi.service.KullaniciService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/kullanicilar")
@RequiredArgsConstructor
public class KullaniciController {

    private final KullaniciService kullaniciService;

    @GetMapping
    public ResponseEntity<List<Kullanici>> tumKullanicilariGetir() {
        return ResponseEntity.ok(kullaniciService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kullanici> idIleKullaniciGetir(@PathVariable int id) {
        return ResponseEntity.ok(kullaniciService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Kullanici> kullaniciKaydet(@RequestBody Kullanici kullanici) {
        return new ResponseEntity<>(kullaniciService.save(kullanici), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> kullaniciSil(@PathVariable int id) {
        kullaniciService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}