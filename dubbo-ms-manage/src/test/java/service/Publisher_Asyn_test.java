package service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.google.common.collect.Lists;
import com.github.middleware.dubbo.monitor.domain.EventHandlerChain;
import com.github.middleware.dubbo.monitor.service.dsrpt.api.DisruptorEngine;
import com.github.middleware.dubbo.monitor.service.dsrpt.builder.AsynDisruptorBuilder;
import com.github.middleware.dubbo.monitor.service.dsrpt.engine.DisruptorEngineImpl;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/6/30
 */
public class Publisher_Asyn_test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Publisher_Asyn_test.class);
    private DisruptorEngine<String> disruptorEngine;

    @Before
    public void go_setup() {

    }

    @After
    public void go_cleardown() {
        if (disruptorEngine != null) disruptorEngine.shutdown();
    }

    //Publisher---> Consumer A
    @Test
    public void go_publish_simple_asyn() {
        ConsumerA consumerA = new ConsumerA();
        EventHandlerChain<String> eventHandlerChain1 = new EventHandlerChain<String>(new EventHandler[]{consumerA});
        List<EventHandlerChain<String>> eventHandlerChains = Lists.newArrayList(eventHandlerChain1);
        disruptorEngine = new DisruptorEngineImpl<>(new AsynDisruptorBuilder<String>(new EventFactory<String>() {
            @Override
            public String newInstance() {
                return null;
            }
        }), eventHandlerChains);
        disruptorEngine.init();
        disruptorEngine.publish(new EventTranslator<String>() {
            @Override
            public void translateTo(String event, long l) {
                LOGGER.info("translateTo");
                event = "hello";
            }
        });
        LOGGER.info("sucess.");
    }

    // Publisher---> Consumer A -> Consumer B1 -> Consumer D
    @Test
    public void go_publish_one2one_case() {
        ConsumerA consumerA = new ConsumerA();
        ConsumerB1 consumerB1 = new ConsumerB1();
        ConsumerD consumerD = new ConsumerD();
        EventHandlerChain<String> eventHandlerChain1 = new EventHandlerChain<String>(new EventHandler[]{consumerA}, new EventHandler[]{consumerB1});
        EventHandlerChain<String> eventHandlerChain2 = new EventHandlerChain<String>(new EventHandler[]{consumerB1}, new EventHandler[]{consumerD});
        List<EventHandlerChain<String>> eventHandlerChains = Lists.newArrayList(eventHandlerChain1, eventHandlerChain2);
        disruptorEngine = new DisruptorEngineImpl<>(new AsynDisruptorBuilder(), eventHandlerChains);
        disruptorEngine.init();
        disruptorEngine.publish(new EventTranslator<String>() {
            @Override
            public void translateTo(String event, long l) {
                LOGGER.info("translateTo");
                event = "hello";
            }
        });
        LOGGER.info("sucess.");
    }

    /**
     * Consumer B1
     * /           \
     * Publisher -> Consumer A -             -> Consumer D
     * \           /
     * Consumer B2
     */
    @Test
    public void go_publish_one2Secord_case() {
        ConsumerA consumerA = new ConsumerA();
        ConsumerB1 consumerB1 = new ConsumerB1();
        ConsumerB2 consumerB2 = new ConsumerB2();
        ConsumerD consumerD = new ConsumerD();

        EventHandlerChain<String> eventHandlerChain1 = new EventHandlerChain<String>(new EventHandler[]{consumerA}, new EventHandler[]{consumerB1, consumerB2});
        EventHandlerChain<String> eventHandlerChain2 = new EventHandlerChain<String>(new EventHandler[]{consumerB1, consumerB2}, new EventHandler[]{consumerD});
        List<EventHandlerChain<String>> eventHandlerChains = Lists.newArrayList(eventHandlerChain1, eventHandlerChain2);
        disruptorEngine = new DisruptorEngineImpl<>(new AsynDisruptorBuilder(), eventHandlerChains);
        disruptorEngine.init();
        disruptorEngine.publish(new EventTranslator<String>() {
            @Override
            public void translateTo(String event, long l) {
                LOGGER.info("translateTo");
                event = "hello";
            }
        });
        LOGGER.info("sucess.");

    }

    /**
     * Consumer B1 -> Consumer C1
     * /                           \
     * Publisher               ---> Consumer A --> Consumer D
     * \                           /
     * Consumer B2 -> Consumer C2
     */
    @Test
    public void go_publish_one2DubleSecord_case() {
        ConsumerA consumerA = new ConsumerA();
        ConsumerB1 consumerB1 = new ConsumerB1();
        ConsumerB2 consumerB2 = new ConsumerB2();
        ConsumerC1 consumerC1 = new ConsumerC1();
        ConsumerC2 consumerC2 = new ConsumerC2();
        ConsumerD consumerD = new ConsumerD();

        EventHandlerChain<String> eventHandlerChain1 = new EventHandlerChain<String>(new EventHandler[]{consumerA}, new EventHandler[]{consumerB1, consumerB2});
        EventHandlerChain<String> eventHandlerChain2 = new EventHandlerChain<String>(new EventHandler[]{consumerB1}, new EventHandler[]{consumerC1});
        EventHandlerChain<String> eventHandlerChain3 = new EventHandlerChain<String>(new EventHandler[]{consumerB2}, new EventHandler[]{consumerC2});
        EventHandlerChain<String> eventHandlerChain4 = new EventHandlerChain<String>(new EventHandler[]{consumerC1, consumerC2}, new EventHandler[]{consumerD});
        List<EventHandlerChain<String>> eventHandlerChains = Lists.newArrayList(eventHandlerChain1, eventHandlerChain2, eventHandlerChain3, eventHandlerChain4);
        disruptorEngine = new DisruptorEngineImpl<>(new AsynDisruptorBuilder(), eventHandlerChains);
        disruptorEngine.init();
        disruptorEngine.publish(new EventTranslator<String>() {
            @Override
            public void translateTo(String event, long l) {
                LOGGER.info("translateTo");
                event = "hello";
            }
        });
        LOGGER.info("sucess.");

    }

    private class DefaultEventFactory implements EventFactory<String> {

        @Override
        public String newInstance() {
            return new String();
        }
    }

    private class ConsumerA implements EventHandler<String> {
        @Override
        public void onEvent(String event, long sequence, boolean endOfBatch)
                throws Exception {
            Publisher_Asyn_test.LOGGER.info("ConsumerA. event:" + event);
        }
    }

    private class ConsumerB1 implements EventHandler<String> {
        @Override
        public void onEvent(String event, long sequence, boolean endOfBatch)
                throws Exception {
            Publisher_Asyn_test.LOGGER.info("ConsumerB1. event:" + event);
        }
    }

    private class ConsumerB2 implements EventHandler<String> {
        @Override
        public void onEvent(String event, long sequence, boolean endOfBatch)
                throws Exception {
            Publisher_Asyn_test.LOGGER.info("ConsumerB2. event:" + event);
        }

    }

    private class ConsumerC1 implements EventHandler<String> {

        @Override
        public void onEvent(String event, long sequence, boolean endOfBatch)
                throws Exception {
            Publisher_Asyn_test.LOGGER.info("ConsumerC1. event:" + event);
        }

    }

    private class ConsumerC2 implements EventHandler<String> {

        @Override
        public void onEvent(String event, long sequence, boolean endOfBatch)
                throws Exception {
            Publisher_Asyn_test.LOGGER.info("ConsumerC2. event:" + event);
        }

    }

    private class ConsumerD implements EventHandler<String> {

        @Override
        public void onEvent(String event, long sequence, boolean endOfBatch)
                throws Exception {
            Publisher_Asyn_test.LOGGER.info("ConsumerD. event:" + event);
        }

    }
}
