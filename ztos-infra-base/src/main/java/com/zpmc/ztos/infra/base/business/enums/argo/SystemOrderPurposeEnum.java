package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.business.interfaces.IOrderPurpose;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SystemOrderPurposeEnum extends AtomizedEnum implements IOrderPurpose {

    private String _descriptionUnlocalized;
    private boolean _isObsolete;
    private OrderPurposeEnum _replacedBy;
    private String _externalId;
    private static Map _externalId2Enum = new HashMap();
    private static final Logger LOGGER = Logger.getLogger(SystemOrderPurposeEnum.class);

    public SystemOrderPurposeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, String inDescription, String inReplacedBy, String inExternalId) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
        this._descriptionUnlocalized = inDescription;
        this._isObsolete = StringUtils.isNotEmpty((String)inReplacedBy);
        if (!this._isObsolete) {
            this._externalId = inExternalId;
            if (!inKey.equals(inExternalId)) {
                LOGGER.info((Object)("OrderPurposeEnum: internal-->external ID's differ: " + inKey + "-->" + inExternalId));
            }
            if (_externalId2Enum.get(inExternalId) != null) {
                throw BizFailure.create((String)("Problem in OrderPurposeEnum.xml - multiple Order Purposes have the same external id: " + inExternalId));
            }
            _externalId2Enum.put(inExternalId, this);
        } else {
            if (!StringUtils.isEmpty((String)inExternalId)) {
                throw BizFailure.create((String)("Problem in OrderPurposeEnum.xml - expected null inExternalId for obsolete enum: " + inKey + " but received: " + inExternalId));
            }
            if (!"OBSOLETE".equals(inReplacedBy)) {
                this._replacedBy = (OrderPurposeEnum) SystemOrderPurposeEnum.getEnum(OrderPurposeEnum.class, (String)inReplacedBy);
                if (this._replacedBy == null) {
                    throw BizFailure.create((String)("Problem in OrderPurposeEnum.xml - could not find replacing Enum with Key: " + inReplacedBy + " for " + inKey));
                }
                if (!inKey.equals(this._replacedBy.getExternalId())) {
                    LOGGER.info((Object)("OrderPurposeEnum: obsolete order purpose: " + inKey + " is now mapped to: " + this._replacedBy.getKey() + " with external id: " + this._replacedBy.getExternalId()));
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

    public boolean isObsolete() {
        return this._isObsolete;
    }

    public OrderPurposeEnum getReplacedBy() {
        return this._replacedBy;
    }

    @Override
    public String getExternalId() {
        return this._externalId;
    }

    public static OrderPurposeEnum getOrderPurposeEnumByExternalId(String inExternalId) {
        return (OrderPurposeEnum)_externalId2Enum.get(inExternalId);
    }
}
