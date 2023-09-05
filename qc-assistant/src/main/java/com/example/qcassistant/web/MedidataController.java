package com.example.qcassistant.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medidata")
public class MedidataController {

    @GetMapping("/add-study")
    public String getAddStudy(){
        return "medidata-add-study";
    }
}
