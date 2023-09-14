package com.example.qcassistant.domain.item.device.android.phone;

import com.example.qcassistant.domain.enums.item.ConnectorType;

public enum MedidataAndroidPhone {

    GALAXY_S7("Medidata\\s*Samsung\\s*Galaxy\\s*S7.{0,16}\\s*SmartPhone Shell\\s*(?<serial>[A-Z0-9]{11})",
            "Galaxy S7",
            ConnectorType.MICRO_USB),
    GALAXY_J3("Medidata\\s*Samsung\\s*Galaxy\\s*J3.{0,32}\\s*SmartPhone Shell\\s*(?<serial>[A-Z0-9]{11})",
            "Galaxy J3",
            ConnectorType.MICRO_USB);

    private String regexPattern;
    private String shortName;
    private ConnectorType connectorType;

    MedidataAndroidPhone(String regexPattern, String shortName, ConnectorType connectorType) {
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
