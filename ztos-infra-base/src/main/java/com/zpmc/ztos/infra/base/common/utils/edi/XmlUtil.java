package com.zpmc.ztos.infra.base.common.utils.edi;

//import com.sun.org.apache.xml.internal.serialize.OutputFormat;
//import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
//import com.sun.org.apache.xpath.internal.XPathAPI;

import com.zpmc.ztos.infra.base.business.interfaces.IEdiPropertyKeys;
import com.zpmc.ztos.infra.base.business.interfaces.IPropertyKey;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.dom4j.Element;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlUtil {

    private static final String REG_EXP_SPACE_OR_NEWLINE = "\\s*\\n*\\r*";
    private static final String NAMESPACE = "xmlns:";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String REG_EXP_BETWEEN_QUOTES = "\"[^\"]*\"";
    private static final Logger LOGGER = Logger.getLogger(XmlUtil.class);

    public static org.w3c.dom.Element getXmlRootElement(String inEdiDoc) throws BizFailure {
        org.w3c.dom.Element xmlRootElm = null;
        try {
            StringReader sr = null;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            DocumentBuilder builder = factory.newDocumentBuilder();
            sr = new StringReader(inEdiDoc);
            org.w3c.dom.Document doc = builder.parse(new InputSource(sr));
            xmlRootElm = doc.getDocumentElement();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return xmlRootElm;
    }

    @Nullable
    public static String getXmlAttrElementValue(org.w3c.dom.Element inRoot, String inAttr) throws TransformerException {
        Node attrNode;
        org.w3c.dom.Element el;
        String prefix = XmlUtil.getPrefix(inRoot);
        String attrValue = null;
        String xpath = "//*[@" + inAttr + "]";
        String xpathElement = "//" + inAttr + "/text()";
//        NodeList nodelist = XPathAPI.selectNodeList(inRoot.getOwnerDocument(), xpath);
//        if (nodelist != null && nodelist.getLength() > 0 && (el = (org.w3c.dom.Element)nodelist.item(0)) != null && (attrNode = el.getAttributes().getNamedItem(prefix + ":" + inAttr)) != null) {
//            attrValue = attrNode.getNodeValue();
//        }
//        if (nodelist != null && nodelist.getLength() == 0 && attrValue == null && (nodelist = XPathAPI.selectNodeList(inRoot.getOwnerDocument(), xpathElement)) != null && nodelist.item(0) != null) {
//            attrValue = nodelist.item(0).getNodeValue();
//        }
        return attrValue;
    }

    @Nullable
    public static String setXmlAttrElementValue(String inTranXml, String inFieldId, String inFromValue, String inToValue, Pattern inPattern) {
        if (inTranXml != null) {
            String lowerCase;
            String string = lowerCase = inFromValue == null ? "" : inFromValue.toLowerCase();
            if (inTranXml.toLowerCase().contains(lowerCase)) {
                Matcher matcher = inPattern.matcher(inTranXml);
                StringBuffer output = new StringBuffer();
                while (matcher.find()) {
                    String foundMatch = matcher.group();
                    if (foundMatch.contains(">")) {
                        matcher.appendReplacement(output, inFieldId + ">" + inToValue);
                    } else {
                        matcher.appendReplacement(output, inFieldId + "=\"" + inToValue + "\"");
                    }
                    if (!LOGGER.isInfoEnabled()) continue;
                    LOGGER.info((Object) XmlUtil.getFilterInfo(inFieldId, inFromValue, inToValue));
                }
                matcher.appendTail(output);
                inTranXml = output.toString();
            }
        }
        return inTranXml;
    }

    private static String getFilterInfo(String inFieldId, String inFromValue, String inToValue) {
        StringBuilder sb = new StringBuilder();
        sb.append("apply filter: fromValue : ");
        sb.append(inFromValue);
        sb.append(" --> toValue : ");
        sb.append(inToValue);
        sb.append(" for field Id : ");
        sb.append(inFieldId);
        return sb.toString();
    }

    public static String getPatternExpression(String inFieldIdValue, String inFromValue) {
        String frmValue = inFromValue == null ? Pattern.quote("") : Pattern.quote(inFromValue);
        return inFieldIdValue + REG_EXP_SPACE_OR_NEWLINE + "=" + REG_EXP_SPACE_OR_NEWLINE + "\"" + REG_EXP_SPACE_OR_NEWLINE + frmValue + REG_EXP_SPACE_OR_NEWLINE + "\"" + "|" + inFieldIdValue + REG_EXP_SPACE_OR_NEWLINE + ">" + REG_EXP_SPACE_OR_NEWLINE + frmValue;
    }

    public static Pattern getPattern(String inFieldIdValue, String inFromValue) {
        String patternExpression = XmlUtil.getPatternExpression(inFieldIdValue, inFromValue);
        return Pattern.compile(patternExpression, 2);
    }

    public static org.w3c.dom.Element getXmlRootElement(File inXmlFile) throws BizFailure {
        org.w3c.dom.Element xmlRootElm;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(inXmlFile);
            xmlRootElm = doc.getDocumentElement();
        }
        catch (Exception e) {
            throw new BizFailure(IEdiPropertyKeys.UNABLE_TO_READ_XML, (Throwable)e, null);
        }
        return xmlRootElm;
    }

    @Nullable
    public static org.w3c.dom.Element getXmlElement(org.w3c.dom.Element inRootElm, String inXpath) {
        NodeList list = null;
        org.w3c.dom.Element element = null;
//        try {
//            list = XPathAPI.selectNodeList(inRootElm.getOwnerDocument(), inXpath);
//        }
//        catch (TransformerException e) {
//            throw new BizFailure(IEdiPropertyKeys.UNABLE_TO_READ_XML, (Throwable)e, null);
//        }
        if (list != null && list.getLength() > 0) {
            element = (org.w3c.dom.Element)list.item(0);
        }
        return element;
    }

    public static String getPrefix(org.w3c.dom.Element inElement) {
        String strElement = XmlUtil.nodeSnapshot(inElement);
        StringTokenizer st = new StringTokenizer(strElement, " ");
        while (st.hasMoreTokens()) {
            String part = st.nextToken();
            if (!part.startsWith(NAMESPACE)) continue;
            int index = part.indexOf(58);
            String nameSpace = part.substring(index + 1);
            int namespaceIndex = nameSpace.indexOf(61);
            return nameSpace.substring(0, namespaceIndex);
        }
        return "";
    }

    public static String nodeSnapshot(Node inNode) {
        StringWriter sw = new StringWriter();
        XmlUtil.nodeSnapshot(inNode, sw);
        return sw.toString();
    }

    public static void nodeSnapshot(Node inNode, Writer inWriter) {
        try {
            //OutputFormat format = new OutputFormat(inNode.getOwnerDocument(), DEFAULT_ENCODING, false);
//            OutputFormat format = new OutputFormat(inNode.getOwnerDocument().toString(), DEFAULT_ENCODING, false);
//            format.setOmitXMLDeclaration(true);
//            format.setEncoding(DEFAULT_ENCODING);
//            XMLSerializer serial = new XMLSerializer(inWriter, format);
//            serial.setNamespaces(true);
//            if (inNode instanceof org.w3c.dom.Element) {
//                serial.serialize((org.w3c.dom.Element)inNode);
//            }
        }
        catch (Exception ex) {
            LOGGER.error((Object)ex.getStackTrace());
            throw BizFailure.create((IPropertyKey) IEdiPropertyKeys.CLASSIFY_FAILURE, (Throwable)ex);
        }
    }

    public static String getAttrValue(org.w3c.dom.Element inXmldoc, String inAttr) {
        String keyvalue = "";
        try {
            keyvalue = XmlUtil.getXmlAttrElementValue(inXmldoc, inAttr);
        }
        catch (Exception e) {
            LOGGER.error((Object)e.getStackTrace());
            throw new BizFailure(IEdiPropertyKeys.UNABLE_TO_PARSE_XML, (Throwable)e, null);
        }
        return keyvalue == null ? "" : keyvalue;
    }

    public static String convertXmlObjectToString(XmlObject inXmlObject) {
        XmlOptions options = new XmlOptions();
        String text = inXmlObject.xmlText(options);
        options.setCharacterEncoding(DEFAULT_ENCODING);
        options.setSavePrettyPrint();
        options.setSavePrettyPrintIndent(4);
        options.setCompileNoValidation();
        inXmlObject.xmlText(options);
        return text;
    }

    @Nullable
    public static String toString(Element inXmlDoc, boolean inFormat) {
        return XmlUtil.convertToString(inXmlDoc, inFormat);
    }

    @Nullable
    public static String convertToString(Element inElement, boolean inFormatted) {
  //      return com.zpmc.ztos.infra.base.common.utils.XmlUtil.convertToString((Element)inElement, (boolean)inFormatted);
        return null;
    }

    public static List splitTransactions(Element inXmlDoc) {
        ArrayList<String> transactions = new ArrayList<String>();
//        try {
//            List childList = inXmlDoc.getChildren();
//            int size = childList.size();
//            for (int i = 0; i < size; ++i) {
//                Element child = (Element)childList.get(0);
//                child.detach();
//                Element newParent = new Element(inXmlDoc.getName(), inXmlDoc.getNamespace());
//                if (inXmlDoc.getParent() == null) {
//                    newParent.setAttributes(inXmlDoc.getAttributes());
//                }
//                newParent.addContent((Content)child);
//                transactions.add(XmlUtil.toString(newParent, false));
//            }
//        }
//        catch (Exception e) {
//            throw BizFailure.create((IPropertyKey)IEdiPropertyKeys.TRANSFORM_FAILURE, (Throwable)e, (Object)e.getMessage());
//        }
        return transactions;
    }

    public static List splitTransactions(org.w3c.dom.Element inXmlDoc) {
        ArrayList<String> transactions = new ArrayList<String>();
        NodeList childList = inXmlDoc.getChildNodes();
        int childListLength = childList.getLength();
        for (int i = 0; i < childListLength; ++i) {
            StringWriter writer = new StringWriter();
            try {
                Node parentNode = inXmlDoc.cloneNode(false);
                Node node = childList.item(0);
                parentNode.appendChild(node);
                writer = new StringWriter();
                XmlUtil.nodeSnapshot(parentNode, writer);
                transactions.add(writer.toString());
                continue;
            }
            catch (Exception e) {
                throw BizFailure.create((IPropertyKey) IEdiPropertyKeys.TRANSACTION_SPLIT__FAILURE, (Throwable)e, (Object)e.getMessage());
            }
            finally {
                if (writer != null) {
                    try {
                        writer.close();
                    }
                    catch (IOException e) {
                        LOGGER.error((Object)e.getStackTrace());
                    }
                }
            }
        }
        return transactions;
    }

    @Nullable
    public static String extractAttributeValueFromXml(String inAttKey, String inXmlContent) {
        if (inAttKey == null || inXmlContent == null) {
            return null;
        }
        Pattern pattern1 = Pattern.compile(inAttKey + REG_EXP_SPACE_OR_NEWLINE + "=" + REG_EXP_SPACE_OR_NEWLINE + REG_EXP_BETWEEN_QUOTES);
        Matcher matcher = pattern1.matcher(inXmlContent);
        if (matcher.find()) {
            String actualValue;
            String attribValue = matcher.group();
            Pattern pattern2 = Pattern.compile(REG_EXP_BETWEEN_QUOTES);
            matcher = pattern2.matcher(attribValue);
            if (matcher.find() && (actualValue = matcher.group()) != null && actualValue.length() > 2) {
                return actualValue.subSequence(1, actualValue.length() - 1).toString();
            }
        }
        return null;
    }

//    public static Document convertToJdom(String inXmlStr) throws JDOMException, IOException {
//        SAXBuilder sb = new SAXBuilder();
//        Document doc = sb.build((Reader)new StringReader(inXmlStr));
//        return doc;
//    }
//
//    public static List splitTransactions(Document inTranDocument) throws TransformerConfigurationException, JDOMException, IOException {
//        List transactions = inTranDocument.getRootElement().getChildren();
//        return transactions;
//    }

    public static List convertXmlToList(Element inXmlDoc) {
        ArrayList<ByteArrayInputStream> result = new ArrayList<ByteArrayInputStream>();
        result.add(new ByteArrayInputStream(inXmlDoc.toString().getBytes()));
        return result;
    }
}
