package com.example.qcassistant.integration;

import com.example.qcassistant.domain.dto.orderNotes.IqviaOrderNotesDto;
import com.example.qcassistant.domain.dto.orderNotes.MedidataOrderNotesDto;
import com.example.qcassistant.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistant.domain.entity.destination.Destination;
import com.example.qcassistant.domain.entity.sponsor.IqviaSponsor;
import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistant.domain.entity.study.IqviaStudy;
import com.example.qcassistant.domain.entity.study.MedidataStudy;
import com.example.qcassistant.domain.entity.study.environment.IqviaEnvironment;
import com.example.qcassistant.domain.entity.study.environment.MedidataEnvironment;
import com.example.qcassistant.domain.enums.item.PlugType;
import com.example.qcassistant.domain.enums.item.SimType;
import com.example.qcassistant.domain.note.Note;
import com.example.qcassistant.domain.note.noteText.NoteText;
import com.example.qcassistant.domain.order.IqviaOrder;
import com.example.qcassistant.domain.order.MedidataOrder;
import com.example.qcassistant.service.noteGeneration.IqviaNoteGenerationService;
import com.example.qcassistant.service.noteGeneration.MedidataNoteGenerationService;
import com.example.qcassistant.service.orderParse.IqviaOrderParseService;
import com.example.qcassistant.service.orderParse.MedidataOrderParseService;
import com.example.qcassistant.service.study.IqviaStudyService;
import com.example.qcassistant.service.study.MedidataStudyService;
import com.example.qcassistant.unit.orderParse.IqviaTestOrderInput;
import com.example.qcassistant.unit.orderParse.MedidataTestOrderInput;
import com.example.qcassistant.util.TrinaryBoolean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootTest
public class IqviaNoteGenerationTests {

    private IqviaOrderParseService orderParseService;

    private IqviaNoteGenerationService noteGenerationService;

    private IqviaStudyService studyService;

    private static Long UNKNOWN_STUDY_ID;

    private static Destination SPECIAL_REQUIREMENTS_DESTINATION;

    private static IqviaStudy SPECIAL_DOCS_STUDY;

    private static IqviaStudy SPECIAL_ENV_STUDY;

    @Autowired
    public IqviaNoteGenerationTests(IqviaOrderParseService orderParseService,
                                    IqviaNoteGenerationService noteGenerationService,
                                    IqviaStudyService studyService) {
        this.orderParseService = orderParseService;
        this.noteGenerationService = noteGenerationService;
        this.studyService = studyService;
    }

    @BeforeAll
    public static void setUp(){
        SPECIAL_REQUIREMENTS_DESTINATION = new Destination()
                .setName("Special Requirements Destination")
                .setLanguages(new ArrayList<>())
                .setPlugType(PlugType.G)
                .setSimType(SimType.SIMON_IOT)
                .setRequiresSpecialModels(TrinaryBoolean.TRUE)
                .setRequiresUnusedDevices(TrinaryBoolean.TRUE)
                .setRequiresInvoice(TrinaryBoolean.TRUE);

        IqviaSponsor placeholderSponsor = new IqviaSponsor();
        placeholderSponsor.setName("Placeholder Sponsor")
                .setAreStudyNamesSimilar(TrinaryBoolean.TRUE);

        IqviaEnvironment placeholderEnvironment = new IqviaEnvironment()
                .setSiteApps(new ArrayList<>())
                .setPatientApps(new ArrayList<>());

        placeholderEnvironment.setIsOsSeparated(TrinaryBoolean.FALSE)
                .setIsDestinationSeparated(TrinaryBoolean.FALSE)
                .setIsSitePatientSeparated(TrinaryBoolean.FALSE);

        SPECIAL_DOCS_STUDY = new IqviaStudy()
                .setSponsor(placeholderSponsor)
                .setEnvironment(placeholderEnvironment)
                .setContainsTranslatedLabels(TrinaryBoolean.TRUE)
                .setContainsTranslatedDocs(TrinaryBoolean.TRUE)
                .setContainsSepSitePatientLabels(TrinaryBoolean.TRUE)
                .setIsGsgPlain(TrinaryBoolean.FALSE);
        SPECIAL_DOCS_STUDY.setName("Special Docs Study").setId(UNKNOWN_STUDY_ID);

        IqviaEnvironment specialEnvironment = new IqviaEnvironment()
                .setSiteApps(new ArrayList<>())
                .setPatientApps(new ArrayList<>());

        specialEnvironment.setIsOsSeparated(TrinaryBoolean.TRUE)
                .setIsDestinationSeparated(TrinaryBoolean.TRUE)
                .setIsSitePatientSeparated(TrinaryBoolean.TRUE);

        SPECIAL_ENV_STUDY = new IqviaStudy()
                .setSponsor(placeholderSponsor)
                .setEnvironment(specialEnvironment)
                .setContainsTranslatedLabels(TrinaryBoolean.FALSE)
                .setContainsTranslatedDocs(TrinaryBoolean.FALSE)
                .setContainsSepSitePatientLabels(TrinaryBoolean.TRUE)
                .setIsGsgPlain(TrinaryBoolean.FALSE);
        SPECIAL_ENV_STUDY.setName("Special Env Study").setId(UNKNOWN_STUDY_ID);
    }

    @BeforeEach
    public void setStudyIDs(){
        UNKNOWN_STUDY_ID = this.studyService.getUnknownStudy().getId();
        SPECIAL_DOCS_STUDY.setId(UNKNOWN_STUDY_ID);
        SPECIAL_ENV_STUDY.setId(UNKNOWN_STUDY_ID);
    }

    @Test
    public void testDestinationNoteGeneration(){
        IqviaOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(IqviaTestOrderInput.IOS_DEVICES_ORDER));

        order.setDestination(SPECIAL_REQUIREMENTS_DESTINATION);

        IqviaOrderNotesDto notesFromService = this
                .noteGenerationService.generateNotes(order);

        List<String> expectedNotes = new ArrayList<>();
        expectedNotes.add(NoteText.CONFIRM_INVOICE_APPROVED);
        expectedNotes.add(String.format(NoteText.VERIFY_PLUG_TYPE,
                SPECIAL_REQUIREMENTS_DESTINATION.getPlugType()));
        expectedNotes.add(NoteText.VERIFY_DVC_MODELS);
        expectedNotes.add(NoteText.VERIFY_UNUSED_DEVICES);
        expectedNotes.add(NoteText.VERIFY_BOX_SERIALS);
        expectedNotes.add(NoteText.VERIFY_SERIALS);


        filterNotes(notesFromService.getShellCheckNotes(), expectedNotes);
        filterNotes(notesFromService.getDocumentationNotes(), expectedNotes);
        filterNotes(notesFromService.getAndroidNotes(), expectedNotes);
        filterNotes(notesFromService.getIosNotes(), expectedNotes);

        Assertions.assertEquals(0, expectedNotes.size(),
                String.join(", ", expectedNotes));
    }

    @Test
    public void testLabelDocsStudyNoteGeneration(){
        IqviaOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(MedidataTestOrderInput.HEADPHONES_STYLUSES_DOCUMENTS_ORDER));

        order.setStudy(SPECIAL_DOCS_STUDY);

        IqviaOrderNotesDto notesFromService = this
                .noteGenerationService.generateNotes(order);

        List<String> expectedNotesTexts = new ArrayList<>();
        expectedNotesTexts.add(NoteText.STUDY_CONTAINS_TRANSLATED_DOCS);
        expectedNotesTexts.add(NoteText.CONTAINS_TRANSLATED_LABELS);
        expectedNotesTexts.add(NoteText.VERIFY_LABEL_TYPE);
        expectedNotesTexts.add(NoteText.CONFIRM_NO_PRINTING_ERRORS);
        expectedNotesTexts.add(NoteText.CONFIRM_TRANSLATED_DOCS_LANG);
        expectedNotesTexts.add(NoteText.CONFIRM_LABEL_DETAILS);

        filterNotes(notesFromService.getShellCheckNotes(), expectedNotesTexts);
        filterNotes(notesFromService.getDocumentationNotes(), expectedNotesTexts);
        filterNotes(notesFromService.getAndroidNotes(), expectedNotesTexts);
        filterNotes(notesFromService.getIosNotes(), expectedNotesTexts);

        Assertions.assertEquals(0, expectedNotesTexts.size(),
                String.join(", ", expectedNotesTexts));
    }

    private void filterNotes(Collection<Note> actualNotes, List<String> expectedNoteTexts) {
        for (int i = 0; i < expectedNoteTexts.size(); i++) {
            String expectedNoteText = expectedNoteTexts.get(i);
            for(Note addedNote : actualNotes){
                if(addedNote.getText().equals(expectedNoteText)){
                    expectedNoteTexts.remove(i);
                    i--;
                    break;
                }
            }
        }
    }
}
