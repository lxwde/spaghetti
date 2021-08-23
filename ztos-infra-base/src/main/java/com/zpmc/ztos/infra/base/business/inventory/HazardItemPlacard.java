package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.dataobject.HazardItemPlacardDO;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;

public class HazardItemPlacard extends HazardItemPlacardDO {
    public static HazardItemPlacard createHazardItemPlacardEntity(HazardItem inHazardItem) {
        HazardItemPlacard hzrdip = new HazardItemPlacard();
        hzrdip.setHzrdipHazardItem(inHazardItem);
        HibernateApi.getInstance().save((Object)hzrdip);
        return hzrdip;
    }

    public HazardItemPlacard deepClone() {
        HazardItemPlacard copyHzrdiplacard;
        try {
            copyHzrdiplacard = (HazardItemPlacard)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new Error("This should not occur since we implement Cloneable");
        }
        copyHzrdiplacard.setPrimaryKey(null);
        return copyHzrdiplacard;
    }

    public void updateParentHazardItem(HazardItem inHazardItem) {
        if (inHazardItem == null) {
            throw BizFailure.create((String)"Update HazardItemPlacard ParentHazard is  null .This is not a valid entry!");
        }
        this.setHzrdipHazardItem(inHazardItem);
    }
}
