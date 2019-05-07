package service;

import com.github.middleware.dubbo.monitor.service.dsrpt.engine.WaitStrategyType;
import com.lmax.disruptor.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/6/30
 */
public class WaitStrategy_test {
    @Test
    public void go_All_WaitStrategies() {
        assertTrue(WaitStrategyType.BLOCKING.instance() instanceof BlockingWaitStrategy);
        assertTrue(WaitStrategyType.BUSY_SPIN.instance() instanceof BusySpinWaitStrategy);
        assertTrue(WaitStrategyType.LITE_BLOCKING.instance() instanceof LiteBlockingWaitStrategy);
        assertTrue(WaitStrategyType.SLEEPING_WAIT.instance() instanceof SleepingWaitStrategy);
        assertTrue(WaitStrategyType.YIELDING.instance() instanceof YieldingWaitStrategy);
    }
}
