package com.lz.game.multi;
/**
 * User: Teaey
 * Date: 13-7-2
 */
public class TestCtx
{
    private int       playerId;
    private String    account;
    private OioClient client;
    public int getPlayerId()
    {
        return playerId;
    }
    public void setPlayerId(int playerId)
    {
        this.playerId = playerId;
    }
    public String getAccount()
    {
        return account;
    }
    public void setAccount(String account)
    {
        this.account = account;
    }
    public OioClient getClient()
    {
        return client;
    }
    public void setClient(OioClient client)
    {
        this.client = client;
    }
}
