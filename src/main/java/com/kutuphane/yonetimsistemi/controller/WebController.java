package com.kutuphane.yonetimsistemi.controller;

import com.kutuphane.yonetimsistemi.repository.KullaniciRepository;
import com.kutuphane.yonetimsistemi.entity.*;
import com.kutuphane.yonetimsistemi.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final YazarService yazarService;
    private final KitapService kitapService;
    private final KategoriService kategoriService;
    private final KullaniciService kullaniciService;
    private final OduncAlmaService oduncAlmaService;
    private final CezalarService cezalarService;
    private final KullaniciRepository kullaniciRepository;

    @GetMapping("/login")
    public String girisSayfasi() {
        return "login";
    }
    @GetMapping("/")
    public String anaSayfayiGoster(Model model, java.security.Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }
        String email = principal.getName();
        com.kutuphane.yonetimsistemi.entity.Kullanici girisYapan = kullaniciRepository.findByEmail(email).get();

        if (girisYapan.getRol().name().equals("ADMIN")) {
            return "index";
        } else {
            model.addAttribute("kullanici", girisYapan);
            model.addAttribute("kitaplarim", oduncAlmaService.getKullaniciGecmisi(girisYapan.getId()));
            return "kullanici_panel";
        }
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
    public String kitaplariListele(Model model, @RequestParam(required = false) String keyword) {

        List<Kitap> liste;

        if (keyword != null && !keyword.isEmpty()) {
            liste = kitapService.search(keyword);
        } else {
            liste = kitapService.findAll();
        }

        model.addAttribute("kitapListesi", liste);
        model.addAttribute("keyword", keyword);
        model.addAttribute("tumYazarlar", yazarService.findAll());
        model.addAttribute("tumKategoriler", kategoriService.findAll());
        model.addAttribute("yeniKitap", new Kitap());

        return "kitaplar";
    }

    @PostMapping("/kitaplar/kaydet")
    public String kitapKaydet(@Valid @ModelAttribute("yeniKitap") Kitap kitap,
                              BindingResult bindingResult,
                              Model model) {

        if (kitap.getId() == null && kitapService.isbnVarMi(kitap.getIsbn())) {
            bindingResult.rejectValue("isbn", "error.kitap", "Bu ISBN numarası sistemde zaten kayıtlı!");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("kitapListesi", kitapService.findAll());
            model.addAttribute("tumYazarlar", yazarService.findAll());
            model.addAttribute("tumKategoriler", kategoriService.findAll());
            model.addAttribute("keyword", null);

            return "kitaplar";
        }
        try {
             kitapService.save(kitap);
        } catch (Exception e) {
            model.addAttribute("hataMesaji", "Beklenmedik bir hata oluştu: " + e.getMessage());
            model.addAttribute("kitapListesi", kitapService.findAll());
            model.addAttribute("tumYazarlar", yazarService.findAll());
            model.addAttribute("tumKategoriler", kategoriService.findAll());
            return "kitaplar";
        }

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

    @PostMapping("/kullanicilar/admin-sifre-guncelle")
    public String adminSifreGuncelle(@RequestParam("id") int id,
                                     @RequestParam("yeniSifre") String yeniSifre) {
        kullaniciService.adminSifreGuncelle(id, yeniSifre);
        return "redirect:/kullanicilar?msg=sifreGuncellendi";
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

    @GetMapping("/ogrenci/kitap-al/{kitapId}")
    public String ogrenciKitapAl(@PathVariable Integer kitapId, java.security.Principal principal) {
        String email = principal.getName();
        com.kutuphane.yonetimsistemi.entity.Kullanici ogrenci = kullaniciService.findAll().stream()
                .filter(k -> k.getEmail().equals(email)).findFirst().get();

        Kitap kitap = kitapService.getById(kitapId);

        if (kitap.getAdet() <= 0) {
            return "redirect:/kitaplar?error=stok";
        }

        OduncAlma islem = new OduncAlma();
        islem.setKullanici(ogrenci);
        islem.setKitap(kitap);
        islem.setSonIadeTarihi(java.time.LocalDate.now().plusDays(15));

        oduncAlmaService.oduncVer(islem);

        return "redirect:/";
    }

    @GetMapping("/ogrenci/iade-et/{oduncId}")
    public String ogrenciIadeEt(@PathVariable Integer oduncId) {
        oduncAlmaService.kitapIadeEt(oduncId);
        return "redirect:/";
    }

    @GetMapping("/sifremi-unuttum")
    public String sifremiUnuttumSayfasi() {
        return "sifremi_unuttum";
    }

    @PostMapping("/sifremi-unuttum")
    public String sifremiUnuttumIslemi(@RequestParam("email") String email, Model model) {
        try {
            kullaniciService.sifreSifirlamaKoduGonder(email);
            return "redirect:/token-gir";
        } catch (RuntimeException e) {
            model.addAttribute("hata", e.getMessage());
            return "sifremi_unuttum";
        }
    }

    @GetMapping("/token-gir")
    public String tokenGirSayfasi() {
        return "token_gir";
    }

    @PostMapping("/token-kontrol")
    public String tokenKontrolIslemi(@RequestParam("token") String token, Model model) {
        if (kullaniciService.tokenGecerliMi(token)) {
            return "redirect:/yeni-sifre?token=" + token;
        } else {
            model.addAttribute("hata", "Girdiğiniz kod hatalı veya geçersiz!");
            return "token_gir";
        }
    }

    @GetMapping("/yeni-sifre")
    public String yeniSifreSayfasi(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "yeni_sifre";
    }

    @PostMapping("/sifre-degis")
    public String sifreDegisIslemi(@RequestParam("token") String token,
                                   @RequestParam("sifre1") String sifre1,
                                   @RequestParam("sifre2") String sifre2,
                                   Model model) {
        if (!sifre1.equals(sifre2)) {
            model.addAttribute("hata", "Şifreler birbiriyle uyuşmuyor!");
            model.addAttribute("token", token);
            return "yeni_sifre";
        }

        try {
            kullaniciService.sifreyiGuncelle(token, sifre1);
            return "redirect:/login?sifreDegisti=true";
        } catch (RuntimeException e) {
            model.addAttribute("hata", e.getMessage());
            model.addAttribute("token", token);
            return "yeni_sifre";
        }
    }
    @GetMapping("/profil/sifre-degistir")
    public String profilSifreDegistirSayfasi() {
        return "profil_sifre_degistir";
    }

    @PostMapping("/profil/sifre-guncelle")
    public String profilSifreGuncelle(Principal principal,
                                      @RequestParam("eskiSifre") String eskiSifre,
                                      @RequestParam("yeniSifre") String yeniSifre,
                                      @RequestParam("yeniSifreTekrar") String yeniSifreTekrar,
                                      Model model) {

        if (!yeniSifre.equals(yeniSifreTekrar)) {
            model.addAttribute("hata", "Yeni şifreler birbiriyle uyuşmuyor!");
            return "profil_sifre_degistir";
        }
        boolean sonuc = kullaniciService.kullaniciKendiSifresiniGuncelle(principal.getName(), eskiSifre, yeniSifre);

        if (sonuc) {
            return "redirect:/profil/sifre-degistir?basarili=true";
        } else {
            model.addAttribute("hata", "Mevcut şifrenizi yanlış girdiniz!");
            return "profil_sifre_degistir";
        }
    }
}
