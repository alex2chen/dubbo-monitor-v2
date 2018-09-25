package com.kxtx.dubbo.monitor.domain;

import java.io.Serializable;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/16
 */
public class RegistryInfo implements Serializable {
    private String server;
    private String hostname;
    private boolean available;
    private int registeredCount;
    private int subscribedCount;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getRegisteredCount() {
        return registeredCount;
    }

    public void setRegisteredCount(int registeredCount) {
        this.registeredCount = registeredCount;
    }

    public int getSubscribedCount() {
        return subscribedCount;
    }

    public void setSubscribedCount(int subscribedCount) {
        this.subscribedCount = subscribedCount;
    }
}
