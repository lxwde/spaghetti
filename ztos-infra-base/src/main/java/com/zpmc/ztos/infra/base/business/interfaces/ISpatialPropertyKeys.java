package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.model.PropertyKeyFactory;

public interface ISpatialPropertyKeys {
    public static final IPropertyKey SPATIAL_MAIN_MESSAGE_FILE_GENERATED = PropertyKeyFactory.valueOf((String)"SPATIAL_MAIN_MESSAGE_FILE_GENERATED");
    public static final IPropertyKey CANNOT_COPY_OBJECT = PropertyKeyFactory.valueOf((String)"CANNOT_COPY_OBJECT");
    public static final IPropertyKey FOUND_DUPLICATE_BIN_NAME = PropertyKeyFactory.valueOf((String)"FOUND_DUPLICATE_BIN_NAME");
    public static final IPropertyKey FOUND_NULL_BIN_NAME = PropertyKeyFactory.valueOf((String)"FOUND_NULL_BIN_NAME");
    public static final IPropertyKey LABEL_FORMATED_GRAPH_PATH_ID = PropertyKeyFactory.valueOf((String)"LABEL_FORMATED_GRAPH_PATH_ID");
    public static final IPropertyKey BIN_NAME_EXCEEDS_MAX_LENGTH = PropertyKeyFactory.valueOf((String)"BIN_NAME_EXCEEDS_MAX_LENGTH");
    public static final IPropertyKey VERTEX_ID_PROCESSOR_REACHED_MAX_VALUE = PropertyKeyFactory.valueOf((String)"VERTEX_ID_PROCESSOR_REACHED_MAX_VALUE");
    public static final IPropertyKey DUPLICATE_VERTEX_FOR_YARD_MODEL = PropertyKeyFactory.valueOf((String)"DUPLICATE_VERTEX_FOR_YARD_MODEL");
    public static final IPropertyKey BIN_COPY_FAILED = PropertyKeyFactory.valueOf((String)"BIN_COPY_FAILED");

}
