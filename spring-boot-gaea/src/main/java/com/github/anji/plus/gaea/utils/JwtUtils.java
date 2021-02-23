package com.github.anji.plus.gaea.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * JWT工具类
 * @author lirui
 * @since 2021-01-09
 */
public abstract class JwtUtils {

    public static final String Authorization = "Authorization";

    private static String SECRET = "anji_plus_bms_p@ss1234";

    /**
     * 秘钥8
     * @param username 7
     * @return
     */
    public static String createToken(String username) {
        String token = JWT.create()
                .withExpiresAt(DateUtils.toDate(LocalDateTime.now().plusHours(4)))
                .withClaim("username", username)
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    /**
     * 根据用户名、角色、权限、菜单等信息生成token
     * @param username 用户名
     * @param roles 角色
     * @param authorities 权限
     * @param menus 菜单
     * @return
     */
    public static String createToken(String username, List<String> roles, List<String> authorities, List<String> menus) {
        String token = JWT.create()
                .withExpiresAt(DateUtils.toDate(LocalDateTime.now().plusHours(4)))
                .withClaim("username", username)
                .withClaim("role", roles)
                .withClaim("authorities", authorities)
                .withClaim("menus", menus)
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    /**
     * 获取jwt的负载
     * @param token
     * @return
     */
    public static Map<String, Claim> getClaim(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getClaims();
    }

    /**
     * 获取用户名
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        Claim claim = getClaim(token).get("username");
        if(claim == null) {
            return null;
        }
        return claim.asString();
    }
}
