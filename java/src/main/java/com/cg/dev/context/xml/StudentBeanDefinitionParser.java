package com.cg.dev.context.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class StudentBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser{
    private static final String STUDENT_NAME_ATTRIBUTE="name";

    private static final String STUDENT_AGE_ATTRIBUTE="age";

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String name = element.getAttribute(STUDENT_NAME_ATTRIBUTE);
        String age = element.getAttribute(STUDENT_AGE_ATTRIBUTE);
        if(StringUtils.hasText(name)){
            builder.addPropertyValue(STUDENT_NAME_ATTRIBUTE, name);
        }
        if(StringUtils.hasText(age)){
            builder.addPropertyValue(STUDENT_AGE_ATTRIBUTE,Integer.valueOf(age));
        }
    }

    @Override
    protected Class<?> getBeanClass(Element element) {
        return Student.class;
    }
}
