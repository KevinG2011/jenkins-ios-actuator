package com.pepper;

import static org.junit.Assert.assertNotNull;

import com.pepper.component.dao.impl.IOSReleaseInfoJdbcDao;
import com.pepper.spring.pojo.Instrumentalist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppContextTest {
    private ApplicationContext context;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("application-context.xml");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testServiceBean() {
        Instrumentalist il = (Instrumentalist) context.getBean("releaseInfoDao");
        // IOSReleaseInfoService ris = (IOSReleaseInfoService)
        // context.getBean("releaseInfoService");
        assertNotNull(il.getSong());
        assertNotNull(il.getInstrument());
    }

    @Test
    public void testJdbcDao() {
        IOSReleaseInfoJdbcDao dao = (IOSReleaseInfoJdbcDao) context.getBean("releaseInfoDao");
        // IOSReleaseInfoService ris = (IOSReleaseInfoService)
        // context.getBean("releaseInfoService");
        assertNotNull(dao.getJdbcTemplate());
    }
}