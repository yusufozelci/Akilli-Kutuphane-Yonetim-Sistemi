package com.kutuphane.yonetimsistemi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "Islem_Loglari")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IslemLoglari {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "islem_turu", nullable = false)
    private String islemTuru;

    @Column(name = "aciklama")
    private String aciklama;

    @Column(name = "kullanici_id")
    private Integer kullaniciId;

    @Column(name = "kitap_id")
    private Integer kitapId;

    @Column(name = "tarih", nullable = false)
    private LocalDateTime tarih;
}