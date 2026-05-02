# Akıllı Kütüphane Yönetim Sistemi

Bu proje, Spring Boot ve Thymeleaf kullanılarak geliştirilmiş, Java tabanlı bir **web uygulamasıdır**. Kitapların, üyelerin ve ödünç alma/iade işlemlerinin kolayca takip edilmesini sağlar.

## Projenin Amacı

Bu uygulama, kütüphane görevlilerinin günlük işlemlerini web tabanlı bir arayüz üzerinden dijital ortama taşıyarak verimliliği artırmayı ve manuel işlemlerdeki hata payını azaltmayı hedefler.

## Özellikler

- **Kitap Yönetimi**: Kitap ekleme, silme, güncelleme ve arama.
- **Üye Yönetimi**: Üye kaydı, bilgi güncelleme ve silme.
- **Ödünç Alma ve İade**: Kitapların ödünç verilmesi ve iade alınması işlemlerinin takibi.
- **Arama ve Filtreleme**: Kitapları ve üyeleri çeşitli kriterlere (isim, yazar, tür vb.) göre arama.
- **Raporlama**: Geciken kitaplar, en çok okunan kitaplar gibi temel raporların oluşturulması.

## Kullanılan Teknolojiler

- **Programlama Dili**: Java
- **Framework**: Spring Boot
- **Arayüz**: Thymeleaf
- **Veritabanı**: MySQL
- **Build Aracı**: Maven

## Kurulum

Projeyi yerel makinenizde çalıştırmak için aşağıdaki adımları izleyin:

1.  **Projeyi klonlayın:**
    ```sh
    git clone https://github.com/kullanici-adiniz/Akilli-Kutuphane-Yonetim-Sistemi.git
    ```

2.  **Proje dizinine gidin:**
    ```sh
    cd Akilli-Kutuphane-Yonetim-Sistemi
    ```

3.  **Veritabanı ayarlarını yapın:**
    - Proje içerisindeki `src/main/resources/application.properties` dosyasını kendi yerel veritabanı bilgilerinize göre güncelleyin.
    - Gerekliyse, veritabanı şemasını oluşturmak için sağlanan SQL script'ini çalıştırın.

4.  **Projeyi derleyin ve çalıştırın:**
    - Projeyi tercih ettiğiniz IDE (IntelliJ IDEA, Eclipse vb.) ile açın.
    - Gerekli Maven bağımlılıklarının indirilmesini bekleyin.
    - Ana sınıfı (`YonetimsistemiApplication.java`) çalıştırın.
    - Uygulama varsayılan olarak `http://localhost:8080` adresinde çalışacaktır.
