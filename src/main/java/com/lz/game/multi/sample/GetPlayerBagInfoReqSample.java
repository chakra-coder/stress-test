package com.lz.game.multi.sample;
import com.lz.game.fund.comm.proto.CommProto;
import com.lz.game.multi.Sample;
import com.lz.game.multi.TestCtx;
import com.lz.game.platf.gamelogic.bag.proto.BagProto;
import com.lz.game.platf.global.proto.ServiceNameProto;
/**
 * User: Teaey
 * Date: 13-7-2
 */
public class GetPlayerBagInfoReqSample extends Sample
{
    @Override
    protected void doTest(TestCtx testCtx) throws Exception
    {
        BagProto.GetPlayerBagInfoReq.Builder req = BagProto.GetPlayerBagInfoReq.newBuilder();
        req.setPlayerId(testCtx.getPlayerId());
        CommProto.RpcResponse resp = testCtx.getClient().sendWaitBack(getRpcRequestBuilder(ServiceNameProto.ServiceName.BAG_VALUE, BagProto.MethodNameForBag.GET_PLAYER_BAG_INFO_VALUE, req.build()));
        if (null != resp && !resp.getIsFailed())
        {
            BagProto.GetPlayerBagInfoResp.parseFrom(resp.getProtoParam());
        }
    }
}
