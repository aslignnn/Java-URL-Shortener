# URL Shortener

Uzun URL'leri kısa kodlara dönüştüren basit bir web servisi. Pure Java ile geliştirilmiş, backend öğrenmek için ideal bir başlangıç projesi.

## 🎯 Proje Amacı

Bu proje backend geliştirme becerilerini öğrenmek için tasarlanmıştır. HTTP server kurma, REST API tasarımı, JSON işlemleri ve hashing algoritmalarını pratik yaparak öğrenmeyi hedefler.

## ✨ Özellikler

- ✅ Uzun URL'leri kısa kodlara dönüştürme
- ✅ Kısa kodlarla orijinal URL'lere yönlendirme
- ✅ Tıklanma sayısı takibi
- ✅ RESTful API desteği
- ✅ JSON formatında yanıtlar
- ✅ Hata yönetimi

## 🛠 Teknolojiler

- **Java 11+** - Ana programlama dili
- **HttpServer** - Built-in HTTP server
- **MD5 Hashing** - Kısa kod üretimi
- **JSON** - API yanıt formatı
- **Pure Java** - Harici framework yok

## 🚀 Kurulum ve Çalıştırma

### Gereksinimler
- Java 11 veya üzeri
- Herhangi bir IDE (IntelliJ IDEA, Eclipse, VS Code)

### Çalıştırma
```bash
# Projeyi klonla veya indir
git clone [repo-url]

# Klasöre gir
cd url-shortener

# Derle ve çalıştır
javac -d out src/main/java/com/urlshortener/*.java
java -cp out com.urlshortener.Main
```

Server `http://localhost:8080` adresinde çalışacak.

## 📡 API Endpoints

### 1. URL Kısaltma
```http
POST /shorten
Content-Type: application/json

{
  "url": "https://www.example.com/very/long/url"
}
```

**Yanıt:**
```json
{
  "shortCode": "a1b2c3",
  "shortUrl": "http://localhost:8080/a1b2c3",
  "originalUrl": "https://www.example.com/very/long/url"
}
```

### 2. URL Yönlendirme
```http
GET /{shortCode}
```

**Yanıt:** 301 Redirect orijinal URL'e

### 3. İstatistik Görüntüleme
```http
GET /stats/{shortCode}
```

**Yanıt:**
```json
{
  "shortCode": "a1b2c3",
  "originalUrl": "https://www.example.com/very/long/url",
  "clickCount": 5,
  "createdAt": "2025-06-28T10:30:00"
}
```

## 🧪 Test Örnekleri

### cURL ile Test
```bash
# URL kısaltma
curl -X POST http://localhost:8080/shorten \
  -H "Content-Type: application/json" \
  -d '{"url": "https://www.google.com"}'

# İstatistik görüntüleme
curl http://localhost:8080/stats/a1b2c3
```

### Browser ile Test
1. POST endpoint'i için Postman veya benzer tool kullan
2. Dönen kısa URL'i browser'da aç
3. Orijinal siteye yönlendirildiğini gör

## 📁 Proje Yapısı

```
url-shortener/
├── README.md
└── src/
    └── main/
        └── java/
            └── com/
                └── urlshortener/
                    ├── Main.java                    # Ana sınıf
                    ├── UrlShortenerServer.java      # HTTP Server
                    ├── UrlShortener.java            # Kısaltma mantığı
                    ├── UrlStorage.java              # Veri saklama
                    └── models/
                        └── UrlData.java             # URL veri modeli
```

## 🎓 Öğrenilen Konular

- HTTP Server kurma (HttpServer sınıfı)
- Request/Response işleme
- JSON parsing ve oluşturma
- RESTful API tasarımı
- Hash algoritmaları (MD5)
- Error handling
- URL validation
- HTTP status kodları

## 🔄 Gelecek Geliştirmeler

- [ ] Veritabanı desteği (H2/SQLite)
- [ ] Custom domain desteği
- [ ] Expiration date özelliği
- [ ] Admin panel
- [ ] Rate limiting
- [ ] Analytics dashboard

## 👨‍💻 Geliştirici Notları

Bu proje backend öğrenme serüveninin ilk adımıdır. Sonraki projeler:
- Forum API'si (veritabanı işlemleri)
- Chat Sistemi (real-time communication)
- CMS (kapsamlı backend)

---
**Not:** Bu proje eğitim amaçlıdır. Production kullanımı için güvenlik ve performans iyileştirmeleri gereklidir.
