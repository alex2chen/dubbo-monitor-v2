package com.github.middleware.dubbo.monitor.controller;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.github.middleware.dubbo.monitor.domain.ServiceInfo;
import com.github.middleware.dubbo.monitor.service.RegistryContainer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/16
 */
@Api(description = "服务列表")
@RestController
@RequestMapping(value = "/service",produces = {"application/json;charset=UTF-8"})
public class ServiceController {
    @Autowired
    private RegistryContainer registryContainer;

    @ApiOperation(value = "获取服务列表")
    @RequestMapping(method = RequestMethod.GET)
    public List<ServiceInfo> services() {
        List<ServiceInfo> rows = new ArrayList<ServiceInfo>();
        Set<String> services = registryContainer.getServices();
        if (services != null && services.size() > 0) {
            ServiceInfo dubboService;
            for (String service : services) {
                dubboService = new ServiceInfo();
                dubboService.setName(service);
                List<URL> providers = registryContainer.getProvidersByService(service);
                int providerSize = providers == null ? 0 : providers.size();
                dubboService.setProviderCount(providerSize);

                List<URL> consumers = registryContainer.getConsumersByService(service);
                int consumerSize = consumers == null ? 0 : consumers.size();
                dubboService.setConsumerCount(consumerSize);

                if (providerSize > 0) {
                    URL provider = providers.iterator().next();
                    dubboService.setApplication(provider.getParameter(Constants.APPLICATION_KEY, ""));
                    dubboService.setOwner(provider.getParameter("owner", ""));
                    dubboService.setOrganization((provider.hasParameter("organization") ? provider.getParameter("organization") : ""));
                }

                rows.add(dubboService);
            }
        }
        return rows;
    }

    @ApiOperation(value = "获取某个服务(提供方)")
    @RequestMapping(value = "/providers", method = RequestMethod.GET)
    public List<String> providers(@RequestParam String service) {
        List<URL> providers = registryContainer.getProvidersByService(service);
        List<String> rows = new ArrayList<String>();
        if (providers != null && providers.size() > 0) {
            for (URL u : providers) {
                rows.add(u.toFullString());
            }
        }
        return rows;
    }

    @ApiOperation(value = "获取某个服务(消费方)")
    @RequestMapping(value = "/consumers", method = RequestMethod.GET)
    public List<String> consumers(@RequestParam String service) {
        List<URL> consumers = registryContainer.getConsumersByService(service);
        List<String> rows = new ArrayList<String>();
        if (consumers != null && consumers.size() > 0) {
            for (URL u : consumers) {
                rows.add(u.toFullString());
            }
        }
        return rows;
    }
}
