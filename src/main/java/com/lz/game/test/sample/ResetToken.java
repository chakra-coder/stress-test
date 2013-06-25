package com.lz.game.test.sample;
import com.lz.game.test.common.Redis;
import com.lz.game.test.config.PlayerService;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * User: Teaey
 * Date: 13-5-27
 */
public class ResetToken extends AbstractJavaSamplerClient
{
    private static volatile Redis redis;
    private static Object lock = new Object();
    @Override
    public Arguments getDefaultParameters()
    {
        Arguments params = new Arguments();
        params.addArgument("redis_ip", "${redis_ip}");
        params.addArgument("redis_port", "${redis_port}");
        params.addArgument("redis_database", "${redis_database}");
        params.addArgument("player_account", "${player_account}");
        return params;
    }
    @Override
    public void setupTest(JavaSamplerContext context)
    {
        if (null == redis)
        {
            synchronized (lock)
            {
                if (null == redis)
                {
                    String ip = context.getParameter("redis_ip");
                    String port = context.getParameter("redis_port");
                    String redis_database = context.getParameter("redis_database");
                    redis = new Redis(ip, Integer.valueOf(port), Integer.valueOf(redis_database));
                }
            }
        }
    }
    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext)
    {
        String playerAccount = javaSamplerContext.getParameter("player_account");
        {
            SampleResult sample = new SampleResult();
            sample.setSampleLabel(this.getClass().getSimpleName());
            sample.sampleStart();
            try
            {
                redis.hset(playerAccount, "token", playerAccount);
                sample.setResponseData("reset:" + playerAccount, "utf8");
                sample.setSuccessful(true);
                //System.out.println("重置Token:" + playerAccount);
            } catch (Exception e)
            {
                sample.setSuccessful(false);
                e.printStackTrace();
            } finally
            {
                sample.sampleEnd();
            }
            return sample;
        }
    }
    @Override
    public void teardownTest(JavaSamplerContext context)
    {
        if (null != redis)
        {
            redis.shuwdown();
            redis = null;
            //System.out.println("关闭测试：" + this.getClass().getSimpleName());
        }
    }
}
