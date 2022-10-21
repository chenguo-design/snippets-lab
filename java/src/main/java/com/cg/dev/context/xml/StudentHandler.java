package com.cg.dev.context.xml;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class StudentHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        System.out.println("注册parser到命名空间handler");
        registerBeanDefinitionParser("student", new StudentBeanDefinitionParser());
    }
}
