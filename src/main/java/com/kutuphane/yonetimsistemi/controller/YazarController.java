package com.kutuphane.yonetimsistemi.controller;

import com.kutuphane.yonetimsistemi.entity.Yazar;
import com.kutuphane.yonetimsistemi.service.YazarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/yazarlar")
@RequiredArgsConstructor
public class YazarController {

    private final YazarService yazarService;

    @GetMapping
    public ResponseEntity<List<Yazar>> tumYazarlariGetir() {
        return ResponseEntity.ok(yazarService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Yazar> idIleYazarGetir(@PathVariable int id) {
        return ResponseEntity.ok(yazarService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Yazar> yazarKaydet(@RequestBody Yazar yazar) {
        return new ResponseEntity<>(yazarService.save(yazar), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Yazar> yazarGuncelle(@PathVariable int id, @RequestBody Yazar yazar) {
        yazar.setId(id);
        return ResponseEntity.ok(yazarService.update(yazar));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> yazarSil(@PathVariable int id) {
        yazarService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}