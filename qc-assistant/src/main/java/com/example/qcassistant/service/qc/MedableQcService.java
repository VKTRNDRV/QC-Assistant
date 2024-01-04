package com.example.qcassistant.service.qc;

import com.example.qcassistant.domain.dto.orderNotes.MedableOrderNotesDto;
import com.example.qcassistant.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistant.domain.order.MedableOrder;
import com.example.qcassistant.service.noteGeneration.MedableNoteGenerationService;
import com.example.qcassistant.service.orderParse.MedableOrderParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MedableQcService {

    private MedableOrderParseService orderParseService;

    private MedableNoteGenerationService noteGenerationService;

    private static int requestsCount = 0;
    private static final String FORMAT = "Medable: %d%n";

    @Autowired
    public MedableQcService(MedableOrderParseService orderParseService,
                            MedableNoteGenerationService noteGenerationService) {
        this.orderParseService = orderParseService;
        this.noteGenerationService = noteGenerationService;
    }

    public MedableOrderNotesDto generateOrderNotes(RawOrderInputDto rawOrderInputDto) {
        MedableOrder order = this.orderParseService.parseOrder(rawOrderInputDto);
        MedableOrderNotesDto notes = this.noteGenerationService.generateNotes(order);
        incrementOrderCount();
        return notes;
    }

    private synchronized void incrementOrderCount(){
        requestsCount++;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void printRequestsCount(){
        System.out.println(LocalDate.now());
        System.out.printf(String.format(FORMAT, requestsCount));
        requestsCount = 0;
    }
}
