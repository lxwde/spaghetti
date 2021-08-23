package com.zpmc.ztos.infra.base.common.systems;

import com.zpmc.ztos.infra.base.business.interfaces.IArgoField;
import com.zpmc.ztos.infra.base.business.interfaces.IDomainQuery;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.callbacks.CarinaPersistenceCallback;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.database.PersistenceTemplate;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.utils.TimeUtils;
import com.zpmc.ztos.infra.base.utils.QueryUtils;

import java.util.Calendar;
import java.util.Date;

public class ArgoSequenceProvider {
    private Long _nextValue;

    protected synchronized Long getNextSeqValue(String inSeqId, Long inTopologyGkey) {
        final String seqId = inSeqId;
        final Long topologyGkey = inTopologyGkey;
        PersistenceTemplate pt = this.getPersistenceTemplate();
        pt.invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
//                IDomainQuery dq = QueryUtils.createDomainQuery((String)"ArgoSequence").addDqPredicate(PredicateFactory.eq((MetafieldId)ArgoField.SEQ_ID, (Object)seqId)).addDqPredicate(PredicateFactory.eq((MetafieldId)ArgoField.SEQ_TOPOLOGY_GKEY, (Object)topologyGkey)).setSelectForUpdate(true);
//                ArgoSequence seq = (ArgoSequence) HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
//                if (seq == null) {
//                    seq = ArgoSequence.create(seqId, topologyGkey);
//                }
//                ArgoSequenceProvider.this._nextValue = seq.getSeqNextValue();
//                ArgoSequenceProvider.this._nextValue = ArgoSequenceProvider.this._nextValue == null ? 1L : ArgoSequenceProvider.this._nextValue;
//                seq.setSeqNextValue(ArgoSequenceProvider.this._nextValue + 1L);
//                ArgoSequenceProvider.resetSeqNbrIfNeeded(seq);
            }
        });
        HibernateApi.getInstance().flush();
        return this._nextValue;
    }

    protected PersistenceTemplate getPersistenceTemplate() {
        return new PersistenceTemplate(ContextHelper.getThreadUserContext());
    }

//    private static void resetSeqNbrIfNeeded(ArgoSequence inSeq) {
//        Long value;
//        String s;
//        Long maxDigits = inSeq.getSeqMaximumDigits();
//        if (maxDigits != null && (long)(s = String.valueOf(value = inSeq.getSeqNextValue())).length() > maxDigits) {
//            inSeq.setSeqNextValue(1L);
//        }
//    }

    protected Long getNextSeqValueWithinDay(String inSeqId, Long inTopologyGkey) {
        return this.getNextSeqValueWithinPeriod(inSeqId, inTopologyGkey, 6);
    }

    protected Long getNextSeqValueWithinWeek(String inSeqId, Long inTopologyGkey) {
        return this.getNextSeqValueWithinPeriod(inSeqId, inTopologyGkey, 3);
    }

    protected Long getNextSeqValueWithinMonth(String inSeqId, Long inTopologyGkey) {
        return this.getNextSeqValueWithinPeriod(inSeqId, inTopologyGkey, 2);
    }

    protected Long getNextSeqValueWithinYear(String inSeqId, Long inTopologyGkey) {
        return this.getNextSeqValueWithinPeriod(inSeqId, inTopologyGkey, 1);
    }

    private Long getNextSeqValueWithinPeriod(String inSeqId, Long inTopologyGkey, int inCalendarPeriod) {
        final String seqId = inSeqId;
        final Long topologyGkey = inTopologyGkey;
        final int calendarPeriod = inCalendarPeriod;
        PersistenceTemplate pt = new PersistenceTemplate(ContextHelper.getThreadUserContext());
        pt.invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                IDomainQuery dq = QueryUtils.createDomainQuery((String)"ArgoSequence").addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.SEQ_ID, (Object)seqId)).addDqPredicate(PredicateFactory.eq((IMetafieldId) IArgoField.SEQ_TOPOLOGY_GKEY, (Object)topologyGkey)).setSelectForUpdate(true);
//                ArgoSequence seq = (ArgoSequence)HibernateApi.getInstance().getUniqueEntityByDomainQuery(dq);
//                if (seq == null) {
//                    seq = ArgoSequence.create(seqId, topologyGkey);
//                } else if (ArgoSequenceProvider.this.timeToReset(calendarPeriod, seq.getSeqPreviousDate())) {
//                    seq.setSeqNextValue(new Long(1L));
//                }
//                ArgoSequenceProvider.this._nextValue = seq.getSeqNextValue();
//                seq.setSeqNextValue(new Long(seq.getSeqNextValue() + 1L));
//                seq.setSeqPreviousDate(new Date(TimeUtils.getCurrentTimeMillis()));
            }
        });
        return this._nextValue;
    }

    private boolean timeToReset(int inCalendarPeriod, Date inPreviousDate) {
        boolean reset = true;
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date(TimeUtils.getCurrentTimeMillis()));
        Calendar previous = Calendar.getInstance();
        previous.setTime(inPreviousDate);
        if (rightNow.get(inCalendarPeriod) == previous.get(inCalendarPeriod)) {
            reset = false;
        }
        return reset;
    }
}
