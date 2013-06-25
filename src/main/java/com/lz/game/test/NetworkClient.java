package com.lz.game.test;
import com.lz.game.fund.comm.proto.CommProto;
import com.lz.game.platf.gateway.proto.GatewayProto;
import com.lz.game.platf.global.proto.ServiceNameProto;
import com.lz.game.test.common.DataHolder;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.compression.ZlibDecoder;
import org.jboss.netty.handler.codec.compression.ZlibEncoder;
import org.jboss.netty.handler.codec.compression.ZlibWrapper;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufEncoder;

import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.lang.System;
import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
/**
 * User: Teaey
 * Date: 13-5-27
 */
public class NetworkClient
{
    public NetworkClient(String ip, String port)
    {
        this.ip = ip;
        this.port = port;
    }
    private String ip;
    private String port;
    private Channel channel;
    private ClientBootstrap bootstrap;
    private Object syn = new Object();
    private static final int Receive_Timeout = 10000;       //ms
    private CommProto.RpcResponse response = null;
    private CountDownLatch latch = new CountDownLatch(1);
    public void connect()
    {
        bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        bootstrap.setOption("tcpNoDelay", true);
        bootstrap.setPipelineFactory(new ClientPipelineFactory());
        while (true)
        {
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(ip, Integer.parseInt(port)));
            future.awaitUninterruptibly(5000);
            if (future.isDone())
            {
                channel = future.getChannel();
                if (channel != null && channel.isConnected())
                {
                    break;
                }
            }
        }
    }
    public void disconnect()
    {
        if (channel.isConnected())
        {
            channel.disconnect();
        }
    }
    public boolean isConnected()
    {
        return channel.isConnected();
    }
    public void close()
    {
        if (this.channel.isOpen())
        {
            this.channel.close();
        }
        bootstrap.releaseExternalResources();
    }
    /**
     * 发送消息，无需返回
     */
    public void send(Object message)
    {
        channel.write(message);
    }
    /**
     * 发送消息，等待返回
     */
    public CommProto.RpcResponse sendWaitBack(CommProto.RpcRequest message) throws InterruptedException, TimeoutException
    {
        response = null;
        channel.write(message);
        latch.await(Receive_Timeout, TimeUnit.MILLISECONDS);
        latch = new CountDownLatch(1);
        if (null == response)
        {
            System.err.println("接受数据超时，当前超时时间为 " + Receive_Timeout + "ms");
            throw new TimeoutException("接受数据超时");
        }
        return response;
    }
    private static final int MAX_FRAME_BYTES_LENGTH = 1048576;
    class ClientPipelineFactory implements ChannelPipelineFactory
    {
        public ChannelPipeline getPipeline() throws Exception
        {
            ChannelPipeline p = Channels.pipeline();
            p.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(MAX_FRAME_BYTES_LENGTH, 0, 4, 0, 4));
            p.addLast("protobufDecoder", new ProtobufDecoder(CommProto.RpcResponse.getDefaultInstance()));
            p.addLast("frameEncoder", new LengthFieldPrepender(4));
            p.addLast("protobufEncoder", new ProtobufEncoder());
            p.addLast("logicHandler", new ClientMsgHandler());
            return p;
        }
    }
    class ClientMsgHandler extends SimpleChannelHandler
    {
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
        {
            Object obj = e.getMessage();
            CommProto.RpcResponse msg = (CommProto.RpcResponse) obj;
            response = msg;
            latch.countDown();
        }
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
        {
            System.out.println("==============>" + ctx.getChannel());
        }
        public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
        {
            System.out.println("::::::::::::::>" + ctx.getChannel());
        }
        public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception
        {
            Channel c = ctx.getChannel();
            System.out.println("Netty捕获到异常，关闭连接:" + e.getCause());
            System.out.println("关闭连接:" + c);
            c.close();
        }
    }
}
