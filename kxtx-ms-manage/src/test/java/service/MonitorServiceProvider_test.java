package service;

import com.kxtx.dubbo.monitor.service.dsrpt.api.DisruptorEngine;
import com.kxtx.dubbo.monitor.domain.UrlEvent;
import com.lmax.disruptor.EventTranslator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/6/30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-disruptor.xml")
public class MonitorServiceProvider_test {
    @Autowired
    private DisruptorEngine disruptorEngine;

    @Test
    public void go_publish() {
        disruptorEngine.publish(new EventTranslator<UrlEvent>() {
            @Override
            public void translateTo(UrlEvent url, long l) {
                url.setProtocol("tcp");
            }
        });
        System.out.println("test");
    }
}
