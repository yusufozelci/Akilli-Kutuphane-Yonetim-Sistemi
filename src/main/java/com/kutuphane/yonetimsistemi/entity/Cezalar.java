package com.kutuphane.yonetimsistemi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "Cezalar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cezalar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "odunc_alma_id", nullable = false, unique = true)
    private OduncAlma oduncAlma;


    @Column(name = "ceza_tutari", nullable = false, precision = 10, scale = 2)
    private BigDecimal cezaTutari;

    @Column(name = "odendi_mi", nullable = false)
    private boolean odendiMi = false;
}
