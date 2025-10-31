package com.kutuphane.yonetimsistemi.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Kitaplar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kitap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "kitap_adi", nullable = false)
    private String kitapAdi;

    @Column(name = "isbn", unique = true)
    private String isbn;

    @Column(name = "yayin_yili")
    private String yayinYili;

    @Column(name = "adet" , nullable = false)
    private String adet;

    @Column(name = "yazar_id" , nullable = false)
    private String yazarId;

    @Column(name = "kategori_id" , nullable = false)
    private String kategoriId;

    @ManyToOne
    @JoinColumn(name = "yazar_id" , referencedColumnName = "id")
    private Yazar yazar;

    @ManyToOne
    @JoinColumn(name = "kategori_id" , referencedColumnName = "id")
    private Kategori kategori;
}
