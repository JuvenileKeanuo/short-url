package me.shorturl;

import me.shorturl.Constant.Constant;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ShorturlApplicationTests {

    @Test
    void contextLoads() {
        Url url = new Url();
        System.out.println("shortUrl    " + url.decimalToShortUrl(300));
    }

    @Test
    void testMap() {
        Url url = new Url();
        for (long i = 1; i < 1000; i++) {
            System.out.println("shortUrl    " + url.decimalToShortUrl(i));
            System.out.println("decimal " + url.shortUrlToDecimal(url.decimalToShortUrl(i)));
        }
    }

}
