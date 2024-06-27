package online.dadazi.dadaziblog.config.tools;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.Resource;
import online.dadazi.dadaziblog.config.pojo.ProjectConfig;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * jwt 工具
 *
 * @author lqk
 */

public class JwtUtil {
    public final static Long  expireTime =TimeUnit.DAYS.toMillis(12);

    /**
     * 接收业务数据,生成token并返回
     *
     * @param claims token实体
     * @return {@link String }
     */
    public static String genToken(Map<String, Object> claims) {
        String token = JWT.create()
                .withClaim("claims", claims).
                withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                .sign(Algorithm.HMAC256(ProjectConfig.name));
        return token;
    }

    /**
     * 接收token,验证token,并返回业务数据
     *
     * @param token token
     * @return {@link Map }<{@link String }, {@link Object }>
     */
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(ProjectConfig.name)).build().verify(token).getClaim("claims").asMap();
    }

}
