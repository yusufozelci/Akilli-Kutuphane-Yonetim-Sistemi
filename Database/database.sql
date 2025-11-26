CREATE DATABASE IF NOT EXISTS db_kutuphane
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;


USE db_kutuphane;


CREATE TABLE Yazarlar (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          ad VARCHAR(100) NOT NULL,
                          soyad VARCHAR(100) NOT NULL
);


CREATE TABLE Kategoriler (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             ad VARCHAR(100) NOT NULL UNIQUE
);


CREATE TABLE Kullanicilar (
                              id INT PRIMARY KEY AUTO_INCREMENT,
                              ad VARCHAR(100) NOT NULL,
                              soyad VARCHAR(100) NOT NULL,
                              email VARCHAR(150) NOT NULL UNIQUE,
                              sifre VARCHAR(255) NOT NULL,
                              rol ENUM('ADMIN', 'KULLANICI') NOT NULL DEFAULT 'KULLANICI'
);


CREATE TABLE Kitaplar (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          kitap_adi VARCHAR(255) NOT NULL,
                          isbn VARCHAR(20) UNIQUE,
                          yayin_yili INT,
                          adet INT NOT NULL DEFAULT 1,
                          yazar_id INT,
                          kategori_id INT,
                          FOREIGN KEY (yazar_id) REFERENCES Yazarlar(id) ON DELETE SET NULL,
                          FOREIGN KEY (kategori_id) REFERENCES Kategoriler(id) ON DELETE SET NULL
);


CREATE TABLE OduncAlma (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           kullanici_id INT,
                           kitap_id INT,
                           odunc_alma_tarihi TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           son_iade_tarihi DATE NOT NULL,
                           gercek_iade_tarihi DATE,
                           FOREIGN KEY (kullanici_id) REFERENCES Kullanicilar(id) ON DELETE CASCADE,
                           FOREIGN KEY (kitap_id) REFERENCES Kitaplar(id) ON DELETE CASCADE
);


CREATE TABLE Cezalar (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         odunc_alma_id INT,
                         ceza_tutari DECIMAL(10, 2) NOT NULL,
                         odendi_mi BOOLEAN NOT NULL DEFAULT FALSE,
                         FOREIGN KEY (odunc_alma_id) REFERENCES OduncAlma(id) ON DELETE CASCADE
);

-- Ceza Hesaplama Trigger'ı
DROP TRIGGER IF EXISTS after_odunc_iade_ceza_hesapla;

CREATE TRIGGER after_odunc_iade_ceza_hesapla
    AFTER UPDATE ON OduncAlma
    FOR EACH ROW
BEGIN
    DECLARE gecikme_gun INT;
    DECLARE gunluk_ceza DECIMAL(10, 2);

    SET gunluk_ceza = 20.00;

    IF (OLD.gercek_iade_tarihi IS NULL AND NEW.gercek_iade_tarihi IS NOT NULL)
        AND (NEW.gercek_iade_tarihi > NEW.son_iade_tarihi) THEN

        SET gecikme_gun = DATEDIFF(NEW.gercek_iade_tarihi, NEW.son_iade_tarihi);

        INSERT INTO Cezalar (odunc_alma_id, ceza_tutari, odendi_mi)
        VALUES (NEW.id, gecikme_gun * gunluk_ceza, FALSE);

    END IF;
END;

-- Stok Yönetimi Trigger'ı

DROP TRIGGER IF EXISTS after_odunc_alma_stok_azalt;
CREATE TRIGGER after_odunc_alma_stok_azalt
    AFTER INSERT ON OduncAlma
    FOR EACH ROW
BEGIN
    UPDATE Kitaplar SET adet = adet - 1 WHERE id = NEW.kitap_id;
END;

DROP TRIGGER IF EXISTS after_odunc_iade_stok_artir;
CREATE TRIGGER after_odunc_iade_stok_artir
    AFTER UPDATE ON OduncAlma
    FOR EACH ROW
BEGIN
    IF (OLD.gercek_iade_tarihi IS NULL AND NEW.gercek_iade_tarihi IS NOT NULL) THEN
        UPDATE Kitaplar SET adet = adet + 1 WHERE id = NEW.kitap_id;
    END IF;
END;

-- Raporlama Prosedür'ü

DROP PROCEDURE IF EXISTS sp_kitap_stok_raporu;

CREATE PROCEDURE sp_kitap_stok_raporu()
BEGIN
    SELECT
        k.kitap_adi AS 'Kitap Adı',
        CONCAT(y.ad, ' ', y.soyad) AS 'Yazar',
        c.ad AS 'Kategori',
        k.adet AS 'Stok Miktarı',
        CASE
            WHEN k.adet > 0 THEN 'Mevcut'
            ELSE 'Tükendi'
            END AS 'Durum'
    FROM Kitaplar k
            INNER JOIN Yazarlar y ON k.yazar_id = y.id
            INNER JOIN Kategoriler c ON k.kategori_id = c.id
    ORDER BY k.adet DESC;
END;
