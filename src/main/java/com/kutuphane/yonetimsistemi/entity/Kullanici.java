package com.kutuphane.yonetimsistemi.entity;
import com.kutuphane.yonetimsistemi.entity.enums.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
@Entity
@Table(name = "Kullanicilar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kullanici {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ad", nullable = false)
    private String ad;

    @Column(name = "soyad", nullable = false)
    private String soyad;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    @NotBlank(message = "Email alanı boş bırakılamaz.")
    @Email(message = "Lütfen geçerli bir email adresi giriniz (Örn: ornek@mail.com).")
    private String email;

    @Column(name = "sifre", nullable = false)
    private String sifre;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol" , nullable = false)
    private Rol rol;
}
