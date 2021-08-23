package com.zpmc.ztos.infra.base.business.enums.argo;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CheInstructionTypeEnum extends AbstractCheInstructionTypeEnum {
    public static final CheInstructionTypeEnum ASC_INBOUND = new CheInstructionTypeEnum("ASC_INBOUND", "atom.CheInstructionTypeEnum.ASC_INBOUND.description", "atom.CheInstructionTypeEnum.ASC_INBOUND.code", "red", "", "", "ASC");
    public static final CheInstructionTypeEnum ASC_OUTBOUND = new CheInstructionTypeEnum("ASC_OUTBOUND", "atom.CheInstructionTypeEnum.ASC_OUTBOUND.description", "atom.CheInstructionTypeEnum.ASC_OUTBOUND.code", "sienna", "", "", "ASC");
    public static final CheInstructionTypeEnum ASC_INTRASTACK = new CheInstructionTypeEnum("ASC_INTRASTACK", "atom.CheInstructionTypeEnum.ASC_INTRASTACK.description", "atom.CheInstructionTypeEnum.ASC_INTRASTACK.code", "darkslateblue", "", "", "ASC");
    public static final CheInstructionTypeEnum AGV_ROW_RECEIVE = new CheInstructionTypeEnum("AGV_ROW_RECEIVE", "atom.CheInstructionTypeEnum.AGV_ROW_RECEIVE.description", "atom.CheInstructionTypeEnum.AGV_ROW_RECEIVE.code", "bisque", "", "", "AGV,ASH");
    public static final CheInstructionTypeEnum AGV_ROW_DELIVER = new CheInstructionTypeEnum("AGV_ROW_DELIVER", "atom.CheInstructionTypeEnum.AGV_ROW_DELIVER.description", "atom.CheInstructionTypeEnum.AGV_ROW_DELIVER.code", "lightblue", "", "", "AGV,ASH");
    public static final CheInstructionTypeEnum AGV_VSL_RECEIVE = new CheInstructionTypeEnum("AGV_VSL_RECEIVE", "atom.CheInstructionTypeEnum.AGV_VSL_RECEIVE.description", "atom.CheInstructionTypeEnum.AGV_VSL_RECEIVE.code", "darkgreen", "", "", "AGV,ASH");
    public static final CheInstructionTypeEnum AGV_VSL_DELIVER = new CheInstructionTypeEnum("AGV_VSL_DELIVER", "atom.CheInstructionTypeEnum.AGV_VSL_DELIVER.description", "atom.CheInstructionTypeEnum.AGV_VSL_DELIVER.code", "brown", "", "", "AGV,ASH");
    public static final CheInstructionTypeEnum AGV_RAIL_RECEIVE = new CheInstructionTypeEnum("AGV_RAIL_RECEIVE", "atom.CheInstructionTypeEnum.AGV_RAIL_RECEIVE.description", "atom.CheInstructionTypeEnum.AGV_RAIL_RECEIVE.code", "maroon", "", "", "AGV,ASH");
    public static final CheInstructionTypeEnum AGV_RAIL_DELIVER = new CheInstructionTypeEnum("AGV_RAIL_DELIVER", "atom.CheInstructionTypeEnum.AGV_RAIL_DELIVER.description", "atom.CheInstructionTypeEnum.AGV_RAIL_DELIVER.code", "olive", "", "", "AGV,ASH");
    public static final CheInstructionTypeEnum AGV_VSL_LOAD = new CheInstructionTypeEnum("AGV_VSL_LOAD", "atom.CheInstructionTypeEnum.AGV_VSL_LOAD.description", "atom.CheInstructionTypeEnum.AGV_VSL_LOAD.code", "deeppink", "", "", "AGV,ASH");
    public static final CheInstructionTypeEnum AGV_REPOSITION = new CheInstructionTypeEnum("AGV_REPOSITION", "atom.CheInstructionTypeEnum.AGV_REPOSITION.description", "atom.CheInstructionTypeEnum.AGV_REPOSITION.code", "burlywood", "", "", "AGV,ASH");
    public static final CheInstructionTypeEnum QC_MOVE = new CheInstructionTypeEnum("QC_MOVE", "atom.CheInstructionTypeEnum.QC_MOVE.description", "atom.CheInstructionTypeEnum.QC_MOVE.code", "seagreen", "", "", "QC");
    public static final CheInstructionTypeEnum QC_INBOUND = new CheInstructionTypeEnum("QC_INBOUND", "atom.CheInstructionTypeEnum.QC_INBOUND.description", "atom.CheInstructionTypeEnum.QC_INBOUND.code", "salmon", "", "", "QC");
    public static final CheInstructionTypeEnum QC_OUTBOUND = new CheInstructionTypeEnum("QC_OUTBOUND", "atom.CheInstructionTypeEnum.QC_OUTBOUND.description", "atom.CheInstructionTypeEnum.QC_OUTBOUND.code", "seagreen", "", "", "QC");

    public static CheInstructionTypeEnum getEnum(String inName) {
        return (CheInstructionTypeEnum) CheInstructionTypeEnum.getEnum(CheInstructionTypeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return CheInstructionTypeEnum.getEnumMap(CheInstructionTypeEnum.class);
    }

    public static List getEnumList() {
        return CheInstructionTypeEnum.getEnumList(CheInstructionTypeEnum.class);
    }

    public static Collection getList() {
        return CheInstructionTypeEnum.getEnumList(CheInstructionTypeEnum.class);
    }

    public static Iterator iterator() {
        return CheInstructionTypeEnum.iterator(CheInstructionTypeEnum.class);
    }

    protected CheInstructionTypeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, String inCheInstructionTypeCategories) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath, inCheInstructionTypeCategories);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeCheInstructionTypeEnum";
    }

}
