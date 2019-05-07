package com.github.middleware.dubbo.monitor.service.dsrpt.engine;

import com.lmax.disruptor.*;

/**
 * Created by YT on 2017/6/30
 */
public enum WaitStrategyType {
    BLOCKING {
        public com.lmax.disruptor.WaitStrategy instance() {
            return new BlockingWaitStrategy();
        }
    },
    BUSY_SPIN {
        public WaitStrategy instance() {
            return new BusySpinWaitStrategy();
        }
    },

    LITE_BLOCKING {
        public WaitStrategy instance() {
            return new LiteBlockingWaitStrategy();
        }
    },
    SLEEPING_WAIT {
        public WaitStrategy instance() {
            return new SleepingWaitStrategy();
        }
    },
    YIELDING {
        public WaitStrategy instance() {
            return new YieldingWaitStrategy();
        }
    };

    public abstract WaitStrategy instance();
}
