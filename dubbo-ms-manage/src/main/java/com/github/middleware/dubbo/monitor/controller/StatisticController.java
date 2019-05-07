package com.github.middleware.dubbo.monitor.controller;


import com.github.middleware.dubbo.monitor.core.DateUtil;
import com.github.middleware.dubbo.monitor.domain.InvokeInfo;
import com.github.middleware.dubbo.monitor.domain.StatisticInfo;
import com.github.middleware.dubbo.monitor.service.StatisticCenter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/16
 */
@Api(description = "统计")
@RestController
@RequestMapping(value = "/statistic", produces = {"application/json;charset=UTF-8"})
public class StatisticController {

    @Autowired
    private StatisticCenter statisticCenter;

    @ApiOperation(value = "获取统计信息")
    @RequestMapping(method = RequestMethod.POST)
    public Map index(@RequestBody InvokeInfo dubboInvoke) {
        Map result = new HashMap();
        // Set default Search Date
        if (dubboInvoke.getInvokeDateFrom() == null && dubboInvoke.getInvokeDateTo() == null) {
            LocalDate localDate = LocalDate.now();
            dubboInvoke.setInvokeDateFrom(DateUtil.parseDate(localDate));
            dubboInvoke.setInvokeDateTo(DateUtil.parseDate(localDate.plusDays(1)));
        }
        //获取Service方法
        Set<String> methods = statisticCenter.getMethodsByService(dubboInvoke);
        List<InvokeInfo> dubboInvokes;
        List<StatisticInfo> dubboStatisticses = new ArrayList<StatisticInfo>();
        StatisticInfo dubboStatistics;
        for (String method : methods) {
            dubboStatistics = new StatisticInfo();
            dubboStatistics.setMethod(method);
            dubboInvoke.setMethod(method);
            dubboInvoke.setType("provider");
            dubboInvokes = statisticCenter.countDubboInvokeInfo(dubboInvoke);
            for (InvokeInfo di : dubboInvokes) {
                if (di == null) {
                    continue;
                }
                dubboStatistics.setProviderSuccess(di.getSuccess());
                dubboStatistics.setProviderFailure(di.getFailure());
                dubboStatistics.setProviderAvgElapsed(di.getSuccess() != 0 ? Double.valueOf(String.format("%.2f", di.getElapsed() / di.getSuccess())) : 0);
                dubboStatistics.setProviderMaxElapsed(di.getMaxElapsed());
                dubboStatistics.setProviderMaxConcurrent(di.getMaxConcurrent());
            }
            dubboInvoke.setType("consumer");
            dubboInvokes = statisticCenter.countDubboInvokeInfo(dubboInvoke);
            for (InvokeInfo di : dubboInvokes) {
                if (di == null) {
                    continue;
                }
                dubboStatistics.setConsumerSuccess(di.getSuccess());
                dubboStatistics.setConsumerFailure(di.getFailure());
                dubboStatistics.setConsumerAvgElapsed(di.getSuccess() != 0 ? Double.valueOf(String.format("%.2f", di.getElapsed() / di.getSuccess())) : 0);
                dubboStatistics.setConsumerMaxElapsed(di.getMaxElapsed());
                dubboStatistics.setConsumerMaxConcurrent(di.getMaxConcurrent());
            }
            dubboStatisticses.add(dubboStatistics);
        }
        result.put("rows", dubboStatisticses);
        result.put("service", dubboInvoke.getService());
        return result;
    }
}
