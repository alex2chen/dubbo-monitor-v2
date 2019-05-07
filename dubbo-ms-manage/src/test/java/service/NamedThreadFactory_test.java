package service;

import com.github.middleware.dubbo.monitor.core.NamedThreadFactory;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/6/30
 */
public class NamedThreadFactory_test {
    @Test
    public void test_DisruptorThreadNaming() {
        final String THREAD_NAME = "thread name";
        NamedThreadFactory namedThreadFactory = new NamedThreadFactory(THREAD_NAME);
        Thread thread = namedThreadFactory.newThread(new Runnable() {
            @Override
            public void run() {
            }
        });
        System.out.println(thread.getName());
        assertTrue(thread.getName().startsWith(THREAD_NAME));
        assertFalse(thread.isDaemon());
    }

}
