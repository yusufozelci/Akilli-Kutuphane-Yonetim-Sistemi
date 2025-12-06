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
}