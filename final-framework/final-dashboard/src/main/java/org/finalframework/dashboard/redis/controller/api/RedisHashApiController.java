package org.finalframework.dashboard.redis.controller.api;

import org.finalframework.annotation.auth.Auth;
import org.finalframework.util.Asserts;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/24 23:09:41
 * @since 1.0
 */
@Auth
@RestController
@RequestMapping("/api/redis/hash")
public class RedisHashApiController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @GetMapping
    public Object hget(String key, String field) {

        if (Asserts.isBlank(field)) {
            return stringRedisTemplate.opsForHash().entries(key);
        }

        return stringRedisTemplate.opsForHash().get(key, field);
    }

    @PostMapping
    public void hset(String key, String field, String value) {
        stringRedisTemplate.opsForHash().put(key, field, value);
    }

}
