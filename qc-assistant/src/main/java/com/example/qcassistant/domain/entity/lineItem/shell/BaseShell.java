package com.example.qcassistant.domain.entity.lineItem.shell;

import com.example.qcassistant.domain.entity.lineItem.BaseItem;
import com.example.qcassistant.domain.enums.ConnectorType;
import com.example.qcassistant.domain.enums.OperatingSystem;
import com.example.qcassistant.domain.enums.ShellType;
import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseShell extends BaseItem {

    @Enumerated(EnumType.STRING)
    @Column(name = "operating_system")
    private OperatingSystem operatingSystem;

    @Enumerated(EnumType.STRING)
    @Column(name = "connector_type")
    private ConnectorType connectorType;

    @Enumerated(EnumType.STRING)
    @Column(name = "shell_type")
    private ShellType shellType;
}
