package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.dataobject.RoutingDO;
import com.zpmc.ztos.infra.base.business.model.CarrierVisit;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.scopes.Complex;
import org.apache.commons.lang.ObjectUtils;

public class Routing extends RoutingDO {
    public static Routing createEmptyRouting() {
        Routing routing = new Routing();
        routing.setRtgDeclaredCv(CarrierVisit.getGenericCarrierVisit((Complex) ContextHelper.getThreadComplex()));
        return routing;
    }

    public Routing getDefensiveCopy() {
        try {
            return (Routing)this.clone();
        }
        catch (CloneNotSupportedException e) {
            throw BizFailure.wrap((Throwable)e);
        }
    }

    public String toString() {
        return "Routing[" + (Object)this.getRtgDeclaredCv() + ':' + (Object)this.getRtgPOL() + ':' + (Object)this.getRtgPOD1() + ':' + (Object)this.getRtgPOD2() + ':' + this.getRtgDescription() + ':' + this.getRtgBondedDestination() + ']';
    }

    public boolean equals(Object inThat) {
        if (!(inThat instanceof Routing)) {
            return false;
        }
        if (this == inThat) {
            return true;
        }
        Routing that = (Routing)inThat;
        return ObjectUtils.equals((Object)this.getRtgCarrierService(), (Object)that.getRtgCarrierService()) && ObjectUtils.equals((Object)this.getRtgDeclaredCv(), (Object)that.getRtgDeclaredCv()) && ObjectUtils.equals((Object)this.getRtgDescription(), (Object)that.getRtgDescription()) && ObjectUtils.equals((Object)this.getRtgExportClearanceNbr(), (Object)that.getRtgExportClearanceNbr()) && ObjectUtils.equals((Object)this.getRtgGroup(), (Object)that.getRtgGroup()) && ObjectUtils.equals((Object)this.getRtgOPL(), (Object)that.getRtgOPL()) && ObjectUtils.equals((Object)this.getRtgOPT1(), (Object)that.getRtgOPT1()) && ObjectUtils.equals((Object)this.getRtgOPT2(), (Object)that.getRtgOPT2()) && ObjectUtils.equals((Object)this.getRtgOPT3(), (Object)that.getRtgOPT3()) && ObjectUtils.equals((Object)this.getRtgPOD1(), (Object)that.getRtgPOD1()) && ObjectUtils.equals((Object)this.getRtgPOD2(), (Object)that.getRtgPOD2()) && ObjectUtils.equals((Object)this.getRtgPOL(), (Object)that.getRtgPOL()) && ObjectUtils.equals((Object)this.getRtgReturnToLocation(), (Object)that.getRtgReturnToLocation()) && ObjectUtils.equals((Object)this.getRtgTruckingCompany(), (Object)that.getRtgTruckingCompany()) && ObjectUtils.equals((Object)this.getRtgBondedDestination(), (Object)that.getRtgBondedDestination());
    }

    public int hashCode() {
        return this.getRtgDeclaredCv().hashCode();
    }

    public Class getArchiveClass() {
//        return ArchiveRouting.class;
        return null;
    }

    public boolean doArchive() {
        return true;
    }

}
