package com.github.middleware.dubbo.monitor.service.dsrpt.engine;

import com.alibaba.dubbo.common.utils.ReflectUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeToken;
import com.lmax.disruptor.EventFactory;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/6/30
 */
public class DefaultEventFactory<T> implements EventFactory<T> {
    @SuppressWarnings("unchecked")
    @Override
    public T newInstance() {
        try {
            return (T) ReflectUtils.getGenericClass(this.getClass()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
