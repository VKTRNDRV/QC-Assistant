package com.example.qcassistant.web.tag;

import com.example.qcassistant.service.tag.IqviaTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class IqviaTagController {

    private IqviaTagService tagService;

    @Autowired
    public IqviaTagController(IqviaTagService tagService) {
        this.tagService = tagService;
    }
}
