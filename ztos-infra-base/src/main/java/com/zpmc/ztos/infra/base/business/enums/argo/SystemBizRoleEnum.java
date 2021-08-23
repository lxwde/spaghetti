package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SystemBizRoleEnum extends AtomizedEnum {
    private boolean _hasCustomUI;

    public SystemBizRoleEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, boolean inHasCustomUI) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
        this._hasCustomUI = inHasCustomUI;
    }

    public String getId() {
        return this.getKey();
    }

    public String getDescription() {
        return this.getName();
    }

    public boolean hasCustomUI() {
        return this._hasCustomUI;
    }

    public static Collection getKeyListWithNoCustomUI() {
        ArrayList list = SystemBizRoleEnum.getListWithNoCustomUI();
        ArrayList<String> keyList = new ArrayList<String>();
        if (!list.isEmpty()) {
            for (Object role : list) {
                keyList.add(((BizRoleEnum)role).getKey());
            }
        }
        return keyList;
    }

    public static Collection getClassListWithNoCustomUI() {
        ArrayList list = SystemBizRoleEnum.getListWithNoCustomUI();
        ArrayList classList = new ArrayList();
        if (!list.isEmpty()) {
            for (Object role : list) {
                classList.add(((Object)((Object)role)).getClass());
            }
        }
        return classList;
    }

    public static ArrayList getListWithNoCustomUI() {
        List list = SystemBizRoleEnum.getEnumList(BizRoleEnum.class);
        ArrayList<BizRoleEnum> results = new ArrayList<BizRoleEnum>();
        for (Object roleEnum : list) {
            if (((BizRoleEnum)roleEnum).hasCustomUI()) continue;
            results.add(((BizRoleEnum)roleEnum));
        }
        return results;
    }
}
