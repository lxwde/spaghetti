package com.zpmc.ztos.infra.base.common.utils;

//import com.sun.org.apache.xpath.internal.XPathAPI;

import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class XmlUtil {

    public static final String UNIT_ATTRIBUTE = "unit";
    public static final boolean FORMATTED = true;
    public static final boolean PLAIN = false;
    public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    public static final String XML_FILE_EXTENSION = ".xml";
    public static final String SCHEMA_LOCATION = "schemaLocation";
//    public static final Namespace ARGO_NAMESPACE = Namespace.getNamespace((String)"argo", (String)"http://www.navis.com/argo");
//    public static final Namespace ARGO_DEFAULT_NAMESPACE = Namespace.getNamespace((String)"http://www.navis.com/argo");
//    public static final Namespace XSI_NAMESPACE = Namespace.getNamespace((String)"xsi", (String)"http://www.w3.org/2001/XMLSchema-instance");
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final Logger LOGGER = Logger.getLogger(XmlUtil.class);

    private XmlUtil() {
    }
//
//    public static boolean hasAttribute(org.jdom.Element inElement, String inAttributeName, Namespace inNs) {
//        return inElement.getAttribute(inAttributeName, inNs) != null;
//    }
//
//    public static Long getAttributeLongValue(org.jdom.Element inElement, String inAttributeName, Namespace inNamespace) {
//        String attrValue;
//        Long l = null;
//        if (inElement != null && StringUtils.hasLength((String)(attrValue = inElement.getAttributeValue(inAttributeName, inNamespace)))) {
//            l = Long.valueOf(attrValue);
//        }
//        return l;
//    }
//
//    public static Integer getAttributeIntegerValue(org.jdom.Element inElement, String inAttributeName, Namespace inNamespace) {
//        String attrValue;
//        Integer i = null;
//        if (inElement != null && StringUtils.hasLength((String)(attrValue = inElement.getAttributeValue(inAttributeName, inNamespace)))) {
//            i = Integer.valueOf(attrValue);
//        }
//        return i;
//    }
//
//    public static Double getAttributeDoubleValue(org.jdom.Element inElement, String inAttributeName, Namespace inNamespace) {
//        String attrValue;
//        Double d = null;
//        if (inElement != null && StringUtils.hasLength((String)(attrValue = inElement.getAttributeValue(inAttributeName, inNamespace)))) {
//            d = Double.valueOf(attrValue);
//        }
//        return d;
//    }
//
//    public static Date getAttributeDateValue(org.jdom.Element inElement, String inAttributeName, Namespace inNamespace, TimeZone inTz) {
//        String attrValue;
//        Date datetime = null;
//        if (inElement != null && StringUtils.hasLength((String)(attrValue = inElement.getAttributeValue(inAttributeName, inNamespace)))) {
//            datetime = XmlUtil.toDate(attrValue, inTz);
//        }
//        return datetime;
//    }
//
//    @Nullable
//    public static Boolean getAttributeBooleanValue(org.jdom.Element inElement, String inAttributeName, Namespace inNamespace) {
//        String attrValue;
//        Boolean b = null;
//        if (inElement != null && StringUtils.hasLength((String)(attrValue = inElement.getAttributeValue(inAttributeName, inNamespace)))) {
//            b = Boolean.valueOf(attrValue);
//        }
//        return b;
//    }
//
//    public static boolean getAttributeBooleanValue(org.jdom.Element inElement, String inAttributeName, boolean inValueIfNull, Namespace inNamespace) {
//        String attrValue;
//        Boolean b = null;
//        if (inElement != null && StringUtils.hasLength((String)(attrValue = inElement.getAttributeValue(inAttributeName, inNamespace)))) {
//            b = Boolean.valueOf(attrValue);
//        }
//        return b != null ? b : inValueIfNull;
//    }
//
//    public static String getAttributeStringValue(org.jdom.Element inElement, String inAttributeName, Namespace inNs) {
//        if (inElement == null) {
//            return null;
//        }
//        return inElement.getAttributeValue(inAttributeName, inNs);
//    }
//
//    public static String getRequiredAttributeStringValue(org.jdom.Element inElement, String inAttributeName, Namespace inNs) {
//        if (inElement == null) {
//            return null;
//        }
//        if (inElement.getAttribute(inAttributeName, inNs) == null) {
//            throw BizFailure.create((PropertyKey)ArgoPropertyKeys.ATTRIBUTE_BAD_OR_MISSING, null, (Object)inAttributeName, (Object)inElement.getName());
//        }
//        return inElement.getAttributeValue(inAttributeName, inNs);
//    }
//
//    public static Object evalXpath(org.jdom.Element inElement, String inXpathExpr) {
//        try {
//            return XPath.newInstance((String)inXpathExpr).selectSingleNode((Object)inElement);
//        }
//        catch (JDOMException e) {
//            return null;
//        }
//    }
//
//    public static org.jdom.Element evalXpathElement(org.jdom.Element inElement, String inXpathExpr) {
//        Object object = XmlUtil.evalXpath(inElement, inXpathExpr);
//        if (object instanceof org.jdom.Element) {
//            return (org.jdom.Element)object;
//        }
//        return null;
//    }
//
//    public static Attribute evalXpathAttr(org.jdom.Element inElement, String inXpathExpr) {
//        Object object = XmlUtil.evalXpath(inElement, inXpathExpr);
//        if (object instanceof Attribute) {
//            return (Attribute)object;
//        }
//        return null;
//    }
//
//    @Nullable
//    public static Element getXmlElement(Element inRootElm, String inXpath) {
//        NodeList list;
//        Element element = null;
//        try {
//            list = XPathAPI.selectNodeList(inRootElm.getOwnerDocument(), inXpath);
//        }
//        catch (TransformerException e) {
//            throw BizFailure.create((String)("Unable to read XML : " + e));
//        }
//        if (list != null && list.getLength() > 0) {
//            element = (Element)list.item(0);
//        }
//        return element;
//    }
//
//    public static void setOptionalAttribute(org.jdom.Element inElement, String inAttributeName, Date inDateValue, Namespace inNamespace) {
//        XmlUtil.setOptionalAttribute(inElement, inAttributeName, inDateValue, inNamespace, (XmlContentFilter)null);
//    }
//
//    public static void setOptionalAttribute(org.jdom.Element inElement, String inAttributeName, Date inDateValue, Namespace inNamespace, XmlContentFilter inContentFilter) {
//        XmlUtil.setOptionalAttribute(inElement, inAttributeName, XmlUtil.format(inDateValue), inNamespace, inContentFilter);
//    }
//
//    public static void setOptionalAttribute(org.jdom.Element inElement, String inAttributeName, Object inValue, Namespace inNamespace) {
//        XmlUtil.setOptionalAttribute(inElement, inAttributeName, inValue, inNamespace, (XmlContentFilter)null);
//    }
//
//    public static void setOptionalAttribute(org.jdom.Element inElement, String inAttributeName, Object inValue, Namespace inNamespace, XmlContentFilter inContentFilter) {
//        if (inValue != null) {
//            XmlUtil.setAttribute(inElement, inAttributeName, inValue, inNamespace, inContentFilter);
//        }
//    }
//
//    public static void setAttribute(org.jdom.Element inElement, String inAttributeName, Object inValue, Namespace inNamespace) {
//        XmlUtil.setAttribute(inElement, inAttributeName, inValue, inNamespace, null);
//    }
//
//    public static void setAttribute(org.jdom.Element inElement, String inAttributeName, Object inValue, Namespace inNamespace, XmlContentFilter inContentFilter) {
//        if (inContentFilter == null || inContentFilter.includes(inAttributeName)) {
//            Namespace ns;
//            Namespace namespace = ns = inNamespace == null ? Namespace.NO_NAMESPACE : inNamespace;
//            if (inValue == null) {
//                inElement.setAttribute(inAttributeName, "", ns);
//            } else if (inValue instanceof Date) {
//                inElement.setAttribute(inAttributeName, XmlUtil.format((Date)inValue), ns);
//            } else if (inValue instanceof AtomizedEnum) {
//                inElement.setAttribute(inAttributeName, ((AtomizedEnum)inValue).getKey(), ns);
//            } else {
//                inElement.setAttribute(inAttributeName, inValue.toString(), ns);
//            }
//        }
//    }
//
//    public static String getText(org.jdom.Element inElement) {
//        if (inElement == null) {
//            return null;
//        }
//        return inElement.getText();
//    }
//
//    @Nullable
//    public static org.jdom.Element addElement(org.jdom.Element inEParent, String inName, XmlContentFilter inContentFilter) {
//        org.jdom.Element eAdded;
//        if (inContentFilter.includes(inName)) {
//            eAdded = new org.jdom.Element(inName);
//            inEParent.addContent((Content)eAdded);
//        } else {
//            eAdded = null;
//        }
//        return eAdded;
//    }
//
//    public static Double getMassUnitTypeValue(org.jdom.Element inParentElement, String inElementName) {
//        if (inParentElement == null || inElementName == null) {
//            return null;
//        }
//        org.jdom.Element element = inParentElement.getChild(inElementName);
//        if (element == null) {
//            return null;
//        }
//        String unit = XmlUtil.getAttributeStringValue(element, UNIT_ATTRIBUTE, element.getNamespace());
//        MassUnit srcUnit = MassUnitEnum.getEnum((String)unit);
//        MassUnit destUnit = MassUnit.KILOGRAMS;
//        Double value = Double.parseDouble(element.getText());
//        return UnitUtils.convertTo((double)value, (MeasurementUnit)srcUnit, (MeasurementUnit)destUnit);
//    }
//
//    public static Double getTemperatureUnitTypeValue(org.jdom.Element inParentElement, String inElementName) {
//        if (inParentElement == null || inElementName == null) {
//            return null;
//        }
//        org.jdom.Element element = inParentElement.getChild(inElementName);
//        if (element == null) {
//            return null;
//        }
//        String unit = XmlUtil.getAttributeStringValue(element, UNIT_ATTRIBUTE, element.getNamespace());
//        TemperatureUnit srcUnit = TemperatureUnitEnum.getEnum((String)unit);
//        TemperatureUnit destUnit = TemperatureUnit.C;
//        Double value = Double.parseDouble(element.getText());
//        return UnitUtils.convertTo((double)value, (MeasurementUnit)srcUnit, (MeasurementUnit)destUnit);
//    }
//
//    public static Double getElementDoubleValue(org.jdom.Element inParentElement, String inElementName) {
//        if (inParentElement == null || inElementName == null) {
//            return null;
//        }
//        org.jdom.Element element = inParentElement.getChild(inElementName);
//        if (element == null) {
//            return null;
//        }
//        Double value = Double.parseDouble(element.getText());
//        return value;
//    }
//
    public static String format(Date inDate) {
        if (inDate == null) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
        return df.format(inDate) + "T" + tf.format(inDate);
    }

    public static Date toDate(String inTimeString, TimeZone inTz) {
        Calendar cal = XmlUtil.toCalendar(inTimeString, inTz);
        return cal != null ? cal.getTime() : null;
    }

    public static Calendar toCalendar(String inTimeString, TimeZone inTz) {
        Calendar datetime = null;
        if (inTimeString != null) {
            String dtFormat;
            if (inTimeString.length() < 10) {
                throw new IllegalArgumentException("inTimeString length < 10");
            }
            switch (inTimeString.length()) {
                case 10:
                case 16:
                case 19:
                case 23: {
                    dtFormat = "yyyy-MM-dd HH:mm:ss.SSS".substring(0, inTimeString.length());
                    datetime = XmlUtil.toCalendar(inTimeString, dtFormat, inTz);
                    break;
                }
                case 25: {
                    dtFormat = "yyyy-MM-dd HH:mm:ss";
                    XmlUtil.checkZoneConflict(inTimeString, inTz);
                    datetime = XmlUtil.toCalendar(inTimeString, dtFormat, XmlUtil.extractTimeZone(inTimeString));
                    break;
                }
                case 29: {
                    dtFormat = "yyyy-MM-dd HH:mm:ss.SSS";
                    XmlUtil.checkZoneConflict(inTimeString, inTz);
                    datetime = XmlUtil.toCalendar(inTimeString, dtFormat, XmlUtil.extractTimeZone(inTimeString));
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Date Format: " + inTimeString);
                }
            }
            if (XmlUtil.zoneTypeZ(inTimeString)) {
                datetime = XmlUtil.toCalendar(inTimeString, dtFormat, TimeZone.getTimeZone("GMT"));
            }
        }
        return datetime;
    }

    public static TimeZone extractTimeZone(String inTimeString) {
        if (inTimeString.length() < 6) {
            throw new IllegalArgumentException(inTimeString);
        }
        return TimeZone.getTimeZone("GMT" + inTimeString.substring(inTimeString.length() - 6));
    }

    private static void checkZoneConflict(String inTimeString, TimeZone inTz) {
        if (inTimeString.length() >= 25 && XmlUtil.zoneTypeZ(inTimeString)) {
            throw new IllegalArgumentException("timeString length==" + inTimeString.length() + ", zoneType == 'Z'");
        }
    }

    private static Character extractZoneType(String inTimeString) {
        if (inTimeString.length() <= 10) {
            return null;
        }
        return new Character(inTimeString.charAt(10));
    }

    private static boolean zoneTypeZ(String inTimeString) {
        Character zoneType = XmlUtil.extractZoneType(inTimeString);
        return zoneType != null && zoneType.charValue() == 'Z';
    }

    private static Calendar toCalendar(String inTimeString, String inFormat, TimeZone inTz) {
        GregorianCalendar cal;
        String timeString = inTimeString.replace('T', ' ').replace('Z', ' ');
        if (inFormat.length() < inTimeString.length()) {
            timeString = timeString.substring(0, inFormat.length());
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(inFormat);
            if (inTz != null) {
                df.setTimeZone(inTz);
                cal = new GregorianCalendar(inTz, Locale.ENGLISH);
            } else {
                cal = new GregorianCalendar();
            }
            cal.setTime(new Date(df.parse(timeString).getTime()));
        }
        catch (ParseException pe) {
            LOGGER.error((Object)"Exception when converting date string to date: ", (Throwable)pe);
            throw BizFailure.wrap((Throwable)pe);
        }
        return cal;
    }
//
//    public static String toString(org.jdom.Element inElement, boolean inFormatted) {
//        XMLOutputter xout = inFormatted ? new XMLOutputter(Format.getPrettyFormat()) : new XMLOutputter(Format.getRawFormat());
//        if (inElement == null) {
//            return null;
//        }
//        return xout.outputString(inElement);
//    }
//
//    public static String toString(Document inDocument, boolean inFormatted) {
//        XMLOutputter xout = inFormatted ? new XMLOutputter(Format.getPrettyFormat()) : new XMLOutputter(Format.getRawFormat());
//        if (inDocument == null) {
//            return null;
//        }
//        return xout.outputString(inDocument);
//    }
//
//    public static void writeXmlToFile(org.jdom.Element inRoot, String inFileName) {
//        FileOutputStream fout;
//        try {
//            fout = new FileOutputStream(inFileName + XML_FILE_EXTENSION);
//        }
//        catch (FileNotFoundException e) {
//            LOGGER.error((Object)("writeXmlToFile: could not open file with name " + inFileName), (Throwable)e);
//            return;
//        }
//        XMLOutputter displayer = new XMLOutputter(Format.getPrettyFormat());
//        try {
//            displayer.output(inRoot, (OutputStream)fout);
//            fout.flush();
//            fout.close();
//        }
//        catch (IOException e) {
//            LOGGER.error((Object)("writeXmlToFile: problem writing file for " + (Object)inRoot), (Throwable)e);
//        }
//    }
//
//    public static String comment(String inString) {
//        return "<!-- " + inString + " -->";
//    }
//
//    public static String ensureHeader(String inString) {
//        if (inString != null && !inString.startsWith("?xml ")) {
//            return XML_HEADER + inString;
//        }
//        return inString;
//    }
//
//    public static Document load(String inFilename) {
//        Document xmlDocument;
//        SAXBuilder builder = new SAXBuilder();
//        InputStream fileStream = null;
//        try {
//            Resource resource = ResourceUtils.loadResource((String)inFilename);
//            if (resource != null) {
//                fileStream = resource.getInputStream();
//            }
//            if (fileStream == null) {
//                return null;
//            }
//            xmlDocument = builder.build(fileStream);
//        }
//        catch (Exception e) {
//            LOGGER.error((Object)("Could not load file " + inFilename), (Throwable)e);
//            return null;
//        }
//        return xmlDocument;
//    }
//
//    public static Document parse(String inString) {
//        Document xmlDocument = null;
//        SAXBuilder builder = new SAXBuilder();
//        try {
//            xmlDocument = builder.build((Reader)new StringReader(inString));
//        }
//        catch (IOException ioe) {
//            LOGGER.error((Object)ExceptionUtils.getStackTrace((Throwable)ioe));
//        }
//        catch (JDOMException jdom) {
//            LOGGER.error((Object)ExceptionUtils.getStackTrace((Throwable)jdom));
//        }
//        return xmlDocument;
//    }

//    public static Document parse(String inString, String inSchemaPath, Namespace inNs) throws JDOMException {
//        Document xmlDocument = null;
//        SAXBuilder builder = XmlUtil.getValidatingSAXBuilder(inSchemaPath, inNs);
//        try {
//            xmlDocument = builder.build((Reader)new StringReader(inString));
//        }
//        catch (IOException ioe) {
//            LOGGER.error((Object)ExceptionUtils.getStackTrace((Throwable)ioe));
//        }
//        return xmlDocument;
//    }
//
//    public static org.jdom.Element createRootElement(String inRootTag, String inSchemaUrl) {
//        org.jdom.Element root = new org.jdom.Element(inRootTag);
//        root.addNamespaceDeclaration(XSI_NAMESPACE);
//        root.setNamespace(ARGO_NAMESPACE);
//        root.setAttribute(new Attribute(SCHEMA_LOCATION, inSchemaUrl, XSI_NAMESPACE));
//        return root;
//    }
//
//    public static SAXBuilder getValidatingSAXBuilder(String inSchemaPath, Namespace inNs) {
//        SAXBuilder builder = new SAXBuilder();
//        builder.setFeature("http://apache.org/xml/features/validation/schema", true);
//        builder.setFeature("http://xml.org/sax/features/validation", true);
//        if (Namespace.NO_NAMESPACE.equals((Object)inNs)) {
//            builder.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", (Object)inSchemaPath);
//        } else {
//            builder.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation", (Object)(inNs.getURI() + " " + inSchemaPath));
//        }
//        return builder;
//    }
//
//    public static URL writeElementsToFile(String inEntityName, Iterator inElementIterator, String inRootTag, String inSchemaUrl, boolean inUseTempFile) {
//        XmlFile outputFile = new XmlFile("exported-fast" + inEntityName, inRootTag, inSchemaUrl, inUseTempFile);
//        while (inElementIterator.hasNext()) {
//            try {
//                org.jdom.Element el = (org.jdom.Element)inElementIterator.next();
//                outputFile.write(XmlUtil.toString(el, true));
//            }
//            catch (Exception e) {
//                StringBuilder sb = new StringBuilder("<!--");
//                sb.append(e.fillInStackTrace());
//                sb.append("-->");
//                outputFile.write(sb.toString());
//            }
//            outputFile.newLine();
//            outputFile.flush();
//        }
//        outputFile.close();
//        return outputFile.getXmlFileUrl();
//    }
//
//    @Nullable
//    public static String convertToString(org.jdom.Element inElement, boolean inFormatted) {
//        XMLOutputter xout;
//        if (inFormatted) {
//            xout = new XMLOutputter(Format.getPrettyFormat());
//        } else {
//            Format format = Format.getRawFormat();
//            format.setEncoding(DEFAULT_ENCODING);
//            xout = new XMLOutputter(format);
//        }
//        if (inElement == null) {
//            return null;
//        }
//        return xout.outputString(inElement);
//    }
//
//    public static String getSchemaFromClasspath(String inSchemaFilename) {
//        String schemaPath = null;
//        URL url = XmlUtil.class.getClassLoader().getResource(inSchemaFilename);
//        if (url != null) {
//            schemaPath = url.toString();
//        }
//        return schemaPath;
//    }
//
//    public static void addSchemaLocation(org.jdom.Element inElement, Namespace inNamespace, String inPath) {
//        inElement.setAttribute(SCHEMA_LOCATION, inNamespace.getURI() + " " + inPath, XSI_NAMESPACE);
//    }

    public static boolean isXml(String inString) {
        return inString != null && inString.startsWith("<?xml");
    }

    public static boolean isMaybeXml(String inString) {
        boolean isMaybeXml = false;
        if (inString != null) {
            isMaybeXml = inString.trim().startsWith("<") && inString.trim().endsWith(">");
        }
        return isMaybeXml;
    }
}
