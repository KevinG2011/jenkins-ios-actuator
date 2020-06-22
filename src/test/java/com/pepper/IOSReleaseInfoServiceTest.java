package com.pepper;

import static org.junit.Assert.fail;

import com.pepper.component.service.IOSReleaseInfoService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IOSReleaseInfoServiceTest {
    private ApplicationContext context;
    private static final Logger logger = LoggerFactory.getLogger(IOSReleaseInfoServiceTest.class);

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("application-context.xml");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetService() {
        IOSReleaseInfoService service = (IOSReleaseInfoService) context.getBean("releaseInfoService",
                IOSReleaseInfoService.class);
        try {
            service.getAll();
        } catch (Exception e) {
            fail();
        }
    }
}