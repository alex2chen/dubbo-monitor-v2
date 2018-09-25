package com.kxtx.dubbo.monitor.controller;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.kxtx.dubbo.monitor.domain.HostInfo;
import com.kxtx.dubbo.monitor.service.RegistryContainer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/16
 */
@Api(description = "应用服务器列表")
@RestController
@RequestMapping(value = "/host", produces = {"application/json;charset=UTF-8"})
public class HostController {

    @Autowired
    private RegistryContainer registryContainer;

    @ApiOperation(value = "获取所有服务器")
    @RequestMapping(method = RequestMethod.GET)
    public List<HostInfo> hosts() {
        List<HostInfo> rows = new ArrayList<HostInfo>();
        Set<String> hosts = registryContainer.getHosts();

        if (hosts != null && hosts.size() > 0) {
            HostInfo dubboHost;
            for (String host : hosts) {
                dubboHost = new HostInfo();
                dubboHost.setHost(host);
                dubboHost.setHostname(NetUtils.getHostName(host));
                List<URL> providers = registryContainer.getProvidersByHost(host);
                List<URL> consumers = registryContainer.getConsumersByHost(host);
                if ((providers != null && providers.size() > 0) || (consumers != null && consumers.size() > 0)) {
                    URL url = (providers != null && providers.size() > 0) ? providers.iterator().next() : consumers.iterator().next();
                    dubboHost.setApplication(url.getParameter(Constants.APPLICATION_KEY, ""));
                    dubboHost.setOwner(url.getParameter("owner", ""));
                    dubboHost.setOrganization((url.hasParameter("organization") ? url.getParameter("organization") : ""));
                }
                int providerSize = providers == null ? 0 : providers.size();
                dubboHost.setProviderCount(providerSize);
                int consumerSize = consumers == null ? 0 : consumers.size();
                dubboHost.setConsumerCount(consumerSize);
                rows.add(dubboHost);
            }
        }
        return rows;
    }

    @ApiOperation(value = "获取某个应用上的注册信息")
    @RequestMapping(value = "/providers", method = RequestMethod.GET)
    public Map providers(@RequestParam String host) {
        Map result = new HashMap();
        List<URL> providers = registryContainer.getProvidersByHost(host);
        List<String> rows = new ArrayList<String>();
        if (providers != null && providers.size() > 0) {
            for (URL u : providers) {
                rows.add(u.toFullString());
            }
        }
        result.put("host", host);
        result.put("address", NetUtils.getHostName(host) + "/" + host);
        result.put("rows", rows);
        return result;
    }

    @ApiOperation(value = "获取某个应用上的订阅信息")
    @RequestMapping(value = "/consumers", method = RequestMethod.GET)
    public Map consumers(@RequestParam String host) {
        Map result = new HashMap();
        List<URL> consumers = registryContainer.getConsumersByHost(host);
        List<String> rows = new ArrayList<String>();
        if (consumers != null && consumers.size() > 0) {
            for (URL u : consumers) {
                rows.add(u.toFullString());
            }
        }
        result.put("host", host);
        result.put("address", NetUtils.getHostName(host) + "/" + host);
        result.put("rows", rows);
        return result;
    }
}
