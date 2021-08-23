package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.interfaces.IExtensionTypeId;

public class ExtensionTypeIdFactory {

    private ExtensionTypeIdFactory() {
    }

    public static final IExtensionTypeId valueOf(String inTypeId) {
        return new DefaultExtensionId(inTypeId);
    }

    static class DefaultExtensionId
            implements IExtensionTypeId {
        private String _typeId;

        DefaultExtensionId(String inTypeId) {
            this._typeId = inTypeId;
        }

        @Override
        public String getTypeId() {
            return this._typeId;
        }
    }
}
