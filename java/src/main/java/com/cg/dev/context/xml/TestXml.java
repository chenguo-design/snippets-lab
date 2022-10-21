package com.cg.dev.context.xml;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestXml {
    @Test
    public void test(){

            ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
            Student testServer = ac.getBean("student3", Student.class);
            System.out.println(testServer);


    }
}
