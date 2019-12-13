package me.shorturl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class UrlDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UrlDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getUrlByShortUrl(String text) {
        Url thisUrl = new Url();
        thisUrl.setShortUrl(text);
        String sql = "select * from urls where shortUrl = ?";
        //记录链接被访问的次数
        String countSql = "update urls set count=count+1 where shortUrl = ?";
        jdbcTemplate.update(countSql, thisUrl.getId());

        List<Url> list = jdbcTemplate.query(sql, new Url(), thisUrl.getId());
        if (list.size() > 0)
            return list.get(0).getLongUrl();
        else
            return "啥也没找到";
    }

    public Url addNewUrl(String longUrl) {
        Url newUrl = new Url();
        newUrl.setLongUrl(longUrl);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SimpleJdbcInsert simpleJdbcInsert;
        String sql = "INSERT INTO shorturl.urls (shortUrl, longUrl) VALUES(NULL," +
                "?);";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newUrl.getLongUrl());
            return ps;
        }, keyHolder);
        newUrl.setShortUrlById(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return newUrl;
    }
}
