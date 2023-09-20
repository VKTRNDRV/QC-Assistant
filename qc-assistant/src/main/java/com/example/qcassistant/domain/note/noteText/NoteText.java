package com.example.qcassistant.domain.note.noteText;

public class NoteText {

    public static final String SIM_TYPE_NOT_MATCHING = "Destination SIM Type does not match Order SIM type (confirm with Senior)";
    public static final String VERIFY_SIM_TYPE = "Verify Destination SIM Type (Unknown Destination)";



    public static final String VERIFY_AFW_CHARGERS = "Verify correct connector type for Android chargers";
    public static final String VERIFY_CHARGER_COUNT = "Verify correct count of chargers";
    public static final String VERIFY_PLUG_TYPE = "Verify chargers of plug type %s are present";



    public static final String VERIFY_HEADPHONES_PRESENT = "Verify Headphones present in bin";
    public static final String HEADPHONES_NOT_DETECTED = "Headphones not detected in order (study marked as requiring headphones)";
    public static final String VERIFY_STYLUS_PRESENT = "Verify Stylus(es) present in bin";
    public static final String STYLUSES_NOT_DETECTED = "Styluses not detected in order (study marked as requiring styluses)";



    public static final String VERIFY_SERIALS = "Verify actual devices' serials match serials on labels";
    public static final String VERIFY_DVC_MODELS = "Verify appropriate device models (Destination requires special device models)";
    public static final String CHECK_DESTINATION_FOR_SPECIAL_MODELS = "Check if models appropriate for destination (Unknown Destination)";
    public static final String VERIFY_UNUSED_DEVICES = "Verify devices are unused (Destination requires unused devices)";
    public static final String VERIFY_CE_MARKS = "Verify CE Marks visible/accessible on devices (Turkey)";
    public static final String VERIFY_BOX_SERIALS = "(If devices in boxes) Verify serials on boxes match their respective device";



    public static final String INCLUDE_ABBVIE_DOC = "Verify Device Decontamination Document is included (sponsor Abbvie)";
    public static final String VERIFY_WELCOME_LETTER = "Verify Welcome Letter fields are populated properly (if necessary at all)";
    public static final String SEPARATE_BUILD_DOCS = "Verify all devices have separate build docs";
    public static final String MULTIPLE_DOCS_REQ = "Multiple Documents requested";
    public static final String CHECK_FOR_TRANSLATED_DOCS = "Check Study folder for Translated Documents";
    public static final String STUDY_CONTAINS_TRANSLATED_DOCS = "Study marked as containing Translated Documents";
    public static final String CONFIRM_DOCS_PRINTED = "Confirm all requested documents are printed";
    public static final String CONFIRM_CORRECT_EDGE = "Verify all docs are printed on the correct edge (short/long)";
    public static final String CONFIRM_TRANSLATED_DOCS_LANG = "Confirm any translated docs are printed in the correct language";
    public static final String INCLUDE_PATIENT_CLOUD_INSERTS = "Verify P. Cloud Insert(s) included for every Site Device w/ the app installed";
    public static final String INCLUDE_RAVE_ECONSENT_INSERTS = "Verify Rave eConsent Insert(s) included for every Site Device w/ the app installed";
    public static final String PATIENT_CLOUD_INSERT_UNKNOWN = "If Patient Cloud present on Site Devices - include Insert(s)";
    public static final String RAVE_ECONSENT_INSERT_UNKNOWN = "If Rave eConsent present on Site Devices - include Insert(s)";


    public static final String CONFIRM_SITE_PATIENT_LABELS = "Confirm 'Site Use' on Site labels and 'Patient use' on Patient labels";
    public static final String CONFIRM_LABEL_DETAILS = "Verify Site Number (If present) and correct Destination on labels";
    public static final String VERIFY_PATIENT_IPAD_LABEL = "Verify Patient Label used on iPad Patient Device";
    public static final String CHECK_FOR_TRANSLATED_LABELS = "Check Study folder for translated labels";
    public static final String CONTAINS_TRANSLATED_LABELS = "Study contains translated labels";
    public static final String VERIFY_LABEL_TYPE = "(If transl. labels required) Verify correct label type used (Site/Patient)";
    public static final String CONFIRM_NO_PRINTING_ERRORS = "Verify no printing errors on the labels (e.g. text overflowing)";
}
