package com.example.qcassistant.service.qc;

import com.example.qcassistant.domain.dto.orderNotes.MedableOrderNotesDto;
import com.example.qcassistant.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistant.domain.order.MedableOrder;
import com.example.qcassistant.service.orderParse.MedableOrderParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedableQcService {

    private MedableOrderParseService orderParseService;

    @Autowired
    public MedableQcService(MedableOrderParseService orderParseService) {
        this.orderParseService = orderParseService;
    }

    public MedableOrderNotesDto generateOrderNotes(RawOrderInputDto rawOrderInputDto) {
        MedableOrder order = this.orderParseService.parseOrder(rawOrderInputDto);
        //this.noteGenerationService.generateNotes(order);

        return null;
    }
}
