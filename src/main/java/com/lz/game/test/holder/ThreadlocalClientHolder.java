package com.lz.game.test.holder;
import com.lz.game.test.AbstractClientHolder;
import com.lz.game.test.NetworkClient;
/**
 * User: Teaey
 * Date: 13-5-27
 */
public class ThreadlocalClientHolder implements AbstractClientHolder
{
    private static ThreadlocalClientHolder instance = new ThreadlocalClientHolder();
    public static ThreadlocalClientHolder getInstance()
    {
        return instance;
    }
    /**
     * 这里使用ThradLocal存储BaseClient
     * 方便一轮测试的每个sample都是由同一个socketChannel发送
     * 更真实的模拟用户
     */
    private static ThreadLocal<NetworkClient> clientHolder = new ThreadLocal<NetworkClient>();
    public NetworkClient getClient(String ip, String port)
    {
        NetworkClient client = clientHolder.get();
        if (null == client)
        {
            client = new NetworkClient(ip, port);
            client.connect();
            clientHolder.set(client);
        }
        return client;
    }
}
