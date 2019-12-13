package me.shorturl;

import me.shorturl.Constant.Constant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class Url implements RowMapper<Url> {
    private String longUrl;
    private String shortUrl;
    private long id;
    private int status = 200;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrlById(long id) {
        this.id = id;
        this.shortUrl = decimalToShortUrl(id);
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
        this.id = shortUrlToDecimal(shortUrl);
    }

    public long getId() {
        return id;
    }

    private final Map<Long, Character> longMapper = new Constant().longMapper;
    private final Map<Character, Long> charMapper = new Constant().charMapper;

    public String decimalToShortUrl(long demical) {

        String test = "";
        long d = demical;
        while (d > 0) {
            long cur = d % 62;
            d = d / 62;
            test = test.concat(String.valueOf(longMapper.get(cur)));
        }
        String reverse = "";
        for (int i = test.length() - 1; i >= 0; --i) {
            reverse = reverse.concat(String.valueOf(test.charAt(i)));
        }
        return reverse;
    }

    public long shortUrlToDecimal(String shortUrl) {
        long sum = 0;
        int i = 0;
        for (i = 0; i < shortUrl.length(); ++i) {
            long curPos = charMapper.get(shortUrl.charAt(i));
            sum = sum * 62 + curPos;
        }
        return sum;
    }

    @Override
    public Url mapRow(ResultSet resultSet, int i) throws SQLException {
        Url url = new Url();
        url.setShortUrlById(resultSet.getLong("shortUrl"));
        url.setLongUrl(resultSet.getString("longUrl"));
        return url;
    }
}
