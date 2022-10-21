package com.cg.dev;

import com.cg.dev.context.xml.Student;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TestAlias implements ApplicationContextAware , BeanNameAware {
    @Resource
    Student student;

    @Resource
    Student student1;

    @Resource
    Student student2;

    @Resource
    Student student3;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Object student = applicationContext.getBean("student");
        System.out.println(student);

    }

    @Override
    public void setBeanName(String name) {

    }
}
