import com.sun.net.httpserver.HttpServer;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.security.MessageDigest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class UrlShortenerServer {
    public static void main(String[] args) {
        // Test metodları
        System.out.println("https://google.com: " + isValidUrl("https://google.com"));
        System.out.println("google.com: " + isValidUrl("google.com"));
        System.out.println("boş string: " + isValidUrl(""));
        System.out.println("null: " + isValidUrl(null));

        System.out.println("hash testi: " + getMD5Hash("hello"));

        System.out.println("https://google.com kısa kod: " + generateShortCode("https://google.com"));
        System.out.println("google.com kısa kod: " + generateShortCode("google.com"));
        System.out.println("https://youtube.com kısa kod: " + generateShortCode("https://youtube.com"));

        // Server'ı başlat
        startServer();
    }

    private static boolean isValidUrl(String url) {
        if (url == null || url.trim().isEmpty() || (!url.startsWith("http://") && !url.startsWith("https://"))) {
            return false;
        }
        return true;
    }

    public static String getMD5Hash(String input) {
        try {
            // Java'nın hash algoritmaları sınıfı
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Hash algoritmaları byte'lar ile çalışır
            byte[] inputBytes = input.getBytes();
            byte[] hashBytes = md.digest(inputBytes);
            StringBuilder sb = new StringBuilder();
            for (byte asByte : hashBytes) {
                sb.append(String.format("%02x", asByte));
            }
            return sb.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String generateShortCode(String url) {
        if (isValidUrl(url)) {
            String hash = getMD5Hash(url);
            return hash.substring(0, 8);
        }
        return null;
    }

    public static String extractUrlFromJson(String json) {
        int urlStart = json.indexOf("\"url\":");
        int urlValueStart = json.indexOf("\"", urlStart + 6);
        int urlValueEnd = json.indexOf("\"", urlValueStart + 1);
        String url = json.substring(urlValueStart + 1, urlValueEnd);
        return url;
    }

    // HashMap key value put(k,v)=>ekle/guncelle get(key)->Valueyu getir  containsKey(k)key var mı kontrolü
    static class UrlStorage {
        private static HashMap<String, String> urls = new HashMap<>();
        private static HashMap<String, Integer> clickCount = new HashMap<>();

        public static void save(String shortCode, String url) {
            if (!urls.containsKey(shortCode)) {
                urls.put(shortCode, url);
                clickCount.put(shortCode, 0);
            }
        }

        public static String getUrl(String shortCode) {
            return urls.get(shortCode);
        }

        public static int getClickCount(String shortCode) {
            return clickCount.get(shortCode);
        }

        public static void incrementClick(String shortCode) {
            int currentCount = clickCount.getOrDefault(shortCode, 0);
            clickCount.put(shortCode, currentCount + 1);
        }
    }

    public static void startServer() {
        try {
            // HttpServer factory pattern örneği
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            server.createContext("/test", exchange -> {
                try {
                    System.out.println("Handler çalıştı!");
                    String response = "Merhaba! Server çalışıyor!";

                    byte[] responseBytes = response.getBytes();

                    exchange.sendResponseHeaders(200, responseBytes.length);
                    exchange.getResponseBody().write(responseBytes);
                    exchange.getResponseBody().close();
                    System.out.println("Yanıt gönderildi!");
                } catch (Exception e) {
                    System.out.println("Handler hatası: " + e.getMessage());
                }
            });

            server.createContext("/shorten", exchange -> {
                try {
                    String method = exchange.getRequestMethod();
                    System.out.println("Method: " + method);
                    if (!method.equals("POST")) {
                        exchange.sendResponseHeaders(405, 0);
                        exchange.close();
                        return;
                    }
                    InputStream body = exchange.getRequestBody();
                    Scanner scanner = new Scanner(body);
                    scanner.useDelimiter("\\A"); // Scanner'a "nerede duracağını" söyler.
                    String requestBody = scanner.next();
                    System.out.println("Body: " + requestBody);

                    String url = extractUrlFromJson(requestBody);
                    System.out.println("Çıkarılan URL: " + url);

                    String shortCode = generateShortCode(url);
                    System.out.println("Shortcode: " + shortCode);

                    UrlStorage.save(shortCode, url);
                    System.out.println("Storage'a kaydedildi!");

                    String jsonResponse = "{\"shortCode\": \"" + shortCode + "\", \"shortUrl\": \"http://localhost:8080/" + shortCode + "\", \"originalUrl\": \"" + url + "\"}";
                    String response = jsonResponse;
                    byte[] responseBytes = response.getBytes();
                    exchange.sendResponseHeaders(200, responseBytes.length);
                    exchange.getResponseBody().write(responseBytes);
                    exchange.getResponseBody().close();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });

            server.createContext("/", exchange -> {
                try {
                    String method = exchange.getRequestMethod();
                    if (!method.equals("GET")) {
                        exchange.sendResponseHeaders(405, 0);
                        exchange.close();
                        return;
                    }

                    String path = exchange.getRequestURI().getPath();  // "/99999ebc"
                    String shortCode = path.substring(1);              // "99999ebc" (ilk / sil)

                    System.out.println("Aranan shortCode: " + shortCode);

                    String originalUrl = UrlStorage.getUrl(shortCode);

                    if (originalUrl != null) {
                        // 301 Redirect - Tıklanma sayısını artır
                        UrlStorage.incrementClick(shortCode);
                        System.out.println("Yönlendiriliyor: " + originalUrl);
                        System.out.println("Tıklanma sayısı: " + UrlStorage.getClickCount(shortCode));

                        exchange.getResponseHeaders().add("Location", originalUrl);
                        exchange.sendResponseHeaders(301, 0);
                    } else {
                        // 404 Not Found
                        System.out.println("Short URL bulunamadı: " + shortCode);
                        String notFound = "Short URL not found!";
                        byte[] bytes = notFound.getBytes();
                        exchange.sendResponseHeaders(404, bytes.length);
                        exchange.getResponseBody().write(bytes);
                    }
                    exchange.close();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });

            server.start();
            System.out.println("Server started on port 8080");
            System.out.println("Test URL: http://localhost:8080/test");
            System.out.println("API: POST http://localhost:8080/shorten");

        } catch (IOException e) {
            System.out.println("Server hatası: " + e.getMessage());
        }
    }
}