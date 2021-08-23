package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.interfaces.IPrivilege;
import com.zpmc.ztos.infra.base.common.security.PrivilegeFactory;

public class InventoryPrivs {
    public static final IPrivilege INSPECT_UNIT_DENY_DAMAGES = PrivilegeFactory.valueOf((String)"INSPECT_UNIT_DENY_DAMAGES");
    public static final IPrivilege INSPECT_UNIT_DENY_MOVE_HISTORY = PrivilegeFactory.valueOf((String)"INSPECT_UNIT_DENY_MOVE_HISTORY");
    public static final IPrivilege INSPECT_UNIT_DENY_DECLARED_GOODS = PrivilegeFactory.valueOf((String)"INSPECT_UNIT_DENY_DECLARED_GOODS");
    public static final IPrivilege UNIT_OBSERVED_PLACARDS_VIEW = PrivilegeFactory.valueOf((String)"UNIT_OBSERVED_PLACARDS_VIEW");
    public static final IPrivilege UNIT_OBSERVED_PLACARDS_ADD = PrivilegeFactory.valueOf((String)"UNIT_OBSERVED_PLACARDS_ADD");
    public static final IPrivilege UNIT_OBSERVED_PLACARDS_EDIT = PrivilegeFactory.valueOf((String)"UNIT_OBSERVED_PLACARDS_EDIT");
    public static final IPrivilege UNIT_OBSERVED_PLACARDS_DELETE = PrivilegeFactory.valueOf((String)"UNIT_OBSERVED_PLACARDS_DELETE");
    public static final IPrivilege INV_QUERY_UNIT_CHARGES = PrivilegeFactory.valueOf((String)"INV_QUERY_UNIT_CHARGES");
    public static final IPrivilege INV_IFT = PrivilegeFactory.valueOf((String)"INV_IFT");
    public static final IPrivilege INV_PAY_INVOICE = PrivilegeFactory.valueOf((String)"INV_PAY_INVOICE");
    public static final IPrivilege INV_PAY_SELECTED = PrivilegeFactory.valueOf((String)"INV_PAY_SELECTED");
    public static final IPrivilege UNIT_PREPAY_GUARANTEE_ADD = PrivilegeFactory.valueOf((String)"UNIT_PREPAY_GUARANTEE_ADD");
    public static final IPrivilege INV_FINALIZE_INVOICE = PrivilegeFactory.valueOf((String)"INV_FINALIZE_INVOICE");
    public static final IPrivilege ALLOW_VESSEL_TO_YARD_MOVES = PrivilegeFactory.valueOf((String)"ALLOW_VESSEL_TO_YARD_MOVES");
    public static final IPrivilege UNIT_WAIVER_ADD = PrivilegeFactory.valueOf((String)"UNIT_WAIVER_ADD");
    public static final IPrivilege UNIT_GUARANTEE_ADD = PrivilegeFactory.valueOf((String)"UNIT_GUARANTEE_ADD");
    public static final IPrivilege INSPECT_UNIT_ROUTING = PrivilegeFactory.valueOf((String)"INSPECT_UNIT_ROUTING");
    public static final IPrivilege INSPECT_UNIT_POWER = PrivilegeFactory.valueOf((String)"INSPECT_UNIT_POWER");
    public static final IPrivilege INSPECT_UNIT_STORAGE = PrivilegeFactory.valueOf((String)"INSPECT_UNIT_STORAGE");
    public static final IPrivilege INSPECT_UNIT_BILL_OF_LADING = PrivilegeFactory.valueOf((String)"INSPECT_UNIT_BILL_OF_LADING");
    public static final IPrivilege INSPECT_HAZARDOUS_GOODS = PrivilegeFactory.valueOf((String)"INSPECT_HAZARDOUS_GOODS");
    public static final IPrivilege HAZARDOUS_GOODS_ADD = PrivilegeFactory.valueOf((String)"HAZARDOUS_GOODS_ADD");
    public static final IPrivilege HAZARDOUS_GOODS_VIEW = PrivilegeFactory.valueOf((String)"HAZARDOUS_GOODS_VIEW");
    public static final IPrivilege HAZARDOUS_GOODS_UPDATE = PrivilegeFactory.valueOf((String)"HAZARDOUS_GOODS_UPDATE");
    public static final IPrivilege HAZARDOUS_GOODS_DELETE = PrivilegeFactory.valueOf((String)"HAZARDOUS_GOODS_DELETE");
    public static final IPrivilege SUBSIDIARY_RISK_ADD = PrivilegeFactory.valueOf((String)"SUBSIDIARY_RISK_ADD");
    public static final IPrivilege SUBSIDIARY_RISK_VIEW = PrivilegeFactory.valueOf((String)"SUBSIDIARY_RISK_VIEW");
    public static final IPrivilege SUBSIDIARY_RISK_UPDATE = PrivilegeFactory.valueOf((String)"SUBSIDIARY_RISK_UPDATE");
    public static final IPrivilege SUBSIDIARY_RISK_DELETE = PrivilegeFactory.valueOf((String)"SUBSIDIARY_RISK_DELETE");
    public static final IPrivilege VIEW_UFV_MOVE_COUNT = PrivilegeFactory.valueOf((String)"VIEW_UFV_MOVE_COUNT");
    public static final IPrivilege INSPECT_UNIT_LINE_STORAGE = PrivilegeFactory.valueOf((String)"INSPECT_UNIT_LINE_STORAGE");
    public static final IPrivilege INV_UNIT_RECAP = PrivilegeFactory.valueOf((String)"INV_UNIT_RECAP");
    public static final IPrivilege UNIT_UPDATE_POSITION_TO_FROM_PROTECTED_YARD_POSITION = PrivilegeFactory.valueOf((String)"UNIT_UPDATE_POSITION_TO_FROM_PROTECTED_YARD_POSITION");
    public static final IPrivilege INV_FIX_DISCH_MISIDENTIFICATION = PrivilegeFactory.valueOf((String)"INV_FIX_DISCH_MISIDENTIFICATION");

    private InventoryPrivs() {
    }

}
