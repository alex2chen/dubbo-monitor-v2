package com.kxtx.dubbo.monitor.service.support;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.monitor.MonitorService;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.kxtx.dubbo.monitor.core.DateUtil;
import com.kxtx.dubbo.monitor.service.dsrpt.api.DisruptorEngine;
import com.kxtx.dubbo.monitor.domain.UrlEvent;
import com.lmax.disruptor.EventTranslator;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/7/3
 */
@Service
public class SuperMonitorService implements MonitorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SuperMonitorService.class);

    @Autowired
    private DisruptorEngine disruptorEngine;

    @Override
    public void collect(URL data) {
        try {
            LOGGER.info("collect start:" + data);
            UrlEvent urlEvent = parseData(data);
            if (urlEvent.getSuccess() == 0 && urlEvent.getFailure() == 0 && urlEvent.getElapsed() == 0
                    && urlEvent.getConcurrent() == 0 && urlEvent.getMaxElapsed() == 0 && urlEvent.getMaxConcurrent() == 0) {
                LOGGER.info("collect end,data formatter error.");
                return;
            }
            disruptorEngine.publish(new EventTranslator<UrlEvent>() {
                @Override
                public void translateTo(UrlEvent data, long l) {
                    data.copy(urlEvent);
                }
            });
            LOGGER.info("collect end.");
        } catch (Exception ex) {
            LOGGER.error("collect end,error:", ex);
        }
    }

    private UrlEvent parseData(URL data) throws ParseException {
        UrlEvent ret = new UrlEvent();
        if ("poison".equals(data.getProtocol())) {
            throw new RuntimeException("记录统计日志协议有误.");
        }
        if (data.hasParameter(PROVIDER)) {
            ret.setType(CONSUMER);
            ret.setConsumer(data.getHost());
            ret.setProvider(data.getParameter(PROVIDER));
            int i = ret.getProvider().indexOf(':');
            if (i > 0) {
                ret.setProvider(ret.getProvider().substring(0, i));
            }
        } else {
            ret.setType(PROVIDER);
            ret.setConsumer(data.getParameter(CONSUMER));
            int i = ret.getConsumer().indexOf(':');
            if (i > 0) {
                ret.setConsumer(ret.getConsumer().substring(0, i));
            }
            ret.setProvider(data.getHost());
        }
        Date invokeDate = DateUtil.stringToDate(data.getParameter(Constants.TIMESTAMP_KEY));
        ret.setInvokeDate(DateUtil.dateToString(invokeDate));
        ret.setServiceInterface(data.getServiceInterface());
        ret.setMethod(data.getParameter(METHOD));
        ret.setInvokeTime(data.getParameter(TIMESTAMP, System.currentTimeMillis()));
        ret.setSuccess(data.getParameter(SUCCESS, 0));
        ret.setFailure(data.getParameter(FAILURE, 0));
        ret.setElapsed(data.getParameter(ELAPSED, 0));
        ret.setConcurrent(data.getParameter(CONCURRENT, 0));
        ret.setMaxElapsed(data.getParameter(MAX_ELAPSED, 0));
        ret.setMaxConcurrent(data.getParameter(MAX_CONCURRENT, 0));
        return ret;
    }

    @Override
    public List<URL> lookup(URL url) {
        return null;
    }
}
