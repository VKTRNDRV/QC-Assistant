package com.example.qcassistant.web.sponsor;

import com.example.qcassistant.domain.dto.sponsor.sponsorAddDto;
import com.example.qcassistant.domain.dto.sponsor.sponsorEditDto;
import com.example.qcassistant.service.sponsor.MedableSponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/medable/sponsors")
public class MedableSponsorController {

    private MedableSponsorService sponsorService;

    @Autowired
    public MedableSponsorController(MedableSponsorService sponsorService) {
        this.sponsorService = sponsorService;
    }

    @GetMapping
    public String getAllSponsors(Model model){
        model.addAttribute("sponsors", this.sponsorService.displayAllSponsors());
        return "medable-sponsors";
    }

    @GetMapping("/add")
    public String getAddSponsor(Model model){
        model.addAttribute("sponsorAddDto",
                new sponsorAddDto());

        return "medable-sponsors-add";
    }

    @PostMapping("/add")
    public String addSponsor(@ModelAttribute sponsorAddDto sponsorAddDto,
                             Model model, RedirectAttributes redirectAttributes){
        try {
            this.sponsorService.addSponsor(sponsorAddDto);
        }catch (RuntimeException exc){
            model.addAttribute("sponsorAddDto", sponsorAddDto);
            model.addAttribute("error", true);
            model.addAttribute("message", exc.getMessage());
            return "medable-sponsors-add";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String getEditSponsor(@PathVariable Long id, Model model){
        model.addAttribute("sponsorEditDto",
                this.sponsorService.getSponsorEditById(id));
        return "medable-sponsors-edit";
    }

    @PostMapping("/edit")
    public String editSponsor(@ModelAttribute sponsorEditDto sponsorEditDto,
                              Model model, RedirectAttributes redirectAttributes){
        try {
            this.sponsorService.editSponsor(sponsorEditDto);
        }catch (RuntimeException exc){
            model.addAttribute("sponsorEditDto", sponsorEditDto);
            model.addAttribute("error", true);
            model.addAttribute("message", exc.getMessage());
            return "medable-sponsors-edit";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }
}
