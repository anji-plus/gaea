package com.anji.mirror.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * 根据userId生成token凭据
 * @author anji gaea teams
 * @since 2020-08-20
 */
public class JwtTokenUtil {


    private static String TOKEN_SIGNING_KEY = "anji_mirror_token_signing_key";
    private static final String AUTH_HEADER_NAME = "AuthorizationToken";

    /**
     * 生成令牌
     * @author anji gaea teams
     * @param subject（用户名或id）
     * @param expirationSeconds
     * @return
     */
    public static String createToken(String subject, long expirationSeconds) {
        return Jwts.builder().setClaims(null).setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + expirationSeconds * 1000))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SIGNING_KEY).compact();
    }

    public static String createToken(String subject) {
        return Jwts.builder().setClaims(null).setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, TOKEN_SIGNING_KEY).compact();
    }

    /**
     * 判断令牌是否过期
     * @author anji gaea teams
     * @param token
     * @return
     */
    public static boolean isExpired(String token) {
        boolean resuslt = false;
        try {
            Claims claims = Jwts.parser().setSigningKey(TOKEN_SIGNING_KEY).parseClaimsJws(token).getBody();

            if (claims.isEmpty()) {
                return true;
            }
        } catch (ExpiredJwtException e) {
            resuslt = true;
        } catch (Exception e) {
            e.printStackTrace();
            resuslt = true;
        }
        return resuslt;
    }

    /**
     * 从令牌中获取subject（用户名或id）
     * @author anji gaea teams
     * @param token
     * @return
     */
    public static String parseToken(String token) {
        String subject="";
        try {
            Claims claims = Jwts.parser().setSigningKey(TOKEN_SIGNING_KEY).parseClaimsJws(token).getBody();
            subject=claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subject;
    }

    public static String parseToken(HttpServletRequest request) {
        String subject="";
        String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null && !token.isEmpty()) {
            subject = parseToken(token);
        }
        return subject;
    }
}