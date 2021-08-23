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

public interface IOrganizationCodes {
    public static final SchemaType type = (SchemaType) XmlBeans.typeSystemForClassLoader((ClassLoader) IOrganizationCodes.class.getClassLoader(), (String)"schemaorg_apache_xmlbeans.system.s7B6816315C323FA4243DDCCA8685AE51").resolveHandle("organizationcodesc95btype");

    public String getId();

    public XmlString xgetId();

    public boolean isSetId();

    public void setId(String var1);

    public void xsetId(XmlString var1);

    public void unsetId();

    public String getScacCode();

    public XmlString xgetScacCode();

    public boolean isSetScacCode();

    public void setScacCode(String var1);

    public void xsetScacCode(XmlString var1);

    public void unsetScacCode();

    public String getBicCode();

    public XmlString xgetBicCode();

    public boolean isSetBicCode();

    public void setBicCode(String var1);

    public void xsetBicCode(XmlString var1);

    public void unsetBicCode();

    public String getDunsCode();

    public XmlString xgetDunsCode();

    public boolean isSetDunsCode();

    public void setDunsCode(String var1);

    public void xsetDunsCode(XmlString var1);

    public void unsetDunsCode();

    public static final class Factory {
        public static IOrganizationCodes newInstance() {
            return (IOrganizationCodes)XmlBeans.getContextTypeLoader().newInstance(type, null);
        }

        public static IOrganizationCodes newInstance(XmlOptions xmlOptions) {
            return (IOrganizationCodes)XmlBeans.getContextTypeLoader().newInstance(type, xmlOptions);
        }

        public static IOrganizationCodes parse(String string) throws XmlException {
            return (IOrganizationCodes)XmlBeans.getContextTypeLoader().parse(string, type, null);
        }

        public static IOrganizationCodes parse(String string, XmlOptions xmlOptions) throws XmlException {
            return (IOrganizationCodes)XmlBeans.getContextTypeLoader().parse(string, type, xmlOptions);
        }

        public static IOrganizationCodes parse(File file) throws XmlException, IOException {
            return (IOrganizationCodes)XmlBeans.getContextTypeLoader().parse(file, type, null);
        }

        public static IOrganizationCodes parse(File file, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IOrganizationCodes)XmlBeans.getContextTypeLoader().parse(file, type, xmlOptions);
        }

        public static IOrganizationCodes parse(URL uRL) throws XmlException, IOException {
            return (IOrganizationCodes)XmlBeans.getContextTypeLoader().parse(uRL, type, null);
        }

        public static IOrganizationCodes parse(URL uRL, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IOrganizationCodes)XmlBeans.getContextTypeLoader().parse(uRL, type, xmlOptions);
        }

        public static IOrganizationCodes parse(InputStream inputStream) throws XmlException, IOException {
            return (IOrganizationCodes)XmlBeans.getContextTypeLoader().parse(inputStream, type, null);
        }

        public static IOrganizationCodes parse(InputStream inputStream, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IOrganizationCodes)XmlBeans.getContextTypeLoader().parse(inputStream, type, xmlOptions);
        }

        public static IOrganizationCodes parse(Reader reader) throws XmlException, IOException {
            return (IOrganizationCodes)XmlBeans.getContextTypeLoader().parse(reader, type, null);
        }

        public static IOrganizationCodes parse(Reader reader, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IOrganizationCodes)XmlBeans.getContextTypeLoader().parse(reader, type, xmlOptions);
        }

        public static IOrganizationCodes parse(XMLStreamReader xMLStreamReader) throws XmlException {
            return (IOrganizationCodes)XmlBeans.getContextTypeLoader().parse(xMLStreamReader, type, null);
        }

        public static IOrganizationCodes parse(XMLStreamReader xMLStreamReader, XmlOptions xmlOptions) throws XmlException {
            return (IOrganizationCodes)XmlBeans.getContextTypeLoader().parse(xMLStreamReader, type, xmlOptions);
        }

        public static IOrganizationCodes parse(Node node) throws XmlException {
            return (IOrganizationCodes)XmlBeans.getContextTypeLoader().parse(node, type, null);
        }

        public static IOrganizationCodes parse(Node node, XmlOptions xmlOptions) throws XmlException {
            return (IOrganizationCodes)XmlBeans.getContextTypeLoader().parse(node, type, xmlOptions);
        }

        @Deprecated
        public static IOrganizationCodes parse(XMLInputStream xMLInputStream) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (IOrganizationCodes)XmlBeans.getContextTypeLoader().parse(xMLInputStream, type, null);
        }

        @Deprecated
        public static IOrganizationCodes parse(XMLInputStream xMLInputStream, XmlOptions xmlOptions) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (IOrganizationCodes)XmlBeans.getContextTypeLoader().parse(xMLInputStream, type, xmlOptions);
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
