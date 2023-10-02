package com.example.qcassistant.service.qc;

import com.example.qcassistant.domain.dto.orderNotes.MedableOrderNotesDto;
import com.example.qcassistant.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistant.domain.order.MedableOrder;
import com.example.qcassistant.service.noteGeneration.MedableNoteGenerationService;
import com.example.qcassistant.service.orderParse.MedableOrderParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedableQcService {

    private MedableOrderParseService orderParseService;

    private MedableNoteGenerationService noteGenerationService;

    @Autowired
    public MedableQcService(MedableOrderParseService orderParseService, MedableNoteGenerationService noteGenerationService) {
        this.orderParseService = orderParseService;
        this.noteGenerationService = noteGenerationService;
    }

    public MedableOrderNotesDto generateOrderNotes(RawOrderInputDto rawOrderInputDto) {
        MedableOrder order = this.orderParseService.parseOrder(rawOrderInputDto);
        MedableOrderNotesDto notes = this.noteGenerationService.generateNotes(order);

        return notes;
    }
}
