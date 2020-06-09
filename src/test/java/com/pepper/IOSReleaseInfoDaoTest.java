package com.pepper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import com.pepper.component.dao.IOSReleaseInfoDao;
import com.pepper.component.dao.impl.IOSReleaseInfoDaoImpl;
import com.pepper.entity.IOSReleaseInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IOSReleaseInfoDaoTest {
    private ApplicationContext context;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("application-context.xml");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFindAll() {
        IOSReleaseInfoDao dao = (IOSReleaseInfoDaoImpl) context.getBean("releaseInfoDao");
        List<IOSReleaseInfo> list = dao.findAll();
        assertTrue(list.size() > 0);
    }

    @Test
    public void testFindById() {
        IOSReleaseInfoDao dao = (IOSReleaseInfoDaoImpl) context.getBean("releaseInfoDao");
        IOSReleaseInfo releaseInfo = dao.findById("5");
        assertNotNull(releaseInfo);
    }
}