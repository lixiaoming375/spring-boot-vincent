package com.vincent.utils;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Map;
import java.util.UUID;

import static io.jsonwebtoken.Jwts.parser;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/9/1915:40
 */
public class JwtTokenUtils {

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
    }

    private static Key generatorKey(){
        byte[] bin = DatatypeConverter.parseBase64Binary("471b759aad8143dcba476a3c38bacdec");
        Key key = new SecretKeySpec(bin, SignatureAlgorithm.HS256.getJcaName());
        return key;
    }

    public static String generatorToken(Map<String, Object> payLoad) {
        return Jwts.builder()
                .setPayload(JSON.toJSONString(payLoad))
                .signWith(SignatureAlgorithm.HS256, generatorKey())
                .compact();
    }

    public static Claims phaseToken(String token){
       Jws<Claims> claimsJws=  Jwts.parser().setSigningKey(generatorKey()).parseClaimsJws(token);
       return claimsJws.getBody();
     }


}
