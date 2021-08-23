package com.zpmc.ztos.infra.base.business.inventory;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.UnitYardVisitDO;
import com.zpmc.ztos.infra.base.business.enums.argo.WiMoveStageEnum;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.LocPosition;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
import com.zpmc.ztos.infra.base.business.plans.WorkInstruction;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.events.AuditEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.model.Ordering;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

public class UnitYardVisit extends UnitYardVisitDO {
    private static final IMetafieldId UYV_LINE_OPERATOR = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) UnitField.UYV_UNIT, (IMetafieldId)IInventoryField.UNIT_LINE_OPERATOR);
    private static final IMetafieldId UYV_OWNER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) UnitField.UYV_PRIMARY_UE, (IMetafieldId) UnitField.UE_EQ_OWNER);
    private static final Logger LOGGER = Logger.getLogger(UnitYardVisit.class);

    public static UnitYardVisit hydrate(Serializable inPrimaryKey) {
        return (UnitYardVisit) HibernateApi.getInstance().load(UnitYardVisit.class, inPrimaryKey);
    }

    public UnitYardVisit() {
    }

    private UnitYardVisit(Yard inYard, UnitFacilityVisit inUfv) {
        if (!ObjectUtils.equals((Object)inYard.getYrdFacility(), (Object)inUfv.getUfvFacility())) {
            throw BizFailure.create((IPropertyKey) IInventoryPropertyKeys.UNITS__YRD_NOT_IN_FCY, null, (Object)inYard, (Object)inUfv.getUfvFacility());
        }
        this.setUyvUfv(inUfv);
        ArrayList<UnitYardVisit> uyvList = (ArrayList<UnitYardVisit>) inUfv.getUfvUyvList();
        if (uyvList == null) {
            uyvList = new ArrayList<UnitYardVisit>();
            inUfv.setUfvUyvList(uyvList);
        }
        uyvList.add(this);
        this.setUyvYard(inYard);
    }

    protected static UnitYardVisit createUnitYardVisit(Yard inYard, UnitFacilityVisit inUfv) {
        return new UnitYardVisit(inYard, inUfv);
    }

    public AuditEvent vetAuditEvent(AuditEvent inAuditEvent) {
        return inAuditEvent;
    }

    @NotNull
    public List<WorkInstruction> getSortedMoveChain() {
        Map<Double, List<Long>> dupes;
        LinkedList<WorkInstruction> wiMoveChain = (LinkedList<WorkInstruction>) this.getUyvWiList();
        if (wiMoveChain != null && !wiMoveChain.isEmpty()) {
            Collections.sort(wiMoveChain, new Comparator<WorkInstruction>(){

                @Override
                public int compare(WorkInstruction inWi1, WorkInstruction inWi2) {
                    return inWi1.getWiMoveNumber() < inWi2.getWiMoveNumber() ? -1 : (inWi1.getWiMoveNumber() > inWi2.getWiMoveNumber() ? 1 : 0);
                }
            });
        } else {
            wiMoveChain = new LinkedList<WorkInstruction>();
        }
        if (LOGGER.isTraceEnabled() && !(dupes = this.getWisInMoveChainWithDupeMoveNums(wiMoveChain)).isEmpty()) {
            LOGGER.trace((Object)this.createDupeWiMoveNumLoggingMessage(dupes));
        }
        return wiMoveChain;
    }

    @Nullable
    String createDupeWiMoveNumLoggingMessage(@NotNull Map<Double, List<Long>> inMoveNumToWiDupes) {
        if (inMoveNumToWiDupes.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder("The following WIs with duplicate move numbers were found for UYV " + this.getPrimaryKey() + " : ");
        for (Double moveNum : inMoveNumToWiDupes.keySet()) {
            sb.append(" Move # : " + moveNum);
            sb.append(" ; associated WIs : " + inMoveNumToWiDupes.get(moveNum));
        }
        return sb.toString();
    }

    @NotNull
    Map<Double, List<Long>> getWisInMoveChainWithDupeMoveNums(@NotNull List<WorkInstruction> inWiMoveChain) {
        if (inWiMoveChain == null) {
            return Collections.EMPTY_MAP;
        }
        HashedMap dupes = new HashedMap();
        Double lastMoveNumber = null;
        Long lastWiGkey = null;
        for (WorkInstruction workInstruction : inWiMoveChain) {
            if (lastMoveNumber != null && lastWiGkey != null && lastMoveNumber.equals(workInstruction.getWiMoveNumber())) {
                List existingWis = (List)dupes.get(lastMoveNumber);
                if (existingWis == null) {
                    ArrayList<Long> wis = new ArrayList<Long>();
                    wis.add(lastWiGkey);
                    wis.add(workInstruction.getWiGkey());
                    dupes.put(lastMoveNumber, wis);
                } else {
                    existingWis.add(workInstruction.getWiGkey());
                    dupes.put(lastMoveNumber, existingWis);
                }
            }
            lastMoveNumber = workInstruction.getWiMoveNumber();
            lastWiGkey = workInstruction.getWiGkey();
        }
        return dupes;
    }

    public final String toString() {
        Equipment eq;
        String eqId = null;
        UnitFacilityVisit ufv = this.getUyvUfv();
        Unit unit = ufv.getUfvUnit();
        UnitEquipment ue = unit.getUnitPrimaryUe();
        if (ue != null && (eq = ue.getUeEquipment()) != null) {
            eqId = eq.getEqIdFull();
        }
        return "UYV[" + this.getCacheEntityName() + ':' + unit.getUnitVisitState().getKey() + ':' + ufv.getUfvLastKnownPosition().toString() + ']';
    }

    public final String getCacheEntityName() {
        String unitId;
        UnitEquipment ue;
        UnitFacilityVisit ufv = this.getUyvUfv();
        Unit unit = ufv == null ? null : ufv.getUfvUnit();
        UnitEquipment unitEquipment = ue = unit == null ? null : unit.getUnitPrimaryUe();
        if (ue == null && unit != null && (unitId = unit.getUnitId()) != null) {
            return unitId;
        }
        Equipment eq = ue == null ? null : ue.getUeEquipment();
        String eqId = eq == null ? "!" : eq.getEqIdFull();
        return eqId;
    }

    public List ensureWiList() {
        ArrayList wiList = (ArrayList) this.getUyvWiList();
        if (wiList == null) {
            wiList = new ArrayList();
            this.setUyvWiList(wiList);
        }
        return wiList;
    }

    public static UnitYardVisit findByPkey(Serializable inYardGkey, Serializable inUyvPkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"UnitYardVisit").addDqPredicate(PredicateFactory.eq((IMetafieldId) IInventoryField.UYV_YARD, (Object)inYardGkey)).addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.UYV_PKEY, (Object)inUyvPkey));
        return (UnitYardVisit)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    @Nullable
    public static UnitYardVisit findByGkey(Serializable inUyvPkey) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"UnitYardVisit").addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.UYV_GKEY, (Object)inUyvPkey));
        return (UnitYardVisit)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
    }

    @Override
    public Serializable getPrimaryKey() {
        return this.getUyvGkey();
    }

    public Boolean getUnitYardVisitHasPlannedMove() {
        List uyvWiList = this.getUyvWiList();
        if (uyvWiList != null && !uyvWiList.isEmpty()) {
            for (int i = 0; i < uyvWiList.size(); ++i) {
                WiMoveStageEnum currentMoveStage;
                WorkInstruction currentWI = (WorkInstruction)uyvWiList.get(i);
                if (currentWI == null || WiMoveStageEnum.COMPLETE.equals((Object)(currentMoveStage = currentWI.getWiMoveStage()))) continue;
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static MetafieldIdList getPredicateFields() {
        MetafieldIdList ids = new MetafieldIdList();
        ids.add(UnitField.getQualifiedField(UnitField.UYV_FACILITY, "UnitYardVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UYV_OPERATOR, "UnitYardVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UYV_PRIMARY_EQTYPE, "UnitYardVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UYV_CATEGORY, "UnitYardVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UYV_REQUIRES_POWER, "UnitYardVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UYV_POS_LOC_TYPE, "UnitYardVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UYV_ORIGIN, "UnitYardVisit"));
        ids.add(UnitField.getQualifiedField(UnitField.UYV_CMDTY, "UnitYardVisit"));
        ids.add(UnitField.getQualifiedField(UYV_LINE_OPERATOR, "UnitYardVisit"));
        ids.add(UnitField.getQualifiedField(UYV_OWNER, "UnitYardVisit"));
        return ids;
    }

    protected UnitYardVisit clone(UnitFacilityVisit inNewUfv) {
        return UnitYardVisit.createUnitYardVisit(this.getUyvYard(), inNewUfv);
    }

    public Class getArchiveClass() {
 //       return ArchiveUnitYardVisit.class;
        return null;
    }

    public boolean doArchive() {
        return true;
    }

    @Nullable
    public WorkInstruction getNextWorkInstruction() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_UYV, (Object)this.getUyvGkey())).addDqPredicate(PredicateFactory.not((IPredicate)PredicateFactory.in((IMetafieldId)IMovesField.WI_MOVE_STAGE, Arrays.asList(new WiMoveStageEnum[]{WiMoveStageEnum.PUT_COMPLETE, WiMoveStageEnum.COMPLETE})))).addDqOrdering(Ordering.asc((IMetafieldId)IMovesField.WI_MOVE_NUMBER)).addDqOrdering(Ordering.asc((IMetafieldId)IMovesField.WI_GKEY));
        dq.setMaxResults(1);
        List entities = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (entities == null || entities.isEmpty()) {
            return null;
        }
        return (WorkInstruction)entities.get(0);
    }

    @Nullable
    public WorkInstruction getLastWorkInstruction() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"WorkInstruction").addDqPredicate(PredicateFactory.eq((IMetafieldId)IMovesField.WI_UYV, (Object)this.getUyvGkey())).addDqPredicate(PredicateFactory.not((IPredicate)PredicateFactory.in((IMetafieldId)IMovesField.WI_MOVE_STAGE, Arrays.asList(new WiMoveStageEnum[]{WiMoveStageEnum.PUT_COMPLETE, WiMoveStageEnum.COMPLETE})))).addDqOrdering(Ordering.desc((IMetafieldId)IMovesField.WI_MOVE_NUMBER));
        dq.setMaxResults(1);
        List entities = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (entities == null || entities.isEmpty()) {
            return null;
        }
        return (WorkInstruction)entities.get(0);
    }

    public LocPosition getNextWiPosition() {
        WorkInstruction wi = this.getNextWorkInstruction();
        return wi == null ? null : wi.getWiPosition();
    }

    public void deleteWis(List<WorkInstruction> inWisToDelete) {
        int countDeleted = 0;
        Iterator wiItr = this.getUyvWiList().iterator();
        while (wiItr.hasNext()) {
            WorkInstruction theWi = (WorkInstruction)wiItr.next();
            if (!inWisToDelete.contains(theWi)) continue;
            wiItr.remove();
            HibernateApi.getInstance().delete((Object)theWi, true);
            ++countDeleted;
        }
        if (countDeleted < inWisToDelete.size()) {
            throw BizFailure.create((String)("Did not find all WIs to delete for " + this + ": " + WorkInstruction.listOfWisAsString(inWisToDelete)));
        }
    }
}
