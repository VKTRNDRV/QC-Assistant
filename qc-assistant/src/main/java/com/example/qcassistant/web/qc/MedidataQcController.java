package com.example.qcassistant.web.qc;

import com.example.qcassistant.domain.dto.OrderNotesDto;
import com.example.qcassistant.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistant.exception.OrderParsingException;
import com.example.qcassistant.service.qc.MedidataQcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medidata/qc")
public class MedidataQcController {

    private MedidataQcService qcService;

    @Autowired
    public MedidataQcController(MedidataQcService qcService) {
        this.qcService = qcService;
    }

    @GetMapping
    public String getGenerateQcNotes(Model model){
        model.addAttribute("rawOrderInput",
                new RawOrderInputDto());
        return "medidata-qc";
    }

    @PostMapping
    public String generateQcNotes(@ModelAttribute RawOrderInputDto rawOrderInputDto,
                                  Model model){
        try {
            OrderNotesDto notes = this.qcService.generateOrderNotes(rawOrderInputDto);
            model.addAttribute("notes", notes);
        }catch (OrderParsingException exception){
            model.addAttribute("rawOrderInput", rawOrderInputDto);
            model.addAttribute("error", true);
            model.addAttribute("message", exception.getMessage());
            return "medidata-qc";
        }


        return "medidata-qc-notes";
    }
}
