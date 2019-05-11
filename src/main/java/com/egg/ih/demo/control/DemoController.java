package com.egg.ih.demo.control;

import com.egg.ih.demo.service.DemoService;
import com.egg.ih.demo.vo.DemoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/demo")
@RestController
public class DemoController {
    @Autowired
    private DemoService demoService;

    @GetMapping(value = "/demo/{id}")
    public DemoVO findById(@PathVariable Integer id) {
        return demoService.findById(id);
    }
}
