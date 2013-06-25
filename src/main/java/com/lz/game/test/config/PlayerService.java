package com.lz.game.test.config;
import com.lz.game.test.common.DataHolder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * User: Teaey
 * Date: 13-5-28
 */
public class PlayerService
{
    private PlayerService()
    {
        //        File config = ConfigLoader.getInstance().getConfig("player.conf");
        //        try
        //        {
        //            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(config), "utf8"));
        //            String line = null;
        //            while ((line = reader.readLine()) != null)
        //            {
        //                Player player = new Player();
        //                player.setPlayerAccount(line.trim().toLowerCase());
        //                playerList.add(player);
        //            }
        //            System.out.println("total " + playerList.size() + " players");
        //        } catch (Exception e)
        //        {
        //            e.printStackTrace();
        //            System.exit(1);
        //        }
    }
    private static final PlayerService instance = new PlayerService();
    public static PlayerService getInstance()
    {
        return instance;
    }
    private List<Player> playerList = new ArrayList<>();
    public List<Player> getPlayerList()
    {
        return playerList;
    }
//    public List<Player> getPlayerListPerThread()
//    {
//        int threadId = DataHolder.getThreadId();
//        int threadNum = ConfigService.getInstance().getThreadNum();
//        List<Player> ret = new ArrayList<>();
//        int index = threadId - 1;
//        while (index < playerList.size())
//        {
//            ret.add(playerList.get(index));
//            index += threadNum;
//        }
//        return ret;
//    }
    public static class Player
    {
        private String playerAccount;
        private Integer playerId;
        public String getPlayerAccount()
        {
            return playerAccount;
        }
        public void setPlayerAccount(String playerAccount)
        {
            this.playerAccount = playerAccount;
        }
        public Integer getPlayerId()
        {
            return playerId;
        }
        public void setPlayerId(Integer playerId)
        {
            this.playerId = playerId;
        }
    }
}
