package com.lz.game.multi;
import com.google.protobuf.MessageLite;
import com.lz.game.fund.comm.proto.CommProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeoutException;
/**
 * User: Teaey
 * Date: 13-5-27
 */
public class OioClient
{
    private static final Logger log = LoggerFactory.getLogger(OioClient.class);
    public OioClient(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
    }
    private String       ip;
    private int          port;
    private Socket       socket;
    private InputStream  inputStream;
    private OutputStream outputStream;
    private Object                writeLock = new Object();
    private Object                readLock  = new Object();
    //private static final int                   Receive_Timeout = 15000;       //ms
    private CommProto.RpcResponse response  = null;
    //private              CountDownLatch        latch           = new CountDownLatch(1);
    public void connect() throws IOException
    {
        if (!isConnected())
        {
            socket = new Socket();
            //->@wjw_add
            //socket.setReuseAddress(true);
            socket.setKeepAlive(true);  //Will monitor the TCP connection is valid
            socket.setTcpNoDelay(true);  //Socket buffer Whetherclosed, to ensure timely delivery of data
            //socket.setSoLinger(true,0);  //Control calls close () method, the underlying socket is closed immediately
            //<-@wjw_add
            socket.connect(new InetSocketAddress(ip, port), 120000);
            socket.setSoTimeout(10000);
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
        }
    }
    public void disconnect() throws Exception
    {
        if (isConnected())
        {
            if (null != inputStream)
                inputStream.close();
            if (null != outputStream)
                outputStream.close();
            if (!socket.isClosed())
            {
                socket.close();
            }
        }
    }
    public boolean isConnected()
    {
        return socket != null && socket.isBound() && !socket.isClosed() && socket.isConnected() && !socket.isInputShutdown() && !socket.isOutputShutdown();
    }
    /**
     * 发送消息，无需返回
     */
    public void send(MessageLite message) throws IOException
    {
        if (isConnected())
        {
            synchronized (writeLock)
            {
                byte[] data = message.toByteArray();
                ByteBuffer length = ByteBuffer.allocate(4);
                length.putInt(data.length);
                outputStream.write(length.array());
                outputStream.write(data);
            }
        }
    }
    /**
     * 发送消息，等待返回
     */
    public CommProto.RpcResponse sendWaitBack(CommProto.RpcRequest message) throws InterruptedException, TimeoutException, IOException
    {
        response = null;
        send(message);
        try
        {
            revcMsg();
        } catch (Exception e)
        {
            System.err.println("获取数据包出错");
            e.printStackTrace();
            try
            {
                disconnect();
            } catch (Exception e1)
            {
                log.error("", e);
            }
        }
        //latch.await(Receive_Timeout, TimeUnit.MILLISECONDS);
        //latch = new CountDownLatch(1);
        if (null == response)
        {
            System.err.println("没收到数据");
            try
            {
                disconnect();
            } catch (Exception e)
            {
                log.error("", e);
            }
        }
        return response;
    }
    private void revcMsg() throws IOException
    {
        if (isConnected())
        {
            synchronized (readLock)
            {
                byte[] header = new byte[4];
                inputStream.read(header);
                ByteBuffer lenBuf = ByteBuffer.wrap(header);
                int length = lenBuf.getInt();
                byte[] data = new byte[length];
                inputStream.read(data);
                response = CommProto.RpcResponse.parseFrom(data);
            }
        }
    }
}
