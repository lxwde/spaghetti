package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.common.database.HierarchicalDiagnosticEvent;
import com.zpmc.ztos.infra.base.common.events.SimpleDiagnosticEvent;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class DiagnosticUtils {

    private static final Logger LOGGER = Logger.getLogger(DiagnosticUtils.class);

    private DiagnosticUtils() {
    }

    public static String getDiagnosticDetails(@NotNull Object inObject, boolean inGetGenericBeanValues) {
        StringBuilder buf = new StringBuilder();
        try {
            buf.append("" + inObject.toString());
            if (inObject instanceof IDetailedDiagnostics) {
                buf.append("\n\nDetailed Diagnostics for " + inObject.getClass().getSimpleName() + " :\n");
                buf.append(((IDetailedDiagnostics)inObject).getDetailedDiagnostics());
            }
            if (inGetGenericBeanValues) {
                buf.append(DiagnosticUtils.describeGenericBeanProperties(inObject));
            }
        }
        catch (Throwable t) {
            LOGGER.error((Object)("trouble getting diagnostics for  " + inObject + " because: " + t));
        }
        return buf.toString();
    }

    public static String getBriefDetails(@NotNull Object inObject) {
        if (inObject instanceof IBriefDiagnostics) {
            try {
                return ((IBriefDiagnostics)inObject).getBriefDetails();
            }
            catch (Exception e) {
                return "Error getting brief diagnostics due to " + e + "\n" + DiagnosticUtils.getSafeToString(inObject);
            }
        }
        return DiagnosticUtils.getSafeToString(inObject);
    }

    public static String getSafeToString(@NotNull Object inObject) {
        try {
            return inObject.toString();
        }
        catch (Exception e) {
            return "Error getting toString for " + inObject.getClass() + " due to " + e;
        }
    }

    public static String describeGenericBeanProperties(@NotNull Object inBean) {
        StringBuilder description = new StringBuilder();
        try {
            description.append("\nGeneric Bean Properties for " + inBean.getClass().getSimpleName() + " :");
            PropertyUtilsBean propertyUtilsBean = BeanUtilsBean.getInstance().getPropertyUtils();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(inBean);
            for (int i = 0; i < descriptors.length; ++i) {
                String name = descriptors[i].getName();
                if (descriptors[i].getReadMethod() == null) continue;
                try {
                    if ("detailedDiagnostics".equals(name)) continue;
                    Object value = propertyUtilsBean.getNestedProperty(inBean, name);
                    description.append("\n    -" + name + "=" + DiagnosticUtils.convertToDebugFormat(value));
                    continue;
                }
                catch (Throwable t) {
                    LOGGER.error((Object)("trouble converting bean value for " + name + " because:  " + t));
                }
            }
        }
        catch (Throwable t) {
            LOGGER.error((Object)("trouble getting bean values because:  " + t));
        }
        return description.toString();
    }

    public static String convertToDebugFormat(Object inValue) {
        String output = null;
        if (inValue instanceof Object[]) {
            output = DiagnosticUtils.getFormattedContents((Object[])inValue);
        } else if (inValue instanceof Map) {
            output = DiagnosticUtils.getFormattedContents((Map)inValue, true);
        } else if (inValue instanceof Element) {
//            Format prettyFormat = Format.getPrettyFormat();
//            prettyFormat.setIndent("      ");
//            XMLOutputter xmlOut = new XMLOutputter(prettyFormat);
//            output = "\n" + xmlOut.outputString((Element)inValue);
        } else if (inValue != null) {
            output = inValue.toString();
        }
        return output;
    }

    public static String getFormattedContents(@NotNull Object[] inArray) {
        StringBuilder buf = new StringBuilder();
        try {
            String oneline;
            if (inArray.length < 6 && (oneline = StringUtils.arrayToCommaDelimitedString((Object[])inArray)).length() < 85) {
                return oneline;
            }
            buf.append("{" + StringUtils.arrayToDelimitedString((Object[])inArray, (String)",\n          ") + "}\n");
        }
        catch (Throwable t) {
            LOGGER.error((Object) CarinaUtils.getStackTrace(t));
        }
        return buf.toString();
    }

    public static String getSafeFormattedContents(Object[] inArray, String inDelimitors) {
        StringBuilder buf = new StringBuilder();
        try {
            if (inArray != null && inArray.length > 0) {
                buf.append(StringUtils.arrayToDelimitedString((Object[])inArray, (String)inDelimitors));
            }
        }
        catch (Throwable t) {
            LOGGER.error((Object)CarinaUtils.getStackTrace(t));
        }
        return buf.toString();
    }

    public static String getFormattedContents(Map inMap, boolean inSort) {
        StringBuilder buf = new StringBuilder();
        int min = 2;
        if (inMap != null && !inMap.isEmpty()) {
            if (inMap.size() > min) {
                buf.append("  " + inMap.size() + " entries {");
            }
            try {
                Collection keys = inMap.keySet();
                if (inSort) {
                    ArrayList sorted = new ArrayList(inMap.keySet());
                    Collections.sort(sorted);
                    keys = sorted;
                }
                for (Object key : keys) {
                    buf.append("\n       -" + key + "=" + inMap.get(key));
                }
            }
            catch (Exception e) {
                LOGGER.warn((Object)("trouble sorting map values due to " + e));
                for (Object key : inMap.keySet()) {
                    buf.append("\n       -" + key + "=" + inMap.get(key));
                }
            }
            if (inMap.size() > min) {
                buf.append("}");
            }
        }
        return buf.toString();
    }

    @Nullable
    public static IDiagnosticRequestManager shouldRecordDiagnostic() {
        try {
            IDiagnosticRequestManager requestManager;
            ICallingContextManager callingContextManager = PortalBeanUtils.getCallingContextManager();
            ICallingContext context = callingContextManager.getContext();
//            if (context != null && (requestManager = context.getDiagnosticRequestManager()) != null && requestManager.isRecordingEnabled()) {
//                return requestManager;
//            }
        }
        catch (Throwable t) {
            LOGGER.error((Object)CarinaUtils.getStackTrace(t));
        }
        return null;
    }

    public static IDiagnosticEvent recordHiearchicalDiagnosticIfEnabled(String inEventType) {
        try {
            IDiagnosticRequestManager requestManager = DiagnosticUtils.shouldRecordDiagnostic();
            if (requestManager != null) {
                HierarchicalDiagnosticEvent event = HierarchicalDiagnosticEvent.createEvent(requestManager, inEventType);
                requestManager.recordEvent(event);
                return event;
            }
        }
        catch (Throwable t) {
            LOGGER.error((Object)("Error in recordHiearchicalDiagnosticIfNeeded " + CarinaUtils.getStackTrace(t)));
        }
        return null;
    }

    public static IDiagnosticEvent recordSimpleDiagnosticEventIfEnabled(String inEventType, String inDesc) {
        try {
            IDiagnosticRequestManager requestManager = DiagnosticUtils.shouldRecordDiagnostic();
            if (requestManager != null) {
                IDiagnosticEvent event = SimpleDiagnosticEvent.createEvent(inEventType);
                event.setDescription(inDesc);
                requestManager.recordEvent(event);
                return event;
            }
        }
        catch (Throwable t) {
            LOGGER.error((Object)("Error in recordHiearchicalDiagnosticIfNeeded " + CarinaUtils.getStackTrace(t)));
        }
        return null;
    }

    public static void closeLastEventIfNeeded() {
        IDiagnosticRequestManager requestManager = DiagnosticUtils.shouldRecordDiagnostic();
        if (requestManager != null) {
            requestManager.closeLastEvent();
        }
    }

    public static void recordToLastEventIfEnabled(String inMessage, boolean inClose) {
        try {
            IDiagnosticRequestManager diagnosticManager = DiagnosticUtils.shouldRecordDiagnostic();
            if (diagnosticManager != null) {
                IDiagnosticEvent recordedEvent = diagnosticManager.getLastRecordedEvent();
                if (recordedEvent != null) {
                    recordedEvent.appendMessage(inMessage);
                } else {
                    LOGGER.error((Object)("Last message not found for " + inMessage));
                }
                if (inClose) {
                    diagnosticManager.closeLastEvent();
                }
            }
        }
        catch (Throwable t) {
            LOGGER.error((Object)t);
        }
    }
}
