package com.github.middleware.dubbo.monitor.controller;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.registry.NotifyListener;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.support.AbstractRegistry;
import com.alibaba.dubbo.registry.support.AbstractRegistryFactory;
import com.github.middleware.dubbo.monitor.domain.RegistryInfo;
import com.github.middleware.dubbo.monitor.domain.example.JSONResult;
import com.github.middleware.dubbo.monitor.service.RegistryContainer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/16
 */
@Api(description = "注册中心(注册表)")
@RestController
@RequestMapping(value = "/registry")
public class RegistryController {
    @Autowired
    private RegistryContainer registryContainer;

    @ApiOperation(value = "获取所有的注册表")
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public List<RegistryInfo> registries() {
        List<RegistryInfo> rows = new ArrayList<RegistryInfo>();
        Collection<Registry> registries = AbstractRegistryFactory.getRegistries();
        if (registries.size() > 0) {
            RegistryInfo dubboRegistry;
            for (Registry registry : registries) {
                dubboRegistry = new RegistryInfo();
                dubboRegistry.setServer(registry.getUrl().getAddress());
                dubboRegistry.setHostname(NetUtils.getHostName(dubboRegistry.getServer()));
                dubboRegistry.setAvailable(registry.isAvailable());
                if (registry instanceof AbstractRegistry) {
                    dubboRegistry.setRegisteredCount(((AbstractRegistry) registry).getRegistered().size());
                    dubboRegistry.setSubscribedCount(((AbstractRegistry) registry).getSubscribed().size());
                }
                rows.add(dubboRegistry);
            }
        }
        return rows;
    }

    @ApiOperation(value = "获取某个注册表上监控服务")
    @RequestMapping(value = "/registered", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public List<String> registered(@RequestParam String registry) {
        Collection<Registry> registries = AbstractRegistryFactory.getRegistries();
        Registry reg = null;
        if (registries.size() > 0) {
            for (Registry r : registries) {
                String sp = r.getUrl().getAddress();
                if (((registry == null || registry.length() == 0) && reg == null) || sp.equals(registry)) {
                    reg = r;
                }
            }
        }
        List<String> rows = new ArrayList<String>();

        if (reg instanceof AbstractRegistry) {
            Set<URL> services = ((AbstractRegistry) reg).getRegistered();
            if (services != null && services.size() > 0) {
                for (URL u : services) {
                    rows.add(u.toFullString());
                }
            }
        }
        return rows;
    }

    @ApiOperation(value = "获取某个注册表上监控服务的订阅情况")
    @RequestMapping(value = "/subscribed", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public List<String> subscribed(@RequestParam String registry) {
        Collection<Registry> registries = AbstractRegistryFactory.getRegistries();
        Registry reg = null;
        if (registries.size() > 0) {
            for (Registry r : registries) {
                String sp = r.getUrl().getAddress();
                if (((registry == null || registry.length() == 0) && reg == null) || sp.equals(registry)) {
                    reg = r;
                }
            }
        }
        List<String> rows = new ArrayList<String>();
        if (reg instanceof AbstractRegistry) {
            Set<URL> services = ((AbstractRegistry) reg).getSubscribed().keySet();
            if (services.size() > 0) {
                for (URL u : services) {
                    rows.add(u.toFullString());
                }
            }
        }
        return rows;
    }

    @ApiOperation(value = "取消服务的注册")
    @RequestMapping(value = "/op/unregister", method = RequestMethod.GET)
    public JSONResult unregister(@RequestParam String provider, HttpServletRequest request) {
        URL providerUrl = URL.valueOf(provider);
        registryContainer.getRegistry().unregister(providerUrl);
        JSONResult jsonResult = new JSONResult(0, "success");
        return jsonResult;
    }

    @ApiOperation(value = "取消服务的订阅")
    @RequestMapping(value = "/op/unsubscribe", method = RequestMethod.GET)
    public JSONResult unsubscribe(@RequestParam String consumer, HttpServletRequest request) {
        URL consumerUrl = URL.valueOf(consumer);
        registryContainer.getRegistry().unsubscribe(consumerUrl, NotifyListenerAdapter.NOTIFY_LISTENER);
        JSONResult jsonResult = new JSONResult(0, "success");
        return jsonResult;
    }

    private static class NotifyListenerAdapter implements NotifyListener {

        public static final NotifyListener NOTIFY_LISTENER = new NotifyListenerAdapter();

        private NotifyListenerAdapter() {
        }

        public void notify(List<URL> urls) {
        }

    }
}
