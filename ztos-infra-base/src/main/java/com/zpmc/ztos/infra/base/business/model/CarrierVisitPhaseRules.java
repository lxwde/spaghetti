package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.enums.argo.CarrierVisitPhaseEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IArgoPropertyKeys;
import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.model.ArgoPrivs;
import com.zpmc.ztos.infra.base.common.model.UserContext;

import java.util.ArrayList;

public class CarrierVisitPhaseRules {

    public static CarrierVisitPhaseEnum[] getValidPhaseTransitions(CarrierVisitPhaseEnum inPhase) {
        return CarrierVisitPhaseRules.getPhaseTransitions(inPhase);
    }

    public static CarrierVisitPhaseEnum[] getValidPhaseTransitionsForRail(CarrierVisitPhaseEnum inPhase) {
        return CarrierVisitPhaseRules.getPhaseTransitions(inPhase);
    }

    public static CarrierVisitPhaseEnum[] getValidPhaseTransitions(CarrierVisitPhaseEnum inPhase, UserContext inUserContext) {
        boolean isArchiveAllowed = ArgoPrivs.ARCHIVE_VESSEL_VISIT.isAllowed(inUserContext);
        return CarrierVisitPhaseRules.getPhaseTransition(inPhase, isArchiveAllowed);
    }

    public static CarrierVisitPhaseEnum[] getValidPhaseTransitionsForRail(CarrierVisitPhaseEnum inPhase, UserContext inUserContext) {
        boolean isArchiveAllowed = ArgoPrivs.ARCHIVE_TRAIN_VISIT.isAllowed(inUserContext);
        return CarrierVisitPhaseRules.getPhaseTransition(inPhase, isArchiveAllowed);
    }

    public static CarrierVisitPhaseEnum[] getValidPhaseTransitions(UserContext inUserContext, CarrierVisitPhaseEnum inCurrentPhase) {
        if (ArgoPrivs.ALLOW_VESSEL_TRAIN_RECALL.isAllowed(inUserContext)) {
            return CarrierVisitPhaseRules.getValidPhaseTransitionsIncludingPrevPhase(inCurrentPhase, inUserContext);
        }
        return CarrierVisitPhaseRules.getValidPhaseTransitions(inCurrentPhase, inUserContext);
    }

    public static CarrierVisitPhaseEnum[] getValidPhaseTransitionsForRail(UserContext inUserContext, CarrierVisitPhaseEnum inCurrentPhase) {
        if (ArgoPrivs.ALLOW_VESSEL_TRAIN_RECALL.isAllowed(inUserContext)) {
            return CarrierVisitPhaseRules.getValidPhaseTransitionsIncludingPrevPhaseForRail(inCurrentPhase, inUserContext);
        }
        return CarrierVisitPhaseRules.getValidPhaseTransitionsForRail(inCurrentPhase, inUserContext);
    }

    public static CarrierVisitPhaseEnum[] getValidPhaseTransitionsIncludingPrevPhase(CarrierVisitPhaseEnum inPhase) {
        return CarrierVisitPhaseRules.getValidPhaseTransitionsIncludingPrevPhase(inPhase, null);
    }

    public static CarrierVisitPhaseEnum[] getValidPhaseTransitionsIncludingPrevPhase(CarrierVisitPhaseEnum inPhase, UserContext inUserContext) {
        CarrierVisitPhaseEnum[] fwdPhases = inUserContext == null ? CarrierVisitPhaseRules.getValidPhaseTransitions(inPhase) : CarrierVisitPhaseRules.getValidPhaseTransitions(inPhase, inUserContext);
        return CarrierVisitPhaseRules.getFwdPreviousPhaseTransitions(inPhase, fwdPhases);
    }

    public static CarrierVisitPhaseEnum[] getValidPhaseTransitionsIncludingPrevPhaseForRail(CarrierVisitPhaseEnum inPhase, UserContext inUserContext) {
        CarrierVisitPhaseEnum[] fwdPhases = inUserContext == null ? CarrierVisitPhaseRules.getValidPhaseTransitionsForRail(inPhase) : CarrierVisitPhaseRules.getValidPhaseTransitionsForRail(inPhase, inUserContext);
        return CarrierVisitPhaseRules.getFwdPreviousPhaseTransitions(inPhase, fwdPhases);
    }

    private static boolean isPrevPhaseNotInFwdPhases(CarrierVisitPhaseEnum inPrevTrans, CarrierVisitPhaseEnum[] inFwdPhases) {
        for (int i = 0; i < inFwdPhases.length; ++i) {
            if (!inFwdPhases[i].equals((Object)inPrevTrans)) continue;
            return false;
        }
        return true;
    }

    public static CarrierVisitPhaseEnum getPreviousPhaseTransition(CarrierVisitPhaseEnum inPhase) {
        if (CarrierVisitPhaseEnum.INBOUND.equals((Object)inPhase)) {
            return CarrierVisitPhaseEnum.CREATED;
        }
        if (CarrierVisitPhaseEnum.ARRIVED.equals((Object)inPhase)) {
            return CarrierVisitPhaseEnum.INBOUND;
        }
        if (CarrierVisitPhaseEnum.WORKING.equals((Object)inPhase)) {
            return CarrierVisitPhaseEnum.ARRIVED;
        }
        if (CarrierVisitPhaseEnum.COMPLETE.equals((Object)inPhase)) {
            return CarrierVisitPhaseEnum.WORKING;
        }
        if (CarrierVisitPhaseEnum.DEPARTED.equals((Object)inPhase)) {
            return CarrierVisitPhaseEnum.COMPLETE;
        }
        if (CarrierVisitPhaseEnum.CLOSED.equals((Object)inPhase)) {
            return CarrierVisitPhaseEnum.DEPARTED;
        }
        return null;
    }

    public static CarrierVisitPhaseEnum[] getPhaseTransition(CarrierVisitPhaseEnum inPhase, boolean inArchiveAllowed) {
        ArrayList<CarrierVisitPhaseEnum> phaseEnumList = new ArrayList<CarrierVisitPhaseEnum>();
        if (CarrierVisitPhaseEnum.CREATED.equals((Object)inPhase)) {
            return new CarrierVisitPhaseEnum[]{CarrierVisitPhaseEnum.INBOUND, CarrierVisitPhaseEnum.ARRIVED, CarrierVisitPhaseEnum.CANCELED};
        }
        if (CarrierVisitPhaseEnum.INBOUND.equals((Object)inPhase)) {
            return new CarrierVisitPhaseEnum[]{CarrierVisitPhaseEnum.ARRIVED, CarrierVisitPhaseEnum.CANCELED};
        }
        if (CarrierVisitPhaseEnum.ARRIVED.equals((Object)inPhase)) {
            return new CarrierVisitPhaseEnum[]{CarrierVisitPhaseEnum.WORKING, CarrierVisitPhaseEnum.COMPLETE};
        }
        if (CarrierVisitPhaseEnum.WORKING.equals((Object)inPhase)) {
            return new CarrierVisitPhaseEnum[]{CarrierVisitPhaseEnum.COMPLETE, CarrierVisitPhaseEnum.DEPARTED};
        }
        if (CarrierVisitPhaseEnum.COMPLETE.equals((Object)inPhase)) {
            return new CarrierVisitPhaseEnum[]{CarrierVisitPhaseEnum.DEPARTED};
        }
        if (CarrierVisitPhaseEnum.DEPARTED.equals((Object)inPhase)) {
            return new CarrierVisitPhaseEnum[]{CarrierVisitPhaseEnum.CLOSED};
        }
        if (CarrierVisitPhaseEnum.CLOSED.equals((Object)inPhase)) {
            phaseEnumList.add(CarrierVisitPhaseEnum.DEPARTED);
            if (inArchiveAllowed) {
                phaseEnumList.add(CarrierVisitPhaseEnum.ARCHIVED);
            }
    //        return phaseEnumList.toArray((T[])new CarrierVisitPhaseEnum[phaseEnumList.size()]);
        }
        if (CarrierVisitPhaseEnum.CANCELED.equals((Object)inPhase)) {
            if (inArchiveAllowed) {
                return new CarrierVisitPhaseEnum[]{CarrierVisitPhaseEnum.ARCHIVED};
            }
            return new CarrierVisitPhaseEnum[0];
        }
        if (CarrierVisitPhaseEnum.ARCHIVED.equals((Object)inPhase)) {
            return new CarrierVisitPhaseEnum[]{CarrierVisitPhaseEnum.DEPARTED, CarrierVisitPhaseEnum.CLOSED};
        }
        return new CarrierVisitPhaseEnum[0];
    }

    public static CarrierVisitPhaseEnum[] getFwdPreviousPhaseTransitions(CarrierVisitPhaseEnum inPhase, CarrierVisitPhaseEnum[] inFwdPhases) {
        CarrierVisitPhaseEnum prevTrans = CarrierVisitPhaseRules.getPreviousPhaseTransition(inPhase);
        if (prevTrans == null) {
            return inFwdPhases;
        }
        if (CarrierVisitPhaseRules.isPrevPhaseNotInFwdPhases(prevTrans, inFwdPhases)) {
            int sourceArrLen = inFwdPhases.length;
            CarrierVisitPhaseEnum[] withPrevPhase = new CarrierVisitPhaseEnum[sourceArrLen + 1];
            System.arraycopy(inFwdPhases, 0, withPrevPhase, 0, sourceArrLen);
            withPrevPhase[sourceArrLen] = prevTrans;
            return withPrevPhase;
        }
        return inFwdPhases;
    }

    public static CarrierVisitPhaseEnum[] getPhaseTransitions(CarrierVisitPhaseEnum inPhase) {
        if (CarrierVisitPhaseEnum.CREATED.equals((Object)inPhase)) {
            return new CarrierVisitPhaseEnum[]{CarrierVisitPhaseEnum.INBOUND, CarrierVisitPhaseEnum.ARRIVED, CarrierVisitPhaseEnum.CANCELED};
        }
        if (CarrierVisitPhaseEnum.INBOUND.equals((Object)inPhase)) {
            return new CarrierVisitPhaseEnum[]{CarrierVisitPhaseEnum.ARRIVED, CarrierVisitPhaseEnum.CANCELED};
        }
        if (CarrierVisitPhaseEnum.ARRIVED.equals((Object)inPhase)) {
            return new CarrierVisitPhaseEnum[]{CarrierVisitPhaseEnum.WORKING, CarrierVisitPhaseEnum.COMPLETE};
        }
        if (CarrierVisitPhaseEnum.WORKING.equals((Object)inPhase)) {
            return new CarrierVisitPhaseEnum[]{CarrierVisitPhaseEnum.COMPLETE, CarrierVisitPhaseEnum.DEPARTED};
        }
        if (CarrierVisitPhaseEnum.COMPLETE.equals((Object)inPhase)) {
            return new CarrierVisitPhaseEnum[]{CarrierVisitPhaseEnum.DEPARTED};
        }
        if (CarrierVisitPhaseEnum.DEPARTED.equals((Object)inPhase)) {
            return new CarrierVisitPhaseEnum[]{CarrierVisitPhaseEnum.CLOSED};
        }
        if (CarrierVisitPhaseEnum.CLOSED.equals((Object)inPhase)) {
            return new CarrierVisitPhaseEnum[]{CarrierVisitPhaseEnum.DEPARTED, CarrierVisitPhaseEnum.ARCHIVED};
        }
        if (CarrierVisitPhaseEnum.CANCELED.equals((Object)inPhase)) {
            return new CarrierVisitPhaseEnum[]{CarrierVisitPhaseEnum.ARCHIVED};
        }
        if (CarrierVisitPhaseEnum.ARCHIVED.equals((Object)inPhase)) {
            return new CarrierVisitPhaseEnum[]{CarrierVisitPhaseEnum.DEPARTED, CarrierVisitPhaseEnum.CLOSED};
        }
        return new CarrierVisitPhaseEnum[0];
    }

    public static void validatePhase(UserContext inUserContext, CarrierVisitPhaseEnum inCurrentPhase, CarrierVisitPhaseEnum inNewPhase) throws BizViolation {
        CarrierVisitPhaseEnum[] validPhases;
        if (inNewPhase == null) {
            return;
        }
        for (CarrierVisitPhaseEnum phase : validPhases = CarrierVisitPhaseRules.getValidPhaseTransitions(inUserContext, inCurrentPhase)) {
            if (!inNewPhase.equals((Object)phase)) continue;
            return;
        }
        throw BizViolation.create((IPropertyKey) IArgoPropertyKeys.ADVANCE_ILLEGAL, null, (Object)((Object)inCurrentPhase), (Object)((Object)inNewPhase));
    }

}
