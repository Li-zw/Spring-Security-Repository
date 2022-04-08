package com.lzw.springsecurityjwtdemo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class SpringsecurityjwtdemoApplicationTests {


    /**
     * 创建Token
     */
    @Test
    public void createToken() {
        JwtBuilder jwtBuilder = Jwts.builder()
                //声明的标识{"jti":"888"}
                .setId("888")
                //主体，用户{"sub":"Rose"}
                .setSubject("Rose")
                //创建日期{"ita":"xxxxxx"}
                .setIssuedAt(new Date())
                //签名手段，参数1：算法，参数2：言
                .signWith(SignatureAlgorithm.HS256, "xxxx");
        //获取jwt的token
        String token = jwtBuilder.compact();
        System.out.println(token);
        //三部分的base64解密
        System.out.println("--------------------");
        String[] split = token.split("\\.");
        System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
        System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
        //无法解密
        System.out.println(Base64Codec.BASE64.decodeToString(split[2]));

    }


    /**
     * 解析Token
     */
    @Test
    public void parseToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9." +
                "eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjQ5MzgyOTEzfQ." +
                "OlxZEYYqlWJds71YzRxtR6qrRi5YiSwrvMbh_68RisE";
        /**
         * 解析Token
         */
        Claims claims = Jwts.parser()
                .setSigningKey("xxxx")
                .parseClaimsJws(token)
                .getBody();
        //打印声明的属性
        System.out.println("id:" + claims.getId());
        System.out.println("subject:" + claims.getSubject());
        System.out.println("issuedAt:" + claims.getIssuedAt());
    }


    /**
     * 创建Token（失效时间）
     */
    @Test
    public void createTokenHasExp() {
        // 获取当前时间
        long now = System.currentTimeMillis();
        // 失效时间 一分钟
        long exp = now + 60 * 1000;
        JwtBuilder jwtBuilder = Jwts.builder()
                //声明的标识{"jti":"888"}
                .setId("888")
                //主体，用户{"sub":"Rose"}
                .setSubject("Rose")
                //创建日期{"ita":"xxxxxx"}
                .setIssuedAt(new Date())
                //签名手段，参数1：算法，参数2：言
                .signWith(SignatureAlgorithm.HS256, "xxxx")
                // 设置过期时间
                .setExpiration(new Date(exp));
        //获取jwt的token
        String token = jwtBuilder.compact();
        System.out.println(token);
        //三部分的base64解密
        System.out.println("--------------------");
        String[] split = token.split("\\.");
        System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
        System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
        //无法解密
        System.out.println(Base64Codec.BASE64.decodeToString(split[2]));

    }


    /**
     * 解析Token（失效时间）
     */
    @Test
    public void parseTokenHasExp() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjQ5MzgzODczLCJleHAiOjE2NDkzODM5MzN9.DniTjZeHyQjzpozKQ0gLPqg2Q9ptNF2eWiKsm-vaH4o";
        /**
         * 解析Token
         */
        Claims claims = Jwts.parser()
                .setSigningKey("xxxx")
                .parseClaimsJws(token)
                .getBody();
        //打印声明的属性
        System.out.println("id:" + claims.getId());
        System.out.println("subject:" + claims.getSubject());
        System.out.println("issuedAt:" + claims.getIssuedAt());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("签发时间: "+simpleDateFormat.format(claims.getIssuedAt()));
        System.out.println("过期时间: "+simpleDateFormat.format(claims.getExpiration()));
        System.out.println("当前时间: "+simpleDateFormat.format(new Date()));
    }



    /**
     * 创建Token（失效时间）
     */
    @Test
    public void createTokenByClaim() {
        // 获取当前时间
        long now = System.currentTimeMillis();
        // 失效时间 一分钟
        long exp = now + 60 * 1000;
        JwtBuilder jwtBuilder = Jwts.builder()
                //声明的标识{"jti":"888"}
                .setId("888")
                //主体，用户{"sub":"Rose"}
                .setSubject("Rose")
                //创建日期{"ita":"xxxxxx"}
                .setIssuedAt(new Date())
                //签名手段，参数1：算法，参数2：言
                .signWith(SignatureAlgorithm.HS256, "xxxx")
                // 设置过期时间
                .setExpiration(new Date(exp))
                // 直接传入 Map
//                .addClaims(map)
                .claim("roles","admin")
                .claim("logo","xxx.jpg");

        //获取jwt的token
        String token = jwtBuilder.compact();
        System.out.println(token);
        //三部分的base64解密
        System.out.println("--------------------");
        String[] split = token.split("\\.");
        System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
        System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
        //无法解密
        System.out.println(Base64Codec.BASE64.decodeToString(split[2]));

    }


    /**
     * 解析Token（自定义声明）
     */
    @Test
    public void parseTokenByClaim() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjQ5Mzg0NDE1LCJleHAiOjE2NDkzODQ0NzUsInJvbGVzIjoiYWRtaW4iLCJsb2dvIjoieHh4LmpwZyJ9.yKlWlRgQmxAKiTbLnabX0l1rGM-02LjYHUKx-vuCnNg";
        /**
         * 解析Token
         */
        Claims claims = Jwts.parser()
                .setSigningKey("xxxx")
                .parseClaimsJws(token)
                .getBody();
        //打印声明的属性
        System.out.println("id:" + claims.getId());
        System.out.println("subject:" + claims.getSubject());
        System.out.println("issuedAt:" + claims.getIssuedAt());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("签发时间: "+simpleDateFormat.format(claims.getIssuedAt()));
        System.out.println("过期时间: "+simpleDateFormat.format(claims.getExpiration()));
        System.out.println("当前时间: "+simpleDateFormat.format(new Date()));
        System.out.println("roles:"+claims.get("roles"));
        System.out.println("logo:"+claims.get("logo"));
    }







}
