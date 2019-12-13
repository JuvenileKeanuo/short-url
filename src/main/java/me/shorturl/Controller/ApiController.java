package me.shorturl.Controller;

import me.shorturl.Url;
import me.shorturl.UrlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class ApiController {

    private final UrlDao dao;

    @Autowired
    public ApiController(UrlDao urlDao) {
        this.dao = urlDao;
    }

    @RequestMapping("/new_url")
    public Url addNewUrl(String longUrl) throws IOException {
        return dao.addNewUrl(longUrl);
    }

    @RequestMapping("favicon.ico")
    public String fav() {
        return "no icon";
    }

    @RequestMapping("/{shortUrl}")
    public void a(@PathVariable("shortUrl") String text, HttpServletResponse response) throws Exception {
        System.out.println(text);
        System.out.println("redirecting!");
        response.sendRedirect(URLDecoder.decode(dao.getUrlByShortUrl(text), StandardCharsets.UTF_8));
    }
}
