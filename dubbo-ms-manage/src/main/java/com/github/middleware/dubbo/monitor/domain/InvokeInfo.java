package com.github.middleware.dubbo.monitor.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Strings;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/13
 */
@Document(collection = "invokeInfo")
public class InvokeInfo implements Serializable {
    @Id
    private String id;
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    // private Date invokeDate;
    private String invokeDate;
    private String service;
    private String method;
    private String consumer;
    private String provider;
    private String type;
    private double success;
    private double failure;
    private double elapsed;
    private int concurrent;
    private int maxElapsed;
    private int maxConcurrent;
    private long invokeTime;
    //统计时间粒度(毫秒)
    private Long timeParticle = 60000L;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date invokeDateFrom;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date invokeDateTo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvokeDate() {
        return invokeDate;
    }

    public void setInvokeDate(String invokeDate) {
        this.invokeDate = invokeDate;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getType() {
        if (Strings.isNullOrEmpty(type)) {
            return "provider";
        }
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSuccess() {
        return success;
    }

    public void setSuccess(double success) {
        this.success = success;
    }

    public double getFailure() {
        return failure;
    }

    public void setFailure(double failure) {
        this.failure = failure;
    }

    public double getElapsed() {
        return elapsed;
    }

    public void setElapsed(double elapsed) {
        this.elapsed = elapsed;
    }

    public int getConcurrent() {
        return concurrent;
    }

    public void setConcurrent(int concurrent) {
        this.concurrent = concurrent;
    }

    public int getMaxElapsed() {
        return maxElapsed;
    }

    public void setMaxElapsed(int maxElapsed) {
        this.maxElapsed = maxElapsed;
    }

    public int getMaxConcurrent() {
        return maxConcurrent;
    }

    public void setMaxConcurrent(int maxConcurrent) {
        this.maxConcurrent = maxConcurrent;
    }

    public long getInvokeTime() {
        return invokeTime;
    }

    public void setInvokeTime(long invokeTime) {
        this.invokeTime = invokeTime;
    }

    public Long getTimeParticle() {
        return timeParticle;
    }

    public void setTimeParticle(Long timeParticle) {
        this.timeParticle = timeParticle;
    }

    public Date getInvokeDateFrom() {
        return invokeDateFrom;
    }

    public void setInvokeDateFrom(Date invokeDateFrom) {
        this.invokeDateFrom = invokeDateFrom;
    }

    public Date getInvokeDateTo() {
        return invokeDateTo;
    }

    public void setInvokeDateTo(Date invokeDateTo) {
        this.invokeDateTo = invokeDateTo;
    }
}
