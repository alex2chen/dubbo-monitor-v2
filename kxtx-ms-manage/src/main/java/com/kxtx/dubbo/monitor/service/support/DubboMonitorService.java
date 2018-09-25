package com.kxtx.dubbo.monitor.service.support;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.monitor.MonitorService;
import com.google.common.base.Strings;
import com.kxtx.dubbo.monitor.core.DateUtil;
import com.kxtx.dubbo.monitor.domain.InvokeInfo;
import com.kxtx.dubbo.monitor.service.StatisticCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.expression.spel.ast.Identifier;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/13
 */
//@Service
@Deprecated
public class DubboMonitorService implements MonitorService {
    private static final Logger LOG = LoggerFactory.getLogger(DubboMonitorService.class);
    private static final String POISON_PROTOCOL = "poison";
    private static final String TIMESTAMP = "timestamp";
    private volatile boolean running = true;
    private Thread writeThread;
    private BlockingQueue<URL> queue;
    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    private void init() {
        queue = new LinkedBlockingQueue<URL>(Integer.parseInt(ConfigUtils.getProperty("dubbo.monitor.queue", "100000")));
        writeThread = new Thread(new Runnable() {
            public void run() {
                while (running) {
                    try {
                        writeDataToDB();
                    } catch (Throwable t) { // 防御性容错
                        LOG.error("记录统计日志发送错误: " + t.getMessage(), t);
                        try {
                            Thread.sleep(5000); // 失败延迟
                        } catch (Throwable t2) {
                        }
                    }
                }
            }
        });
        writeThread.setDaemon(true);
        writeThread.setName("DubboAsyncWriteLogThread");
        writeThread.start();
    }

    /**
     * 记录统计日志
     */
    private void writeDataToDB() {
        try {
            URL data = queue.take();
            if (POISON_PROTOCOL.equals(data.getProtocol())) {
                return;
            }
            InvokeInfo invokeInfo = new InvokeInfo();
            //dubboInvoke.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            if (data.hasParameter(PROVIDER)) {
                invokeInfo.setType(CONSUMER);
                invokeInfo.setConsumer(data.getHost());
                invokeInfo.setProvider(data.getParameter(PROVIDER));
                int i = invokeInfo.getProvider().indexOf(':');
                if (i > 0) {
                    invokeInfo.setProvider(invokeInfo.getProvider().substring(0, i));
                }
            } else {
                invokeInfo.setType(PROVIDER);
                invokeInfo.setConsumer(data.getParameter(CONSUMER));
                int i = invokeInfo.getConsumer().indexOf(':');
                if (i > 0) {
                    invokeInfo.setConsumer(invokeInfo.getConsumer().substring(0, i));
                }
                invokeInfo.setProvider(data.getHost());
            }
            Date invokeDate = DateUtil.stringToDate(data.getParameter(Constants.TIMESTAMP_KEY));
            invokeInfo.setInvokeDate(DateUtil.dateToString(invokeDate));
            invokeInfo.setService(data.getServiceInterface());
            invokeInfo.setMethod(data.getParameter(METHOD));
            invokeInfo.setInvokeTime(data.getParameter(TIMESTAMP, System.currentTimeMillis()));
            invokeInfo.setSuccess(data.getParameter(SUCCESS, 0));
            invokeInfo.setFailure(data.getParameter(FAILURE, 0));
            invokeInfo.setElapsed(data.getParameter(ELAPSED, 0));
            invokeInfo.setConcurrent(data.getParameter(CONCURRENT, 0));
            invokeInfo.setMaxElapsed(data.getParameter(MAX_ELAPSED, 0));
            invokeInfo.setMaxConcurrent(data.getParameter(MAX_CONCURRENT, 0));
            if (invokeInfo.getSuccess() == 0 && invokeInfo.getFailure() == 0 && invokeInfo.getElapsed() == 0
                    && invokeInfo.getConcurrent() == 0 && invokeInfo.getMaxElapsed() == 0 && invokeInfo.getMaxConcurrent() == 0) {
                LOG.warn("记录统计日志信息有误");
                return;
            }
            invokeInfo.setTimeParticle(null);
            mongoTemplate.insert(invokeInfo);
        } catch (Exception e) {
            LOG.error("记录统计日志出错", e);
        }
    }

    /**
     * 收集日志服务
     *
     * @param data
     */
    public void collect(URL data) {
        queue.offer(data);
        if (LOG.isInfoEnabled()) {
            LOG.info("已收集统计日志：" + data);
        }
    }

    public List<URL> lookup(URL url) {
        // TODO: 2017/1/13
        return null;
    }
}
