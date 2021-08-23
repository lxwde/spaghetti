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

public interface IShippingLine {
    public static final SchemaType type = (SchemaType) XmlBeans.typeSystemForClassLoader((ClassLoader) IShippingLine.class.getClassLoader(), (String)"schemaorg_apache_xmlbeans.system.s7B6816315C323FA4243DDCCA8685AE51").resolveHandle("shippinglineca5ctype");

    public IOrganizationCodes getOrganizationCodes();

    public boolean isSetOrganizationCodes();

    public void setOrganizationCodes(IOrganizationCodes var1);

    public IOrganizationCodes addNewOrganizationCodes();

    public void unsetOrganizationCodes();

    public String getShippingLineName();

    public XmlString xgetShippingLineName();

    public boolean isSetShippingLineName();

    public void setShippingLineName(String var1);

    public void xsetShippingLineName(XmlString var1);

    public void unsetShippingLineName();

    public String getShippingLineCode();

    public XmlString xgetShippingLineCode();

    public boolean isSetShippingLineCode();

    public void setShippingLineCode(String var1);

    public void xsetShippingLineCode(XmlString var1);

    public void unsetShippingLineCode();

    public String getShippingLineCodeAgency();

//    public Agency xgetShippingLineCodeAgency();

    public boolean isSetShippingLineCodeAgency();

    public void setShippingLineCodeAgency(String var1);

//    public void xsetShippingLineCodeAgency(Agency var1);

    public void unsetShippingLineCodeAgency();

    public static final class Factory {
        public static IShippingLine newInstance() {
            return (IShippingLine)XmlBeans.getContextTypeLoader().newInstance(type, null);
        }

        public static IShippingLine newInstance(XmlOptions xmlOptions) {
            return (IShippingLine)XmlBeans.getContextTypeLoader().newInstance(type, xmlOptions);
        }

        public static IShippingLine parse(String string) throws XmlException {
            return (IShippingLine)XmlBeans.getContextTypeLoader().parse(string, type, null);
        }

        public static IShippingLine parse(String string, XmlOptions xmlOptions) throws XmlException {
            return (IShippingLine)XmlBeans.getContextTypeLoader().parse(string, type, xmlOptions);
        }

        public static IShippingLine parse(File file) throws XmlException, IOException {
            return (IShippingLine)XmlBeans.getContextTypeLoader().parse(file, type, null);
        }

        public static IShippingLine parse(File file, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IShippingLine)XmlBeans.getContextTypeLoader().parse(file, type, xmlOptions);
        }

        public static IShippingLine parse(URL uRL) throws XmlException, IOException {
            return (IShippingLine)XmlBeans.getContextTypeLoader().parse(uRL, type, null);
        }

        public static IShippingLine parse(URL uRL, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IShippingLine)XmlBeans.getContextTypeLoader().parse(uRL, type, xmlOptions);
        }

        public static IShippingLine parse(InputStream inputStream) throws XmlException, IOException {
            return (IShippingLine)XmlBeans.getContextTypeLoader().parse(inputStream, type, null);
        }

        public static IShippingLine parse(InputStream inputStream, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IShippingLine)XmlBeans.getContextTypeLoader().parse(inputStream, type, xmlOptions);
        }

        public static IShippingLine parse(Reader reader) throws XmlException, IOException {
            return (IShippingLine)XmlBeans.getContextTypeLoader().parse(reader, type, null);
        }

        public static IShippingLine parse(Reader reader, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IShippingLine)XmlBeans.getContextTypeLoader().parse(reader, type, xmlOptions);
        }

        public static IShippingLine parse(XMLStreamReader xMLStreamReader) throws XmlException {
            return (IShippingLine)XmlBeans.getContextTypeLoader().parse(xMLStreamReader, type, null);
        }

        public static IShippingLine parse(XMLStreamReader xMLStreamReader, XmlOptions xmlOptions) throws XmlException {
            return (IShippingLine)XmlBeans.getContextTypeLoader().parse(xMLStreamReader, type, xmlOptions);
        }

        public static IShippingLine parse(Node node) throws XmlException {
            return (IShippingLine)XmlBeans.getContextTypeLoader().parse(node, type, null);
        }

        public static IShippingLine parse(Node node, XmlOptions xmlOptions) throws XmlException {
            return (IShippingLine)XmlBeans.getContextTypeLoader().parse(node, type, xmlOptions);
        }

        @Deprecated
        public static IShippingLine parse(XMLInputStream xMLInputStream) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (IShippingLine)XmlBeans.getContextTypeLoader().parse(xMLInputStream, type, null);
        }

        @Deprecated
        public static IShippingLine parse(XMLInputStream xMLInputStream, XmlOptions xmlOptions) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (IShippingLine)XmlBeans.getContextTypeLoader().parse(xMLInputStream, type, xmlOptions);
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
