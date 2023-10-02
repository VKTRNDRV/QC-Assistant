package com.example.qcassistant.service.qc;

import com.example.qcassistant.domain.dto.orderNotes.IqviaOrderNotesDto;
import com.example.qcassistant.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistant.domain.order.IqviaOrder;
import com.example.qcassistant.service.noteGeneration.IqviaNoteGenerationService;
import com.example.qcassistant.service.orderParse.IqviaOrderParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IqviaQcService {

    private IqviaOrderParseService orderParseService;

    private IqviaNoteGenerationService noteGenerationService;

    @Autowired
    public IqviaQcService(IqviaOrderParseService orderParseService, IqviaNoteGenerationService noteGenerationService) {
        this.orderParseService = orderParseService;
        this.noteGenerationService = noteGenerationService;
    }

    public IqviaOrderNotesDto generateOrderNotes(RawOrderInputDto inputDto){
        IqviaOrder order = this.orderParseService.parseOrder(inputDto);
        IqviaOrderNotesDto notes = this.noteGenerationService.generateNotes(order);

        return notes;
    }
}
