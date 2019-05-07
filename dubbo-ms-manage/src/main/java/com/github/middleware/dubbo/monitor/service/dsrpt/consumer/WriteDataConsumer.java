package com.github.middleware.dubbo.monitor.service.dsrpt.consumer;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.github.middleware.dubbo.monitor.domain.InvokeInfo;
import com.github.middleware.dubbo.monitor.domain.UrlEvent;
import com.lmax.disruptor.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/7/3
 */
public class WriteDataConsumer implements EventHandler<UrlEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WriteDataConsumer.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void onEvent(UrlEvent data, long l, boolean b) {
        LOGGER.info("写入数据开始," + data);
        try {
            if (data == null) return;
            InvokeInfo invokeInfo = new InvokeInfo();
            invokeInfo.setType(data.getType());
            invokeInfo.setConsumer(data.getConsumer());
            invokeInfo.setProvider(data.getProvider());
            invokeInfo.setInvokeDate(data.getInvokeDate());
            invokeInfo.setService(data.getServiceInterface());
            invokeInfo.setMethod(data.getMethod());
            invokeInfo.setInvokeTime(data.getInvokeTime());
            invokeInfo.setSuccess(data.getSuccess());
            invokeInfo.setFailure(data.getFailure());
            invokeInfo.setElapsed(data.getElapsed());
            invokeInfo.setConcurrent(data.getConcurrent());
            invokeInfo.setMaxElapsed(data.getMaxElapsed());
            invokeInfo.setMaxConcurrent(data.getMaxConcurrent());
            invokeInfo.setTimeParticle(null);
            mongoTemplate.insert(invokeInfo);
        } catch (Exception ex) {
            LOGGER.error("写入数据出错,", ex);
            throw ex;
        }
    }
}
