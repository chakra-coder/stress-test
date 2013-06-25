package com.lz.game.test.config;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
/**
 * User: Teaey
 * Date: 13-5-28
 */
public class ConfigService
{
    private ConfigService()
    {
        File config = ConfigLoader.getInstance().getConfig("jmeter.conf");
        try
        {
            prop.load(new FileInputStream(config));
        } catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
    private static final ConfigService configService = new ConfigService();
    public static ConfigService getInstance()
    {
        return configService;
    }
    private Properties prop = new Properties();
    public int getThreadNum()
    {
        return Integer.valueOf((String) prop.get("thread_num"));
    }
    public static void main(String[] args)
    {
        System.out.println(ConfigService.getInstance().getThreadNum());
    }
}
