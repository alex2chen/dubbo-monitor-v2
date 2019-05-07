package core;

import com.github.middleware.dubbo.monitor.core.DateUtil;
import junit.framework.Assert;
import org.junit.Test;

import java.time.LocalDate;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/4/25
 */
public class DateUtil_test {
    @Test
    public void go_parseDate() {
        LocalDate localDate = LocalDate.now();
        Assert.assertNotNull(DateUtil.parseDate(localDate));
        Assert.assertNotNull(DateUtil.parseDate(localDate.plusDays(1)));
    }
}
