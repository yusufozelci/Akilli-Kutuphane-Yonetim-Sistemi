package com.kutuphane.yonetimsistemi.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void gecikmeUyarisiGonder(String aliciEmail, String kitapAdi, String sonIadeTarihi) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(aliciEmail);
        message.setSubject("Kütüphane Ödünç Hatırlatması: GECİKME UYARISI!");

        String metin = "Sayın üyemiz,\n\n"
                + kitapAdi + " adlı kitap için son iade tarihiniz (" + sonIadeTarihi + ") geçmiştir.\n"
                + "Lütfen en kısa sürede iade işlemini gerçekleştirin. Aksi takdirde ceza uygulanacaktır.\n\n"
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