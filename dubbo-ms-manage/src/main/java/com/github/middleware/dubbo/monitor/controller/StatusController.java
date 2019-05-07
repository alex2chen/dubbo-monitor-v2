package com.github.middleware.dubbo.monitor.controller;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.common.status.Status;
import com.alibaba.dubbo.common.status.StatusChecker;
import com.github.middleware.dubbo.monitor.domain.StatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/16
 */
@Api(description = "status")
@RestController
@RequestMapping(value = "/status", produces = {"application/json;charset=UTF-8"})
public class StatusController {

    @ApiOperation(value = "获取当前依赖组件运行的情况")
    @RequestMapping(method = RequestMethod.GET)
    public List<StatusInfo> status() {
        List<StatusInfo> rows = new ArrayList<StatusInfo>();
        Set<String> names = ExtensionLoader.getExtensionLoader(StatusChecker.class).getSupportedExtensions();
        StatusInfo dubboStatus;
        for (String name : names) {
            StatusChecker checker = ExtensionLoader.getExtensionLoader(StatusChecker.class).getExtension(name);
            Status status = checker.check();
            if (status != null && !Status.Level.UNKNOWN.equals(status.getLevel())) {
                dubboStatus = new StatusInfo();
                dubboStatus.setName(name);
                dubboStatus.setStatus(status);
                dubboStatus.setDescription(status.getMessage());
                rows.add(dubboStatus);
            }
        }
        return rows;
    }
}
