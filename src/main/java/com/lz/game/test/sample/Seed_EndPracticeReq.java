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
public class Seed_EndPracticeReq  extends AbstractSample
{
    @Override
    public void addParameter(Arguments params)
    {
        params.addArgument("player_id", "${player_id}");
    }
    @Override
    public MessageLite getRequest(JavaSamplerContext context)
    {
        MapProto.EndPracticeReq.Builder req = MapProto.EndPracticeReq.newBuilder();
        req.setPlayerId(context.getIntParameter("player_id"));
        return req.build();
    }
    @Override
    public MessageLite doTest(MessageLite realReq) throws Exception
    {
        CommProto.RpcResponse resp = client.sendWaitBack(getRpcRequestBuilder(ServiceNameProto.ServiceName.MAP_VALUE, MapProto.MethodNameForMap.END_PRACTICE_VALUE, realReq));
        MapProto.EndPracticeResp ret = MapProto.EndPracticeResp.parseFrom(resp.getProtoParam());
        return ret;
    }
}
