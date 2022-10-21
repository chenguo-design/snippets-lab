package com.cg.dev;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;

@Component
public class ApplicationContextAcquirer implements ApplicationContextAware {
    /**
     * 1.直接获取
     */
    @Resource
    private ApplicationContext applicationContext;


    @Resource
    private ApplicationContext applicationContext2;
    /**
     * 第二种 实现ApplicationContextAware接口
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext2=applicationContext;
    }

    private ApplicationContext applicationContext3;
    public ApplicationContextAcquirer(ApplicationContext applicationContext){
        this.applicationContext3=applicationContext;
    }

    /**
     * 第四种
     *
     *
     * applicationContext = SpringApplication.run(Main.class,args);\
     */

    /**
     * 第五种
     */
    //public ApplicationContext getApplicationContext(){
    //    WebApplicationContextUtils.getWebApplicationContext();
    //}
}
