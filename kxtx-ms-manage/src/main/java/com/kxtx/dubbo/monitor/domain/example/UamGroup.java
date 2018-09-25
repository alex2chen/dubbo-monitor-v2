package com.kxtx.dubbo.monitor.domain.example;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/4/13
 */

/**
 * 群组
 */
@ApiModel
public class UamGroup implements Serializable {
    @ApiModelProperty(value = "群组的Id", required = true)
    private String groupId;
    @ApiModelProperty(value = "群组的名称", required = true)
    private String name;
    @ApiModelProperty(value = "群组的头像", required = false)
    private String icon;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
