package com.kutuphane.yonetimsistemi.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Yazarlar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Yazar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ad", nullable = false)
    private String ad;
    @Column(name = "soyad" , nullable = false)
    private String soyad;
}
