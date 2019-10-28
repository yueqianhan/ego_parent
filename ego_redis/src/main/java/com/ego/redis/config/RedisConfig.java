package com.ego.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author hanyueqian
 * @date 2019/9/27 0027-下午 17:26
 */
@Configuration
public class RedisConfig
{
    @Bean
    public RedisTemplate<String,Object> getTemplate(RedisConnectionFactory factory)
    {
        RedisTemplate<String,Object> template =new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        template.setConnectionFactory(factory);
        return template;
    }
}
