package com.zpmc.ztos.infra.base.business.inventory;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.dataobject.UnitComboDO;
import com.zpmc.ztos.infra.base.common.configs.ArgoConfig;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UnitCombo extends UnitComboDO {

    private static final Logger LOGGER = Logger.getLogger(UnitCombo.class);

    static UnitCombo internalCreate() {
        UnitCombo uc = new UnitCombo();
        HibernateApi.getInstance().save((Object)uc);
        return uc;
    }

    public static UnitCombo create(Unit inUnit) {
        UnitCombo uc = UnitCombo.internalCreate();
        uc.addUnit(inUnit);
        return uc;
    }

    private void deleteIfEmpty() {
        if (this.size() == 0) {
            HibernateApi.getInstance().delete((Object)this);
        }
    }

    private void addUnit(@NotNull Unit inUnit) {
        Set<Unit> units = this.getUcUnitsSafe();
        if (units == null) {
            units = new HashSet<Unit>();
            this.setUcUnits(units);
        }
        units.add(inUnit);
        inUnit.setUnitCombo(this);
    }

    private void removeUnit(Unit inUnit) {
        Set<Unit> units = this.getUcUnitsNullSafe();
        if (!units.isEmpty()) {
            units.remove(inUnit);
            inUnit.setUnitCombo(null);
        }
        this.deleteIfEmpty();
    }

    public boolean contains(@NotNull UnitNode inUnitNode) {
        return this.getUcUnitsNullSafe().contains(inUnitNode.getUnit());
    }

    public int size() {
        return this.getUcUnitsNullSafe().size();
    }

    public void add(@NotNull UnitNode inUnitNode) {
        this.addUnit(inUnitNode.getUnit());
    }

    public Iterator<UnitNode> iterator() {
        return new UnitNodeItr();
    }

    public void remove(UnitNode inUnitNode) {
        this.removeUnit(inUnitNode.getUnit());
    }

    public void clear() {
        this.getUcUnitsNullSafe().clear();
        HibernateApi.getInstance().delete((Object)this);
    }

    public Class getArchiveClass() {
 //       return ArchiveUnitCombo.class;
        return null;
    }

    public boolean doArchive() {
        UserContext userContext = ContextHelper.getThreadUserContext();
        return ArgoConfig.ARCHIVE_UNITS_PRIOR_TO_PURGE.isOn(userContext);
    }

    @NotNull
    Set<Unit> getUcUnitsNullSafe() {
        Set<Unit> unitSet = this.getUcUnitsSafe();
        return unitSet == null ? Collections.emptySet() : unitSet;
    }

    @Nullable
    Set<Unit> getUcUnitsSafe() {
        try {
            return super.getUcUnits();
        }
        catch (Throwable ex) {
            LOGGER.error((Object)("Error while getting combo units: " + ex));
            return null;
        }
    }

    private class UnitNodeItr
            implements Iterator<UnitNode> {
        private Iterator<Unit> _unitItr;

        UnitNodeItr() {
            Set<Unit> unitSet = UnitCombo.this.getUcUnitsNullSafe();
            this._unitItr = unitSet.iterator();
        }

        @Override
        public boolean hasNext() {
            return this._unitItr.hasNext();
        }

        @Override
        public UnitNode next() {
            Unit unit = this._unitItr.next();
            return unit.getUnitNode();
        }

        @Override
        public void remove() {
            this._unitItr.remove();
        }
    }
}
