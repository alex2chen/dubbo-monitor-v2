package com.github.middleware.dubbo.monitor.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/16
 */
public class LineChartSeries implements Serializable {

    private String name;

    private List<double[]> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<double[]> getData() {
        return data;
    }

    public void setData(List<double[]> data) {
        this.data = data;
    }

}
