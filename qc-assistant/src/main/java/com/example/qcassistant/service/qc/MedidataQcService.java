package com.example.qcassistant.service.qc;

import com.example.qcassistant.domain.dto.OrderNotesDto;
import com.example.qcassistant.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistant.domain.order.MedidataOrder;
import com.example.qcassistant.service.noteGeneration.MedidataNoteGenerationService;
import com.example.qcassistant.service.orderParse.MedidataOrderParseService;
import jakarta.annotation.PostConstruct;
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

    public OrderNotesDto generateOrderNotes(RawOrderInputDto inputDto){
        MedidataOrder order = this.orderParseService.parseOrder(inputDto);
        OrderNotesDto notes = this.noteGenerationService.generateNotes(order);
        return notes;
    }
}
