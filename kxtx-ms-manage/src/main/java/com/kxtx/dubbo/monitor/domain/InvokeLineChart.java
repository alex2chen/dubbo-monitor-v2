package com.kxtx.dubbo.monitor.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/16
 */
public class InvokeLineChart implements Serializable {

    private String title;
    private String subtext;
    private String chartType;
    private List<String> xAxisCategories;
    private List<LineChartSeries> seriesData;
    private String method;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public List<String> getxAxisCategories() {
        return xAxisCategories;
    }

    public void setxAxisCategories(List<String> xAxisCategories) {
        this.xAxisCategories = xAxisCategories;
    }

    public String getSubtext() {
        return subtext;
    }

    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }

    public List<LineChartSeries> getSeriesData() {
        return seriesData;
    }

    public void setSeriesData(List<LineChartSeries> seriesData) {
        this.seriesData = seriesData;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
