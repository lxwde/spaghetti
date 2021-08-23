package com.zpmc.ztos.infra.base.business.enums.service;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ServiceRuleTypeEnum extends AtomizedEnum {

    public static final ServiceRuleTypeEnum SIMPLE_HOLD = new ServiceRuleTypeEnum("SIMPLE_HOLD", "atom.ServiceRuleTypeEnum.SIMPLE_HOLD.description", "atom.ServiceRuleTypeEnum.SIMPLE_HOLD.code", "", "", "");
    public static final ServiceRuleTypeEnum SIMPLE_PERMISSION = new ServiceRuleTypeEnum("SIMPLE_PERMISSION", "atom.ServiceRuleTypeEnum.SIMPLE_PERMISSION.description", "atom.ServiceRuleTypeEnum.SIMPLE_PERMISSION.code", "", "", "");
    public static final ServiceRuleTypeEnum HOLD_ON_GUARDIAN = new ServiceRuleTypeEnum("HOLD_ON_GUARDIAN", "atom.ServiceRuleTypeEnum.HOLD_ON_GUARDIAN.description", "atom.ServiceRuleTypeEnum.HOLD_ON_GUARDIAN.code", "", "", "");
    public static final ServiceRuleTypeEnum PERMISSION_ON_GUARDIAN = new ServiceRuleTypeEnum("PERMISSION_ON_GUARDIAN", "atom.ServiceRuleTypeEnum.PERMISSION_ON_GUARDIAN.description", "atom.ServiceRuleTypeEnum.PERMISSION_ON_GUARDIAN.code", "", "", "");
    public static final ServiceRuleTypeEnum HOLD_ON_GUARDED = new ServiceRuleTypeEnum("HOLD_ON_GUARDED", "atom.ServiceRuleTypeEnum.HOLD_ON_GUARDED.description", "atom.ServiceRuleTypeEnum.HOLD_ON_GUARDED.code", "", "", "");
    public static final ServiceRuleTypeEnum PERMISSION_ON_GUARDED = new ServiceRuleTypeEnum("PERMISSION_ON_GUARDED", "atom.ServiceRuleTypeEnum.PERMISSION_ON_GUARDED.description", "atom.ServiceRuleTypeEnum.PERMISSION_ON_GUARDED.code", "", "", "");
    public static final ServiceRuleTypeEnum PREREQUISITE_SERVICE = new ServiceRuleTypeEnum("PREREQUISITE_SERVICE", "atom.ServiceRuleTypeEnum.PREREQUISITE_SERVICE.description", "atom.ServiceRuleTypeEnum.PREREQUISITE_SERVICE.code", "", "", "");

    public static ServiceRuleTypeEnum getEnum(String inName) {
        return (ServiceRuleTypeEnum) ServiceRuleTypeEnum.getEnum(ServiceRuleTypeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return ServiceRuleTypeEnum.getEnumMap(ServiceRuleTypeEnum.class);
    }

    public static List getEnumList() {
        return ServiceRuleTypeEnum.getEnumList(ServiceRuleTypeEnum.class);
    }

    public static Collection getList() {
        return ServiceRuleTypeEnum.getEnumList(ServiceRuleTypeEnum.class);
    }

    public static Iterator iterator() {
        return ServiceRuleTypeEnum.iterator(ServiceRuleTypeEnum.class);
    }

    protected ServiceRuleTypeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
    }

    public String getMappingClassName() {
        return "com.navis.services.persistence.atoms.UserTypeServiceRuleTypeEnum";
    }

}
