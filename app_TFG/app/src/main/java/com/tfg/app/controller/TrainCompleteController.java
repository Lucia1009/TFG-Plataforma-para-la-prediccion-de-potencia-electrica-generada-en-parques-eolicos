package com.tfg.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrainCompleteController {

    @GetMapping("/train_complete")
    public String trainComplete(ModelMap modelMap) {
        modelMap.addAttribute("message", "Training Complete");
        return "train_complete";
    }
}
