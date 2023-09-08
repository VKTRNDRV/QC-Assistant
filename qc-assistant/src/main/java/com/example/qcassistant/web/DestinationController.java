package com.example.qcassistant.web;

import com.example.qcassistant.domain.dto.DestinationAddDto;
import com.example.qcassistant.domain.dto.DestinationEditDto;
import com.example.qcassistant.domain.dto.LanguageDto;
import com.example.qcassistant.service.DestinationService;
import com.example.qcassistant.service.LanguageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DestinationController {

    private LanguageService languageService;

    private DestinationService destinationService;

    public DestinationController(LanguageService languageService, DestinationService destinationService) {
        this.languageService = languageService;
        this.destinationService = destinationService;
    }


    @GetMapping("/destinations/add")
    public String getAddDestination(Model model){
        List<LanguageDto> languages = this.languageService.getAllLanguages();
        model.addAttribute("allLanguages", languages);
        model.addAttribute("destinationAddDto", new DestinationAddDto());
        return "destinations-add";
    }

    @PostMapping("/destinations/add")
    public String addDestination(@ModelAttribute DestinationAddDto destinationAddDto, Model model){
        try {
            this.destinationService.addDestination(destinationAddDto);
        }catch (RuntimeException exc){
            model.addAttribute("allLanguages", this.languageService.getAllLanguages());
            model.addAttribute("error", true);
            model.addAttribute("message", exc.getMessage());
            return "destinations-add";
        }
        return "redirect:/";
    }

    @GetMapping("/destinations")
    public String getAllDestinations(Model model){
        model.addAttribute("destinations",
                this.destinationService.displayDestinations());
        return "destinations-all";
    }


    @GetMapping("/destinations/edit/{id}")
    public String getEditDestination(@PathVariable Long id, Model model){
//        model.addAttribute("destinationUpdated", new DestinationEditDto());
        model.addAttribute("destinationUpdated",
                this.destinationService.getDestinationEditById(id));
        model.addAttribute("allLanguages",
                this.languageService.getAllLanguages());

        return "destination-edit";
    }

    @PostMapping("/destinations/edit")
    public String editDestination(@ModelAttribute DestinationEditDto editDto, Model model){
        try {
            this.destinationService.updateDestination(editDto);
        }catch (RuntimeException exc){
            model.addAttribute("allLanguages", this.languageService.getAllLanguages());
            model.addAttribute("error", true);
            model.addAttribute("message", exc.getMessage());
        }
        return "redirect:/";
    }
}
