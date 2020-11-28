package com.vma.push.business.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Set;

/** Redis操作工具类
 * Created by zhangshilin on 2017/9/28.
 */
public class RedisUtil {
    private static String HOST = null;
    private static Integer PORT = null;
    private static String PASSWORD = null;
    private static Integer maxTotal = null;
    private static Integer maxIdle = null;
    private static Integer maxWaitMillis = null;
    private static Integer poolTimeOut = null;

    private static JedisPool jedisPool = null;

    //初始化redis连接池
    static{
        HOST = ConfigUtil.getStringValue("redis.host");
        PORT = ConfigUtil.getIntgerValue("redis.port");
        PASSWORD = ConfigUtil.getStringValue("redis.password");
        maxTotal = ConfigUtil.getIntgerValue("redis.maxTotal");
        maxIdle = ConfigUtil.getIntgerValue("redis.maxIdle");
        maxWaitMillis = ConfigUtil.getIntgerValue("redis.maxWaitMillis");
        poolTimeOut = ConfigUtil.getIntgerValue("redis.poolTimeOut");

        JedisPoolConfig config = new JedisPoolConfig();
        //配置最大jedis实例数
        config.setMaxTotal(maxTotal);
        //配置资源池最大闲置数
        config.setMaxIdle(maxIdle);
        //等待可用连接的最大时间
        config.setMaxWaitMillis(maxWaitMillis);
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
        config.setTestOnBorrow(true);

        jedisPool = new JedisPool(config,HOST,PORT,poolTimeOut,PASSWORD);
    }

    //获取Jedis实例
    public synchronized static Jedis getJedis(){
        if(jedisPool != null){
            Jedis resource = jedisPool.getResource();
            return resource;
        }else{
            return null;
        }
    }


    //释放Jedis资源
    private static void returnResource(final Jedis jedis){
        if(jedis != null){
            jedis.close();
        }
    }

    /**
     * 设置缓存值
     * @param key
     * @param value
     * @return
     */
    public static boolean set(String key, String value){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            String ok = jedis.set(key,value);
            //System.out.println(ok);
            return ok.equals("OK")?true:false;
        }catch (JedisConnectionException e){
            e.printStackTrace();
            System.out.println("连接redis服务器失败");
        } finally {
            returnResource(jedis);
        }
        return false;
    }

    /**
     * 设置缓存值
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public static boolean set(String key, String value,Integer expireTime){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            String ok = jedis.set(key,value);
            if (null != expireTime) {
                /*
                 * nxxx参数项
                 * NX ：只在键不存在时，才对键进行设置操作。
                 * XX ：只在键已经存在时，才对键进行设置操作。
                 *
                 * expx参数项
                 * EX second ：设置键的过期时间为 second 秒。
                 * PX millisecond ：设置键的过期时间为 millisecond 毫秒。
                 * return jedis.set(key, value, "NX", "PX", expire);
                 */
                jedis.expire(key, expireTime);
            }
//            System.out.println(ok);
            return ok.equals("OK")?true:false;
        }catch (JedisConnectionException e){
            e.printStackTrace();
            System.out.println("连接redis服务器失败");
        } finally {
            returnResource(jedis);
        }
        return false;
    }


    /**
     * 根据key获取缓存值
     * @param key
     * @return
     */
    public static String get(String key){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            System.out.println("key:"+key);
            return jedis.get(key);
        }catch (JedisConnectionException e){
            e.printStackTrace();
            System.out.println("连接redis服务器失败");
        } finally {
            returnResource(jedis);
        }
        return "";
    }

    /**
     * 根据key删除
     * @param key
     * @return
     */
    public static boolean delKey(String key){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            Long result =  jedis.del(key);
            return  result != 0?true:false;
        }catch (JedisConnectionException e){
            e.printStackTrace();
            System.out.println("连接redis服务器失败");
        } finally {
            returnResource(jedis);
        }
        return false;
    }


    /**
     * 根据pattern删除所有key
     * @param pattern
     */
    public static void delKeys(String pattern){
        try{
            deletekeys(pattern+"_*");
        }catch (JedisConnectionException e){
            e.printStackTrace();
            System.out.println("连接redis服务器失败");
        }

    }

    private synchronized static void deletekeys(String pattern){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<String> keySet = jedis.keys(pattern);
            if (keySet == null || keySet.size() == 0){
                return;
            }
            String[] keys = new String[keySet.size()];
            int i = 0;
            for (String key:keySet){
                keys[i] = key;
                i++;
            }
            jedis.del(keys);
        }catch (JedisConnectionException e){
            e.printStackTrace();
            System.out.println("连接redis服务器失败");
        }finally {
            returnResource(jedis);
        }

    }

    public static void main(String args[]){
        boolean ok = RedisUtil.set("test123","test123",60);
        System.out.println(ok);
        //System.out.println(RedisUtil.get("test123"));
    }
}
