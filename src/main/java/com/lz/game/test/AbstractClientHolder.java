package com.lz.game.test;
import java.lang.String; /**
 * User: Teaey
 * Date: 13-5-27
 */
public interface AbstractClientHolder
{
    NetworkClient getClient(String ip, String port);
}
