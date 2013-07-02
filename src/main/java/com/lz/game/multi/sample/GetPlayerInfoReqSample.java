package com.lz.game.multi.sample;
import com.lz.game.fund.comm.proto.CommProto;
import com.lz.game.multi.Sample;
import com.lz.game.multi.TestCtx;
import com.lz.game.platf.gamelogic.player.proto.PlayerProto;
import com.lz.game.platf.global.proto.ServiceNameProto;
/**
 * User: Teaey
 * Date: 13-7-2
 */
public class GetPlayerInfoReqSample extends Sample
{
    @Override
    protected void doTest(TestCtx testCtx) throws Exception
    {
        PlayerProto.GetPlayerInfoReq.Builder req = PlayerProto.GetPlayerInfoReq.newBuilder();
        req.setPlayerId(testCtx.getPlayerId());
        CommProto.RpcResponse resp = testCtx.getClient().sendWaitBack(getRpcRequestBuilder(ServiceNameProto.ServiceName.PLAYER_VALUE, PlayerProto.MethodNameForPlayer.GET_PLAYER_INFO_VALUE, req.build()));
        if (null != resp && !resp.getIsFailed())
        {
            PlayerProto.GetPlayerInfoResp.parseFrom(resp.getProtoParam());
        }
    }
}
