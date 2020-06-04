package com.pepper;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import com.pepper.component.service.IOSReleaseInfoService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppContextTest {
    @Before
    public void init() throws IOException {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testServiceBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        IOSReleaseInfoService ris = (IOSReleaseInfoService) context.getBean("releaseInfoService");
        assertNotNull(ris);
    }
}