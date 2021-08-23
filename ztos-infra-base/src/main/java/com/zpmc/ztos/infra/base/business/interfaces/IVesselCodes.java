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

public interface IVesselCodes {
    public static final SchemaType type = (SchemaType) XmlBeans.typeSystemForClassLoader((ClassLoader) IVesselCodes.class.getClassLoader(), (String)"schemaorg_apache_xmlbeans.system.s7B6816315C323FA4243DDCCA8685AE51").resolveHandle("vesselcodes3938type");

    public String getId();

    public XmlString xgetId();

    public boolean isSetId();

    public void setId(String var1);

    public void xsetId(XmlString var1);

    public void unsetId();

    public String getCallSign();

    public XmlString xgetCallSign();

    public boolean isSetCallSign();

    public void setCallSign(String var1);

    public void xsetCallSign(XmlString var1);

    public void unsetCallSign();

    public String getLloydsCode();

    public XmlString xgetLloydsCode();

    public boolean isSetLloydsCode();

    public void setLloydsCode(String var1);

    public void xsetLloydsCode(XmlString var1);

    public void unsetLloydsCode();

    public static final class Factory {
        public static IVesselCodes newInstance() {
            return (IVesselCodes)XmlBeans.getContextTypeLoader().newInstance(type, null);
        }

        public static IVesselCodes newInstance(XmlOptions xmlOptions) {
            return (IVesselCodes)XmlBeans.getContextTypeLoader().newInstance(type, xmlOptions);
        }

        public static IVesselCodes parse(String string) throws XmlException {
            return (IVesselCodes)XmlBeans.getContextTypeLoader().parse(string, type, null);
        }

        public static IVesselCodes parse(String string, XmlOptions xmlOptions) throws XmlException {
            return (IVesselCodes)XmlBeans.getContextTypeLoader().parse(string, type, xmlOptions);
        }

        public static IVesselCodes parse(File file) throws XmlException, IOException {
            return (IVesselCodes)XmlBeans.getContextTypeLoader().parse(file, type, null);
        }

        public static IVesselCodes parse(File file, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IVesselCodes)XmlBeans.getContextTypeLoader().parse(file, type, xmlOptions);
        }

        public static IVesselCodes parse(URL uRL) throws XmlException, IOException {
            return (IVesselCodes)XmlBeans.getContextTypeLoader().parse(uRL, type, null);
        }

        public static IVesselCodes parse(URL uRL, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IVesselCodes)XmlBeans.getContextTypeLoader().parse(uRL, type, xmlOptions);
        }

        public static IVesselCodes parse(InputStream inputStream) throws XmlException, IOException {
            return (IVesselCodes)XmlBeans.getContextTypeLoader().parse(inputStream, type, null);
        }

        public static IVesselCodes parse(InputStream inputStream, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IVesselCodes)XmlBeans.getContextTypeLoader().parse(inputStream, type, xmlOptions);
        }

        public static IVesselCodes parse(Reader reader) throws XmlException, IOException {
            return (IVesselCodes)XmlBeans.getContextTypeLoader().parse(reader, type, null);
        }

        public static IVesselCodes parse(Reader reader, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IVesselCodes)XmlBeans.getContextTypeLoader().parse(reader, type, xmlOptions);
        }

        public static IVesselCodes parse(XMLStreamReader xMLStreamReader) throws XmlException {
            return (IVesselCodes)XmlBeans.getContextTypeLoader().parse(xMLStreamReader, type, null);
        }

        public static IVesselCodes parse(XMLStreamReader xMLStreamReader, XmlOptions xmlOptions) throws XmlException {
            return (IVesselCodes)XmlBeans.getContextTypeLoader().parse(xMLStreamReader, type, xmlOptions);
        }

        public static IVesselCodes parse(Node node) throws XmlException {
            return (IVesselCodes)XmlBeans.getContextTypeLoader().parse(node, type, null);
        }

        public static IVesselCodes parse(Node node, XmlOptions xmlOptions) throws XmlException {
            return (IVesselCodes)XmlBeans.getContextTypeLoader().parse(node, type, xmlOptions);
        }

        @Deprecated
        public static IVesselCodes parse(XMLInputStream xMLInputStream) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (IVesselCodes)XmlBeans.getContextTypeLoader().parse(xMLInputStream, type, null);
        }

        @Deprecated
        public static IVesselCodes parse(XMLInputStream xMLInputStream, XmlOptions xmlOptions) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (IVesselCodes)XmlBeans.getContextTypeLoader().parse(xMLInputStream, type, xmlOptions);
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
