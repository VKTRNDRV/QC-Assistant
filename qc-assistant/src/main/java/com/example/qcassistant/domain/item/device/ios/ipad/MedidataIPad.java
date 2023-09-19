package com.example.qcassistant.domain.item.device.ios.ipad;

import com.example.qcassistant.domain.enums.item.ConnectorType;

public enum MedidataIPad {
    SIXTH_GEN("Medidata\\s*Apple\\s*iPad\\s*6th Gen.{0,16}\\s*Tablet Shell\\s*(?<serial>[A-Z0-9]{12})",
            "iPad 6th Gen",
            ConnectorType.LIGHTNING),

    AIR_2("Medidata\\s*Apple\\s*iPad\\s*Air\\s*2.{0,16}\\s*Tablet\\s*Shell\\s*(?<serial>[A-Z0-9]{12})",
            "iPad Air 2",
            ConnectorType.LIGHTNING),

    MINI("Medidata\\s*Apple\\s*iPad\\s*Mini\\s*.{0,16}\\s*Tablet\\s*Shell\\s*(?<serial>[A-Z0-9]{12})",
            "iPad Mini",
            ConnectorType.LIGHTNING);

    private String regexPattern;
    private String shortName;
    private ConnectorType connectorType;

    public static final String SERIAL_GROUP_NAME = "serial";

    MedidataIPad(String regexPattern, String shortName, ConnectorType connectorType) {
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
