package com.kutuphane.yonetimsistemi.controller;

import com.kutuphane.yonetimsistemi.entity.Cezalar;
import com.kutuphane.yonetimsistemi.service.CezalarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cezalar")
@RequiredArgsConstructor
public class CezalarController {

    private final CezalarService cezalarService;

    @GetMapping
    public ResponseEntity<List<Cezalar>> tumCezalariGetir() {
        return ResponseEntity.ok(cezalarService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cezalar> idIleCezaGetir(@PathVariable int id) {
        return ResponseEntity.ok(cezalarService.getById(id));
    }

    @PutMapping("/ode/{id}")
    public ResponseEntity<Cezalar> cezaOde(@PathVariable int id) {
        Cezalar odemeYapilan = cezalarService.cezaOde(id);
        return ResponseEntity.ok(odemeYapilan);
    }
}