package org.finalframework.dashboard.redis.controller.api;

import org.finalframework.annotation.auth.Auth;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/24 22:10:55
 * @since 1.0
 */
@Auth
@RestController
@RequestMapping("/api/redis/value")
public class RedisValueApiController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @GetMapping
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @PostMapping
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }


}
