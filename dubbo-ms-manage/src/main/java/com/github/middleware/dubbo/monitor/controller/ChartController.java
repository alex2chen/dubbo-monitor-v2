package com.github.middleware.dubbo.monitor.controller;

import com.github.middleware.dubbo.monitor.domain.InvokeInfo;
import com.github.middleware.dubbo.monitor.domain.InvokeLineChart;
import com.github.middleware.dubbo.monitor.domain.LineChartSeries;
import com.github.middleware.dubbo.monitor.service.StatisticCenter;
import com.google.common.collect.Lists;
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
@Api(description = "报表统计")
@RestController
@RequestMapping(value = "/chart", produces = {"application/json;charset=UTF-8"})
public class ChartController {
    @Autowired
    private StatisticCenter statisticCenter;

    @ApiOperation(value = "查询服务中的详细情况")
    @RequestMapping(value = "loadMethods", method = RequestMethod.GET)
    public Set<String> loadMethods(@RequestParam(required = true) String service) {
        //获取Service方法
        return statisticCenter.getMethods(service);
    }

    @ApiOperation(value = "查询报表数据")
    @RequestMapping(value = "loadChartsData", method = RequestMethod.POST)
    public List<InvokeLineChart> loadChartsData(@RequestBody InvokeInfo dubboInvoke, HttpServletRequest httpRequest) {
        // 计算统计平均请求次数的时间粒度
        long timeParticle = dubboInvoke.getTimeParticle() / 1000;
        List<InvokeLineChart> dubboInvokeLineChartList = new ArrayList<InvokeLineChart>();
        InvokeLineChart qpsLineChart;
        InvokeLineChart artLineChart;
        List<LineChartSeries> qpsLineChartSeriesList;
        List<LineChartSeries> artLineChartSeriesList;
        LineChartSeries qpsLineChartSeries;
        LineChartSeries artLineChartSeries;
        List<double[]> qpsSeriesDatas;
        List<double[]> artSeriesDatas;
        Set<String> methods = statisticCenter.getMethodsByService(dubboInvoke);
        for (String method : methods) {
            qpsLineChart = new InvokeLineChart();
            artLineChart = new InvokeLineChart();
            qpsLineChartSeriesList = new ArrayList<LineChartSeries>();
            artLineChartSeriesList = new ArrayList<LineChartSeries>();
            dubboInvoke.setMethod(method);
            // 组织Provider折线数据
            qpsLineChartSeries = new LineChartSeries();
            artLineChartSeries = new LineChartSeries();
            dubboInvoke.setType("provider");
            List<InvokeInfo> providerDubboInvokeDetails = statisticCenter.countDubboInvoke(dubboInvoke);
            qpsLineChartSeries.setName(dubboInvoke.getType());
            artLineChartSeries.setName(dubboInvoke.getType());
            qpsSeriesDatas = Lists.newArrayList();
            artSeriesDatas = Lists.newArrayList();
            for (InvokeInfo dubboInvokeDetail : providerDubboInvokeDetails) {
                qpsSeriesDatas.add(new double[]{dubboInvokeDetail.getInvokeTime(), Double.valueOf(String.format("%.4f", dubboInvokeDetail.getSuccess() / timeParticle))});
                artSeriesDatas.add(new double[]{dubboInvokeDetail.getInvokeTime(), Double.valueOf(String.format("%.4f", dubboInvokeDetail.getElapsed()))});
            }
            qpsLineChartSeries.setData(qpsSeriesDatas);
            qpsLineChartSeriesList.add(qpsLineChartSeries);
            artLineChartSeries.setData(artSeriesDatas);
            artLineChartSeriesList.add(artLineChartSeries);
            // 组织Consumer折线数据
            qpsLineChartSeries = new LineChartSeries();
            artLineChartSeries = new LineChartSeries();
            dubboInvoke.setType("consumer");
            List<InvokeInfo> consumerDubboInvokeDetails = statisticCenter.countDubboInvoke(dubboInvoke);
            qpsLineChartSeries.setName(dubboInvoke.getType());
            artLineChartSeries.setName(dubboInvoke.getType());
            qpsSeriesDatas = Lists.newArrayList();
            artSeriesDatas = Lists.newArrayList();
            for (InvokeInfo dubboInvokeDetail : consumerDubboInvokeDetails) {
                qpsSeriesDatas.add(new double[]{dubboInvokeDetail.getInvokeTime(), Double.valueOf(String.format("%.4f", dubboInvokeDetail.getSuccess() / timeParticle))});
                artSeriesDatas.add(new double[]{dubboInvokeDetail.getInvokeTime(), Double.valueOf(String.format("%.4f", dubboInvokeDetail.getElapsed()))});
            }
            qpsLineChartSeries.setData(qpsSeriesDatas);
            qpsLineChartSeriesList.add(qpsLineChartSeries);
            artLineChartSeries.setData(artSeriesDatas);
            artLineChartSeriesList.add(artLineChartSeries);

            //====================== 统计QPS ===========================
            qpsLineChart.setSeriesData(qpsLineChartSeriesList);
            qpsLineChart.setChartType("Qps");
            qpsLineChart.setTitle("QPS");
            qpsLineChart.setSubtext("Requests per second (t/s)");
            qpsLineChart.setMethod(method);

            dubboInvokeLineChartList.add(qpsLineChart);

            //====================== 统计ART ===========================
            artLineChart.setSeriesData(artLineChartSeriesList);
            artLineChart.setTitle("ART");
            artLineChart.setChartType("Art");
            artLineChart.setSubtext("Average response time (ms/t)");
            artLineChart.setMethod(method);

            dubboInvokeLineChartList.add(artLineChart);
        }
        return dubboInvokeLineChartList;
    }

    @ApiOperation(value = "首页服务调用报表（TOP20 SUCCESS/FAIL）")
    @RequestMapping(value = "loadTopData", method = RequestMethod.POST)
    public List<InvokeLineChart> loadTopDate(@RequestBody InvokeInfo dubboInvoke) {
        List<InvokeLineChart> dubboInvokeLineChartList = new ArrayList<InvokeLineChart>();
        InvokeLineChart successDubboInvokeLineChart = new InvokeLineChart();
        List<String> sxAxisCategories = Lists.newArrayList();
        LineChartSeries slineChartSeries = new LineChartSeries();
        List<double[]> sdataList = Lists.newArrayList();
        Map dubboInvokeMap = statisticCenter.countDubboInvokeTopTen(dubboInvoke);
        List<InvokeInfo> success = (List<InvokeInfo>) dubboInvokeMap.get("success");
        for (InvokeInfo di : success) {
            sxAxisCategories.add(di.getMethod());
            sdataList.add(new double[]{di.getSuccess()});
        }
        slineChartSeries.setData(sdataList);
        slineChartSeries.setName("provider");

        successDubboInvokeLineChart.setxAxisCategories(sxAxisCategories);
        successDubboInvokeLineChart.setSeriesData(Arrays.asList(slineChartSeries));
        successDubboInvokeLineChart.setTitle("调用成功.Top 20");
        successDubboInvokeLineChart.setSubtext("某个时间段的统计");
        dubboInvokeLineChartList.add(successDubboInvokeLineChart);

        InvokeLineChart failureDubboInvokeLineChart = new InvokeLineChart();
        List<String> fxAxisCategories = Lists.newArrayList();
        LineChartSeries flineChartSeries = new LineChartSeries();
        List<double[]> fdataList = Lists.newArrayList();
        List<InvokeInfo> failure = (List<InvokeInfo>) dubboInvokeMap.get("failure");
        for (InvokeInfo di : failure) {
            fxAxisCategories.add(di.getMethod());
            fdataList.add(new double[]{di.getFailure()});
        }
        flineChartSeries.setData(fdataList);
        flineChartSeries.setName("provider");

        failureDubboInvokeLineChart.setxAxisCategories(fxAxisCategories);
        failureDubboInvokeLineChart.setSeriesData(Arrays.asList(flineChartSeries));
        failureDubboInvokeLineChart.setTitle("调用失败.Top 20");
        failureDubboInvokeLineChart.setSubtext("某个时间段的统计");
        dubboInvokeLineChartList.add(failureDubboInvokeLineChart);
        return dubboInvokeLineChartList;
    }
}
