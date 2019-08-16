package com.booxj.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MvcController {

    @RequestMapping("page")
    public String page(Model model, @RequestParam(value = "message", defaultValue = "message") String message) {
        model.addAttribute("message", message);
        return "mvc";
    }

    @RequestMapping("api")
    @ResponseBody
    public String api(@RequestParam(value = "message", defaultValue = "message") String message) {
        return message;
    }
}
