package com.kutuphane.yonetimsistemi.controller;

import com.kutuphane.yonetimsistemi.entity.IslemLoglari;
import com.kutuphane.yonetimsistemi.service.IslemLoglariService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IslemLoglariController {

    private final IslemLoglariService islemLoglariService;

    @GetMapping("/loglar")
    public String loglariListele(Model model) {
        model.addAttribute("logListesi", islemLoglariService.findAll());
        return "loglar";
    }
}