package com.example.qcassistant.domain.item.device.ios.iphone;

import com.example.qcassistant.domain.enums.ConnectorType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MedidataIPhone {

    IPHONE_8("Medidata\\s*Apple\\s*iPhone\\s*8.{0,16}\\s*Shell\\s*(?<serial>[A-Z0-9]{12})",
            "iPhone 8",
            ConnectorType.LIGHTNING),
    IPHONE_SE_2ND_GEN("Medidata\\s*Apple\\s*iPhone SE.{0,16}\\s*2nd\\s*Gen\\s*Shell\\s*(?<serial>[A-Z0-9]{12})",
            "iPhone SE 2nd Gen",
            ConnectorType.LIGHTNING);

    private String regexPattern;
    private String shortName;
    private ConnectorType connectorType;

    public static final String SERIAL_GROUP_NAME = "serial";

    MedidataIPhone(String regexPattern, String shortName, ConnectorType connectorType) {
        this.regexPattern = regexPattern;
        this.shortName = shortName;
        this.connectorType = connectorType;
    }

    public String getRegexPattern() {
        return regexPattern;
    }

    public String getShortName() {
        return shortName;
    }

    public ConnectorType getConnectorType() {
        return connectorType;
    }
}
