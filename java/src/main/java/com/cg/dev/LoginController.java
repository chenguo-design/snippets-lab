package com.cg.dev;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller

public class LoginController {

    @Resource
    private ApplicationContextAcquirer acquirer;

    @RequestMapping("/acuqireApplicationContext")
    @ResponseBody
    public String testAcquire(){
        System.out.println(1);
        return "yes";
    }
}
