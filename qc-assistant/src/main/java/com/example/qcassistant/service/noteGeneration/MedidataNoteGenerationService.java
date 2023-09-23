package com.example.qcassistant.service.noteGeneration;

import com.example.qcassistant.domain.dto.OrderNotesDto;
import com.example.qcassistant.domain.entity.app.MedidataApp;
import com.example.qcassistant.domain.entity.destination.Destination;
import com.example.qcassistant.domain.entity.study.MedidataStudy;
import com.example.qcassistant.domain.entity.study.environment.MedidataEnvironment;
import com.example.qcassistant.domain.enums.OrderType;
import com.example.qcassistant.domain.enums.Severity;
import com.example.qcassistant.domain.enums.item.PlugType;
import com.example.qcassistant.domain.enums.item.SimType;
import com.example.qcassistant.domain.item.accessory.MedidataAccessory;
import com.example.qcassistant.domain.item.device.ios.ipad.MedidataIPad;
import com.example.qcassistant.domain.note.Note;
import com.example.qcassistant.domain.note.noteText.NoteText;
import com.example.qcassistant.domain.order.AccessoryRepository;
import com.example.qcassistant.domain.order.MedidataOrder;
import com.example.qcassistant.util.TrinaryBoolean;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MedidataNoteGenerationService extends NoteGenerationService {

    private static final int LARGE_ORDER_COUNT = 10;
    private static final String ISRAEL = "Israel";
    private static final String TURKEY = "Turkey";
    private static final String ABBVIE = "Abbvie";

    @Autowired
    public MedidataNoteGenerationService(ModelMapper modelMapper) {
        super(modelMapper);
    }

    public OrderNotesDto generateNotes(MedidataOrder order) {
        OrderNotesDto notes = new OrderNotesDto();
        notes.setItems(super.mapDevices(order))
                .setShellCheckNotes(this.genShellCheckNotes(order))
                .setDocumentationNotes(this.genDocumentationNotes(order));

        if(order.containsIosDevices()){
            notes.setIosNotes(this.genIosNotes(order));
        }
        return notes;
    }

    private Collection<Note> genShellCheckNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        notes.addAll(this.genShellCheckAccessoryNotes(order));
        notes.addAll(this.genShellCheckDeviceNotes(order));
        return notes;
    }

    private Collection<Note> genDocumentationNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        notes.addAll(this.genDocsNotes(order));
        notes.addAll(this.genLabelsNotes(order));
        notes.addAll(super.genCommentNotes(order));
        return notes;
    }

    private Collection<Note> genIosNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        notes.addAll(this.genIosDeviceNotes(order));

        return notes;
    }

    private Collection<? extends Note> genIosDeviceNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        MedidataStudy study = order.getStudy();

        notes.addAll(this.genHubLoggingNotes(order));
        notes.addAll(this.genLanguageNotes(order));

        if(order.getOrderType().equals(OrderType.PROD)){
            notes.addAll(this.genIosAppNotes(order));
        }else{
            notes.add(new Note(Severity.MEDIUM, NoteText.UAT_CHECK_APPS));
        }

        notes.addAll(this.genAirWatchNotes(order));

        return notes;
    }

    private Collection<Note> genAirWatchNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        MedidataEnvironment environment = order.getStudy().getEnvironment();

        notes.add(new Note(Severity.LOW, NoteText.VERIFY_NO_DECOM_TAG));

        if(!order.getSponsor().getAreStudyNamesSimilar().equals(TrinaryBoolean.FALSE)){
            notes.add(new Note(Severity.MEDIUM, NoteText.CAREFUL_SIMILAR_STUDY_NAMES));
        }

        if(order.getOrderType().equals(OrderType.PROD)){
            if(environment.getIsLegacy().equals(TrinaryBoolean.TRUE)){
                notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_RETROFIT_TAG));
                if(order.getSimType().equals(SimType.NONE)){
                    notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_LEGACY_APN_TAG));
                }
            }

            TrinaryBoolean isSitePatientSeparated = environment.getIsSitePatientSeparated();
            TrinaryBoolean isDestinationSeparated = environment.getIsDestinationSeparated();
            if(!(isDestinationSeparated.equals(TrinaryBoolean.FALSE) &&
                    isSitePatientSeparated.equals(TrinaryBoolean.FALSE))){

                notes.add(new Note(Severity.MEDIUM, NoteText.CONFIRM_APPROPRIATE_GROUP));

                if(isSitePatientSeparated.equals(TrinaryBoolean.TRUE)){
                    notes.add(new Note(Severity.MEDIUM, NoteText.IS_SITE_PATIENT_SEPARATED));
                }

                if(isDestinationSeparated.equals(TrinaryBoolean.TRUE)){
                    notes.add(new Note(Severity.MEDIUM, NoteText.IS_DESTINATION_SEPARATED));
                }
            }
        }

        return notes;
    }

    private Collection<Note> genIosAppNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        MedidataStudy study = order.getStudy();

        if(order.containsSiteDevices()){
            List<MedidataApp> siteApps = study.getEnvironment().getSiteApps();
            if(siteApps.size() > 1){
                notes.add(new Note(Severity.MEDIUM, String.format(
                        NoteText.CONFIRM_SITE_APPS_INSTALLED,
                        super.getAppNamesList(siteApps))));
            }

            if(study.containsSiteApp(MedidataApp.PATIENT_CLOUD)){
                notes.add(new Note(Severity.LOW, NoteText.VERIFY_P_CLOUD_MULTIUSER_MODE));
            }
        }

        if(order.containsPatientDevices()){
            List<MedidataApp> patientApps = study.getEnvironment().getPatientApps();
            if(patientApps.size() > 1){
                notes.add(new Note(Severity.MEDIUM, String.format(
                        NoteText.CONFIRM_PATIENT_APPS_INSTALLED,
                        super.getAppNamesList(patientApps))));
            }

            if(study.containsPatientApp(MedidataApp.PATIENT_CLOUD)){
                notes.add(new Note(Severity.LOW, NoteText.VERIFY_P_CLOUD_UPDATED));

                if(study.getIsPatientDeviceIpad().equals(TrinaryBoolean.TRUE) || order
                        .getDeviceRepository().containsDevice(MedidataIPad.MINI.getShortName())){
                    notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_P_CLOUD_SINGLEUSER_MODE_TABLET));
                }
            }
        }

        notes.add(new Note(Severity.LOW, NoteText.VERIFY_APPS_GREEN_CHECK));

        return notes;
    }

    private Collection<Note> genLanguageNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();

        if(order.getDestination().isUnknown()){
            notes.add(new Note(Severity.MEDIUM, NoteText.UNKNOWN_DESTINATION));
        }

        if(order.areNoLanguagesDetected()){
            notes.add(new Note(Severity.MEDIUM, NoteText.NO_LANGUAGES_DETECTED));
            return notes;
        }

        if(order.isEnglishRequested()){
            Note note = new Note(Severity.MEDIUM, NoteText.ENGLISH_REQUESTED);
            if(order.getDestination().isEnglishSpeaking()){
                note.setSeverity(Severity.LOW);
            }
            notes.add(note);

        }else{
            if(order.areMultipleLanguagesRequested()){
                notes.add(new Note(Severity.MEDIUM, NoteText.MULTIPLE_LANGS_REQUESTED));
            }else {
                notes.add(new Note(Severity.LOW, NoteText.CHANGE_DEVICE_LANGUAGE));
            }

            if(order.requestsUnusualLanguages()){
                notes.add(new Note(Severity.HIGH, NoteText.UNUSUAL_LANGUAGES_REQUESTED));
            }
        }

        return notes;
    }

    private Collection<Note> genHubLoggingNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        switch (order.getSimType()) {
            case VF_GLOBAL:
                notes.add(new Note(Severity.LOW, NoteText.HUB_LOG_VF_GLOBAL));
                if(!order.getStudy().getEnvironment().getIsLegacy().equals(TrinaryBoolean.TRUE)){
                    notes.add(new Note(Severity.LOW, NoteText.VERIFY_VFGO_APN));
                }
                break;
            case SIMON_IOT:
                notes.add(new Note(Severity.MEDIUM, NoteText.HUB_LOG_SIMON_IOT));
                if(!order.getStudy().getEnvironment().getIsLegacy().equals(TrinaryBoolean.TRUE)){
                    notes.add(new Note(Severity.LOW, NoteText.VERIFY_SIMON_APN));
                }
            case NONE:
                if (order.getDestination().getName().equals(Destination.EGYPT)) {
                    notes.add(new Note(Severity.MEDIUM, NoteText.EGYPT_NO_SIM_HUB_LOG));
                } else {
                    notes.add(new Note(Severity.MEDIUM, NoteText.HUB_LOG_NO_SIM));
                }
                break;
        }

        return notes;
    }


    private Collection<Note> genLabelsNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        MedidataStudy study = order.getStudy();

        notes.add(new Note(Severity.LOW, NoteText.CONFIRM_LABEL_DETAILS));
        notes.add(new Note(Severity.LOW, NoteText.CONFIRM_NO_PRINTING_ERRORS));


        if(order.containsSiteDevices() && order.containsPatientDevices()){
            notes.add(new Note(Severity.LOW, NoteText.CONFIRM_SITE_PATIENT_LABELS));
        }

        if(study.getIsPatientDeviceIpad().equals(TrinaryBoolean.TRUE) &&
                order.containsPatientDevices()){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_PATIENT_IPAD_LABEL));
        }

        if(!study.getContainsTranslatedLabels().equals(
                TrinaryBoolean.FALSE) && !order.isEnglishRequested()){
            notes.addAll(this.genTranslatedLabelsNotes(order));
        }

        return notes;
    }

    private Collection<Note> genTranslatedLabelsNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        notes.add(new Note(Severity.LOW, NoteText.VERIFY_LABEL_TYPE));

        switch (order.getStudy().getContainsTranslatedLabels()){
            case UNKNOWN:
                notes.add(new Note(Severity.MEDIUM, NoteText.CHECK_FOR_TRANSLATED_LABELS));
                break;
            case TRUE:
                notes.add(new Note(Severity.MEDIUM, NoteText.CONTAINS_TRANSLATED_LABELS));
                break;
        }

        return notes;
    }

    private Collection<Note> genDocsNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();

        notes.add(new Note(Severity.LOW, NoteText.SEPARATE_BUILD_DOCS));

        if(order.getSponsor().getName().equals(ABBVIE)){
            notes.add(new Note(Severity.MEDIUM, NoteText.INCLUDE_ABBVIE_DOC));
        }

        if(!order.getStudy().getContainsEditableWelcomeLetter()
                .equals(TrinaryBoolean.FALSE)){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_WELCOME_LETTER));
        }

        if(order.getDocumentRepository()
                .areMultipleCopiesRequested()){
            notes.add(new Note(Severity.MEDIUM, NoteText.MULTIPLE_DOCS_REQ));
        }

        if(order.getDocumentRepository().areUserGuidesRequested()){
            notes.add(new Note(Severity.LOW, NoteText.CONFIRM_DOCS_PRINTED));
            notes.add(new Note(Severity.LOW, NoteText.CONFIRM_CORRECT_EDGE));
            if(!order.isEnglishRequested()){
                notes.addAll(this.genTranslatedDocNotes(order));
            }
        }

        if(order.containsSiteDevices()){
            notes.addAll(this.genCartonInsertsNotes(order));
        }

        return notes;
    }

    private Collection<Note> genCartonInsertsNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        MedidataStudy study = order.getStudy();
        if(study.isUnknown()){
            notes.add(new Note(Severity.MEDIUM, NoteText.PATIENT_CLOUD_INSERT_UNKNOWN));
            notes.add(new Note(Severity.LOW, NoteText.RAVE_ECONSENT_INSERT_UNKNOWN));
        }else{
            if(study.containsSiteApp(MedidataApp.PATIENT_CLOUD)){
                notes.add(new Note(Severity.MEDIUM, NoteText.INCLUDE_PATIENT_CLOUD_INSERTS));
            }

            if(study.containsSiteApp(MedidataApp.RAVE_ECONSENT)){
                notes.add(new Note(Severity.MEDIUM, NoteText.INCLUDE_RAVE_ECONSENT_INSERTS));
            }
        }

        return notes;
    }

    private Collection<Note> genTranslatedDocNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        switch (order.getStudy().getContainsTranslatedDocs()){
            case UNKNOWN:
                notes.add(new Note(Severity.MEDIUM, NoteText.CHECK_FOR_TRANSLATED_DOCS));
                break;
            case TRUE:
                notes.add(new Note(Severity.MEDIUM, NoteText.STUDY_CONTAINS_TRANSLATED_DOCS));
                notes.add(new Note(Severity.LOW, NoteText.CONFIRM_TRANSLATED_DOCS_LANG));
                break;
            default:
                break;
        }

        return notes;
    }

    private Collection<Note> genShellCheckDeviceNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        notes.addAll(this.genSpecialDeviceReqNotes(order));
        return notes;
    }

    private Collection<Note> genSpecialDeviceReqNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        notes.add(new Note(Severity.LOW, NoteText.VERIFY_SERIALS));
        Destination destination = order.getDestination();

        switch (destination.getRequiresSpecialModels()){
            case TRUE: notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_DVC_MODELS));
                break;
            case UNKNOWN: notes.add(new Note(Severity.MEDIUM, NoteText.CHECK_DESTINATION_FOR_SPECIAL_MODELS));
                break;
            default:
                break;
        }

        if(destination.getRequiresUnusedDevices()
                .equals(TrinaryBoolean.TRUE)){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_UNUSED_DEVICES));
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_BOX_SERIALS));
        }

        if(destination.getName().equals(TURKEY)){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_CE_MARKS));
        }

        return notes;
    }

    private Collection<Note> genShellCheckAccessoryNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        Destination destination = order.getDestination();

        if(order.getDeviceRepository().count() > LARGE_ORDER_COUNT &&
                !destination.getName().equals(ISRAEL)){
            notes.add(new Note(Severity.LOW, NoteText.VERIFY_CHARGER_COUNT));
        }

        if(!destination.getPlugType().equals(PlugType.C)){
            notes.add(new Note(Severity.MEDIUM, String.format(
                    NoteText.VERIFY_PLUG_TYPE, destination.getPlugType().name())));
        }

        if(!destination.isUnknown()){
            if(!destination.getSimType()
                    .equals(order.getSimType())){
                notes.add(new Note(Severity.HIGH, NoteText.SIM_TYPE_NOT_MATCHING));
            }
        }else{
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_SIM_TYPE));
        }


        if(order.getDeviceRepository()
                .containsAndroidDevices()){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_AFW_CHARGERS));
        }

        if(order.containsSiteDevices()){
            notes.addAll(this.genStylusHeadphonesNotes(order));

            if(order.getStudy().getIsPatientDeviceIpad()
                    .equals(TrinaryBoolean.TRUE)){
                notes.add(new Note(Severity.LOW, NoteText.IPAD_PATIENT_DEVICE_CASE));
            }
        }

        return notes;
    }

    private Collection<Note> genStylusHeadphonesNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        AccessoryRepository accessories = order.getAccessoryRepository();

        if(accessories.containsAccessory(
                MedidataAccessory.HEADPHONES.getShortName())){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_HEADPHONES_PRESENT));
        }
        if(accessories.containsAccessory(
                MedidataAccessory.STYLUS.getShortName())){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_STYLUS_PRESENT));
        }

        switch (order.getStudy().getIncludesHeadphonesStyluses()){
            case TRUE:
                notes.add(new Note(Severity.LOW, NoteText.STUDY_CONTAINS_HEADPH_STYLUSES));
                if(!accessories.containsAccessory(
                        MedidataAccessory.HEADPHONES.getShortName())){
                    notes.add(new Note(Severity.HIGH, NoteText.HEADPHONES_NOT_DETECTED));
                }

                if(!accessories.containsAccessory(
                        MedidataAccessory.STYLUS.getShortName())){
                    notes.add(new Note(Severity.HIGH, NoteText.STYLUSES_NOT_DETECTED));
                }
                break;

            case UNKNOWN:
                notes.add(new Note(Severity.LOW, NoteText.CONFIRM_IF_HEADPH_STYLUSES_NEEDED));
                break;

            default:
                break;
        }

        return notes;
    }
}
