package com.kutuphane.yonetimsistemi.controller;

import com.kutuphane.yonetimsistemi.entity.Kitap;
import com.kutuphane.yonetimsistemi.service.KitapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/kitaplar")
@RequiredArgsConstructor
public class KitapController {

    private final KitapService kitapService;

    @GetMapping
    public ResponseEntity<List<Kitap>> tumKitaplariGetir() {
        return ResponseEntity.ok(kitapService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kitap> idIleKitapGetir(@PathVariable int id) {
        return ResponseEntity.ok(kitapService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Kitap> kitapKaydet(@RequestBody Kitap kitap) {
        return new ResponseEntity<>(kitapService.save(kitap), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> kitapSil(@PathVariable int id) {
        kitapService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}