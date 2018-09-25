package com.kxtx.dubbo.monitor.core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/6/30
 */
public class NamedThreadFactory implements ThreadFactory {
    private static final AtomicInteger POOL_SEQ = new AtomicInteger(1);
    private final AtomicInteger threadNum;
    private final String prefix;
    private final boolean daemo;
    private final ThreadGroup group;

    public NamedThreadFactory() {
        this("pool-" + POOL_SEQ.getAndIncrement(), false);
    }

    public NamedThreadFactory(String prefix) {
        this(prefix, false);
    }

    public NamedThreadFactory(String prefix, boolean daemo) {
        threadNum = new AtomicInteger(1);
        this.prefix = prefix + "-thread-dsrp-";
        this.daemo = daemo;
        SecurityManager s = System.getSecurityManager();
        this.group = s == null ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread ret = new Thread(this.group, runnable, getThreadName(), 0L);
        ret.setDaemon(this.daemo);
        return ret;
    }

    public ThreadGroup getThreadGroup() {
        return this.group;
    }

    private String getThreadName() {
        return this.prefix + this.threadNum.getAndIncrement();
    }
}
