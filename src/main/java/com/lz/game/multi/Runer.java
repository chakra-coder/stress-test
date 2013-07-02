package com.lz.game.multi;
import com.lz.game.fund.comm.proto.CommProto;
import com.lz.game.platf.gateway.proto.GatewayProto;
import com.lz.game.platf.global.proto.ServiceNameProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
/**
 * User: Teaey
 * Date: 13-6-28
 */
public class Runer implements Runnable
{
    private static final Logger       log            = LoggerFactory.getLogger(Runer.class);
    private static final List<Sample> samplePipeline = new ArrayList<>();
    private static final Object       lock           = new Object();
    public static void addSample(Sample sample)
    {
        synchronized (lock)
        {
            samplePipeline.add(sample);
            log.info("添加测试样例：{}", sample.getClass().getSimpleName());
        }
    }
    public static void removeSample(Sample sample)
    {
        synchronized (lock)
        {
            samplePipeline.remove(sample);
            log.info("移除测试样例：{}", sample.getClass().getSimpleName());
        }
    }
    private        List<TestCtx> ctxs;
    private static int           roundCounter;
    public static void setRoundCounter(int roundCounter)
    {
        Runer.roundCounter = roundCounter;
    }
    public Runer(List<TestCtx> ctxList)
    {
        this.ctxs = ctxList;
    }
    public void init()
    {
        Random ran = new Random();
        Iterator<TestCtx> iter = ctxs.iterator();
        while (iter.hasNext())
        {
            TestCtx each = iter.next();
            boolean toRemove = false;
            try
            {
                each.getClient().connect();
                GatewayProto.ConnectGatewayReq.Builder req = GatewayProto.ConnectGatewayReq.newBuilder();
                req.setPlayerAccount(each.getAccount());
                req.setAccessToken(each.getAccount());
                req.setLogicalServerId(1);
                CommProto.RpcResponse response = each.getClient().sendWaitBack(Sample.getRpcRequestBuilder(ServiceNameProto.ServiceName.GATEWAY_VALUE, GatewayProto.MethodNameForGateway.CREATE_CLIENT_SESSION_VALUE, req.build()));
                if (response == null || response.getIsFailed())
                {
                    log.warn("连接网关失败 :{}", each.getAccount());
                    toRemove = true;
                }
                else
                {
                    int playerId = GatewayProto.ConnectGatewayResp.parseFrom(response.getProtoParam()).getPlayerId();
                    if (-1 == playerId)
                    {
                        log.warn("没有该用户 :{}", each.getAccount());
                        toRemove = true;
                    }
                    else
                    {
                        each.setPlayerId(playerId);
                    }
                }
                Thread.sleep(800, ran.nextInt(200));
            } catch (Exception e)
            {
                log.error("", e);
                toRemove = true;
            }
            if (toRemove)
            {
                try
                {
                    each.getClient().disconnect();
                } catch (Exception e)
                {
                    log.error("", e);
                }
                iter.remove();
                ReportCenter.playerNum.decrementAndGet();
            }
        }
    }
    public void run()
    {
        int size = ctxs.size();
        log.info("{} 开始测试", Thread.currentThread().getName());
        init();
        while (roundCounter > 0 && !Thread.currentThread().isInterrupted())
        {
            Iterator<TestCtx> iter = ctxs.iterator();
            while (iter.hasNext())
            {
                TestCtx each = iter.next();
                boolean toRemove = false;
                try
                {
                    for (Sample eachSample : samplePipeline)
                    {
                        eachSample.test(each);
                    }
                } catch (Exception e)
                {
                    log.error("", e);
                    toRemove = true;
                }
                if (toRemove)
                {
                    try
                    {
                        each.getClient().disconnect();
                    } catch (Exception e)
                    {
                        log.error("", e);
                    }
                    iter.remove();
                    ReportCenter.playerNum.decrementAndGet();
                }
            }
            roundCounter--;
        }
        log.info("{} 完成测试, 参测人数[{}]人, 当前在线人数[{}]人", new Object[]{Thread.currentThread().getName(), size, ctxs.size()});
        for (TestCtx each : ctxs)
        {
            try
            {
                each.getClient().disconnect();
            } catch (Exception e)
            {
                log.error("", e);
            }
        }
    }
}
