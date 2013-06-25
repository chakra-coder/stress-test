package com.lz.game.test.common;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * User: Teaey
 * Date: 13-5-28
 */
public class DataHolder
{
    private static final DataHolder instance = new DataHolder();
    //ThreadId
    private final ThreadLocal<String> sessId = new ThreadLocal<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);
    public static String getSessionId()
    {
        return instance.sessId.get();
    }
    public static void setSessionId(String sessId)
    {
        instance.sessId.set(sessId);
    }
}
