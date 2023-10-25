package com.example.qcassistant.web.tag;

import com.example.qcassistant.domain.dto.tag.TagAddDto;
import com.example.qcassistant.service.DestinationService;
import com.example.qcassistant.service.study.MedableStudyService;
import com.example.qcassistant.service.tag.MedableTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/medable/tags")
public class MedableTagController {

    private MedableTagService tagService;

    private DestinationService destinationService;

    private MedableStudyService studyService;

    @Autowired
    public MedableTagController(MedableTagService tagService, DestinationService destinationService, MedableStudyService studyService) {
        this.tagService = tagService;
        this.destinationService = destinationService;
        this.studyService = studyService;
    }

    @GetMapping("/add")
    public ModelAndView getAddStudy(ModelAndView mav){
        mav.addObject("tagAddDto", new TagAddDto());
        addStudiesAndDestinations(mav);
        mav.setViewName("medable-tags-add");
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
            mav.addObject("message", exc.getMessage());
            mav.addObject("error", true);
            mav.addObject("tagAddDto", tagAddDto);
            mav.setViewName("medable-tags-add");
            return mav;
        }

        redirectAttributes.addFlashAttribute("success", true);
        mav.setViewName("redirect:/");
        return mav;
    }

    private ModelAndView addStudiesAndDestinations(ModelAndView modelAndView){
        modelAndView.addObject("destinations", this
                .destinationService.getTagDestinations());
        modelAndView.addObject("studies", this.studyService.getTagStudies());

        return modelAndView;
    }
}
