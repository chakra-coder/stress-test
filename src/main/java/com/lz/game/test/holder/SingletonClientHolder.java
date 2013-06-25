package com.lz.game.test.holder;
import com.lz.game.test.AbstractClientHolder;
import com.lz.game.test.NetworkClient;
/**
 * User: Teaey
 * Date: 13-5-27
 */
public class SingletonClientHolder implements AbstractClientHolder
{
    private static NetworkClient client;
    @Override
    public NetworkClient getClient(String ip, String port)
    {
        synchronized (this)
        {
            if (null == client)
            {
                client = new NetworkClient(ip, port);
                client.connect();
            }
        }
        return client;
    }
}
