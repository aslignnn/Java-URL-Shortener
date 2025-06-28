# URL Shortener - İlk Backend Projem 🎓

Java öğrenme sürecimde yaptığım ilk backend projesi. Bit.ly benzeri URL kısaltma servisi.

## 📚 Neden Bu Proje?

Yazılım geliştirmeyi öğrenirken framework'lerden önce Java'nın temel özelliklerini anlamak istedim. Bu projede:
- Framework kullanmadan HTTP server kurdum
- JSON processing'i manuel yaptım  
- HashMap ile veri yönetimi öğrendim
- REST API prensiplerini uyguladım

## 🎯 Ne Yapar?

- Uzun URL'leri kısa kodlara çevirir
- Kısa kodlarla orijinal sitelere yönlendirir
- Tıklanma sayısını takip eder
- JSON API sağlar

## 🛠 Kullanılan Teknolojiler

- **Pure Java** (Framework yok!)
- **HttpServer** - Java'nın built-in HTTP server'ı
- **HashMap** - Bellek içi veri saklama
- **MD5** - Hash algoritması
- **JSON** - Manuel parsing

## 🚀 Nasıl Çalıştırılır

```bash
# Derle
javac UrlShortenerServer.java

# Çalıştır  
java UrlShortenerServer
```

Server localhost:8080'de çalışır.

## 📡 API Testleri

### URL Kısaltma
```bash
curl -X POST http://localhost:8080/shorten \
  -H "Content-Type: application/json" \
  -d '{"url": "https://www.google.com"}'
```

**Response:**
```json
{
  "shortCode": "d5d1660c",
  "shortUrl": "http://localhost:8080/d5d1660c",
  "originalUrl": "https://www.google.com"
}
```

### Kısa URL Kullanma
Tarayıcıda: `http://localhost:8080/[kısa-kod]`

## 📖 Bu Projede Neler Öğrendim?

### 🌐 **HTTP ve Web Temelleri**
- **HTTP Request/Response döngüsü** - Tarayıcı nasıl serverla konuşur?
- **HTTP Methods** - GET, POST arasındaki farklar
- **HTTP Status Codes** - 200 (OK), 301 (Redirect), 404 (Not Found), 405 (Method Not Allowed)
- **HTTP Headers** - Content-Type, Location header'ları
- **Request Body** - POST isteğinde veri nasıl gönderilir?

### 🔗 **REST API Tasarımı**
- **Endpoint tasarımı** - `/shorten`, `/{shortCode}` gibi mantıklı URL'ler
- **RESTful prensipleri** - Her endpoint'in belirli bir işi olması
- **API response formatı** - Tutarlı JSON yanıtları
- **Error handling** - Hatalı durumlar için uygun yanıtlar

### ☕ **Java Core Konuları**
- **HttpServer sınıfı** - Java'nın built-in web server'ı
- **Lambda expressions** - `exchange -> { ... }` syntax'ı
- **InputStream/Scanner** - Network'ten gelen veriyi okuma
- **Exception handling** - Try-catch blokları ve hata yönetimi
- **Static methods vs instance methods** - Farkları ve kullanım yerleri

### 📊 **Veri Yapıları ve Algoritmalar**
- **HashMap** - Key-value çiftleri, put/get/containsKey metodları
- **Hash algoritmaları** - MD5 ile unique kod üretme
- **String manipülasyonu** - indexOf, substring, startsWith metodları
- **JSON parsing** - Manuel olarak JSON'dan veri çıkarma

### 🛠 **Yazılım Geliştirme Pratikleri**
- **Modüler kod yazma** - Her metodun tek bir işi yapması
- **Debugging** - Console.log ile hata bulma ve çözme
- **Code organization** - Inner class kullanımı (UrlStorage)
- **Validation** - Input kontrolü (URL geçerlilik testi)

### 🔧 **Network ve System Programming**
- **Port ve Socket kavramları** - localhost:8080 nedir?
- **Request routing** - Farklı URL'ler için farklı handler'lar
- **URL encoding** - Özel karakterlerin URL'de nasıl işlendiği
- **Redirect mechanism** - 301 status code ile yönlendirme

### 📱 **API Testing ve Tools**
- **Postman kullanımı** - API test etme, request gönderme
- **cURL commands** - Command line'dan HTTP istekleri
- **JSON formatting** - Doğru Content-Type header'ları
- **Manual testing** - Tarayıcıda redirect'leri test etme


## 🎯 En Büyük Öğrenme

Framework kullanmadan pure Java ile bir şeyler yapabilmek çok güçlendirici! Artık Spring Boot öğrendiğimde altta ne olduğunu anlayacağım. HTTP'nin nasıl çalıştığını, JSON'ın nasıl işlendiğini, API'ların nasıl tasarlandığını temel seviyede kavradım.

## 🎓 Öğrenci Notları

Bu projeyi yaparken AI'dan kod kopyalamak yerine her adımı anlayarak yazdım. Hatalar yaptım, debug ettim, öğrendim. Bu süreç çok değerliydi çünkü:

- **Problem solving** becerilerimi geliştirdi
- **Step-by-step thinking** öğretti

İlerleyen projelerde Spring Boot vs. frameworkleri öğrenmeyi planlıyorum ama bu temel bilgiler sayesinde çok daha rahat olacağım.

---
**Not:** Bu benim yazılım öğrenme yolculuğumdaki ilk backend projesi. Kod kalitesi mükemmel olmayabilir ama öğrenme süreci inanılmaz değerliydi! 🚀
