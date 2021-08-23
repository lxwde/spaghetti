package com.zpmc.ztos.infra.base.business.enums.framework;

import com.zpmc.ztos.infra.base.common.utils.AtomizedEnumUtils;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.apache.commons.lang.enums.Enum;

public class FileTypeEnum extends Enum {
    public static final FileTypeEnum PROPERTIES = new FileTypeEnum("properties");
    public static final FileTypeEnum TXT = new FileTypeEnum("txt");
    public static final FileTypeEnum XML = new FileTypeEnum("xml");

    private FileTypeEnum(String inName) {
        super(inName);
    }

    @Nullable
    public static FileTypeEnum getFileType(String inFileName) {
        int dotIndex = inFileName.lastIndexOf(46);
        String extension = StringUtils.substring((String)inFileName, (int)(dotIndex + 1));
        extension = StringUtils.lowerCase((String)extension);
        return (FileTypeEnum) AtomizedEnumUtils.safeGetEnum(FileTypeEnum.class, extension);
    }

    public String getExtension() {
        return "." + this.getName();
    }
}
