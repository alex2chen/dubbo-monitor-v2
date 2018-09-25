package service;

import com.alibaba.dubbo.common.utils.ReflectUtils;
import com.google.common.reflect.Reflection;
import com.kxtx.dubbo.monitor.service.dsrpt.engine.DefaultEventFactory;
import com.lmax.disruptor.EventFactory;
import org.junit.Test;
import org.springframework.data.util.ReflectionUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/7/4
 */
public class Type_test {
    Map<String, Integer> a;

    @Test
    public void go_generics() throws NoSuchFieldException {
        EventFactory eventFactory = new DefaultEventFactory<String>();
        ParameterizedType mapStringInteger = (ParameterizedType) this.getClass().getDeclaredField("a").getGenericType();
        System.out.println(mapStringInteger);
        ParameterizedType data = (ParameterizedType) eventFactory.getClass().getDeclaredField("data").getGenericType();
        System.out.println(data);


//        System.out.println(ReflectUtils.getGenericClass(eventFactory.getClass()));
//
//        ParameterizedType ptype = (ParameterizedType) eventFactory.getClass().getGenericSuperclass();
//        Class<?> clazz = (Class<?>) ptype.getActualTypeArguments()[0];
//        System.out.println(clazz);
    }

}
