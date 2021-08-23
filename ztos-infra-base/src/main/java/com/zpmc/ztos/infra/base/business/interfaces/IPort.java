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

public interface IPort {
    public static final SchemaType type = (SchemaType) XmlBeans.typeSystemForClassLoader((ClassLoader) IPort.class.getClassLoader(), (String)"schemaorg_apache_xmlbeans.system.s7B6816315C323FA4243DDCCA8685AE51").resolveHandle("porte05btype");

    public IPortCodes getPortCodes();

    public boolean isSetPortCodes();

    public void setPortCodes(IPortCodes var1);

    public IPortCodes addNewPortCodes();

    public void unsetPortCodes();

    public String getPortId();

    public XmlString xgetPortId();

    public boolean isSetPortId();

    public void setPortId(String var1);

    public void xsetPortId(XmlString var1);

    public void unsetPortId();

    public String getPortIdConvention();

    public XmlString xgetPortIdConvention();

    public boolean isSetPortIdConvention();

    public void setPortIdConvention(String var1);

    public void xsetPortIdConvention(XmlString var1);

    public void unsetPortIdConvention();

    public String getPortName();

    public XmlString xgetPortName();

    public boolean isSetPortName();

    public void setPortName(String var1);

    public void xsetPortName(XmlString var1);

    public void unsetPortName();

    public String getPortCountryCode();

    public XmlString xgetPortCountryCode();

    public boolean isSetPortCountryCode();

    public void setPortCountryCode(String var1);

    public void xsetPortCountryCode(XmlString var1);

    public void unsetPortCountryCode();

    public static final class Factory {
        public static IPort newInstance() {
            return (IPort)XmlBeans.getContextTypeLoader().newInstance(type, null);
        }

        public static IPort newInstance(XmlOptions xmlOptions) {
            return (IPort)XmlBeans.getContextTypeLoader().newInstance(type, xmlOptions);
        }

        public static IPort parse(String string) throws XmlException {
            return (IPort)XmlBeans.getContextTypeLoader().parse(string, type, null);
        }

        public static IPort parse(String string, XmlOptions xmlOptions) throws XmlException {
            return (IPort)XmlBeans.getContextTypeLoader().parse(string, type, xmlOptions);
        }

        public static IPort parse(File file) throws XmlException, IOException {
            return (IPort)XmlBeans.getContextTypeLoader().parse(file, type, null);
        }

        public static IPort parse(File file, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IPort)XmlBeans.getContextTypeLoader().parse(file, type, xmlOptions);
        }

        public static IPort parse(URL uRL) throws XmlException, IOException {
            return (IPort)XmlBeans.getContextTypeLoader().parse(uRL, type, null);
        }

        public static IPort parse(URL uRL, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IPort)XmlBeans.getContextTypeLoader().parse(uRL, type, xmlOptions);
        }

        public static IPort parse(InputStream inputStream) throws XmlException, IOException {
            return (IPort)XmlBeans.getContextTypeLoader().parse(inputStream, type, null);
        }

        public static IPort parse(InputStream inputStream, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IPort)XmlBeans.getContextTypeLoader().parse(inputStream, type, xmlOptions);
        }

        public static IPort parse(Reader reader) throws XmlException, IOException {
            return (IPort)XmlBeans.getContextTypeLoader().parse(reader, type, null);
        }

        public static IPort parse(Reader reader, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IPort)XmlBeans.getContextTypeLoader().parse(reader, type, xmlOptions);
        }

        public static IPort parse(XMLStreamReader xMLStreamReader) throws XmlException {
            return (IPort)XmlBeans.getContextTypeLoader().parse(xMLStreamReader, type, null);
        }

        public static IPort parse(XMLStreamReader xMLStreamReader, XmlOptions xmlOptions) throws XmlException {
            return (IPort)XmlBeans.getContextTypeLoader().parse(xMLStreamReader, type, xmlOptions);
        }

        public static IPort parse(Node node) throws XmlException {
            return (IPort)XmlBeans.getContextTypeLoader().parse(node, type, null);
        }

        public static IPort parse(Node node, XmlOptions xmlOptions) throws XmlException {
            return (IPort)XmlBeans.getContextTypeLoader().parse(node, type, xmlOptions);
        }

        @Deprecated
        public static IPort parse(XMLInputStream xMLInputStream) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (IPort)XmlBeans.getContextTypeLoader().parse(xMLInputStream, type, null);
        }

        @Deprecated
        public static IPort parse(XMLInputStream xMLInputStream, XmlOptions xmlOptions) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (IPort)XmlBeans.getContextTypeLoader().parse(xMLInputStream, type, xmlOptions);
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
