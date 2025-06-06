package com.tfg.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("index")
    public String index2() {
        return "index";
    }

    @GetMapping("/ir/upload")
    public String irUpload(ModelMap model) {
        model.addAttribute("file", "");
        return "upload_file";
    }
}
