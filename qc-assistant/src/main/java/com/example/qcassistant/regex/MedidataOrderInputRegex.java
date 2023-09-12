package com.example.qcassistant.regex;

public class MedidataOrderInputRegex {

    public static final String CLIENT_VALIDATION_REGEX = "Organization Medidata";

    public static final String STUDY_REGEX = "Study\\s+(?<studyName>[a-zA-Z0-9]+)\\s+Site";
    public static final String STUDY_GROUP = "studyName";

    public static final String DOCUMENT_REGEX = "Medidata Custom Study Document 55982-DNI (?<copiesCount>[0-9]{1,3}).";
    public static final String DOC_COPIES_COUNT_GROUP = "copiesCount";
    public static final String DOC_SHORTNAME = "Document";
}
