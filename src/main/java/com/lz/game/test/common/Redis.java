package com.lz.game.test.common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.BinaryClient.LIST_POSITION;

import java.util.*;
import java.util.concurrent.TimeUnit;
/**
 * @author Teaey
 * @creation 2012-8-15
 */
public class Redis implements JedisCommands, BinaryJedisCommands
{
    private static final Logger log = LoggerFactory.getLogger(Redis.class);
    private JedisPool pool;
    public void shuwdown()
    {
        if (null != pool)
            pool.destroy();
    }
    public Redis(String ip, int port, int database)
    {
        log.info("开始初始化Redis, ip={}, port={}", ip, port);
        long start = System.nanoTime();
        //JedisPoolConfig poolConfig, String host, int port, int timeout, String password, int database
        Properties props = new Properties();
        //InputStream in = Redis.class.getResourceAsStream("/redis.properties");
        //        try
        //        {
        //            props.load(in);
        //        } catch (IOException e)
        //        {
        //            e.printStackTrace();
        //        }
        //        int poolMaxActive = Integer.valueOf(props.getProperty("pool_max_active"));
        //        int poolMaxIdle = Integer.valueOf(props.getProperty("pool_max_idle"));
        //        long poolMaxWait = Long.valueOf(props.getProperty("pool_max_wait"));
        //        boolean testOnBorrow = Boolean.valueOf(props.getProperty("pool_test_on_borrow"));
        JedisPoolConfig config = new JedisPoolConfig();
        //        config.setMaxActive(poolMaxActive);
        //        config.setMaxIdle(poolMaxIdle);
        //        config.setMaxWait(poolMaxWait);
        //        config.setTestOnBorrow(testOnBorrow);
        pool = new JedisPool(config, ip, port, 5000);
        log.info("成功初始化Redis，耗时 {}ms", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start));
    }
    @Override
    public Long append(String arg0, String arg1)
    {
        Jedis j = pool.getResource();
        try
        {
            Long ret = j.append(arg0, arg1);
            pool.returnResource(j);
            return ret;
        } catch (RuntimeException e)
        {
            pool.returnBrokenResource(j);
            throw e;
        }
    }
    @Override
    public Long decr(String arg0)
    {
        return null;
    }
    @Override
    public Long decrBy(String arg0, long arg1)
    {
        return null;
    }
    @Override
    public Boolean exists(String arg0)
    {
        return null;
    }
    @Override
    public Long expire(String arg0, int arg1)
    {
        return null;
    }
    @Override
    public Long expireAt(String arg0, long arg1)
    {
        return null;
    }
    @Override
    public String get(String arg0)
    {
        return null;
    }
    @Override
    public String getSet(String arg0, String arg1)
    {
        return null;
    }
    @Override
    public Boolean getbit(String arg0, long arg1)
    {
        return null;
    }
    @Override
    public String getrange(String arg0, long arg1, long arg2)
    {
        return null;
    }
    @Override
    public Long hdel(String arg0, String... arg1)
    {
        return null;
    }
    @Override
    public Boolean hexists(String arg0, String arg1)
    {
        return null;
    }
    @Override
    public String hget(String arg0, String arg1)
    {
        Jedis jedis = pool.getResource();
        try
        {
            String ret = jedis.hget(arg0, arg1);
            pool.returnResource(jedis);
            return ret;
        } catch (RuntimeException e)
        {
            pool.returnBrokenResource(jedis);
            throw e;
        }
    }
    @Override
    public Map<String, String> hgetAll(String arg0)
    {
        return null;
    }
    @Override
    public Long hincrBy(String arg0, String arg1, long arg2)
    {
        return null;
    }
    @Override
    public Set<String> hkeys(String arg0)
    {
        return null;
    }
    @Override
    public Long hlen(String arg0)
    {
        return null;
    }
    @Override
    public List<String> hmget(String arg0, String... arg1)
    {
        return null;
    }
    @Override
    public String hmset(String arg0, Map<String, String> arg1)
    {
        return null;
    }
    @Override
    public Long hset(String arg0, String arg1, String arg2)
    {
        Jedis jedis = pool.getResource();
        try
        {
            Long ret = jedis.hset(arg0, arg1, arg2);
            pool.returnResource(jedis);
            return ret;
        } catch (RuntimeException e)
        {
            pool.returnBrokenResource(jedis);
            throw e;
        }
    }
    @Override
    public Long hsetnx(String arg0, String arg1, String arg2)
    {
        return null;
    }
    @Override
    public List<String> hvals(String arg0)
    {
        return null;
    }
    @Override
    public Long incr(String arg0)
    {
        Jedis j = pool.getResource();
        try
        {
            Long ret = j.incr(arg0);
            pool.returnResource(j);
            return ret;
        } catch (RuntimeException e)
        {
            pool.returnBrokenResource(j);
            throw e;
        }
    }
    @Override
    public Long incrBy(String arg0, long arg1)
    {
        return null;
    }
    @Override
    public String lindex(String arg0, long arg1)
    {
        return null;
    }
    @Override
    public Long linsert(String arg0, LIST_POSITION arg1, String arg2, String arg3)
    {
        return null;
    }
    @Override
    public Long llen(String arg0)
    {
        return null;
    }
    @Override
    public String lpop(String arg0)
    {
        return null;
    }
    @Override
    public Long lpush(String arg0, String... arg1)
    {
        return null;
    }
    @Override
    public Long lpushx(String arg0, String arg1)
    {
        return null;
    }
    @Override
    public List<String> lrange(String arg0, long arg1, long arg2)
    {
        return null;
    }
    @Override
    public Long lrem(String arg0, long arg1, String arg2)
    {
        return null;
    }
    @Override
    public String lset(String arg0, long arg1, String arg2)
    {
        return null;
    }
    @Override
    public String ltrim(String arg0, long arg1, long arg2)
    {
        return null;
    }
    @Override
    public String rpop(String arg0)
    {
        return null;
    }
    @Override
    public Long rpush(String arg0, String... arg1)
    {
        return null;
    }
    @Override
    public Long rpushx(String arg0, String arg1)
    {
        return null;
    }
    @Override
    public Long sadd(String arg0, String... arg1)
    {
        return null;
    }
    @Override
    public Long scard(String arg0)
    {
        return null;
    }
    @Override
    public String set(String arg0, String arg1)
    {
        return null;
    }
    @Override
    public Boolean setbit(String arg0, long arg1, boolean arg2)
    {
        return null;
    }
    @Override
    public String setex(String arg0, int arg1, String arg2)
    {
        return null;
    }
    @Override
    public Long setnx(String arg0, String arg1)
    {
        return null;
    }
    @Override
    public Long setrange(String arg0, long arg1, String arg2)
    {
        return null;
    }
    @Override
    public Boolean sismember(String arg0, String arg1)
    {
        return null;
    }
    @Override
    public Set<String> smembers(String arg0)
    {
        return null;
    }
    @Override
    public List<String> sort(String arg0)
    {
        return null;
    }
    @Override
    public List<String> sort(String arg0, SortingParams arg1)
    {
        return null;
    }
    @Override
    public String spop(String arg0)
    {
        return null;
    }
    @Override
    public String srandmember(String arg0)
    {
        return null;
    }
    @Override
    public Long srem(String arg0, String... arg1)
    {
        return null;
    }
    @Override
    public String substr(String arg0, int arg1, int arg2)
    {
        return null;
    }
    @Override
    public Long ttl(String arg0)
    {
        return null;
    }
    @Override
    public String type(String arg0)
    {
        return null;
    }
    @Override
    public Long zadd(String arg0, Map<Double, String> arg1)
    {
        return null;
    }
    @Override
    public Long zadd(String arg0, double arg1, String arg2)
    {
        return null;
    }
    @Override
    public Long zcard(String arg0)
    {
        return null;
    }
    @Override
    public Long zcount(String arg0, double arg1, double arg2)
    {
        return null;
    }
    @Override
    public Long zcount(String arg0, String arg1, String arg2)
    {
        return null;
    }
    @Override
    public Double zincrby(String arg0, double arg1, String arg2)
    {
        return null;
    }
    @Override
    public Set<String> zrange(String arg0, long arg1, long arg2)
    {
        return null;
    }
    @Override
    public Set<String> zrangeByScore(String arg0, double arg1, double arg2)
    {
        return null;
    }
    @Override
    public Set<String> zrangeByScore(String arg0, String arg1, String arg2)
    {
        return null;
    }
    @Override
    public Set<String> zrangeByScore(String arg0, double arg1, double arg2, int arg3, int arg4)
    {
        return null;
    }
    @Override
    public Set<String> zrangeByScore(String arg0, String arg1, String arg2, int arg3, int arg4)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrangeByScoreWithScores(String arg0, double arg1, double arg2)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrangeByScoreWithScores(String arg0, String arg1, String arg2)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrangeByScoreWithScores(String arg0, double arg1, double arg2, int arg3, int arg4)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrangeByScoreWithScores(String arg0, String arg1, String arg2, int arg3, int arg4)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrangeWithScores(String arg0, long arg1, long arg2)
    {
        return null;
    }
    @Override
    public Long zrank(String arg0, String arg1)
    {
        return null;
    }
    @Override
    public Long zrem(String arg0, String... arg1)
    {
        return null;
    }
    @Override
    public Long zremrangeByRank(String arg0, long arg1, long arg2)
    {
        return null;
    }
    @Override
    public Long zremrangeByScore(String arg0, double arg1, double arg2)
    {
        return null;
    }
    @Override
    public Long zremrangeByScore(String arg0, String arg1, String arg2)
    {
        return null;
    }
    @Override
    public Set<String> zrevrange(String arg0, long arg1, long arg2)
    {
        return null;
    }
    @Override
    public Set<String> zrevrangeByScore(String arg0, double arg1, double arg2)
    {
        return null;
    }
    @Override
    public Set<String> zrevrangeByScore(String arg0, String arg1, String arg2)
    {
        return null;
    }
    @Override
    public Set<String> zrevrangeByScore(String arg0, double arg1, double arg2, int arg3, int arg4)
    {
        return null;
    }
    @Override
    public Set<String> zrevrangeByScore(String arg0, String arg1, String arg2, int arg3, int arg4)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String arg0, double arg1, double arg2)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String arg0, String arg1, String arg2)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String arg0, double arg1, double arg2, int arg3, int arg4)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String arg0, String arg1, String arg2, int arg3, int arg4)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrevrangeWithScores(String arg0, long arg1, long arg2)
    {
        return null;
    }
    @Override
    public Long zrevrank(String arg0, String arg1)
    {
        return null;
    }
    @Override
    public Double zscore(String arg0, String arg1)
    {
        return null;
    }
    public JedisPool getPool()
    {
        return pool;
    }
    public void setPool(JedisPool pool)
    {
        this.pool = pool;
    }
    @Override
    public String set(byte[] bytes, byte[] bytes2)
    {
        return null;
    }
    @Override
    public byte[] get(byte[] bytes)
    {
        return new byte[0];
    }
    @Override
    public Boolean exists(byte[] bytes)
    {
        return null;
    }
    @Override
    public String type(byte[] bytes)
    {
        return null;
    }
    @Override
    public Long expire(byte[] bytes, int i)
    {
        return null;
    }
    @Override
    public Long expireAt(byte[] bytes, long l)
    {
        return null;
    }
    @Override
    public Long ttl(byte[] bytes)
    {
        return null;
    }
    @Override
    public byte[] getSet(byte[] bytes, byte[] bytes2)
    {
        return new byte[0];
    }
    @Override
    public Long setnx(byte[] bytes, byte[] bytes2)
    {
        return null;
    }
    @Override
    public String setex(byte[] bytes, int i, byte[] bytes2)
    {
        return null;
    }
    @Override
    public Long decrBy(byte[] bytes, long l)
    {
        return null;
    }
    @Override
    public Long decr(byte[] bytes)
    {
        return null;
    }
    @Override
    public Long incrBy(byte[] bytes, long l)
    {
        return null;
    }
    @Override
    public Long incr(byte[] bytes)
    {
        return null;
    }
    @Override
    public Long append(byte[] bytes, byte[] bytes2)
    {
        return null;
    }
    @Override
    public byte[] substr(byte[] bytes, int i, int i2)
    {
        return new byte[0];
    }
    @Override
    public Long hset(byte[] bytes, byte[] bytes2, byte[] bytes3)
    {
        return null;
    }
    @Override
    public byte[] hget(byte[] bytes, byte[] bytes2)
    {
        return new byte[0];
    }
    @Override
    public Long hsetnx(byte[] bytes, byte[] bytes2, byte[] bytes3)
    {
        return null;
    }
    @Override
    public String hmset(byte[] bytes, Map<byte[], byte[]> map)
    {
        return null;
    }
    @Override
    public List<byte[]> hmget(byte[] bytes, byte[]... bytes2)
    {
        return null;
    }
    @Override
    public Long hincrBy(byte[] bytes, byte[] bytes2, long l)
    {
        return null;
    }
    @Override
    public Boolean hexists(byte[] bytes, byte[] bytes2)
    {
        return null;
    }
    @Override
    public Long hdel(byte[] bytes, byte[]... bytes2)
    {
        return null;
    }
    @Override
    public Long hlen(byte[] bytes)
    {
        return null;
    }
    @Override
    public Set<byte[]> hkeys(byte[] bytes)
    {
        return null;
    }
    @Override
    public Collection<byte[]> hvals(byte[] bytes)
    {
        return null;
    }
    @Override
    public Map<byte[], byte[]> hgetAll(byte[] bytes)
    {
        return null;
    }
    @Override
    public Long rpush(byte[] bytes, byte[]... bytes2)
    {
        return null;
    }
    @Override
    public Long lpush(byte[] bytes, byte[]... bytes2)
    {
        return null;
    }
    @Override
    public Long llen(byte[] bytes)
    {
        return null;
    }
    @Override
    public List<byte[]> lrange(byte[] bytes, int i, int i2)
    {
        return null;
    }
    @Override
    public String ltrim(byte[] bytes, int i, int i2)
    {
        return null;
    }
    @Override
    public byte[] lindex(byte[] bytes, int i)
    {
        return new byte[0];
    }
    @Override
    public String lset(byte[] bytes, int i, byte[] bytes2)
    {
        return null;
    }
    @Override
    public Long lrem(byte[] bytes, int i, byte[] bytes2)
    {
        return null;
    }
    @Override
    public byte[] lpop(byte[] bytes)
    {
        return new byte[0];
    }
    @Override
    public byte[] rpop(byte[] bytes)
    {
        return new byte[0];
    }
    @Override
    public Long sadd(byte[] bytes, byte[]... bytes2)
    {
        return null;
    }
    @Override
    public Set<byte[]> smembers(byte[] bytes)
    {
        return null;
    }
    @Override
    public Long srem(byte[] bytes, byte[]... bytes2)
    {
        return null;
    }
    @Override
    public byte[] spop(byte[] bytes)
    {
        return new byte[0];
    }
    @Override
    public Long scard(byte[] bytes)
    {
        return null;
    }
    @Override
    public Boolean sismember(byte[] bytes, byte[] bytes2)
    {
        return null;
    }
    @Override
    public byte[] srandmember(byte[] bytes)
    {
        return new byte[0];
    }
    @Override
    public Long zadd(byte[] bytes, double v, byte[] bytes2)
    {
        return null;
    }
    @Override
    public Long zadd(byte[] bytes, Map<Double, byte[]> doubleMap)
    {
        return null;
    }
    @Override
    public Set<byte[]> zrange(byte[] bytes, int i, int i2)
    {
        return null;
    }
    @Override
    public Long zrem(byte[] bytes, byte[]... bytes2)
    {
        return null;
    }
    @Override
    public Double zincrby(byte[] bytes, double v, byte[] bytes2)
    {
        return null;
    }
    @Override
    public Long zrank(byte[] bytes, byte[] bytes2)
    {
        return null;
    }
    @Override
    public Long zrevrank(byte[] bytes, byte[] bytes2)
    {
        return null;
    }
    @Override
    public Set<byte[]> zrevrange(byte[] bytes, int i, int i2)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrangeWithScores(byte[] bytes, int i, int i2)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrevrangeWithScores(byte[] bytes, int i, int i2)
    {
        return null;
    }
    @Override
    public Long zcard(byte[] bytes)
    {
        return null;
    }
    @Override
    public Double zscore(byte[] bytes, byte[] bytes2)
    {
        return null;
    }
    @Override
    public List<byte[]> sort(byte[] bytes)
    {
        return null;
    }
    @Override
    public List<byte[]> sort(byte[] bytes, SortingParams sortingParams)
    {
        return null;
    }
    @Override
    public Long zcount(byte[] bytes, double v, double v2)
    {
        return null;
    }
    @Override
    public Long zcount(byte[] bytes, byte[] bytes2, byte[] bytes3)
    {
        return null;
    }
    @Override
    public Set<byte[]> zrangeByScore(byte[] bytes, double v, double v2)
    {
        return null;
    }
    @Override
    public Set<byte[]> zrangeByScore(byte[] bytes, double v, double v2, int i, int i2)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrangeByScoreWithScores(byte[] bytes, double v, double v2)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrangeByScoreWithScores(byte[] bytes, double v, double v2, int i, int i2)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrangeByScoreWithScores(byte[] bytes, byte[] bytes2, byte[] bytes3)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrangeByScoreWithScores(byte[] bytes, byte[] bytes2, byte[] bytes3, int i, int i2)
    {
        return null;
    }
    @Override
    public Set<byte[]> zrevrangeByScore(byte[] bytes, double v, double v2)
    {
        return null;
    }
    @Override
    public Set<byte[]> zrevrangeByScore(byte[] bytes, double v, double v2, int i, int i2)
    {
        return null;
    }
    @Override
    public Set<byte[]> zrevrangeByScore(byte[] bytes, byte[] bytes2, byte[] bytes3)
    {
        return null;
    }
    @Override
    public Set<byte[]> zrevrangeByScore(byte[] bytes, byte[] bytes2, byte[] bytes3, int i, int i2)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(byte[] bytes, double v, double v2)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(byte[] bytes, double v, double v2, int i, int i2)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(byte[] bytes, byte[] bytes2, byte[] bytes3)
    {
        return null;
    }
    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(byte[] bytes, byte[] bytes2, byte[] bytes3, int i, int i2)
    {
        return null;
    }
    @Override
    public Long zremrangeByRank(byte[] bytes, int i, int i2)
    {
        return null;
    }
    @Override
    public Long zremrangeByScore(byte[] bytes, double v, double v2)
    {
        return null;
    }
    @Override
    public Long zremrangeByScore(byte[] bytes, byte[] bytes2, byte[] bytes3)
    {
        return null;
    }
    @Override
    public Long linsert(byte[] bytes, LIST_POSITION list_position, byte[] bytes2, byte[] bytes3)
    {
        return null;
    }
    @Override
    public Long objectRefcount(byte[] bytes)
    {
        return null;
    }
    @Override
    public Long objectIdletime(byte[] bytes)
    {
        return null;
    }
    @Override
    public byte[] objectEncoding(byte[] bytes)
    {
        return new byte[0];
    }
    @Override
    public Long lpushx(byte[] bytes, byte[] bytes2)
    {
        return null;
    }
    @Override
    public Long rpushx(byte[] bytes, byte[] bytes2)
    {
        return null;
    }
}

