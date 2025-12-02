package com.kutuphane.yonetimsistemi.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "OduncAlma")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OduncAlma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "kullanici_id", nullable = false)
    private Kullanici kullanici;

    @ManyToOne
    @JoinColumn(name = "kitap_id", nullable = false)
    private Kitap kitap;


    @Column(name = "odunc_alma_tarihi", nullable = false)
    private LocalDateTime oduncAlmaTarihi;

    @Column(name = "son_iade_tarihi", nullable = false)
    private LocalDate sonIadeTarihi;

    @Column(name = "gercek_iade_tarihi")
    private LocalDate gercekIadeTarihi;
}