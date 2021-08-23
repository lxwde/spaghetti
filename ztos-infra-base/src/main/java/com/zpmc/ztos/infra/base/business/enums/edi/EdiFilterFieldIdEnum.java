package com.zpmc.ztos.infra.base.business.enums.edi;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EdiFilterFieldIdEnum extends AbstractEdiFilterFieldIdEnum {
    public static final EdiFilterFieldIdEnum InterchangeSender = new EdiFilterFieldIdEnum("InterchangeSender", "atom.EdiFilterFieldIdEnum.InterchangeSender.description", "atom.EdiFilterFieldIdEnum.InterchangeSender.code", "", "", "", "");
    public static final EdiFilterFieldIdEnum InterchangeReceipient = new EdiFilterFieldIdEnum("InterchangeReceipient", "atom.EdiFilterFieldIdEnum.InterchangeReceipient.description", "atom.EdiFilterFieldIdEnum.InterchangeReceipient.code", "", "", "", "");
    public static final EdiFilterFieldIdEnum portId = new EdiFilterFieldIdEnum("portId", "atom.EdiFilterFieldIdEnum.portId.description", "atom.EdiFilterFieldIdEnum.portId.code", "", "", "", "");
    public static final EdiFilterFieldIdEnum portName = new EdiFilterFieldIdEnum("portName", "atom.EdiFilterFieldIdEnum.portName.description", "atom.EdiFilterFieldIdEnum.portName.code", "", "", "", "");
    public static final EdiFilterFieldIdEnum vesselName = new EdiFilterFieldIdEnum("vesselName", "atom.EdiFilterFieldIdEnum.vesselName.description", "atom.EdiFilterFieldIdEnum.vesselName.code", "", "", "", "");
    public static final EdiFilterFieldIdEnum operator = new EdiFilterFieldIdEnum("operator", "atom.EdiFilterFieldIdEnum.operator.description", "atom.EdiFilterFieldIdEnum.operator.code", "", "", "", "operator,shippingLineCode,railRoadCode");
    public static final EdiFilterFieldIdEnum containerISOcode = new EdiFilterFieldIdEnum("containerISOcode", "atom.EdiFilterFieldIdEnum.containerISOcode.description", "atom.EdiFilterFieldIdEnum.containerISOcode.code", "", "", "", "containerISOcode,ISOcode,containerType");
    public static final EdiFilterFieldIdEnum facilityId = new EdiFilterFieldIdEnum("facilityId", "atom.EdiFilterFieldIdEnum.facilityId.description", "atom.EdiFilterFieldIdEnum.facilityId.code", "", "", "", "");
    public static final EdiFilterFieldIdEnum origin = new EdiFilterFieldIdEnum("origin", "atom.EdiFilterFieldIdEnum.origin.description", "atom.EdiFilterFieldIdEnum.origin.code", "", "", "", "");
    public static final EdiFilterFieldIdEnum destination = new EdiFilterFieldIdEnum("destination", "atom.EdiFilterFieldIdEnum.destination.description", "atom.EdiFilterFieldIdEnum.destination.code", "", "", "", "");
    public static final EdiFilterFieldIdEnum delayCode = new EdiFilterFieldIdEnum("delayCode", "atom.EdiFilterFieldIdEnum.delayCode.description", "atom.EdiFilterFieldIdEnum.delayCode.code", "", "", "", "");
    public static final EdiFilterFieldIdEnum commodityCode = new EdiFilterFieldIdEnum("commodityCode", "atom.EdiFilterFieldIdEnum.commodityCode.description", "atom.EdiFilterFieldIdEnum.commodityCode.code", "", "", "", "");
    public static final EdiFilterFieldIdEnum scheduleKCode = new EdiFilterFieldIdEnum("scheduleKCode", "atom.EdiFilterFieldIdEnum.scheduleKCode.description", "atom.EdiFilterFieldIdEnum.scheduleKCode.code", "", "", "", "");
    public static final EdiFilterFieldIdEnum scheduleDCode = new EdiFilterFieldIdEnum("scheduleDCode", "atom.EdiFilterFieldIdEnum.scheduleDCode.description", "atom.EdiFilterFieldIdEnum.scheduleDCode.code", "", "", "", "");
    public static final EdiFilterFieldIdEnum imdgClass = new EdiFilterFieldIdEnum("imdgClass", "atom.EdiFilterFieldIdEnum.imdgClass.description", "atom.EdiFilterFieldIdEnum.imdgClass.code", "", "", "", "");

    public static EdiFilterFieldIdEnum getEnum(String inName) {
        return (EdiFilterFieldIdEnum) EdiFilterFieldIdEnum.getEnum(EdiFilterFieldIdEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return EdiFilterFieldIdEnum.getEnumMap(EdiFilterFieldIdEnum.class);
    }

    public static List getEnumList() {
        return EdiFilterFieldIdEnum.getEnumList(EdiFilterFieldIdEnum.class);
    }

    public static Collection getList() {
        return EdiFilterFieldIdEnum.getEnumList(EdiFilterFieldIdEnum.class);
    }

    public static Iterator iterator() {
        return EdiFilterFieldIdEnum.iterator(EdiFilterFieldIdEnum.class);
    }

    protected EdiFilterFieldIdEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, String inXMLFields) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath, inXMLFields);
    }

    public String getMappingClassName() {
        return "com.navis.edi.persistence.atoms.UserTypeEdiFilterFieldIdEnum";
    }

}
