package com.github.middleware.dubbo.monitor.service.support;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.github.middleware.dubbo.monitor.core.DateUtil;
import com.github.middleware.dubbo.monitor.domain.InvokeInfo;
import com.github.middleware.dubbo.monitor.domain.QueryConstructor;
import com.github.middleware.dubbo.monitor.service.StatisticCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/16
 */
@Service
public class SimpleStatisticCenter implements StatisticCenter {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleStatisticCenter.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 统计调用数据用于图表展示
     *
     * @param invokeInfo
     */
    @Override
    public List<InvokeInfo> countDubboInvoke(InvokeInfo invokeInfo) {
        if (Strings.isNullOrEmpty(invokeInfo.getService())) {
            throw new RuntimeException("统计查询缺少必要参数！");
        }
        TypedAggregation<InvokeInfo> aggregation = Aggregation.newAggregation(InvokeInfo.class,
                Aggregation.match(Criteria.where("service").is(invokeInfo.getService())
                        .and("method").is(invokeInfo.getMethod())
                        .and("type").is(invokeInfo.getType())
                        .and("invokeDate").gte(DateUtil.dateToString(invokeInfo.getInvokeDateFrom())).lte(DateUtil.dateToString(invokeInfo.getInvokeDateTo()))
                ),
                Aggregation.project("service", "method", "type", "success", "failure", "elapsed", "maxElapsed", "maxConcurrent", "invokeTime")
                        .andExpression("(invokeTime / " + invokeInfo.getTimeParticle() + ") * " + invokeInfo.getTimeParticle()).as("invokeTime"),
                Aggregation.group("service", "method", "type", "invokeTime")
                        .sum("success").as("success")
                        .sum("failure").as("failure")
                        .sum("elapsed").as("elapsed")
                        .max("maxElapsed").as("maxElapsed")
                        .max("maxConcurrent").as("maxConcurrent"),
                Aggregation.sort(Sort.Direction.ASC, "invokeTime")
        );
        LOG.debug(aggregation.toString());
        AggregationResults<InvokeInfo> result = mongoTemplate.aggregate(aggregation, "invokeInfo", InvokeInfo.class);
        return result.getMappedResults();
    }

    @Override
    public Set<String> getMethods(String service) {
        Query query = QueryConstructor.get().addIsAttribute("service", service).getQuery();
        List<InvokeInfo> result = mongoTemplate.find(query, InvokeInfo.class, "invokeInfo");
        if (result == null || result.isEmpty())
            return null;
        return result.stream().map(InvokeInfo::getMethod).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getMethodsByService(InvokeInfo invokeInfo) {
        Set<String> methods = Sets.newHashSet();
        Query query = QueryConstructor.get()
                .addIsAttribute("service", invokeInfo.getService())
                .addIsAttribute("type", invokeInfo.getType())
                .addBetweenAttribute("invokeDate", DateUtil.dateToString(invokeInfo.getInvokeDateFrom()), DateUtil.dateToString(invokeInfo.getInvokeDateTo()))
                .getQuery();
        if (!Strings.isNullOrEmpty(invokeInfo.getMethod())) {
            query.addCriteria(Criteria.where("method").is(invokeInfo.getMethod()));
        }
        LOG.warn(query.getQueryObject().toString());
        List<InvokeInfo> result = mongoTemplate.find(query, InvokeInfo.class, "invokeInfo");
        for (InvokeInfo di : result) {
            methods.add(di.getMethod());
        }
        return methods;
    }

    /**
     * 统计各方法调用信息
     *
     * @param invokeInfo
     * @return
     */
    @Override
    public List<InvokeInfo> countDubboInvokeInfo(InvokeInfo invokeInfo) {
        if (Strings.isNullOrEmpty(invokeInfo.getService()) || Strings.isNullOrEmpty(invokeInfo.getMethod())
                || Strings.isNullOrEmpty(invokeInfo.getType())) {
            throw new RuntimeException("统计查询缺少必要参数！");
        }
        TypedAggregation<InvokeInfo> aggregation = Aggregation.newAggregation(InvokeInfo.class,
                Aggregation.match(Criteria.where("service").is(invokeInfo.getService())
                        .and("method").is(invokeInfo.getMethod())
                        .and("type").is(invokeInfo.getType())
                        .and("invokeDate").gte(DateUtil.dateToString(invokeInfo.getInvokeDateFrom())).lte(DateUtil.dateToString(invokeInfo.getInvokeDateTo()))
                ),
                Aggregation.group("service", "method")
                        .sum("success").as("success")
                        .sum("failure").as("failure")
                        .sum("elapsed").as("elapsed")
                        .max("maxElapsed").as("maxElapsed")
                        .min("maxConcurrent").as("maxConcurrent")
        );
        LOG.debug(aggregation.toString());
        AggregationResults<InvokeInfo> result = mongoTemplate.aggregate(aggregation, "invokeInfo", InvokeInfo.class);

        return result.getMappedResults();
    }

    /**
     * 统计系统方法调用排序信息
     *
     * @param invokeInfo
     * @return
     */
    @Override
    public Map<String, List> countDubboInvokeTopTen(InvokeInfo invokeInfo) {
        Map<String, List> result = Maps.newHashMap();
        Criteria criteris = Criteria.where("invokeDate").gte(DateUtil.dateToString(invokeInfo.getInvokeDateFrom())).lte(DateUtil.dateToString(invokeInfo.getInvokeDateTo()))
                .and("type").is(invokeInfo.getType());
        LOG.debug(criteris.getCriteriaObject().toString());
        List<InvokeInfo> successList = Lists.newArrayList();
        GroupByResults<InvokeInfo> successResults = mongoTemplate.group(criteris, "invokeInfo",
                GroupBy.key("service", "method").initialDocument("{ success: 0 }")
                        .reduceFunction("function(doc, prev) { prev.success += doc.success }"),
                InvokeInfo.class);
        for (InvokeInfo invokeInfo1 : successResults) {
            successList.add(invokeInfo1);
        }
        Collections.sort(successList, new Comparator<InvokeInfo>() {
            public int compare(InvokeInfo arg0, InvokeInfo arg1) {
                return (int) (arg1.getSuccess() - arg0.getSuccess());
            }
        });
        successList.subList(0, successList.size() > 19 ? 19 : successList.size());
        result.put("success", successList);
        List<InvokeInfo> failureList = Lists.newArrayList();
        GroupByResults<InvokeInfo> failureResults = mongoTemplate.group(criteris, "invokeInfo",
                GroupBy.key("service", "method").initialDocument("{ failure: 0 }")
                        .reduceFunction("function(doc, prev) { prev.failure += doc.failure }"),
                InvokeInfo.class);
        for (InvokeInfo invokeInfo1 : failureResults) {
            failureList.add(invokeInfo1);
        }
        Collections.sort(failureList, new Comparator<InvokeInfo>() {
            public int compare(InvokeInfo arg0, InvokeInfo arg1) {
                return (int) (arg1.getFailure() - arg0.getFailure());
            }
        });
        failureList.subList(0, failureList.size() > 19 ? 19 : failureList.size());
        result.put("failure", failureList);
        return result;
    }

//    private Date parseMongoDate(Date input) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        format.p
//        return format.parse(filter.getValue().toString().replaceAll("-", "/"));
//    }
}
