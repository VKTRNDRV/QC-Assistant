package com.example.qcassistant.web.study;

import com.example.qcassistant.domain.dto.study.MedidataStudyAddDto;
import com.example.qcassistant.domain.dto.study.MedidataStudyEditDto;
import com.example.qcassistant.service.app.MedidataAppService;
import com.example.qcassistant.service.sponsor.MedidataSponsorService;
import com.example.qcassistant.service.study.MedidataStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("medidata/studies")
public class MedidataStudyController {

    private MedidataStudyService studyService;

    private MedidataSponsorService sponsorService;

    private MedidataAppService appService;


    @Autowired
    public MedidataStudyController(MedidataStudyService studyService, MedidataSponsorService sponsorService, MedidataAppService appService) {
        this.studyService = studyService;
        this.sponsorService = sponsorService;
        this.appService = appService;
    }


    @GetMapping("/add")
    public String getAddStudy(Model model){
        addSponsorsAndApps(model);
        model.addAttribute("studyAddDto",
                new MedidataStudyAddDto());

        return "medidata-studies-add";
    }

    @PostMapping("/add")
    public String addStudy(@ModelAttribute MedidataStudyAddDto studyAddDto,
                           Model model){
        try {
            this.studyService.addStudy(studyAddDto);
        }catch (RuntimeException exc){
            model.addAttribute("studyAddDto",
                    studyAddDto);
            addSponsorsAndApps(model);
                    this.appService.getAllEditApps();
            model.addAttribute("error", true);
            model.addAttribute("message", exc.getMessage());
            return "medidata-studies-add";
        }

        return "redirect:/";
    }

    @GetMapping
    public String getViewEditStudies(Model model){
        model.addAttribute("studies", this.studyService.displayAllStudies());
        return "medidata-studies";
    }

    @GetMapping("/edit/{id}")
    public String getEditStudy(@PathVariable Long id, Model model){
        addSponsorsAndApps(model);
        model.addAttribute("studyEditDto", this.studyService.getStudyEditById(id));
        return "medidata-studies-edit";
    }

    @PostMapping("/edit")
    public String editStudy(@ModelAttribute MedidataStudyEditDto studyEditDto, Model model){
        try {
            this.studyService.editStudy(studyEditDto);
        }catch (RuntimeException exc){
            model.addAttribute("studyEditDto", studyEditDto);
            addSponsorsAndApps(model);
            model.addAttribute("error", true);
            model.addAttribute("message", exc.getMessage());
            return "medidata-studies-edit";
        }

        return "redirect:/";
    }

    private Model addSponsorsAndApps(Model model) {
        model.addAttribute("sponsors",
                this.sponsorService.displayAllSponsors());
        model.addAttribute("apps",
                this.appService.getAllEditApps());

        return model;
    }
}
