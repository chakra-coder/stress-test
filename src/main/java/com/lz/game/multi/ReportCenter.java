package com.lz.game.multi;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
/**
 * User: Teaey
 * Date: 13-6-28
 */
public class ReportCenter
{
    public static final AtomicInteger playerNum = new AtomicInteger();
    static class Report
    {
        static String result = "SampleName = %-30s请求次数 = %-8d总共耗时 = %-8d最大耗时 = %-8d最小耗时 = %-8d平均耗时 = %-8fms";
        String        name    = "";
        AtomicInteger counter = new AtomicInteger(0);
        AtomicLong    total   = new AtomicLong(0);
        AtomicLong    max     = new AtomicLong(0);
        AtomicLong    min     = new AtomicLong(0);
        @Override
        public String toString()
        {
            return String.format(result, name, counter.get(), total.get(), max.get(), min.get(), ((float) total.get() / counter.get()));
            //return "SampleName=" + name + " 请求次数=[" + counter + "] 总共耗时=[" + total + "] 最大耗时=[" + max + "] 最小耗时=[" + min + "] 平均耗时=[" + ((float) total.get() / counter.get() + "]ms");
        }
    }
    private final static ConcurrentHashMap<Class, Report> map = new ConcurrentHashMap<>();
    public static final void add(Class clazz, long nanos)
    {
        long timeInMillis = TimeUnit.NANOSECONDS.toMillis(nanos);
        Report report = map.get(clazz);
        if (null == report)
        {
            report = new Report();
            report.name = clazz.getSimpleName();
            Report old = map.putIfAbsent(clazz, report);
            if (null != old)
                report = old;
        }
        //计数器加1
        report.counter.incrementAndGet();
        //增加总耗时
        report.total.addAndGet(timeInMillis);
        long max = report.max.get();
        if (timeInMillis > max)
            report.max.compareAndSet(max, timeInMillis);
        long min = report.max.get();
        if (timeInMillis < min)
            report.min.compareAndSet(min, timeInMillis);
    }
    public static Report get(Class clazz)
    {
        Report ret = map.get(clazz);
        if (null == ret)
        {
            ret = new Report();
            ret.name = clazz.getSimpleName();
            Report old = map.putIfAbsent(clazz, ret);
            if (null != old)
                ret = old;
        }
        return ret;
    }
    public static String statistics()
    {
        StringBuilder sb = new StringBuilder();
        for (Report each : map.values())
        {
            sb.append(each.toString()).append("\n");
        }
        return sb.toString();
    }
}
