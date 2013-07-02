package com.lz.game.multi;
import com.lz.game.multi.sample.GetPlayerBagInfoReqSample;
import com.lz.game.multi.sample.GetPlayerInfoReqSample;
import com.lz.game.test.common.Redis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * User: Teaey
 * Date: 13-6-28
 * 1008957 1009057 gateway_ip gateway_port redis_ip redis_port round thread_num
 *
 * stress_tester_1005056
 */
public class Tester
{
    private static final Logger                      log     = LoggerFactory.getLogger(Tester.class);
    private static final String                      ACC_PRE = "stress_tester_";
    private final        Map<Integer, List<TestCtx>> map     = new HashMap<>();
    private int    startIndex;
    private int    endIndex;
    private String ip;
    private int    port;
    private String redisIp;
    private int    redisPort;
    private int    round;
    private int    threadNum;
    public Tester(int startIndex, int endIndex, String ip, int port, String redisIp, int redisPort, int round, int threadNum)
    {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.ip = ip;
        this.port = port;
        this.redisIp = redisIp;
        this.redisPort = redisPort;
        this.round = round;
        this.threadNum = threadNum;
        ReportCenter.playerNum.set(endIndex - startIndex + 1);
    }
    public void test()
    {
        long start = System.nanoTime();
        Redis redis = new Redis(redisIp, redisPort, 0);
        for (int i = startIndex; i <= endIndex; i++)
        {
            int threadIndex = i % threadNum;
            List<TestCtx> clientList = getTestCtxList(threadIndex);
            String playerAccount = ACC_PRE + i;
            TestCtx testCtx = new TestCtx();
            testCtx.setAccount(playerAccount);
            OioClient oioClient = new OioClient(ip, port);
            testCtx.setClient(oioClient);
            redis.hset(playerAccount, "token", playerAccount);
            clientList.add(testCtx);
        }
        //
        Runer.setRoundCounter(round);
        Runer.addSample(new GetPlayerInfoReqSample());
        Runer.addSample(new GetPlayerBagInfoReqSample());
        //
        ThreadFactory threadFactory = new RunerThreadFactory();
        List<Thread> runerThreadList = new ArrayList<>();
        for (List<TestCtx> each : map.values())
        {
            Runer runner = new Runer(each);
            Thread thread = threadFactory.newThread(runner);
            runerThreadList.add(thread);
        }
        for (Thread each : runerThreadList)
        {
            each.start();
        }
        try
        {
            for (Thread each : runerThreadList)
            {
                each.join();
            }
        } catch (InterruptedException e)
        {
            log.error("", e);
        }
        long end = System.nanoTime();
        log.info("\n{}", ReportCenter.statistics());
        log.info("测试结束，耗时:[{}]ms", TimeUnit.NANOSECONDS.toMillis(end - start));
        log.info("当前在线玩家[{}]人", ReportCenter.playerNum.get());
    }
    static class RunerThreadFactory implements ThreadFactory
    {
        private final AtomicInteger id         = new AtomicInteger(0);
        private final String        threadName = "Test-Thread-";
        @Override
        public Thread newThread(Runnable r)
        {
            Thread ret = new Thread(r, threadName + id.incrementAndGet());
            ret.setDaemon(true);
            return ret;
        }
    }
    public List<TestCtx> getTestCtxList(int threadIndex)
    {
        List<TestCtx> ret = map.get(threadIndex);
        if (null == ret)
        {
            ret = new ArrayList<>();
            map.put(threadIndex, ret);
        }
        return ret;
    }
}
