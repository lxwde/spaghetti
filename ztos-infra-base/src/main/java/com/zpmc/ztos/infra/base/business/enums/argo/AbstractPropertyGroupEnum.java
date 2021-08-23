package com.zpmc.ztos.infra.base.business.enums.argo;

import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public abstract class AbstractPropertyGroupEnum extends AtomizedEnum {
    private static final Logger LOGGER = Logger.getLogger(AbstractPropertyGroupEnum.class);
    private String _owningEntityClassName;
    private Class _owningEntityClass;
    private boolean _resolveAttempted;

    public AbstractPropertyGroupEnum(String inAbbrevation, String inDescriptivePropertyKey, String inCodePropertyKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, String inOwningEntityClassName) {
        super(inAbbrevation, inDescriptivePropertyKey, inCodePropertyKey, inBackgroundColor, inForegroundColor, inIconIdPath);
        this._owningEntityClassName = inOwningEntityClassName;
    }

    public synchronized Class getOwningEntityClass() {
        if (!this._resolveAttempted) {
            this._resolveAttempted = true;
            if (!StringUtils.isEmpty((String)this._owningEntityClassName)) {
                try {
                    this._owningEntityClass = Class.forName(this._owningEntityClassName);
                }
                catch (ClassNotFoundException e) {
                    String className = this._owningEntityClassName != null ? this._owningEntityClassName : "n/a";
                    String message = "problem resolving class: " + className + "; ";
                    LOGGER.error((Object)message, (Throwable)e);
                    throw BizFailure.create((String)(message + CarinaUtils.unwrap((Throwable)e)));
                }
            }
        }
        return this._owningEntityClass;
    }


}
