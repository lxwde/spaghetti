package com.zpmc.ztos.infra.base.business.model;

import com.zpmc.ztos.infra.base.business.interfaces.IEntityId;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.*;


public class MetafieldIdFactory {

    private static final Map REGISTERY = new HashMap();
    private static final char DELIMITTER = '.';
    private static final Logger LOGGER = Logger.getLogger(MetafieldIdFactory.class);

    private MetafieldIdFactory() {
    }

    @Nullable
    public static IMetafieldId valueOf(String inFieldId) {
        IMetafieldId mfd = null;

        if (!REGISTERY.isEmpty()) {
            mfd = (IMetafieldId) REGISTERY.get(inFieldId);

            if (mfd != null) {
                return mfd;
            }
        }
        if (StringUtils.isEmpty((String)inFieldId)) {
            return null;
        }

        mfd = MetafieldIdFactory.createMetafield(inFieldId);
        MetafieldIdFactory.registerNew(mfd);
        return mfd;
    }

    private static IMetafieldId createMetafield(String inFieldId) {
        int delimitter = inFieldId.indexOf(46);
        if (delimitter < 0) {
            SimpleMfidImpl mfd = new SimpleMfidImpl(inFieldId);
            return mfd;
        }
        String leftSide = inFieldId.substring(0, delimitter);
        String rghtSide = inFieldId.substring(delimitter + 1);
        CompoundMfIdImpl mfd = new CompoundMfIdImpl(MetafieldIdFactory.valueOf(leftSide), MetafieldIdFactory.valueOf(rghtSide));
        return mfd;
    }

 //   @Nullable
    public static IMetafieldId getCompoundMetafieldId(IMetafieldId inMeta1, IMetafieldId inMeta2) {
        if (inMeta1 == null) {
            return inMeta2;
        }
        return MetafieldIdFactory.valueOf(inMeta1.getQualifiedId() + '.' + inMeta2.getQualifiedId());
    }

    public static boolean isCompoundMetafield(IMetafieldId inField) {
        return inField != null && !StringUtils.equals((String)inField.getFieldId(), (String)inField.getQualifiedId());
    }

    @Nullable
    public static IMetafieldId convertToNewBaseEntity(IMetafieldId inMfid, IMetafieldId inPathFromOldToNew, IMetafieldId inPathFromNewToOld) {
        IMetafieldId leftMostNode = inMfid.getMfidLeftMostNode();
        Class existingBase = HiberCache.getEntityClassForField(leftMostNode.getFieldId());
        Class newBase = HiberCache.getEntityClassForField(inPathFromNewToOld.getFieldId());
        if (existingBase == null || newBase == null) {
            LOGGER.error((Object)"convertToNewBaseEntity: Either old or new metafieldId is not mapped for HiberCache.getEntityClassForField.");
            return inMfid;
        }
        if (existingBase.equals(newBase)) {
            LOGGER.warn((Object)("convertToNewBaseEntity: input already is of the new base entity: " + inMfid + ". No conversion done."));
            return inMfid;
        }
        if (leftMostNode.equals(inPathFromOldToNew)) {
            return inMfid.getMfidExcludeLeftMostNode();
        }
        return MetafieldIdFactory.getCompoundMetafieldId(inPathFromNewToOld, inMfid);
    }

    public static IMetafieldId getEntityAwareMetafieldId(IEntityId inEntity, IMetafieldId inMeta) {
        if (inMeta == null) {
            throw new IllegalArgumentException();
        }
        if (inEntity == null) {
            return inMeta;
        }
        if (inMeta instanceof AbstractMetafieldId) {
            IMetafieldId entityEnhancedId = MetafieldIdFactory.createMetafield(inMeta.getQualifiedId());
            ((AbstractMetafieldId)entityEnhancedId).setEntityId(inEntity);
            return entityEnhancedId;
        }
        throw new IllegalArgumentException();
    }

    public static String dumpToStr() {
        ArrayList keys = new ArrayList(REGISTERY.keySet());
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        Iterator iterator = keys.iterator();
        while (iterator.hasNext()) {
            sb.append("\n").append(iterator.next());
        }
        return sb.toString();
    }

    private static synchronized void registerNew(IMetafieldId inId) {
        REGISTERY.put(inId.getQualifiedId(), inId);
    }

    private static class CompoundMfIdImpl
            extends AbstractMetafieldId {
        private final IMetafieldId _leftNode;
        private final IMetafieldId _rghtNode;
        private final String _qualifiedId;

        CompoundMfIdImpl(IMetafieldId inMeta1, IMetafieldId inMeta2) {
            this._leftNode = inMeta1;
            this._rghtNode = inMeta2;
            this._qualifiedId = this._leftNode.getQualifiedId() + '.' + this._rghtNode.getQualifiedId();
        }

        @Override
        public String getFieldId() {
            return this._rghtNode.getFieldId();
        }

        @Override
        public String getQualifiedId() {
            return this._qualifiedId;
        }

        @Override
        public IMetafieldId getQualifyingMetafieldId() {
            return this._leftNode;
        }

        @Override
        public IMetafieldId getQualifiedMetafieldId() {
            return this._rghtNode;
        }

        @Override
        public IMetafieldId getMfidLeftMostNode() {
            return this._leftNode;
        }

        @Override
        public IMetafieldId getMfidRightMostNode() {
            return this._rghtNode.getMfidRightMostNode();
        }

        @Override
        public IMetafieldId getMfidExcludeLeftMostNode() {
            return this._rghtNode;
        }

        @Override
        public IMetafieldId getMfidExcludeRightMostNode() {
            IMetafieldId result = this._leftNode;
            IMetafieldId right = this._rghtNode;
            while (right.getMfidExcludeLeftMostNode() != null) {
                result = MetafieldIdFactory.getCompoundMetafieldId(result, right.getMfidLeftMostNode());
                right = right.getMfidExcludeLeftMostNode();
            }
            return result;
        }
    }

    private static final class SimpleMfidImpl
            extends AbstractMetafieldId {
        private final String _fieldId;

        SimpleMfidImpl(String inFieldId) {
            this._fieldId = inFieldId;
        }

        @Override
        public String getFieldId() {
            return this._fieldId;
        }

        @Override
        public String getQualifiedId() {
            return this._fieldId;
        }

        @Override
        @Nullable
        public IMetafieldId getQualifyingMetafieldId() {
            return null;
        }

        @Override
        public IMetafieldId getQualifiedMetafieldId() {
            return this;
        }

        @Override
        public IMetafieldId getMfidLeftMostNode() {
            return this;
        }

        @Override
        public IMetafieldId getMfidRightMostNode() {
            return this;
        }

        @Override
        @Nullable
        public IMetafieldId getMfidExcludeLeftMostNode() {
            return null;
        }

        @Override
        @Nullable
        public IMetafieldId getMfidExcludeRightMostNode() {
            return null;
        }
    }

}
