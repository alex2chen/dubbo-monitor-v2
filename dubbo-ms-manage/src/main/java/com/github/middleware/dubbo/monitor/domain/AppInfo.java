package com.github.middleware.dubbo.monitor.domain;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/13
 */
public class AppInfo implements Serializable {
    @ApiModelProperty(value = "应用名称",required = true)
    private String name;
    @ApiModelProperty(value = "所属人")
    private String owner;
    @ApiModelProperty(value = "所属组织")
    private String organization;
    @ApiModelProperty(value = "服务提供方数量")
    private int providerCount;
    @ApiModelProperty(value = "服务消费方数量")
    private int consumerCount;
    @ApiModelProperty(value = "提供方个数", required = true)
    private int efferentCount;
    @ApiModelProperty(value = "消费方个数", required = true)
    private int afferentCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
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

    public int getEfferentCount() {
        return efferentCount;
    }

    public void setEfferentCount(int efferentCount) {
        this.efferentCount = efferentCount;
    }

    public int getAfferentCount() {
        return afferentCount;
    }

    public void setAfferentCount(int afferentCount) {
        this.afferentCount = afferentCount;
    }
}

