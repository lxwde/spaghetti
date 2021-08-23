package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public enum CheJobStepStateEnum {
//
//
//    IDLE(1, "IDLE", "IDLE.description", "IDLE.code", "IDLE"),
//    LADENTOGRID(2, "LADENTOGRID", "LADENTOGRID.description", "LADENTOGRID.code", "LADENTOGRID"),
//    LADENTOROW(3, "LADENTOROW", "LADENTOROW.description", "LADENTOROW.code", "LADENTOROW"),
//    LADENTOSHIP(4, "LADENTOSHIP", "LADENTOSHIP.description", "LADENTOSHIP.code", "LADENTOSHIP"),
//    LADENTORAIL(5, "LADENTORAIL", "LADENTORAIL.description", "LADENTORAIL.code", "LADENTORAIL"),
//    LADENATGRID(6, "LADENATGRID", "LADENATGRID.description", "LADENATGRID.code", "LADENATGRID"),
//    LADENATROW(7, "LADENATROW", "LADENATROW.description", "LADENATROW.code", "LADENATROW"),
//    LADENATSHIP(8, "LADENATSHIP", "LADENATSHIP.description", "LADENATSHIP.code", "LADENATSHIP"),
//    LADENATRAIL(9, "LADENATRAIL", "LADENATRAIL.description", "LADENATRAIL.code", "LADENATRAIL"),
//    EMPTYTOGRID(10, "EMPTYTOGRID", "EMPTYTOGRID.description", "EMPTYTOGRID.code", "EMPTYTOGRID"),
//    EMPTYTOROW(11, "EMPTYTOROW", "EMPTYTOROW.description", "EMPTYTOROW.code", "EMPTYTOROW"),
//    EMPTYTOSHIP(12, "EMPTYTOSHIP", "EMPTYTOSHIP.description", "EMPTYTOSHIP.code", "EMPTYTOSHIP"),
//    EMPTYTORAIL(13, "EMPTYTORAIL", "EMPTYTORAIL.description", "EMPTYTORAIL.code", "EMPTYTORAIL"),
//    EMPTYATGRID(111111, "EMPTYATGRID", "EMPTYATGRID.description", "EMPTYATGRID.code", "EMPTYATGRID"),
//    EMPTYATROW(14, "EMPTYATROW", "EMPTYATROW.description", "EMPTYATROW.code", "EMPTYATROW"),
//    EMPTYATSHIP(15, "EMPTYATSHIP", "EMPTYATSHIP.description", "EMPTYATSHIP.code", "EMPTYATSHIP"),
//    EMPTYATRAIL(16, "EMPTYATRAIL", "EMPTYATRAIL.description", "EMPTYATRAIL.code", "EMPTYATRAIL"),
//    SHIFTINROW(17, "SHIFTINROW", "SHIFTINROW.description", "SHIFTINROW.code", "SHIFTINROW"),
//    LADENTWINTOROW(18, "LADENTWINTOROW", "LADENTWINTOROW.description", "LADENTWINTOROW.code", "LADENTWINTOROW"),
//    LADENTWINTOSHIP(19, "LADENTWINTOSHIP", "LADENTWINTOSHIP.description", "LADENTWINTOSHIP.code", "LADENTWINTOSHIP"),
//    LADENTWINATROW(20, "LADENTWINATROW", "LADENTWINATROW.description", "LADENTWINATROW.code", "LADENTWINATROW"),
//    LADENTWINATSHIP(21, "LADENTWINATSHIP", "LADENTWINATSHIP.description", "LADENTWINATSHIP.code", "LADENTWINATSHIP"),
//    FETCH2NDTWINTOROW(22, "FETCH2NDTWINTOROW", "FETCH2NDTWINTOROW.description", "FETCH2NDTWINTOROW.code", "FETCH2NDTWINTOROW"),
//    FETCH2NDTWINATROW(23, "FETCH2NDTWINATROW", "FETCH2NDTWINATROW.description", "FETCH2NDTWINATROW.code", "FETCH2NDTWINATROW"),
//    TOROWTODROPCNTR(24, "TOROWTODROPCNTR", "TOROWTODROPCNTR.description", "TOROWTODROPCNTR.code", "TOROWTODROPCNTR"),
//    ATROWTODROPCNTR(25, "ATROWTODROPCNTR", "ATROWTODROPCNTR.description", "ATROWTODROPCNTR.code", "ATROWTODROPCNTR"),
//    TOROWTOCOLLECTCNTR(26, "TOROWTOCOLLECTCNTR", "TOROWTOCOLLECTCNTR.description", "TOROWTOCOLLECTCNTR.code", "TOROWTOCOLLECTCNTR"),
//    ATROWTOCOLLECTCNTR(27, "ATROWTOCOLLECTCNTR", "ATROWTOCOLLECTCNTR.description", "ATROWTOCOLLECTCNTR.code", "ATROWTOCOLLECTCNTR"),
//    TOSHIPTODROPCNTR(28, "TOSHIPTODROPCNTR", "TOSHIPTODROPCNTR.description", "TOSHIPTODROPCNTR.code", "TOSHIPTODROPCNTR"),
//    ATSHIPTODROPCNTR(29, "ATSHIPTODROPCNTR", "ATSHIPTODROPCNTR.description", "ATSHIPTODROPCNTR.code", "ATSHIPTODROPCNTR"),
//    TOSHIPTOCOLLECTCNTR(30, "TOSHIPTOCOLLECTCNTR", "TOSHIPTOCOLLECTCNTR.description", "TOSHIPTOCOLLECTCNTR.code", "TOSHIPTOCOLLECTCNTR"),
//    ATSHIPTOCOLLECTCNTR(31, "ATSHIPTOCOLLECTCNTR", "ATSHIPTOCOLLECTCNTR.description", "ATSHIPTOCOLLECTCNTR.code", "ATSHIPTOCOLLECTCNTR"),
//    TORAILTODROPCNTR(32, "TORAILTODROPCNTR", "TORAILTODROPCNTR.description", "TORAILTODROPCNTR.code", "TORAILTODROPCNTR"),
//    ATRAILTODROPCNTR(33, "ATRAILTODROPCNTR", "ATRAILTODROPCNTR.description", "ATRAILTODROPCNTR.code", "ATRAILTODROPCNTR"),
//    TORAILTOCOLLECTCNTR(34, "TORAILTOCOLLECTCNTR", "TORAILTOCOLLECTCNTR.description", "TORAILTOCOLLECTCNTR.code", "TORAILTOCOLLECTCNTR"),
//    ATRAILTOCOLLECTCNTR(35, "ATRAILTOCOLLECTCNTR", "ATRAILTOCOLLECTCNTR.description", "ATRAILTOCOLLECTCNTR.code", "ATRAILTOCOLLECTCNTR"),
//    EMPTYATBUFFERHEAP(36, "EMPTYATBUFFERHEAP", "EMPTYATBUFFERHEAP.description", "EMPTYATBUFFERHEAP.code", "EMPTYATBUFFERHEAP"),
//    LADENINSHIPBUFFERENTRY(37, "LADENINSHIPBUFFERENTRY", "LADENINSHIPBUFFERENTRY.description", "LADENINSHIPBUFFERENTRY.code", "LADENINSHIPBUFFERENTRY"),
//    EMPTYINSHIPBUFFERENTRY(38, "EMPTYINSHIPBUFFERENTRY", "EMPTYINSHIPBUFFERENTRY.description", "EMPTYINSHIPBUFFERENTRY.code", "EMPTYINSHIPBUFFERENTRY"),
//    LADENINSHIPBUFFEREXIT(39, "LADENINSHIPBUFFEREXIT", "LADENINSHIPBUFFEREXIT.description", "LADENINSHIPBUFFEREXIT.code", "LADENINSHIPBUFFEREXIT"),
//    EMPTYINSHIPBUFFEREXIT(40, "EMPTYINSHIPBUFFEREXIT", "EMPTYINSHIPBUFFEREXIT.description", "EMPTYINSHIPBUFFEREXIT.code", "EMPTYINSHIPBUFFEREXIT"),
//    EMPTYINROWTOCONTAINERPOSITION(41, "EMPTYINROWTOCONTAINERPOSITION", "EMPTYINROWTOCONTAINERPOSITION.description", "EMPTYINROWTOCONTAINERPOSITION.code", "EMPTYINROWTOCONTAINERPOSITION"),
//    LADENINROWTOCARRYCHE(42, "LADENINROWTOCARRYCHE", "LADENINROWTOCARRYCHE.description", "LADENINROWTOCARRYCHE.code", "LADENINROWTOCARRYCHE"),
//    EMPTYINROWTOCARRYCHE(43, "EMPTYINROWTOCARRYCHE", "EMPTYINROWTOCARRYCHE.description", "EMPTYINROWTOCARRYCHE.code", "EMPTYINROWTOCARRYCHE"),
//    LADENINROWTOCONTAINERPLANPOSITION(44, "LADENINROWTOCONTAINERPLANPOSITION", "LADENINROWTOCONTAINERPLANPOSITION.description", "LADENINROWTOCONTAINERPLANPOSITION.code", "LADENINROWTOCONTAINERPLANPOSITION"),
//    REPOSITIONING(45, "REPOSITIONING", "REPOSITIONING.description", "REPOSITIONING.code", "REPOSITIONING"),
//    BADJOBSTEP(46, "BADJOBSTEP", "BADJOBSTEP.description", "BADJOBSTEP.code", "BADJOBSTEP"),
//    NOJOBSTEP(47, "NOJOBSTEP", "NOJOBSTEP.description", "NOJOBSTEP.code", "NOJOBSTEP"),
//    LOGGEDOUT(48, "LOGGEDOUT", "LOGGEDOUT.description", "LOGGEDOUT.code", "LOGGEDOUT"),
//    TOPULLCHASSIS(49, "TOPULLCHASSIS", "TOPULLCHASSIS.description", "TOPULLCHASSIS.code", "TOPULLCHASSIS"),
//    TOPARKCHASSIS(50, "TOPARKCHASSIS", "TOPARKCHASSIS.description", "TOPARKCHASSIS.code", "TOPARKCHASSIS"),
//    ATPARALLELBUFFER(51, "ATPARALLELBUFFER", "ATPARALLELBUFFER.description", "ATPARALLELBUFFER.code", "ATPARALLELBUFFER"),
//    MAINTROLLEYPUT(52, "MAINTROLLEYPUT", "MAINTROLLEYPUT.description", "MAINTROLLEYPUT.code", "MAINTROLLEYPUT"),
//    PORTALTROLLEYPUT(53, "PORTALTROLLEYPUT", "PORTALTROLLEYPUT.description", "PORTALTROLLEYPUT.code", "PORTALTROLLEYPUT"),
//    ATPLATFORM(54, "ATPLATFORM", "ATPLATFORM.description", "ATPLATFORM.code", "ATPLATFORM");
//
//
//    private final int key;
//    private final String code;
//    private final String desc;
//    private final String color;
//    private final String displayName;
//
//    CheJobStepStateEnum(int key, String code, String desc, String color, String displayName) {
//        this.key = key;
//        this.code = code;
//        this.desc = desc;
//        this.color = color;
//        this.displayName = displayName;
//    }
//
//    public int getKey() {
//        return key;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public String getDesc() {
//        return desc;
//    }
//
//    public String getColor() {
//        return color;
//    }
//
//    public String getDisplayName() {
//        return displayName;
//    }
//
//    public static List getEnumList() {
//        return EnumUtils.getEnumList(CheJobStepStateEnum.class);
//    }
//
//    public static EnumSet getEnumSet() {
//        return EnumSet.allOf(CheJobStepStateEnum.class);
//    }
//
//}


public class CheJobStepStateEnum
        extends AtomizedEnum {
    public static final CheJobStepStateEnum IDLE = new CheJobStepStateEnum("IDLE", "atom.CheJobStepStateEnum.IDLE.description", "atom.CheJobStepStateEnum.IDLE.code", "", "", "");
    public static final CheJobStepStateEnum LADENTOGRID = new CheJobStepStateEnum("LADENTOGRID", "atom.CheJobStepStateEnum.LADENTOGRID.description", "atom.CheJobStepStateEnum.LADENTOGRID.code", "", "", "");
    public static final CheJobStepStateEnum LADENTOROW = new CheJobStepStateEnum("LADENTOROW", "atom.CheJobStepStateEnum.LADENTOROW.description", "atom.CheJobStepStateEnum.LADENTOROW.code", "", "", "");
    public static final CheJobStepStateEnum LADENTOSHIP = new CheJobStepStateEnum("LADENTOSHIP", "atom.CheJobStepStateEnum.LADENTOSHIP.description", "atom.CheJobStepStateEnum.LADENTOSHIP.code", "", "", "");
    public static final CheJobStepStateEnum LADENTORAIL = new CheJobStepStateEnum("LADENTORAIL", "atom.CheJobStepStateEnum.LADENTORAIL.description", "atom.CheJobStepStateEnum.LADENTORAIL.code", "", "", "");
    public static final CheJobStepStateEnum LADENATGRID = new CheJobStepStateEnum("LADENATGRID", "atom.CheJobStepStateEnum.LADENATGRID.description", "atom.CheJobStepStateEnum.LADENATGRID.code", "", "", "");
    public static final CheJobStepStateEnum LADENATROW = new CheJobStepStateEnum("LADENATROW", "atom.CheJobStepStateEnum.LADENATROW.description", "atom.CheJobStepStateEnum.LADENATROW.code", "", "", "");
    public static final CheJobStepStateEnum LADENATSHIP = new CheJobStepStateEnum("LADENATSHIP", "atom.CheJobStepStateEnum.LADENATSHIP.description", "atom.CheJobStepStateEnum.LADENATSHIP.code", "", "", "");
    public static final CheJobStepStateEnum LADENATRAIL = new CheJobStepStateEnum("LADENATRAIL", "atom.CheJobStepStateEnum.LADENATRAIL.description", "atom.CheJobStepStateEnum.LADENATRAIL.code", "", "", "");
    public static final CheJobStepStateEnum EMPTYTOGRID = new CheJobStepStateEnum("EMPTYTOGRID", "atom.CheJobStepStateEnum.EMPTYTOGRID.description", "atom.CheJobStepStateEnum.EMPTYTOGRID.code", "", "", "");
    public static final CheJobStepStateEnum EMPTYTOROW = new CheJobStepStateEnum("EMPTYTOROW", "atom.CheJobStepStateEnum.EMPTYTOROW.description", "atom.CheJobStepStateEnum.EMPTYTOROW.code", "", "", "");
    public static final CheJobStepStateEnum EMPTYTOSHIP = new CheJobStepStateEnum("EMPTYTOSHIP", "atom.CheJobStepStateEnum.EMPTYTOSHIP.description", "atom.CheJobStepStateEnum.EMPTYTOSHIP.code", "", "", "");
    public static final CheJobStepStateEnum EMPTYTORAIL = new CheJobStepStateEnum("EMPTYTORAIL", "atom.CheJobStepStateEnum.EMPTYTORAIL.description", "atom.CheJobStepStateEnum.EMPTYTORAIL.code", "", "", "");
    public static final CheJobStepStateEnum EMPTYATGRID = new CheJobStepStateEnum("EMPTYATGRID", "atom.CheJobStepStateEnum.EMPTYATGRID.description", "atom.CheJobStepStateEnum.EMPTYATGRID.code", "", "", "");
    public static final CheJobStepStateEnum EMPTYATROW = new CheJobStepStateEnum("EMPTYATROW", "atom.CheJobStepStateEnum.EMPTYATROW.description", "atom.CheJobStepStateEnum.EMPTYATROW.code", "", "", "");
    public static final CheJobStepStateEnum EMPTYATSHIP = new CheJobStepStateEnum("EMPTYATSHIP", "atom.CheJobStepStateEnum.EMPTYATSHIP.description", "atom.CheJobStepStateEnum.EMPTYATSHIP.code", "", "", "");
    public static final CheJobStepStateEnum EMPTYATRAIL = new CheJobStepStateEnum("EMPTYATRAIL", "atom.CheJobStepStateEnum.EMPTYATRAIL.description", "atom.CheJobStepStateEnum.EMPTYATRAIL.code", "", "", "");
    public static final CheJobStepStateEnum SHIFTINROW = new CheJobStepStateEnum("SHIFTINROW", "atom.CheJobStepStateEnum.SHIFTINROW.description", "atom.CheJobStepStateEnum.SHIFTINROW.code", "", "", "");
    public static final CheJobStepStateEnum LADENTWINTOROW = new CheJobStepStateEnum("LADENTWINTOROW", "atom.CheJobStepStateEnum.LADENTWINTOROW.description", "atom.CheJobStepStateEnum.LADENTWINTOROW.code", "", "", "");
    public static final CheJobStepStateEnum LADENTWINTOSHIP = new CheJobStepStateEnum("LADENTWINTOSHIP", "atom.CheJobStepStateEnum.LADENTWINTOSHIP.description", "atom.CheJobStepStateEnum.LADENTWINTOSHIP.code", "", "", "");
    public static final CheJobStepStateEnum LADENTWINATROW = new CheJobStepStateEnum("LADENTWINATROW", "atom.CheJobStepStateEnum.LADENTWINATROW.description", "atom.CheJobStepStateEnum.LADENTWINATROW.code", "", "", "");
    public static final CheJobStepStateEnum LADENTWINATSHIP = new CheJobStepStateEnum("LADENTWINATSHIP", "atom.CheJobStepStateEnum.LADENTWINATSHIP.description", "atom.CheJobStepStateEnum.LADENTWINATSHIP.code", "", "", "");
    public static final CheJobStepStateEnum FETCH2NDTWINTOROW = new CheJobStepStateEnum("FETCH2NDTWINTOROW", "atom.CheJobStepStateEnum.FETCH2NDTWINTOROW.description", "atom.CheJobStepStateEnum.FETCH2NDTWINTOROW.code", "", "", "");
    public static final CheJobStepStateEnum FETCH2NDTWINATROW = new CheJobStepStateEnum("FETCH2NDTWINATROW", "atom.CheJobStepStateEnum.FETCH2NDTWINATROW.description", "atom.CheJobStepStateEnum.FETCH2NDTWINATROW.code", "", "", "");
    public static final CheJobStepStateEnum TOROWTODROPCNTR = new CheJobStepStateEnum("TOROWTODROPCNTR", "atom.CheJobStepStateEnum.TOROWTODROPCNTR.description", "atom.CheJobStepStateEnum.TOROWTODROPCNTR.code", "", "", "");
    public static final CheJobStepStateEnum ATROWTODROPCNTR = new CheJobStepStateEnum("ATROWTODROPCNTR", "atom.CheJobStepStateEnum.ATROWTODROPCNTR.description", "atom.CheJobStepStateEnum.ATROWTODROPCNTR.code", "", "", "");
    public static final CheJobStepStateEnum TOROWTOCOLLECTCNTR = new CheJobStepStateEnum("TOROWTOCOLLECTCNTR", "atom.CheJobStepStateEnum.TOROWTOCOLLECTCNTR.description", "atom.CheJobStepStateEnum.TOROWTOCOLLECTCNTR.code", "", "", "");
    public static final CheJobStepStateEnum ATROWTOCOLLECTCNTR = new CheJobStepStateEnum("ATROWTOCOLLECTCNTR", "atom.CheJobStepStateEnum.ATROWTOCOLLECTCNTR.description", "atom.CheJobStepStateEnum.ATROWTOCOLLECTCNTR.code", "", "", "");
    public static final CheJobStepStateEnum TOSHIPTODROPCNTR = new CheJobStepStateEnum("TOSHIPTODROPCNTR", "atom.CheJobStepStateEnum.TOSHIPTODROPCNTR.description", "atom.CheJobStepStateEnum.TOSHIPTODROPCNTR.code", "", "", "");
    public static final CheJobStepStateEnum ATSHIPTODROPCNTR = new CheJobStepStateEnum("ATSHIPTODROPCNTR", "atom.CheJobStepStateEnum.ATSHIPTODROPCNTR.description", "atom.CheJobStepStateEnum.ATSHIPTODROPCNTR.code", "", "", "");
    public static final CheJobStepStateEnum TOSHIPTOCOLLECTCNTR = new CheJobStepStateEnum("TOSHIPTOCOLLECTCNTR", "atom.CheJobStepStateEnum.TOSHIPTOCOLLECTCNTR.description", "atom.CheJobStepStateEnum.TOSHIPTOCOLLECTCNTR.code", "", "", "");
    public static final CheJobStepStateEnum ATSHIPTOCOLLECTCNTR = new CheJobStepStateEnum("ATSHIPTOCOLLECTCNTR", "atom.CheJobStepStateEnum.ATSHIPTOCOLLECTCNTR.description", "atom.CheJobStepStateEnum.ATSHIPTOCOLLECTCNTR.code", "", "", "");
    public static final CheJobStepStateEnum TORAILTODROPCNTR = new CheJobStepStateEnum("TORAILTODROPCNTR", "atom.CheJobStepStateEnum.TORAILTODROPCNTR.description", "atom.CheJobStepStateEnum.TORAILTODROPCNTR.code", "", "", "");
    public static final CheJobStepStateEnum ATRAILTODROPCNTR = new CheJobStepStateEnum("ATRAILTODROPCNTR", "atom.CheJobStepStateEnum.ATRAILTODROPCNTR.description", "atom.CheJobStepStateEnum.ATRAILTODROPCNTR.code", "", "", "");
    public static final CheJobStepStateEnum TORAILTOCOLLECTCNTR = new CheJobStepStateEnum("TORAILTOCOLLECTCNTR", "atom.CheJobStepStateEnum.TORAILTOCOLLECTCNTR.description", "atom.CheJobStepStateEnum.TORAILTOCOLLECTCNTR.code", "", "", "");
    public static final CheJobStepStateEnum ATRAILTOCOLLECTCNTR = new CheJobStepStateEnum("ATRAILTOCOLLECTCNTR", "atom.CheJobStepStateEnum.ATRAILTOCOLLECTCNTR.description", "atom.CheJobStepStateEnum.ATRAILTOCOLLECTCNTR.code", "", "", "");
    public static final CheJobStepStateEnum EMPTYATBUFFERHEAP = new CheJobStepStateEnum("EMPTYATBUFFERHEAP", "atom.CheJobStepStateEnum.EMPTYATBUFFERHEAP.description", "atom.CheJobStepStateEnum.EMPTYATBUFFERHEAP.code", "", "", "");
    public static final CheJobStepStateEnum LADENINSHIPBUFFERENTRY = new CheJobStepStateEnum("LADENINSHIPBUFFERENTRY", "atom.CheJobStepStateEnum.LADENINSHIPBUFFERENTRY.description", "atom.CheJobStepStateEnum.LADENINSHIPBUFFERENTRY.code", "", "", "");
    public static final CheJobStepStateEnum EMPTYINSHIPBUFFERENTRY = new CheJobStepStateEnum("EMPTYINSHIPBUFFERENTRY", "atom.CheJobStepStateEnum.EMPTYINSHIPBUFFERENTRY.description", "atom.CheJobStepStateEnum.EMPTYINSHIPBUFFERENTRY.code", "", "", "");
    public static final CheJobStepStateEnum LADENINSHIPBUFFEREXIT = new CheJobStepStateEnum("LADENINSHIPBUFFEREXIT", "atom.CheJobStepStateEnum.LADENINSHIPBUFFEREXIT.description", "atom.CheJobStepStateEnum.LADENINSHIPBUFFEREXIT.code", "", "", "");
    public static final CheJobStepStateEnum EMPTYINSHIPBUFFEREXIT = new CheJobStepStateEnum("EMPTYINSHIPBUFFEREXIT", "atom.CheJobStepStateEnum.EMPTYINSHIPBUFFEREXIT.description", "atom.CheJobStepStateEnum.EMPTYINSHIPBUFFEREXIT.code", "", "", "");
    public static final CheJobStepStateEnum EMPTYINROWTOCONTAINERPOSITION = new CheJobStepStateEnum("EMPTYINROWTOCONTAINERPOSITION", "atom.CheJobStepStateEnum.EMPTYINROWTOCONTAINERPOSITION.description", "atom.CheJobStepStateEnum.EMPTYINROWTOCONTAINERPOSITION.code", "", "", "");
    public static final CheJobStepStateEnum LADENINROWTOCARRYCHE = new CheJobStepStateEnum("LADENINROWTOCARRYCHE", "atom.CheJobStepStateEnum.LADENINROWTOCARRYCHE.description", "atom.CheJobStepStateEnum.LADENINROWTOCARRYCHE.code", "", "", "");
    public static final CheJobStepStateEnum EMPTYINROWTOCARRYCHE = new CheJobStepStateEnum("EMPTYINROWTOCARRYCHE", "atom.CheJobStepStateEnum.EMPTYINROWTOCARRYCHE.description", "atom.CheJobStepStateEnum.EMPTYINROWTOCARRYCHE.code", "", "", "");
    public static final CheJobStepStateEnum LADENINROWTOCONTAINERPLANPOSITION = new CheJobStepStateEnum("LADENINROWTOCONTAINERPLANPOSITION", "atom.CheJobStepStateEnum.LADENINROWTOCONTAINERPLANPOSITION.description", "atom.CheJobStepStateEnum.LADENINROWTOCONTAINERPLANPOSITION.code", "", "", "");
    public static final CheJobStepStateEnum REPOSITIONING = new CheJobStepStateEnum("REPOSITIONING", "atom.CheJobStepStateEnum.REPOSITIONING.description", "atom.CheJobStepStateEnum.REPOSITIONING.code", "", "", "");
    public static final CheJobStepStateEnum BADJOBSTEP = new CheJobStepStateEnum("BADJOBSTEP", "atom.CheJobStepStateEnum.BADJOBSTEP.description", "atom.CheJobStepStateEnum.BADJOBSTEP.code", "", "", "");
    public static final CheJobStepStateEnum NOJOBSTEP = new CheJobStepStateEnum("NOJOBSTEP", "atom.CheJobStepStateEnum.NOJOBSTEP.description", "atom.CheJobStepStateEnum.NOJOBSTEP.code", "", "", "");
    public static final CheJobStepStateEnum LOGGEDOUT = new CheJobStepStateEnum("LOGGEDOUT", "atom.CheJobStepStateEnum.LOGGEDOUT.description", "atom.CheJobStepStateEnum.LOGGEDOUT.code", "", "", "");
    public static final CheJobStepStateEnum TOPULLCHASSIS = new CheJobStepStateEnum("TOPULLCHASSIS", "atom.CheJobStepStateEnum.TOPULLCHASSIS.description", "atom.CheJobStepStateEnum.TOPULLCHASSIS.code", "", "", "");
    public static final CheJobStepStateEnum TOPARKCHASSIS = new CheJobStepStateEnum("TOPARKCHASSIS", "atom.CheJobStepStateEnum.TOPARKCHASSIS.description", "atom.CheJobStepStateEnum.TOPARKCHASSIS.code", "", "", "");
    public static final CheJobStepStateEnum ATPARALLELBUFFER = new CheJobStepStateEnum("ATPARALLELBUFFER", "atom.CheJobStepStateEnum.ATPARALLELBUFFER.description", "atom.CheJobStepStateEnum.ATPARALLELBUFFER.code", "", "", "");
    public static final CheJobStepStateEnum MAINTROLLEYPUT = new CheJobStepStateEnum("MAINTROLLEYPUT", "atom.CheJobStepStateEnum.MAINTROLLEYPUT.description", "atom.CheJobStepStateEnum.MAINTROLLEYPUT.code", "", "", "");
    public static final CheJobStepStateEnum PORTALTROLLEYPUT = new CheJobStepStateEnum("PORTALTROLLEYPUT", "atom.CheJobStepStateEnum.PORTALTROLLEYPUT.description", "atom.CheJobStepStateEnum.PORTALTROLLEYPUT.code", "", "", "");
    public static final CheJobStepStateEnum ATPLATFORM = new CheJobStepStateEnum("ATPLATFORM", "atom.CheJobStepStateEnum.ATPLATFORM.description", "atom.CheJobStepStateEnum.ATPLATFORM.code", "", "", "");

    public static CheJobStepStateEnum getEnum(String inName) {
        return (CheJobStepStateEnum) CheJobStepStateEnum.getEnum(CheJobStepStateEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return CheJobStepStateEnum.getEnumMap(CheJobStepStateEnum.class);
    }

    public static List getEnumList() {
        return CheJobStepStateEnum.getEnumList(CheJobStepStateEnum.class);
    }

    public static Collection getList() {
        return CheJobStepStateEnum.getEnumList(CheJobStepStateEnum.class);
    }

    public static Iterator iterator() {
        return CheJobStepStateEnum.iterator(CheJobStepStateEnum.class);
    }

    protected CheJobStepStateEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypeCheJobStepStateEnum";
    }
}
