package com.zpmc.ztos.infra.base.business.interfaces;

import org.apache.xmlbeans.*;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.w3c.dom.Node;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

public interface IPortCodes {
    public static final SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader((ClassLoader) IPortCodes.class.getClassLoader(), (String)"schemaorg_apache_xmlbeans.system.s7B6816315C323FA4243DDCCA8685AE51").resolveHandle("portcodes1b4dtype");

    public String getId();

    public XmlString xgetId();

    public boolean isSetId();

    public void setId(String var1);

    public void xsetId(XmlString var1);

    public void unsetId();

    public String getUnLocCode();

    public XmlString xgetUnLocCode();

    public boolean isSetUnLocCode();

    public void setUnLocCode(String var1);

    public void xsetUnLocCode(XmlString var1);

    public void unsetUnLocCode();

    public String getScheduleKCode();

    public XmlString xgetScheduleKCode();

    public boolean isSetScheduleKCode();

    public void setScheduleKCode(String var1);

    public void xsetScheduleKCode(XmlString var1);

    public void unsetScheduleKCode();

    public String getScheduleDCode();

    public XmlString xgetScheduleDCode();

    public boolean isSetScheduleDCode();

    public void setScheduleDCode(String var1);

    public void xsetScheduleDCode(XmlString var1);

    public void unsetScheduleDCode();

    public String getSplcCode();

    public XmlString xgetSplcCode();

    public boolean isSetSplcCode();

    public void setSplcCode(String var1);

    public void xsetSplcCode(XmlString var1);

    public void unsetSplcCode();

    public static final class Factory {
        public static IPortCodes newInstance() {
            return (IPortCodes)XmlBeans.getContextTypeLoader().newInstance(type, null);
        }

        public static IPortCodes newInstance(XmlOptions xmlOptions) {
            return (IPortCodes)XmlBeans.getContextTypeLoader().newInstance(type, xmlOptions);
        }

        public static IPortCodes parse(String string) throws XmlException {
            return (IPortCodes)XmlBeans.getContextTypeLoader().parse(string, type, null);
        }

        public static IPortCodes parse(String string, XmlOptions xmlOptions) throws XmlException {
            return (IPortCodes)XmlBeans.getContextTypeLoader().parse(string, type, xmlOptions);
        }

        public static IPortCodes parse(File file) throws XmlException, IOException {
            return (IPortCodes)XmlBeans.getContextTypeLoader().parse(file, type, null);
        }

        public static IPortCodes parse(File file, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IPortCodes)XmlBeans.getContextTypeLoader().parse(file, type, xmlOptions);
        }

        public static IPortCodes parse(URL uRL) throws XmlException, IOException {
            return (IPortCodes)XmlBeans.getContextTypeLoader().parse(uRL, type, null);
        }

        public static IPortCodes parse(URL uRL, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IPortCodes)XmlBeans.getContextTypeLoader().parse(uRL, type, xmlOptions);
        }

        public static IPortCodes parse(InputStream inputStream) throws XmlException, IOException {
            return (IPortCodes)XmlBeans.getContextTypeLoader().parse(inputStream, type, null);
        }

        public static IPortCodes parse(InputStream inputStream, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IPortCodes)XmlBeans.getContextTypeLoader().parse(inputStream, type, xmlOptions);
        }

        public static IPortCodes parse(Reader reader) throws XmlException, IOException {
            return (IPortCodes)XmlBeans.getContextTypeLoader().parse(reader, type, null);
        }

        public static IPortCodes parse(Reader reader, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IPortCodes)XmlBeans.getContextTypeLoader().parse(reader, type, xmlOptions);
        }

        public static IPortCodes parse(XMLStreamReader xMLStreamReader) throws XmlException {
            return (IPortCodes)XmlBeans.getContextTypeLoader().parse(xMLStreamReader, type, null);
        }

        public static IPortCodes parse(XMLStreamReader xMLStreamReader, XmlOptions xmlOptions) throws XmlException {
            return (IPortCodes)XmlBeans.getContextTypeLoader().parse(xMLStreamReader, type, xmlOptions);
        }

        public static IPortCodes parse(Node node) throws XmlException {
            return (IPortCodes)XmlBeans.getContextTypeLoader().parse(node, type, null);
        }

        public static IPortCodes parse(Node node, XmlOptions xmlOptions) throws XmlException {
            return (IPortCodes)XmlBeans.getContextTypeLoader().parse(node, type, xmlOptions);
        }

        @Deprecated
        public static IPortCodes parse(XMLInputStream xMLInputStream) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (IPortCodes) XmlBeans.getContextTypeLoader().parse(xMLInputStream, type, null);
        }

        @Deprecated
        public static IPortCodes parse(XMLInputStream xMLInputStream, XmlOptions xmlOptions) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (IPortCodes)XmlBeans.getContextTypeLoader().parse(xMLInputStream, type, xmlOptions);
        }

        @Deprecated
        public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xMLInputStream) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xMLInputStream, type, null);
        }

        @Deprecated
        public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xMLInputStream, XmlOptions xmlOptions) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xMLInputStream, type, xmlOptions);
        }

        private Factory() {
        }
    }
}
