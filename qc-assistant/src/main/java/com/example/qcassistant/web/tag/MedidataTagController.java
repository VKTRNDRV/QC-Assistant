package com.example.qcassistant.web.tag;

import com.example.qcassistant.domain.dto.tag.TagAddDto;
import com.example.qcassistant.service.DestinationService;
import com.example.qcassistant.service.study.MedidataStudyService;
import com.example.qcassistant.service.tag.MedidataTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/medidata/tags")
public class MedidataTagController {

    private MedidataTagService tagService;
    private DestinationService destinationService;
    private MedidataStudyService studyService;

    @Autowired
    public MedidataTagController(MedidataTagService tagService,
                                 DestinationService destinationService,
                                 MedidataStudyService studyService) {
        this.tagService = tagService;
        this.destinationService = destinationService;
        this.studyService = studyService;
    }

    @GetMapping("/add")
    public ModelAndView getAddStudy(ModelAndView mav){
        mav.addObject("tagAddDto", new TagAddDto());
        addStudiesAndDestinations(mav);
        mav.setViewName("medidata-tags-add");
        return mav;
    }

    @PostMapping("/add")
    public ModelAndView postAddStudy(@ModelAttribute(name = "tagAddDto")
                                         TagAddDto tagAddDto,
                                     ModelAndView mav,
                                     RedirectAttributes redirectAttributes){
        try {
            this.tagService.addTag(tagAddDto);
        }catch (RuntimeException exc){
            mav.addObject("tagAddDto", tagAddDto);
            mav.addObject("error", true);
            mav.addObject("message", exc.getMessage());
            mav.setViewName("medidata-tags-add");
            return mav;
        }

        redirectAttributes.addFlashAttribute("success", true);
        mav.setViewName("redirect:/");
        return mav;
    }

    //@PutMapping for editing tags!!!!

    private ModelAndView addStudiesAndDestinations(ModelAndView modelAndView){
        modelAndView.addObject("destinations", this
                .destinationService.getTagDestinations());
        modelAndView.addObject("studies", this.studyService.getTagStudies());

        return modelAndView;
    }
}
