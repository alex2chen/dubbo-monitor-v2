package com.kxtx.dubbo.monitor.domain;

import java.io.Serializable;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/6/30
 */
public class UrlEvent implements Serializable {
    private String protocol;
    private String type;
    private String host;
    private String provider;
    private String consumer;
    private String invokeDate;
    private String serviceInterface;
    private String method;
    private long invokeTime;
    private Integer success;
    private Integer failure;
    private Integer elapsed;
    private Integer concurrent;
    private Integer maxElapsed;
    private Integer maxConcurrent;

    public UrlEvent copy(UrlEvent from) {
        protocol = from.protocol;
        type=from.type;
        host = from.host;
        provider = from.provider;
        consumer = from.consumer;
        invokeDate = from.invokeDate;
        serviceInterface = from.serviceInterface;
        method = from.method;
        invokeTime = from.invokeTime;
        success = from.success;
        failure = from.failure;
        elapsed = from.elapsed;
        concurrent = from.concurrent;
        maxElapsed = from.maxElapsed;
        maxConcurrent = from.maxConcurrent;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getInvokeDate() {
        return invokeDate;
    }

    public void setInvokeDate(String invokeDate) {
        this.invokeDate = invokeDate;
    }

    public long getInvokeTime() {
        return invokeTime;
    }

    public void setInvokeTime(long invokeTime) {
        this.invokeTime = invokeTime;
    }

    public String getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(String serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getFailure() {
        return failure;
    }

    public void setFailure(Integer failure) {
        this.failure = failure;
    }

    public Integer getElapsed() {
        return elapsed;
    }

    public void setElapsed(Integer elapsed) {
        this.elapsed = elapsed;
    }

    public Integer getConcurrent() {
        return concurrent;
    }

    public void setConcurrent(Integer concurrent) {
        this.concurrent = concurrent;
    }

    public Integer getMaxElapsed() {
        return maxElapsed;
    }

    public void setMaxElapsed(Integer maxElapsed) {
        this.maxElapsed = maxElapsed;
    }

    public Integer getMaxConcurrent() {
        return maxConcurrent;
    }

    public void setMaxConcurrent(Integer maxConcurrent) {
        this.maxConcurrent = maxConcurrent;
    }

    @Override
    public String toString() {
        return "UrlEvent{" +
                "protocol='" + protocol + '\'' +
                ", type='" + type + '\'' +
                ", host='" + host + '\'' +
                ", provider='" + provider + '\'' +
                ", consumer='" + consumer + '\'' +
                ", invokeDate='" + invokeDate + '\'' +
                ", serviceInterface='" + serviceInterface + '\'' +
                ", method='" + method + '\'' +
                ", invokeTime=" + invokeTime +
                ", success=" + success +
                ", failure=" + failure +
                ", elapsed=" + elapsed +
                ", concurrent=" + concurrent +
                ", maxElapsed=" + maxElapsed +
                ", maxConcurrent=" + maxConcurrent +
                '}';
    }
}
