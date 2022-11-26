package com.lingyun.study.springcloud;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "hello Jenkins!";
    }

}
