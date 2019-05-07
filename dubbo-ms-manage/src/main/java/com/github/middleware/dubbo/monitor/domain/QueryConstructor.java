package com.github.middleware.dubbo.monitor.domain;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/16
 */
public class QueryConstructor {
    private Query query = new Query();

    public static QueryConstructor get() {
        return new QueryConstructor();
    }

    public <T> QueryConstructor addIsAttribute(String name, T t) {
        if (t == null && !StringUtils.hasLength(t.toString())) {
            return this;
        }
        query.addCriteria(Criteria.where(name).is(t));
        return this;
    }

    public <T> QueryConstructor addBetweenAttribute(String name, T before, T after) {
        if (before == null && !StringUtils.hasLength(before.toString())) {
            return this;
        }
        if (after == null && !StringUtils.hasLength(after.toString())) {
            return this;
        }
        query.addCriteria(Criteria.where(name).gte(before).lte(after));
        return this;
    }

    public Query getQuery() {
        return query;
    }
}

