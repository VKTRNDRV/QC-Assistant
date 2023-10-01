package com.example.qcassistant.regex;

public class MedidataOrderInputRegex {

    public static final String CLIENT_VALIDATION_REGEX = "Organization\\s*Medidata";


    public static final String MULTI_USER = "Multi-User";
    public static final String SINGLE_USER = "Single-User";

    public static final String DOCUMENT_REGEX = "Medidata\\s*Custom\\s*Study\\s*Document\\s*55982-DNI\\s*(?<copiesCount>[0-9]{1,3}).";
    public static final String DOC_COPIES_COUNT_GROUP = "copiesCount";
    public static final String DOC_SHORTNAME = "Document";

    public static final String WELCOME_LETTER_REGEX = "Medidata\\s*Custom\\s*Study\\s*Welcome\\s*Letter\\s*Document";
    public static final String WELCOME_LETTER_SHORTNAME = "Welcome Letter";
}
