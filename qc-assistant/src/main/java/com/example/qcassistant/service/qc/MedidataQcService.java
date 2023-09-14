package com.example.qcassistant.service.qc;

import com.example.qcassistant.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistant.domain.order.MedidataOrder;
import com.example.qcassistant.service.orderParse.MedidataOrderParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedidataQcService {

    private MedidataOrderParseService orderParseService;

    @Autowired
    public MedidataQcService(MedidataOrderParseService orderParseService) {
        this.orderParseService = orderParseService;
    }

    public Object generateOrderNotes(RawOrderInputDto inputDto){
        MedidataOrder order = this.orderParseService.parseOrder(inputDto);

        return null;
    }
}
