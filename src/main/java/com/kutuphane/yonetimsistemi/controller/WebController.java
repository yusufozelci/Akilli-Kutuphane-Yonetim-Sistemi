package com.kutuphane.yonetimsistemi.controller;

import com.kutuphane.yonetimsistemi.entity.*;
import com.kutuphane.yonetimsistemi.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final YazarService yazarService;
    private final KitapService kitapService;
    private final KategoriService kategoriService;
    private final KullaniciService kullaniciService;
    private final OduncAlmaService oduncAlmaService;
    private final CezalarService cezalarService;

    @GetMapping("/")
    public String anaSayfayiGoster(Model model) {
        return "index";
    }
    @GetMapping("/yazarlar")
    public String yazarlariListele(Model model) {
        model.addAttribute("yazarListesi", yazarService.findAll());
        model.addAttribute("yeniYazar", new Yazar());
        return "yazarlar";
    }

    @PostMapping("/yazarlar/kaydet")
    public String yazarKaydet(@ModelAttribute("yeniYazar") Yazar yazar) {
        yazarService.save(yazar);
        return "redirect:/yazarlar";
    }

    @GetMapping("/yazarlar/sil/{id}")
    public String yazarSil(@PathVariable int id) {
        yazarService.deleteById(id);
        return "redirect:/yazarlar";
    }

    @GetMapping("/yazarlar/duzenle/{id}")
    public String yazarDuzenle(@PathVariable int id, Model model) {
        model.addAttribute("yazarListesi", yazarService.findAll());
        model.addAttribute("yeniYazar", yazarService.getById(id));
        return "yazarlar";
    }

    @GetMapping("/kitaplar")
    public String kitaplariListele(Model model) {
        model.addAttribute("kitapListesi", kitapService.findAll());
        model.addAttribute("tumYazarlar", yazarService.findAll());
        model.addAttribute("tumKategoriler", kategoriService.findAll());
        model.addAttribute("yeniKitap", new Kitap());
        return "kitaplar";
    }

    @PostMapping("/kitaplar/kaydet")
    public String kitapKaydet(@ModelAttribute("yeniKitap") Kitap kitap) {
        kitapService.save(kitap);
        return "redirect:/kitaplar";
    }

    @GetMapping("/kitaplar/sil/{id}")
    public String kitapSil(@PathVariable int id) {
        kitapService.deleteById(id);
        return "redirect:/kitaplar";
    }

    @GetMapping("/kitaplar/duzenle/{id}")
    public String kitapDuzenle(@PathVariable int id, Model model) {
        model.addAttribute("kitapListesi", kitapService.findAll());
        model.addAttribute("tumYazarlar", yazarService.findAll());
        model.addAttribute("tumKategoriler", kategoriService.findAll());
        model.addAttribute("yeniKitap", kitapService.getById(id));
        return "kitaplar";
    }

    @GetMapping("/odunc")
    public String oduncSayfasi(Model model) {
        model.addAttribute("oduncListesi", oduncAlmaService.findAll());
        model.addAttribute("tumKullanicilar", kullaniciService.findAll());
        model.addAttribute("tumKitaplar", kitapService.findAll());
        model.addAttribute("yeniIslem", new OduncAlma());

        return "odunc";
    }

    @PostMapping("/odunc/ver")
    public String kitapOduncVer(@ModelAttribute("yeniIslem") OduncAlma oduncAlma, Model model) {
        try {
            oduncAlmaService.oduncVer(oduncAlma);
            return "redirect:/odunc";
        } catch (RuntimeException e) {
            model.addAttribute("hataMesaji", e.getMessage());
            model.addAttribute("oduncListesi", oduncAlmaService.findAll());
            model.addAttribute("tumKullanicilar", kullaniciService.findAll());
            model.addAttribute("tumKitaplar", kitapService.findAll());
            return "odunc";
        }
    }

    @GetMapping("/odunc/iade/{id}")
    public String kitapIadeAl(@PathVariable int id) {
        oduncAlmaService.kitapIadeEt(id);
        return "redirect:/odunc";
    }

    @GetMapping("/kullanicilar")
    public String kullanicilariListele(Model model) {
        model.addAttribute("kullaniciListesi", kullaniciService.findAll());
        model.addAttribute("yeniKullanici", new com.kutuphane.yonetimsistemi.entity.Kullanici());
        return "kullanicilar";
    }

    @PostMapping("/kullanicilar/kaydet")
    public String kullaniciKaydet(@ModelAttribute("yeniKullanici") com.kutuphane.yonetimsistemi.entity.Kullanici kullanici) {
        kullaniciService.save(kullanici);
        return "redirect:/kullanicilar";
    }

    @GetMapping("/kullanicilar/sil/{id}")
    public String kullaniciSil(@PathVariable int id) {
        kullaniciService.deleteById(id);
        return "redirect:/kullanicilar";
    }

    @GetMapping("/kullanicilar/duzenle/{id}")
    public String kullaniciDuzenle(@PathVariable int id, Model model) {
        model.addAttribute("kullaniciListesi", kullaniciService.findAll());
        model.addAttribute("yeniKullanici", kullaniciService.getById(id));
        return "kullanicilar";
    }

    @GetMapping("/kategoriler")
    public String kategorileriListele(Model model) {
        model.addAttribute("kategoriListesi", kategoriService.findAll());
        model.addAttribute("yeniKategori", new com.kutuphane.yonetimsistemi.entity.Kategori());
        return "kategoriler";
    }

    @PostMapping("/kategoriler/kaydet")
    public String kategoriKaydet(@ModelAttribute("yeniKategori") com.kutuphane.yonetimsistemi.entity.Kategori kategori) {
        kategoriService.save(kategori);
        return "redirect:/kategoriler";
    }

    @GetMapping("/kategoriler/sil/{id}")
    public String kategoriSil(@PathVariable int id) {
        kategoriService.deleteById(id);
        return "redirect:/kategoriler";
    }

    @GetMapping("/kategoriler/duzenle/{id}")
    public String kategoriDuzenle(@PathVariable int id, Model model) {
        model.addAttribute("kategoriListesi", kategoriService.findAll());
        model.addAttribute("yeniKategori", kategoriService.getById(id));
        return "kategoriler";
    }

    @GetMapping("/cezalar")
    public String cezalariListele(Model model) {
        model.addAttribute("cezaListesi", cezalarService.findAll());
        return "cezalar";
    }

    @GetMapping("/cezalar/ode/{id}")
    public String cezaOde(@PathVariable int id) {
        cezalarService.cezaOde(id);
        return "redirect:/cezalar";
    }
}
