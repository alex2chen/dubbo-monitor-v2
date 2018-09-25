package com.kxtx.dubbo.monitor.domain;

import java.io.Serializable;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/16
 */
public class HostInfo implements Serializable {
    private String host;
    private String hostname;
    private String application;
    private String organization;
    private String owner;
    private int providerCount;
    private int consumerCount;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getProviderCount() {
        return providerCount;
    }

    public void setProviderCount(int providerCount) {
        this.providerCount = providerCount;
    }

    public int getConsumerCount() {
        return consumerCount;
    }

    public void setConsumerCount(int consumerCount) {
        this.consumerCount = consumerCount;
    }
}
