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