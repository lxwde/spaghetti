package com.zpmc.ztos.infra.base.common.utils;

import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.business.model.SavedPredicate;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.StringTokenizer;

public class SavedPredicateUtils {
    private static final char ARRAYFLAG = ':';
    private static final String SEPARATOR = "|";
    private static final Logger LOGGER = Logger.getLogger(SavedPredicateUtils.class);

    private SavedPredicateUtils() {
    }

    public static String formValueString(IMetafieldId inMetafieldId, Object inValue) {
        if (inValue instanceof Object[]) {
            Object[] values = (Object[])inValue;
            StringBuilder buf = new StringBuilder();
            buf.append(':');
            for (int i = 0; i < values.length; ++i) {
                buf.append(HiberCache.property2String(inMetafieldId, values[i]));
                buf.append(SEPARATOR);
            }
            LOGGER.info((Object)("encode: " + buf));
            return buf.toString();
        }
        return HiberCache.property2String(inMetafieldId, inValue);
    }

    public static Object parseValueString(IMetafieldId inMetafieldId, String inValueString) {
        if (inValueString == null) {
            return null;
        }
        if (!inValueString.isEmpty() && inValueString.charAt(0) == ':') {
            LOGGER.info((Object)("decode: " + inValueString));
            LOGGER.info((Object)("drop: " + inValueString.substring(1)));
            StringTokenizer st = new StringTokenizer(inValueString.substring(1), SEPARATOR);
            Object[] values = new Object[st.countTokens()];
            int i = 0;
            while (st.hasMoreTokens()) {
                values[i++] = HiberCache.string2Property(inMetafieldId, st.nextToken());
            }
            return values;
        }
        return HiberCache.string2Property(inMetafieldId, inValueString);
    }

    public static void shiftPredicateToNewBaseEntity(SavedPredicate inSavedPredicate, IMetafieldId inPathFromOldToNew, IMetafieldId inPathFromNewToOld) {
        List children;
        IMetafieldId metafieldId = inSavedPredicate.getMetafieldId();
        if (metafieldId != null) {
            IMetafieldId newMetafieldId = MetafieldIdFactory.convertToNewBaseEntity(metafieldId, inPathFromOldToNew, inPathFromNewToOld);
            inSavedPredicate.setPrdctMetafield(newMetafieldId.getQualifiedId());
            Roastery.getHibernateApi().update(inSavedPredicate);
        }
        if ((children = inSavedPredicate.getPrdctChildPrdctList()) != null && !children.isEmpty()) {
            for (Object child : children) {
                SavedPredicateUtils.shiftPredicateToNewBaseEntity((SavedPredicate)child, inPathFromOldToNew, inPathFromNewToOld);
            }
        }
    }
}
