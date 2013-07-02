package com.lz.game.multi;
import com.google.protobuf.MessageLite;
import com.lz.game.fund.comm.proto.CommProto;
import com.lz.game.test.common.DataHolder;
/**
 * User: Teaey
 * Date: 13-7-2
 */
public abstract class Sample
{
    protected abstract void doTest(TestCtx testCtx) throws Exception;
    public void test(TestCtx testCtx) throws Exception
    {
        long start = System.nanoTime();
        doTest(testCtx);
        long end = System.nanoTime();
        ReportCenter.add(this.getClass(), (end - start));
    }
    public static CommProto.RpcRequest getRpcRequestBuilder(int serviceId, int methodId, MessageLite params)
    {
        CommProto.RpcRequest.Builder ret = CommProto.RpcRequest.newBuilder();
        ret.setId(1);
        ret.setProtoParam(params.toByteString());
        ret.setService(serviceId);
        ret.setMethod(methodId);
        String sessId = DataHolder.getSessionId();
        if (null != sessId)
            ret.setSessId(sessId);
        return ret.build();
    }
}
