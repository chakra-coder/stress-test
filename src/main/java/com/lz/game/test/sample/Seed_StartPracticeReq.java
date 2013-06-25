package com.lz.game.test.sample;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.lz.game.fund.comm.proto.CommProto;
import com.lz.game.platf.gamelogic.map.proto.MapProto;
import com.lz.game.platf.global.proto.ServiceNameProto;
import com.lz.game.test.AbstractSample;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
/**
 * User: Teaey
 * Date: 13-5-28
 */
public class Seed_StartPracticeReq  extends AbstractSample
{
    @Override
    public void addParameter(Arguments params)
    {
        params.addArgument("player_id", "${player_id}");
        params.addArgument("zone", "5");
        params.addArgument("hour", "22");
    }
    @Override
    public MessageLite getRequest(JavaSamplerContext context)
    {
        MapProto.StartPracticeReq.Builder req = MapProto.StartPracticeReq.newBuilder();
        req.setPlayerId(context.getIntParameter("player_id"));
        req.setZoneId(context.getIntParameter("zone"));
        req.setHourCnt(context.getIntParameter("hour"));
        return req.build();
    }
    @Override
    public MessageLite doTest(MessageLite realReq) throws Exception
    {
        CommProto.RpcResponse resp = client.sendWaitBack(getRpcRequestBuilder(ServiceNameProto.ServiceName.MAP_VALUE, MapProto.MethodNameForMap.START_PRACTICE_VALUE, realReq));
        MapProto.PracticeResp ret = MapProto.PracticeResp.parseFrom(resp.getProtoParam());
        return ret;
    }
}
