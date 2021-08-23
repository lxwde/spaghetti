package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PostingActionEnum extends AtomizedEnum {
    public static final PostingActionEnum APPLY = new PostingActionEnum("APPLY", "atom.PostingActionEnum.APPLY.description", "atom.PostingActionEnum.APPLY.code", "", "", "");
    public static final PostingActionEnum IGNORE = new PostingActionEnum("IGNORE", "atom.PostingActionEnum.IGNORE.description", "atom.PostingActionEnum.IGNORE.code", "", "", "");

    public static PostingActionEnum getEnum(String inName) {
        return (PostingActionEnum) PostingActionEnum.getEnum(PostingActionEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return PostingActionEnum.getEnumMap(PostingActionEnum.class);
    }

    public static List getEnumList() {
        return PostingActionEnum.getEnumList(PostingActionEnum.class);
    }

    public static Collection getList() {
        return PostingActionEnum.getEnumList(PostingActionEnum.class);
    }

    public static Iterator iterator() {
        return PostingActionEnum.iterator(PostingActionEnum.class);
    }

    protected PostingActionEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.argo.persistence.atoms.UserTypePostingActionEnum";
    }

}
