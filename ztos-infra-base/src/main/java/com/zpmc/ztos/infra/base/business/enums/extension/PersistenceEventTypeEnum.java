package com.zpmc.ztos.infra.base.business.enums.extension;

public enum PersistenceEventTypeEnum {
    START_SYSTEM_ONLY,
    SYSTEM_SCHEMA_UPGRADE,
    SYSTEM_SCHEMA_CREATION,
    RESTART_WITHOUT_EXTENSION_AFTER_ERROR,
    START_WITH_EXTENSIONS,
    SCHEMA_EXTENSION_UPGRADE;

}
