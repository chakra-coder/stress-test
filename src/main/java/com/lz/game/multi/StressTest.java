package com.lz.game.multi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * User: Teaey
 * Date: 13-7-2
 */
public class StressTest
{
    private static final Logger log = LoggerFactory.getLogger(StressTest.class);
    public static void main(String[] args)
    {
        if (args.length < 8)
            throw new IllegalArgumentException("startIndex endIndex gateway_ip gateway_port redis_ip redis_port round thread_num");
        int startIndex = Integer.valueOf(args[0]);
        int endIndex = Integer.valueOf(args[1]);
        String ip = args[2];
        String port = args[3];
        String r_ip = args[4];
        String r_port = args[5];
        int round = Integer.valueOf(args[6]);
        int threadNum = Integer.valueOf(args[7]);
        if ((endIndex - startIndex + 1) < threadNum)
            threadNum = (endIndex - startIndex + 1);
        log.info("{}", String.format("start=stress_tester_%d,end=stress_tester_%d,ip=%s,port=%s,r_ip=%s,r_port=%s,round=%d,threadNum=%d", startIndex, endIndex, ip, port, r_ip, r_port, round, threadNum));
        Tester tester = new Tester(startIndex, endIndex, ip, Integer.valueOf(port), r_ip, Integer.valueOf(r_port), round, threadNum);
        tester.test();
    }
}
