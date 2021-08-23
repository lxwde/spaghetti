package com.zpmc.ztos.infra.base.business.enums.edi;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EdiProcessEnum extends AtomizedEnum {
    public static final EdiProcessEnum receiving = new EdiProcessEnum("receiving", "atom.EdiProcessEnum.receiving.description", "atom.EdiProcessEnum.receiving.code", "", "", "");
    public static final EdiProcessEnum receive = new EdiProcessEnum("receive", "atom.EdiProcessEnum.receive.description", "atom.EdiProcessEnum.receive.code", "", "", "");
    public static final EdiProcessEnum loading = new EdiProcessEnum("loading", "atom.EdiProcessEnum.loading.description", "atom.EdiProcessEnum.loading.code", "", "", "");
    public static final EdiProcessEnum load = new EdiProcessEnum("load", "atom.EdiProcessEnum.load.description", "atom.EdiProcessEnum.load.code", "", "", "");
    public static final EdiProcessEnum classifying = new EdiProcessEnum("classifying", "atom.EdiProcessEnum.classifying.description", "atom.EdiProcessEnum.classifying.code", "", "", "");
    public static final EdiProcessEnum classify = new EdiProcessEnum("classify", "atom.EdiProcessEnum.classify.description", "atom.EdiProcessEnum.classify.code", "", "", "");
    public static final EdiProcessEnum mapping = new EdiProcessEnum("mapping", "atom.EdiProcessEnum.mapping.description", "atom.EdiProcessEnum.mapping.code", "", "", "");
    public static final EdiProcessEnum map = new EdiProcessEnum("map", "atom.EdiProcessEnum.map.description", "atom.EdiProcessEnum.map.code", "", "", "");
    public static final EdiProcessEnum posting = new EdiProcessEnum("posting", "atom.EdiProcessEnum.posting.description", "atom.EdiProcessEnum.posting.code", "", "", "");
    public static final EdiProcessEnum post = new EdiProcessEnum("post", "atom.EdiProcessEnum.post.description", "atom.EdiProcessEnum.post.code", "", "", "");
    public static final EdiProcessEnum extracting = new EdiProcessEnum("extracting", "atom.EdiProcessEnum.extracting.description", "atom.EdiProcessEnum.extracting.code", "", "", "");
    public static final EdiProcessEnum extract = new EdiProcessEnum("extract", "atom.EdiProcessEnum.extract.description", "atom.EdiProcessEnum.extract.code", "", "", "");
    public static final EdiProcessEnum unloading = new EdiProcessEnum("unloading", "atom.EdiProcessEnum.unloading.description", "atom.EdiProcessEnum.unloading.code", "", "", "");
    public static final EdiProcessEnum unload = new EdiProcessEnum("unload", "atom.EdiProcessEnum.unload.description", "atom.EdiProcessEnum.unload.code", "", "", "");
    public static final EdiProcessEnum selecting = new EdiProcessEnum("selecting", "atom.EdiProcessEnum.selecting.description", "atom.EdiProcessEnum.selecting.code", "", "", "");
    public static final EdiProcessEnum select = new EdiProcessEnum("select", "atom.EdiProcessEnum.select.description", "atom.EdiProcessEnum.select.code", "", "", "");
    public static final EdiProcessEnum sending = new EdiProcessEnum("sending", "atom.EdiProcessEnum.sending.description", "atom.EdiProcessEnum.sending.code", "", "", "");
    public static final EdiProcessEnum send = new EdiProcessEnum("send", "atom.EdiProcessEnum.send.description", "atom.EdiProcessEnum.send.code", "", "", "");

    public static EdiProcessEnum getEnum(String inName) {
        return (EdiProcessEnum) EdiProcessEnum.getEnum(EdiProcessEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return EdiProcessEnum.getEnumMap(EdiProcessEnum.class);
    }

    public static List getEnumList() {
        return EdiProcessEnum.getEnumList(EdiProcessEnum.class);
    }

    public static Collection getList() {
        return EdiProcessEnum.getEnumList(EdiProcessEnum.class);
    }

    public static Iterator iterator() {
        return EdiProcessEnum.iterator(EdiProcessEnum.class);
    }

    protected EdiProcessEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.edi.persistence.atoms.UserTypeEdiProcessEnum";
    }

}
