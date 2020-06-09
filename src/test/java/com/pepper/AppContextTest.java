package com.pepper;

import static org.junit.Assert.assertNotNull;

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
    public void testSetup() {
        assertNotNull(context);
    }
}