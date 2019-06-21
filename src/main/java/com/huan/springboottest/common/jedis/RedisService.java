package com.huan.springboottest.common.jedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.function.Function;

/**
 * @Description: ${Description}
 * @Author: Huan
 * @CreateTime: 2019-02-21 12:33
 */
public class RedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private JedisCluster jedisCluster;

    private <T> T execute(Function<JedisCluster, T> function) {
        try{
           return function.apply(jedisCluster);
        }catch (Exception e){
            LOGGER.error("缓存失败",e);
        }
        return null;
    }

    /**
     * 保存数据到redis中
     *
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        Function<JedisCluster,String> function = jedisCluster1 -> jedisCluster1.set(key,value);
        return execute(function);
    }

    public String setObj(String key,Object obj){
        Function<JedisCluster,String> function = jedisCluster1 -> {
            try{
                return jedisCluster1.set(key,new String(SerializeUtils.serialize(obj)));
            }catch (Exception e){
                throw new RuntimeException("序列化异常",e);
            }
        };
        return execute(function);
    }

    /**
     * 保存数据到redis中，生存时间单位是：秒
     *
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public String set(final String key, final String value, final Integer seconds) {
        return this.execute(new Function<JedisCluster, String>() {
            public String apply(JedisCluster shardedJedis) {
                String result = shardedJedis.set(key, value);
                shardedJedis.expire(key, seconds);//设置生存时间
                return result;
            }

        });
    }

    /**
     * 从redis中获取数据
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        return this.execute(new Function<JedisCluster, String>() {
            public String apply(JedisCluster shardedJedis) {
                return shardedJedis.get(key);
            }

        });
    }

    /**
     * 设置key生存时间，单位：秒
     *
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(final String key, final Integer seconds) {
        return this.execute(new Function<JedisCluster, Long>() {
            public Long apply(JedisCluster shardedJedis) {
                return shardedJedis.expire(key, seconds);
            }

        });
    }

    /**
     * 从redis中删除数据
     *
     * @param key
     * @return
     */
    public Long del(final String key) {
        return this.execute(new Function<JedisCluster, Long>() {
            public Long apply(JedisCluster shardedJedis) {
                return shardedJedis.del(key);
            }
        });
    }

    public Long lpush(String key,String ... value){
        Function<JedisCluster,Long> function = jedisCluster1 -> jedisCluster1.lpush(key,value);
        return execute(function);
    }

    /**
     *
     *
     * @param key
     * @return
     */
    public String rPop(String key){
        Function<JedisCluster,String> function = jedisCluster1 -> jedisCluster1.rpop(key);
        return execute(function);
    }

}
