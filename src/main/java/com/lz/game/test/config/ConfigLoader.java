package com.lz.game.test.config;
import java.io.File;
/**
 * User: Teaey
 * Date: 13-5-28
 */
public class ConfigLoader
{
    private ConfigLoader()
    {
    }
    private static final ConfigLoader instance = new ConfigLoader();
    public static ConfigLoader getInstance()
    {
        return instance;
    }
    public File getConfig(String configName)
    {
        if (null == configName)
            throw new NullPointerException("configName");
        String configHome = System.getenv("CONFIG_HOME");
        File config = null;
        if (null == configHome)
        {
            config = new File(configName);
        } else
        {
            config = new File(configHome, configName);
        }
        if (!config.exists())
            throw new RuntimeException("cant find " + configName);
        else
            return config;
    }
}
