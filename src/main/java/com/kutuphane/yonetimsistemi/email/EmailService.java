package com.kutuphane.yonetimsistemi.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String gonderenAdresi;

    public void gecikmeUyarisiGonder(String aliciEmail, String kitapAdi, String sonIadeTarihi,
                                     BigDecimal cezaTutari, long gecikmeGunu) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(gonderenAdresi);
        message.setTo(aliciEmail);
        message.setSubject("Kütüphane Ödünç Hatırlatması: GECİKME UYARISI!");

        String metin = "Sayın üyemiz,\n\n"
                + kitapAdi + " adlı kitap için son iade tarihi (" + sonIadeTarihi + ") geçmiştir.\n"
                + "Kitabı " + gecikmeGunu + " gün gecikmeyle iade ettiğiniz tespit edilmiştir.\n\n"
                + "Hesabınıza yansıyan güncel ceza tutarı: " + cezaTutari + " TL'dir.\n"
                + "NOT: Günlük ceza bedeli 20.00 TL'dir. İade etmediğiniz her gün için bu tutar artmaya devam edecektir.\n\n"
                + "İyi günler dileriz.";

        message.setText(metin);

        try {
            mailSender.send(message);
            System.out.println("E-posta başarıyla gönderildi: " + aliciEmail);
        } catch (Exception e) {
            System.err.println("E-posta gönderme hatası: " + e.getMessage());
        }
    }

    public void sifreSifirlamaMailiGonder(String aliciEmail, String ad, String token) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(gonderenAdresi);
        message.setTo(aliciEmail);
        message.setSubject("Şifre Sıfırlama Kodu");

        String metin = "Merhaba " + ad + ",\n\n"
                + "Şifrenizi sıfırlamak için gereken kod: " + token + "\n\n"
                + "Bu kodu kimseyle paylaşmayınız.\n\n"
                + "İyi günler dileriz.";

        message.setText(metin);

        try {
            mailSender.send(message);
            System.out.println("Sıfırlama maili gönderildi: " + aliciEmail);
        } catch (Exception e) {
            System.err.println("E-posta gönderme hatası: " + e.getMessage());
        }
    }

    public void sifreDegistiBilgilendirmesi(String aliciEmail, String ad) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(gonderenAdresi);
        message.setTo(aliciEmail);
        message.setSubject("Hesap Güvenliği: Şifreniz Değiştirildi");

        String metin = "Merhaba " + ad + ",\n\n"
                + "Kütüphane hesabınızın şifresi az önce değiştirilmiştir.\n"
                + "Eğer bu işlemi siz yaptıysanız bu maili dikkate almayınız.\n\n"
                + "Eğer şifrenizi siz değiştirmediyseniz, hesabınız tehlikede olabilir. Lütfen hemen yönetimle iletişime geçiniz.\n\n"
                + "Saygılarımızla,\nAkıllı Kütüphane Yönetimi";

        message.setText(metin);

        try {
            mailSender.send(message);
            System.out.println("Bilgilendirme maili gönderildi: " + aliciEmail);
        } catch (Exception e) {
            System.err.println("E-posta gönderme hatası: " + e.getMessage());
        }
    }
}