package com.lz.game.test;
import com.google.protobuf.MessageLite;
import com.lz.game.fund.comm.proto.CommProto;
import com.lz.game.test.common.DataHolder;
import com.lz.game.test.holder.ThreadlocalClientHolder;
import com.lz.game.test.sample.Seed_ConnectGatewayReq;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.lang.*;
import java.lang.Override;
import java.lang.String;
import java.lang.System;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * User: Teaey
 * Date: 13-5-27
 */
public abstract class AbstractSample<T> extends AbstractJavaSamplerClient
{
    public static final String PARAM_IP = "gateway_ip";
    public static final String PARAM_PORT = "gateway_port";
    public static final String VAR_IP = "${gateway_ip}";
    public static final String VAR_PORT = "${gateway_port}";
    protected NetworkClient client;
    private static AtomicInteger counter = new AtomicInteger(0);
    public AbstractSample()
    {
        //System.out.println("创建" + this.getClass().getSimpleName());
    }
    public abstract void addParameter(Arguments params);
    /**
     * Jmeter获取消息参数，默认配置ip和port两个参数
     * 如果子类有更多参数，调用super.getDefaultParameters()获取Arguments后，继续设置其他方法
     */
    @Override
    public Arguments getDefaultParameters()
    {
        //System.out.println("1.getDefaultParameters");
        Arguments params = new Arguments();
        params.addArgument(PARAM_IP, VAR_IP);
        params.addArgument(PARAM_PORT, VAR_PORT);
        addParameter(params);
        return params;
    }
    /**
     * runTest的前置方法
     */
    @Override
    public void setupTest(JavaSamplerContext context)
    {
        if (this.getClass() == Seed_ConnectGatewayReq.class)
        {
            String account = context.getParameter("player_account");
            System.out.println("准备测试，玩家帐号（" + account + "），第（" + counter.incrementAndGet() + "）个玩家");
        }
        //System.out.println("2.setupTest:" + context.containsParameter(PARAM_IP));
        String ip = context.getParameter(PARAM_IP);
        String port = context.getParameter(PARAM_PORT);
        this.client = ThreadlocalClientHolder.getInstance().getClient(ip, port);
        //System.out.println("thread--->" + Thread.currentThread().getId() + " client--->" + client);
    }
    /**
     * Jmeter调用，用于实际的测试
     */
    @Override
    public SampleResult runTest(JavaSamplerContext context)
    {
        SampleResult sample = getSample();
        sample.sampleStart();
        try
        {
            MessageLite realReq = getRequest(context);
            sample.setSamplerData(realReq.toString());
            MessageLite res = doTest(realReq);
            String msg = res == null ? "" : res.toString();
            sample.setResponseMessage(msg);
            sample.setResponseData(msg, "utf8");
            sample.setSuccessful(true);
        } catch (java.lang.Exception e)
        {
            sample.setSuccessful(false);
            e.printStackTrace();
            System.out.println("playerId=" + DataHolder.getSessionId());
        } finally
        {
            sample.sampleEnd();
        }
        return sample;
    }
    /**
     * 获取一个带标签的Sample
     */
    public SampleResult getSample()
    {
        SampleResult sample = new SampleResult();
        sample.setSampleLabel(getLabel());
        return sample;
    }
    /**
     * Jmeter调用，用于
     */
    @Override
    public void teardownTest(JavaSamplerContext context)
    {
        client.disconnect();
        //System.out.println("关闭测试：" + this.getClass().getSimpleName());
    }
    public abstract MessageLite getRequest(JavaSamplerContext context);

    /**
     * 需实现，具体测试的方法，调用client的send/sendWithBack发送请求
     * 如无返回，放回null即可
     */
    public abstract MessageLite doTest(MessageLite realReq) throws Exception;
    /**
     * 获取本Sample的标签，子类实现
     */
    public String getLabel()
    {
        return this.getClass().getSimpleName();
    }
    public CommProto.RpcRequest getRpcRequestBuilder(int serviceId, int methodId, MessageLite params)
    {
        CommProto.RpcRequest.Builder ret = CommProto.RpcRequest.newBuilder();
        ret.setId(1);
        ret.setProtoParam(params.toByteString());
        ret.setService(serviceId);
        ret.setMethod(methodId);
        String sessId = DataHolder.getSessionId();
        if (null != sessId)
            ret.setSessId(sessId);
        return ret.build();
    }
}