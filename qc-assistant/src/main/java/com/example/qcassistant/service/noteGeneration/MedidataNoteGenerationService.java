package com.example.qcassistant.service.noteGeneration;

import com.example.qcassistant.domain.dto.OrderNotesDto;
import com.example.qcassistant.domain.note.Note;
import com.example.qcassistant.domain.order.ClinicalOrder;
import com.example.qcassistant.domain.order.MedidataOrder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Qualifier("medidataNoteGenerationService")
public class MedidataNoteGenerationService extends NoteGenerationServiceAbstract{


    public OrderNotesDto generateNotes(MedidataOrder order) {
        OrderNotesDto notes = new OrderNotesDto();

        return notes;
    }
}
