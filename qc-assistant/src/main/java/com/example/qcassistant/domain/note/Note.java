package com.example.qcassistant.domain.note;

import com.example.qcassistant.domain.enums.Severity;

public class Note {

    private Severity severity;
    private String text;



    public Severity getSeverity() {
        return severity;
    }

    public Note setSeverity(Severity severity) {
        this.severity = severity;
        return this;
    }

    public String getText() {
        return text;
    }

    public Note setText(String text) {
        this.text = text;
        return this;
    }
}
