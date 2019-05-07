package com.github.middleware.dubbo.monitor.controller;

import com.alibaba.dubbo.common.URL;
import com.google.common.base.Strings;
import com.github.middleware.dubbo.monitor.domain.AppInfo;
import com.github.middleware.dubbo.monitor.service.RegistryContainer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/13
 */
@Api(description = "应用管理")
@RestController
@RequestMapping(value = "/app", produces = {"application/json;charset=UTF-8"})
public class AppController {
    @Autowired
    private RegistryContainer registryContainer;

    @ApiOperation(value = "获取应用列表")
    @RequestMapping(method = RequestMethod.GET)
    public List<AppInfo> index(@RequestParam(required = false) String application) throws InterruptedException {
        List<AppInfo> rows = new ArrayList<AppInfo>();
        Set<String> applications = registryContainer.getApplications();
        if (!Strings.isNullOrEmpty(application)) {
            applications = applications.stream().filter(n -> n.equals(application)).collect(Collectors.toSet());
        }
        if (applications == null || applications.isEmpty()) return rows;
        AppInfo dubboApplication;
        for (String app : applications) {
            dubboApplication = new AppInfo();
            dubboApplication.setName(app);
            List<URL> providers = registryContainer.getProvidersByApplication(app);
            List<URL> consumers = registryContainer.getConsumersByApplication(app);
            if ((providers != null && providers.size() > 0) || (consumers != null && consumers.size() > 0)) {
                URL url = (providers != null && providers.size() > 0) ? providers.iterator().next() : consumers.iterator().next();
                dubboApplication.setName(app);
                dubboApplication.setOwner(url.getParameter("owner", ""));
                dubboApplication.setOrganization((url.hasParameter("organization") ? url.getParameter("organization") : ""));
            }
            dubboApplication.setProviderCount(providers == null ? 0 : providers.size());
            dubboApplication.setConsumerCount(consumers == null ? 0 : consumers.size());
            Set<String> efferents = registryContainer.getDependencies(app, false);
            dubboApplication.setEfferentCount(efferents == null ? 0 : efferents.size());
            Set<String> afferents = registryContainer.getDependencies(app, true);
            dubboApplication.setAfferentCount(afferents == null ? 0 : afferents.size());
            rows.add(dubboApplication);
        }
        return rows;
    }

    @ApiOperation(value = "查看某个应用的服务提供方列表")
    @RequestMapping(value = "/providers", method = RequestMethod.GET)
    public List<String> providers(@RequestParam String application) {
        List<URL> providers = registryContainer.getProvidersByApplication(application);
        List<String> rows = new ArrayList<String>();
        if (providers != null && providers.size() > 0) {
            for (URL u : providers) {
                rows.add(u.toFullString());
            }
        }
        return rows;
    }

    @ApiOperation(value = "查看某个应用的服务消费方列表")
    @RequestMapping(value = "/consumers", method = RequestMethod.GET)
    public List<String> consumers(@RequestParam String application) {
        List<URL> consumers = registryContainer.getConsumersByApplication(application);
        List<String> rows = new ArrayList<String>();
        if (consumers != null && consumers.size() > 0) {
            for (URL u : consumers) {
                rows.add(u.toFullString());
            }
        }
        return rows;
    }

    @ApiOperation(value = "查看某个应用依赖情况")
    @RequestMapping(value = "/dependencies", method = RequestMethod.GET)
    public List<String> dependencies(@RequestParam String application, @RequestParam(required = false) boolean reverse) {
        List<String> rows = new ArrayList<String>();
        Set<String> directly = registryContainer.getDependencies(application, reverse);
        Set<String> indirectly = new HashSet<String>();
        appendDependency(rows, reverse, application, 0, new HashSet<String>(), indirectly);
        return rows;
    }

    //TODO:fixed
    private void appendDependency(List<String> rows, boolean reverse, String application, int level, Set<String> appended, Set<String> indirectly) {
        StringBuilder buf = new StringBuilder();
        if (level > 0) {
            for (int i = 0; i < level; i++) {
                buf.append("<span style=\"margin-left:" + (level * 30) + "px;\"></span>");
            }
            buf.append(reverse ? "<i class=\"fa fa-level-down\" style=\"-webkit-transform: rotate(180deg);margin-right:5px;\"></i> " : "<i class=\"fa fa-level-up\" style=\"-webkit-transform: rotate(90deg);margin-right:5px;\"></i> ");
        }
        boolean end = false;
        if (level > 5) {
            buf.append(" <span class=\"badge bg-light-blue\">More...</span>");
            end = true;
        } else {
            buf.append(application);
            if (appended.contains(application)) {
                buf.append(" <span class=\"badge bg-red\">Cycle</span>");
                end = true;
            }
        }
        rows.add(buf.toString());
        if (end) {
            return;
        }
        appended.add(application);
        indirectly.add(application);
        Set<String> dependencies = registryContainer.getDependencies(application, reverse);
        if (dependencies != null && dependencies.size() > 0) {
            for (String dependency : dependencies) {
                appendDependency(rows, reverse, dependency, level + 1, appended, indirectly);
            }
        }
        appended.remove(application);
    }
}
