package com.example.qcassistant.service.qc;

import com.example.qcassistant.domain.dto.orderNotes.IqviaOrderNotesDto;
import com.example.qcassistant.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistant.domain.order.IqviaOrder;
import com.example.qcassistant.service.orderParse.IqviaOrderParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IqviaQcService {

    private IqviaOrderParseService orderParseService;

    @Autowired
    public IqviaQcService(IqviaOrderParseService orderParseService) {
        this.orderParseService = orderParseService;
    }

    public IqviaOrderNotesDto generateOrderNotes(RawOrderInputDto inputDto){
        IqviaOrder order = this.orderParseService.parseOrder(inputDto);
        //this.noteGenerationService.generateNotes(order);

        return null;
    }
}
