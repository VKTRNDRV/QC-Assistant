package com.example.qcassistant.service.qc;

import com.example.qcassistant.domain.dto.orderNotes.MedidataOrderNotesDto;
import com.example.qcassistant.domain.dto.orderNotes.OrderNotesDto;
import com.example.qcassistant.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistant.domain.order.MedidataOrder;
import com.example.qcassistant.service.noteGeneration.MedidataNoteGenerationService;
import com.example.qcassistant.service.orderParse.MedidataOrderParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedidataQcService {

    private MedidataOrderParseService orderParseService;
    private MedidataNoteGenerationService noteGenerationService;

    @Autowired
    public MedidataQcService(MedidataOrderParseService orderParseService,
                             MedidataNoteGenerationService noteGenerationService) {
        this.orderParseService = orderParseService;
        this.noteGenerationService = noteGenerationService;
    }

    public MedidataOrderNotesDto generateOrderNotes(RawOrderInputDto inputDto){
        MedidataOrder order = this.orderParseService.parseOrder(inputDto);
        MedidataOrderNotesDto notes = this.noteGenerationService.generateNotes(order);
        return notes;
    }
}
