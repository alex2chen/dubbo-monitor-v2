package com.kxtx.dubbo.monitor.service;

import com.kxtx.dubbo.monitor.domain.InvokeInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/16
 */
public interface StatisticCenter {
    Set<String> getMethods(String service);

    Set<String> getMethodsByService(InvokeInfo dubboInvoke);

    List<InvokeInfo> countDubboInvoke(InvokeInfo dubboInvoke);

    List<InvokeInfo> countDubboInvokeInfo(InvokeInfo dubboInvoke);

    Map<String, List> countDubboInvokeTopTen(InvokeInfo dubboInvoke);
}
