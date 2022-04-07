package com.Controller;

import io.swagger.annotations.Api;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

//演示demo，测试是否能够直接运行
@Api(value = "/hello",tags = "测试接口")
@RestController
public class HelloController {

    @GetMapping(value = "/")
    public String printWelcome(ModelMap model) {

        model.addAttribute("message", "Spring  MVC Hello World");
        return "hello";

    }

    @GetMapping(value = "/hello/{name}")
    public ModelAndView hello(@PathVariable("name") String name) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("hello");
        mv.addObject("msg", name);
        return mv;

    }

}
