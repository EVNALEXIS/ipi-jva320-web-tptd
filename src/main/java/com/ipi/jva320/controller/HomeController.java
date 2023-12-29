package com.ipi.jva320.controller;

import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")

public class HomeController {
    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;

    @GetMapping(value = "")
    public String home() {
        return "home";
    }

    @ModelAttribute("nbSalarie")
    public Long nbSalarie() {
        return salarieAideADomicileService.countSalaries();
    }


}
