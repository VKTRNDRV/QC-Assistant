package com.example.qcassistant.service.qc;

import com.example.qcassistant.domain.dto.orderNotes.IqviaOrderNotesDto;
import com.example.qcassistant.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistant.domain.order.IqviaOrder;
import com.example.qcassistant.service.noteGeneration.IqviaNoteGenerationService;
import com.example.qcassistant.service.orderParse.IqviaOrderParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class IqviaQcService {

    private IqviaOrderParseService orderParseService;

    private IqviaNoteGenerationService noteGenerationService;

    private int requestsCount;
    private static final String FORMAT = "IQVIA: %d%n";

    @Autowired
    public IqviaQcService(IqviaOrderParseService orderParseService,
                          IqviaNoteGenerationService noteGenerationService) {
        this.orderParseService = orderParseService;
        this.noteGenerationService = noteGenerationService;
        this.requestsCount = 0;
    }

    public IqviaOrderNotesDto generateOrderNotes(RawOrderInputDto inputDto){
        IqviaOrder order = this.orderParseService.parseOrder(inputDto);
        IqviaOrderNotesDto notes = this.noteGenerationService.generateNotes(order);
        requestsCount++;
        return notes;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void printRequestsCount(){
        System.out.println(LocalDate.now());
        System.out.printf(String.format(FORMAT, requestsCount));
        requestsCount = 0;
    }
}
