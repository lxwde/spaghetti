package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.business.interfaces.IEventType;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemEventTypeEnum extends AtomizedEnum implements IEventType {
    private boolean _isBillable;
    private boolean _isFacilityService;
    private boolean _isNotifiable;
    private boolean _isAcknowledgeable;
    private boolean _isEventRecorded;
    private LogicalEntityEnum _logicalEntityEnum;
    private String _descriptionUnlocalized;
    private boolean _isObsolete;
    private EventEnum _replacedBy;
    private String _externalId;
    private FunctionalAreaEnum _functionalArea;
    private static Map _externalId2Enum = new HashMap();
    private static final Logger LOGGER = Logger.getLogger(SystemEventTypeEnum.class);

    public SystemEventTypeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, String inDescription, boolean inBillable, boolean inIsFacilityService, boolean inIsNotifiable, boolean inIsEventRecorded, boolean inIsAcknowledgeable, LogicalEntityEnum inLogicalEntityEnum, String inReplacedBy, String inFunctionalArea, String inExternalId) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
        this._isBillable = inBillable;
        this._logicalEntityEnum = inLogicalEntityEnum;
        this._isNotifiable = inIsNotifiable;
        this._isEventRecorded = inIsEventRecorded;
        this._isAcknowledgeable = inIsAcknowledgeable;
        this._isFacilityService = inIsFacilityService;
        this._descriptionUnlocalized = inDescription;
        this._isObsolete = StringUtils.isNotEmpty((String)inReplacedBy);
        this._functionalArea = inFunctionalArea != null ? FunctionalAreaEnum.getEnum(inFunctionalArea) : null;
        if (!this._isObsolete) {
            this._externalId = inExternalId;
            if (!inKey.equals(inExternalId)) {
                LOGGER.info((Object)("EventEnum: internal-->external ID's differ: " + inKey + "-->" + inExternalId));
            }
            if (_externalId2Enum.get(inExternalId) != null) {
                throw BizFailure.create((String)("Problem in EventEnum.xml - multiple Events have the same external id: " + inExternalId));
            }
            _externalId2Enum.put(inExternalId, this);
        } else {
            if (!StringUtils.isEmpty((String)inExternalId)) {
                throw BizFailure.create((String)("Problem in EventEnum.xml - expected null inExternalId for obsolete enum: " + inKey + " but received: " + inExternalId));
            }
            if (!"OBSOLETE".equals(inReplacedBy)) {
                this._replacedBy = (EventEnum) SystemEventTypeEnum.getEnum(EventEnum.class, (String)inReplacedBy);
                if (this._replacedBy == null) {
                    throw BizFailure.create((String)("Problem in EventEnum.xml - could not find replacing Enum with Key: " + inReplacedBy + " for " + inKey));
                }
                if (!inKey.equals(this._replacedBy.getExternalId())) {
                    LOGGER.info((Object)("EventEnum: obsolete event: " + inKey + " is now mapped to: " + this._replacedBy.getKey() + " with external id: " + this._replacedBy.getExternalId()));
                }
            }
        }
    }

    @Override
    public String getId() {
        return this.getKey();
    }

    @Override
    public String getDescription() {
        return this._descriptionUnlocalized;
    }

    @Override
    public boolean isBuiltInEventType() {
        return true;
    }

    @Override
    public boolean isFacilityService() {
        return this._isFacilityService;
    }

    @Override
    public boolean isBillable() {
        return this._isBillable;
    }

    @Override
    public boolean isNotifiable() {
        return this._isNotifiable;
    }

    @Override
    public boolean isAcknowledgeable() {
        return this._isAcknowledgeable;
    }

    @Override
    public boolean isEventRecorded() {
        return this._isEventRecorded;
    }

    @Override
    public boolean canBulkUpdate() {
        return false;
    }

    @Override
    public LogicalEntityEnum getAppliesToEntityType() {
        return this._logicalEntityEnum;
    }

    @Override
    public List getEventEffects() {
        return null;
    }

    public boolean isObsolete() {
        return this._isObsolete;
    }

    public EventEnum getReplacedBy() {
        return this._replacedBy;
    }

    @Override
    public String getExternalId() {
        return this._externalId;
    }

    @Override
    public FunctionalAreaEnum getFunctionalArea() {
        return this._functionalArea;
    }

    @Override
    public boolean isAutoExtractEvent() {
        return false;
    }

    public static EventEnum getEventEnumByExternalId(String inExternalId) {
        return (EventEnum)_externalId2Enum.get(inExternalId);
    }

}
