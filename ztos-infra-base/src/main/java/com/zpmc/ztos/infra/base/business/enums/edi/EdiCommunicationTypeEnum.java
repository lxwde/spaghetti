package com.zpmc.ztos.infra.base.business.enums.edi;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EdiCommunicationTypeEnum extends AtomizedEnum {
    public static final EdiCommunicationTypeEnum FTP = new EdiCommunicationTypeEnum("FTP", "atom.EdiCommunicationTypeEnum.FTP.description", "atom.EdiCommunicationTypeEnum.FTP.code", "", "", "");
    public static final EdiCommunicationTypeEnum SFTP = new EdiCommunicationTypeEnum("SFTP", "atom.EdiCommunicationTypeEnum.SFTP.description", "atom.EdiCommunicationTypeEnum.SFTP.code", "", "", "");
    public static final EdiCommunicationTypeEnum SMTP = new EdiCommunicationTypeEnum("SMTP", "atom.EdiCommunicationTypeEnum.SMTP.description", "atom.EdiCommunicationTypeEnum.SMTP.code", "", "", "");
    public static final EdiCommunicationTypeEnum SOAP = new EdiCommunicationTypeEnum("SOAP", "atom.EdiCommunicationTypeEnum.SOAP.description", "atom.EdiCommunicationTypeEnum.SOAP.code", "", "", "");
    public static final EdiCommunicationTypeEnum NONE = new EdiCommunicationTypeEnum("NONE", "atom.EdiCommunicationTypeEnum.NONE.description", "atom.EdiCommunicationTypeEnum.NONE.code", "", "", "");

    public static EdiCommunicationTypeEnum getEnum(String inName) {
        return (EdiCommunicationTypeEnum) EdiCommunicationTypeEnum.getEnum(EdiCommunicationTypeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return EdiCommunicationTypeEnum.getEnumMap(EdiCommunicationTypeEnum.class);
    }

    public static List getEnumList() {
        return EdiCommunicationTypeEnum.getEnumList(EdiCommunicationTypeEnum.class);
    }

    public static Collection getList() {
        return EdiCommunicationTypeEnum.getEnumList(EdiCommunicationTypeEnum.class);
    }

    public static Iterator iterator() {
        return EdiCommunicationTypeEnum.iterator(EdiCommunicationTypeEnum.class);
    }

    protected EdiCommunicationTypeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.edi.persistence.atoms.UserTypeEdiCommunicationTypeEnum";
    }

}
