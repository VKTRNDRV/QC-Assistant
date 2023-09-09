package com.example.qcassistant.web.medidata;

import com.example.qcassistant.domain.dto.app.MedidataAppAddDto;
import com.example.qcassistant.domain.dto.app.MedidataAppEditDto;
import com.example.qcassistant.service.app.MedidataAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/medidata/apps")
public class MedidataAppController {

    private MedidataAppService appService;

    @Autowired
    public MedidataAppController(MedidataAppService appService) {
        this.appService = appService;
    }

    @GetMapping
    public String getApps(Model model){
        model.addAttribute("apps",
                this.appService.getAllEditApps());
        return "medidata-apps";
    }

    @GetMapping("/add")
    public String getAddApp(Model model){
        model.addAttribute("medidataAppAddDto",
                new MedidataAppAddDto());
        return "medidata-apps-add";
    }


    @PostMapping("/add")
    public String addApp(@ModelAttribute MedidataAppAddDto appAddDto,
                         Model model){
        try {
            this.appService.addApp(appAddDto);
        }catch (RuntimeException exception){
            model.addAttribute("error", true);
            model.addAttribute("message", exception.getMessage());
            return "medidata-apps-add";
        }

        return "redirect:/";
    }


    @GetMapping("/edit/{id}")
    public String getEditApp(@PathVariable Long id, Model model){
        model.addAttribute("medidataAppEditDto",
                this.appService.getEditAppById(id));
        return "medidata-apps-edit";
    }

    @PostMapping("/edit")
    public String editApp(@ModelAttribute MedidataAppEditDto editDto,
                          Model model){
        try {
            this.appService.editApp(editDto);
        }catch (RuntimeException exc){
            model.addAttribute("error", true);
            model.addAttribute("message", exc.getMessage());
            return "medidata-apps-edit";
        }

        return "redirect:/";
    }
}
