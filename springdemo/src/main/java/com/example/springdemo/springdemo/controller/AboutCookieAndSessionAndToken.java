package com.example.springdemo.springdemo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: springBootPractice
 * @description: Session、Cookie、Token的区别
 * @author: hu_pf
 * @create: 2019-12-02 15:07
 **/
@Controller
public class AboutCookieAndSessionAndToken {

    @RequestMapping("/testCookies")
    public String cookies(HttpSession session, HttpServletResponse response){
        Cookie cookie = new Cookie("testUser", "xxxx");
//        // 单位为秒，设置cookie的过期时间
//        cookie.setMaxAge(10);
//        // 指定了Cookie所属的域名
//        cookie.setDomain("localhost");
//        // 指定了Cookie所属的路径d
//        cookie.setPath("/testCookies");
//        // 告诉浏览器此Cookie只能靠浏览器Http协议传输,禁止其他方式访问
//        cookie.setHttpOnly(true);
//        // 告诉浏览器此Cookie只能在Https安全协议中传输,如果是Http则禁止传输
//        cookie.setSecure(true);
        response.addCookie(cookie);
        session.setAttribute("testUser","testUser");
        return "cookies";
    }

    @RequestMapping("/testSession")
    @ResponseBody
    public String testSession(HttpSession session){
        session.setAttribute("testSession","this is my session");
        return "testSession";
    }


    @RequestMapping("/testGetSession")
    @ResponseBody
    public String testGetSession(HttpSession session){
        Object testSession = session.getAttribute("testSession");
        return String.valueOf(testSession);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        // 签名算法 ，将对token进行签名
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 通过秘钥签名JWT
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("SECRET");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        Map<String,Object> claimsMap = new HashMap<>();
        claimsMap.put("name","xiaoMing");
        claimsMap.put("age",14);
        JwtBuilder builderWithSercet = Jwts.builder()
                .setSubject("subject")
                .setIssuer("issuer")
                .addClaims(claimsMap)
                .signWith(signatureAlgorithm, signingKey);
        System.out.printf(builderWithSercet.compact());
        System.out.printf("\n");
        // 获得Token中的信息
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary("SECRET"))
                .parseClaimsJws(builderWithSercet.compact()).getBody();
        System.out.printf(String.valueOf(claims.get("name")));
    }
}
