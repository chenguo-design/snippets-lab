package com.cg.dev.context.inject;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TestInject implements InitializingBean {
    @Resource
    private Injected injected;

    private void init(){
        System.out.println(injected);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(injected);
    }
}
