package com.lz.game.test.sample;
import com.google.protobuf.MessageLite;
import com.lz.game.fund.comm.proto.CommProto;
import com.lz.game.platf.gateway.proto.GatewayProto;
import com.lz.game.platf.global.proto.ServiceNameProto;
import com.lz.game.platf.global.proto.SrcChannelProto;
import com.lz.game.test.AbstractSample;
import com.lz.game.test.common.DataHolder;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
/**
 * User: Teaey
 * Date: 13-5-27
 */
public class Seed_ConnectGatewayReq extends AbstractSample
{
    @Override
    public void addParameter(Arguments params)
    {
        params.addArgument("player_account", "${player_account}");
    }
    @Override
    public MessageLite getRequest(JavaSamplerContext context)
    {
        GatewayProto.ConnectGatewayReq.Builder req = GatewayProto.ConnectGatewayReq.newBuilder();
        String account = context.getParameter("player_account");
        req.setPlayerAccount(account);
        req.setAccessToken(account);
        req.setLogicalServerId(1);
        req.setSrcChannel(SrcChannelProto.SrcChannel.OFFICIAL);
        return req.build();
    }
    @Override
    public MessageLite doTest(MessageLite realReq) throws Exception
    {
        CommProto.RpcResponse res = client.sendWaitBack(getRpcRequestBuilder(ServiceNameProto.ServiceName.GATEWAY_VALUE, GatewayProto.MethodNameForGateway.CREATE_CLIENT_SESSION_VALUE, realReq));
        GatewayProto.ConnectGatewayResp resp = GatewayProto.ConnectGatewayResp.parseFrom(res.getProtoParam());
        DataHolder.setSessionId(String.valueOf(resp.getPlayerId()));
        return resp;
    }
}
