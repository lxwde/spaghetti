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

public interface IEdiFacility {
    public static final SchemaType type = (SchemaType) XmlBeans.typeSystemForClassLoader((ClassLoader) IEdiFacility.class.getClassLoader(), (String)"schemaorg_apache_xmlbeans.system.s7B6816315C323FA4243DDCCA8685AE51").resolveHandle("edifacilitycfd5type");

    public IPort getFacilityPort();

    public boolean isSetFacilityPort();

    public void setFacilityPort(IPort var1);

    public IPort addNewFacilityPort();

    public void unsetFacilityPort();

    public String getFacilityId();

    public XmlString xgetFacilityId();

    public boolean isSetFacilityId();

    public void setFacilityId(String var1);

    public void xsetFacilityId(XmlString var1);

    public void unsetFacilityId();

    public static final class Factory {
        public static IEdiFacility newInstance() {
            return (IEdiFacility)XmlBeans.getContextTypeLoader().newInstance(type, null);
        }

        public static IEdiFacility newInstance(XmlOptions xmlOptions) {
            return (IEdiFacility)XmlBeans.getContextTypeLoader().newInstance(type, xmlOptions);
        }

        public static IEdiFacility parse(String string) throws XmlException {
            return (IEdiFacility)XmlBeans.getContextTypeLoader().parse(string, type, null);
        }

        public static IEdiFacility parse(String string, XmlOptions xmlOptions) throws XmlException {
            return (IEdiFacility)XmlBeans.getContextTypeLoader().parse(string, type, xmlOptions);
        }

        public static IEdiFacility parse(File file) throws XmlException, IOException {
            return (IEdiFacility)XmlBeans.getContextTypeLoader().parse(file, type, null);
        }

        public static IEdiFacility parse(File file, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IEdiFacility)XmlBeans.getContextTypeLoader().parse(file, type, xmlOptions);
        }

        public static IEdiFacility parse(URL uRL) throws XmlException, IOException {
            return (IEdiFacility)XmlBeans.getContextTypeLoader().parse(uRL, type, null);
        }

        public static IEdiFacility parse(URL uRL, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IEdiFacility)XmlBeans.getContextTypeLoader().parse(uRL, type, xmlOptions);
        }

        public static IEdiFacility parse(InputStream inputStream) throws XmlException, IOException {
            return (IEdiFacility)XmlBeans.getContextTypeLoader().parse(inputStream, type, null);
        }

        public static IEdiFacility parse(InputStream inputStream, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IEdiFacility)XmlBeans.getContextTypeLoader().parse(inputStream, type, xmlOptions);
        }

        public static IEdiFacility parse(Reader reader) throws XmlException, IOException {
            return (IEdiFacility)XmlBeans.getContextTypeLoader().parse(reader, type, null);
        }

        public static IEdiFacility parse(Reader reader, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IEdiFacility)XmlBeans.getContextTypeLoader().parse(reader, type, xmlOptions);
        }

        public static IEdiFacility parse(XMLStreamReader xMLStreamReader) throws XmlException {
            return (IEdiFacility)XmlBeans.getContextTypeLoader().parse(xMLStreamReader, type, null);
        }

        public static IEdiFacility parse(XMLStreamReader xMLStreamReader, XmlOptions xmlOptions) throws XmlException {
            return (IEdiFacility)XmlBeans.getContextTypeLoader().parse(xMLStreamReader, type, xmlOptions);
        }

        public static IEdiFacility parse(Node node) throws XmlException {
            return (IEdiFacility)XmlBeans.getContextTypeLoader().parse(node, type, null);
        }

        public static IEdiFacility parse(Node node, XmlOptions xmlOptions) throws XmlException {
            return (IEdiFacility)XmlBeans.getContextTypeLoader().parse(node, type, xmlOptions);
        }

        @Deprecated
        public static IEdiFacility parse(XMLInputStream xMLInputStream) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (IEdiFacility)XmlBeans.getContextTypeLoader().parse(xMLInputStream, type, null);
        }

        @Deprecated
        public static IEdiFacility parse(XMLInputStream xMLInputStream, XmlOptions xmlOptions) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (IEdiFacility)XmlBeans.getContextTypeLoader().parse(xMLInputStream, type, xmlOptions);
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
