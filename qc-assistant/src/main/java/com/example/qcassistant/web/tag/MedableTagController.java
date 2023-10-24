package com.example.qcassistant.web.tag;

import com.example.qcassistant.service.tag.MedableTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MedableTagController {

    private MedableTagService tagService;

    @Autowired
    public MedableTagController(MedableTagService tagService) {
        this.tagService = tagService;
    }
}
