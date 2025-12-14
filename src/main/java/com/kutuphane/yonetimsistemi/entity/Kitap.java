package com.kutuphane.yonetimsistemi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Kitaplar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kitap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "kitap_adi", nullable = false)
    private String kitapAdi;

    @Column(name = "isbn", length = 13, nullable = false, unique = true)
    @NotBlank(message = "ISBN alanı boş bırakılamaz.")
    @Pattern(regexp = "^\\d{13}$", message = "ISBN numarası sadece rakamlardan oluşmalı ve 13 haneli olmalıdır.")
    private String isbn;

    @Column(name = "yayin_yili")
    @NotNull(message = "Yayın yılı boş bırakılamaz.")
    @Min(value = 1000, message = "Yayın yılı en az 1000 olabilir.")
    @Max(value = 2026, message = "Yayın yılı 2026 dan büyük olamaz.")
    private Integer yayinYili;

    @Column(name = "adet", nullable = false)
    private int adet;

    @ManyToOne
    @JoinColumn(name = "yazar_id", referencedColumnName = "id")
    private Yazar yazar;

    @ManyToOne
    @JoinColumn(name = "kategori_id", referencedColumnName = "id")
    private Kategori kategori;
}