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

public interface IEdiVvFlexFields {
    public static final SchemaType type = (SchemaType) XmlBeans.typeSystemForClassLoader((ClassLoader) IEdiVvFlexFields.class.getClassLoader(), (String)"schemaorg_apache_xmlbeans.system.s7B6816315C323FA4243DDCCA8685AE51").resolveHandle("edivvflexfieldsaae4type");

    public String getVvFlexString01();

    public XmlString xgetVvFlexString01();

    public boolean isSetVvFlexString01();

    public void setVvFlexString01(String var1);

    public void xsetVvFlexString01(XmlString var1);

    public void unsetVvFlexString01();

    public String getVvFlexString02();

    public XmlString xgetVvFlexString02();

    public boolean isSetVvFlexString02();

    public void setVvFlexString02(String var1);

    public void xsetVvFlexString02(XmlString var1);

    public void unsetVvFlexString02();

    public String getVvFlexString03();

    public XmlString xgetVvFlexString03();

    public boolean isSetVvFlexString03();

    public void setVvFlexString03(String var1);

    public void xsetVvFlexString03(XmlString var1);

    public void unsetVvFlexString03();

    public String getVvFlexString04();

    public XmlString xgetVvFlexString04();

    public boolean isSetVvFlexString04();

    public void setVvFlexString04(String var1);

    public void xsetVvFlexString04(XmlString var1);

    public void unsetVvFlexString04();

    public String getVvFlexString05();

    public XmlString xgetVvFlexString05();

    public boolean isSetVvFlexString05();

    public void setVvFlexString05(String var1);

    public void xsetVvFlexString05(XmlString var1);

    public void unsetVvFlexString05();

    public String getVvFlexString06();

    public XmlString xgetVvFlexString06();

    public boolean isSetVvFlexString06();

    public void setVvFlexString06(String var1);

    public void xsetVvFlexString06(XmlString var1);

    public void unsetVvFlexString06();

    public String getVvFlexString07();

    public XmlString xgetVvFlexString07();

    public boolean isSetVvFlexString07();

    public void setVvFlexString07(String var1);

    public void xsetVvFlexString07(XmlString var1);

    public void unsetVvFlexString07();

    public String getVvFlexString08();

    public XmlString xgetVvFlexString08();

    public boolean isSetVvFlexString08();

    public void setVvFlexString08(String var1);

    public void xsetVvFlexString08(XmlString var1);

    public void unsetVvFlexString08();

    public Object getVvFlexDate01();

    public XmlDate xgetVvFlexDate01();

    public boolean isSetVvFlexDate01();

    public void setVvFlexDate01(Object var1);

    public void xsetVvFlexDate01(XmlDate var1);

    public void unsetVvFlexDate01();

    public Object getVvFlexDate02();

    public XmlDate xgetVvFlexDate02();

    public boolean isSetVvFlexDate02();

    public void setVvFlexDate02(Object var1);

    public void xsetVvFlexDate02(XmlDate var1);

    public void unsetVvFlexDate02();

    public Object getVvFlexDate03();

    public XmlDate xgetVvFlexDate03();

    public boolean isSetVvFlexDate03();

    public void setVvFlexDate03(Object var1);

    public void xsetVvFlexDate03(XmlDate var1);

    public void unsetVvFlexDate03();

    public Object getVvFlexDate04();

    public XmlDate xgetVvFlexDate04();

    public boolean isSetVvFlexDate04();

    public void setVvFlexDate04(Object var1);

    public void xsetVvFlexDate04(XmlDate var1);

    public void unsetVvFlexDate04();

    public Object getVvFlexDate05();

    public XmlDate xgetVvFlexDate05();

    public boolean isSetVvFlexDate05();

    public void setVvFlexDate05(Object var1);

    public void xsetVvFlexDate05(XmlDate var1);

    public void unsetVvFlexDate05();

    public Object getVvFlexDate06();

    public XmlDate xgetVvFlexDate06();

    public boolean isSetVvFlexDate06();

    public void setVvFlexDate06(Object var1);

    public void xsetVvFlexDate06(XmlDate var1);

    public void unsetVvFlexDate06();

    public Object getVvFlexDate07();

    public XmlDate xgetVvFlexDate07();

    public boolean isSetVvFlexDate07();

    public void setVvFlexDate07(Object var1);

    public void xsetVvFlexDate07(XmlDate var1);

    public void unsetVvFlexDate07();

    public Object getVvFlexDate08();

    public XmlDate xgetVvFlexDate08();

    public boolean isSetVvFlexDate08();

    public void setVvFlexDate08(Object var1);

    public void xsetVvFlexDate08(XmlDate var1);

    public void unsetVvFlexDate08();

    public static final class Factory {
        public static IEdiVvFlexFields newInstance() {
            return (IEdiVvFlexFields)XmlBeans.getContextTypeLoader().newInstance(type, null);
        }

        public static IEdiVvFlexFields newInstance(XmlOptions xmlOptions) {
            return (IEdiVvFlexFields)XmlBeans.getContextTypeLoader().newInstance(type, xmlOptions);
        }

        public static IEdiVvFlexFields parse(String string) throws XmlException {
            return (IEdiVvFlexFields)XmlBeans.getContextTypeLoader().parse(string, type, null);
        }

        public static IEdiVvFlexFields parse(String string, XmlOptions xmlOptions) throws XmlException {
            return (IEdiVvFlexFields)XmlBeans.getContextTypeLoader().parse(string, type, xmlOptions);
        }

        public static IEdiVvFlexFields parse(File file) throws XmlException, IOException {
            return (IEdiVvFlexFields)XmlBeans.getContextTypeLoader().parse(file, type, null);
        }

        public static IEdiVvFlexFields parse(File file, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IEdiVvFlexFields)XmlBeans.getContextTypeLoader().parse(file, type, xmlOptions);
        }

        public static IEdiVvFlexFields parse(URL uRL) throws XmlException, IOException {
            return (IEdiVvFlexFields)XmlBeans.getContextTypeLoader().parse(uRL, type, null);
        }

        public static IEdiVvFlexFields parse(URL uRL, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IEdiVvFlexFields)XmlBeans.getContextTypeLoader().parse(uRL, type, xmlOptions);
        }

        public static IEdiVvFlexFields parse(InputStream inputStream) throws XmlException, IOException {
            return (IEdiVvFlexFields)XmlBeans.getContextTypeLoader().parse(inputStream, type, null);
        }

        public static IEdiVvFlexFields parse(InputStream inputStream, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IEdiVvFlexFields)XmlBeans.getContextTypeLoader().parse(inputStream, type, xmlOptions);
        }

        public static IEdiVvFlexFields parse(Reader reader) throws XmlException, IOException {
            return (IEdiVvFlexFields)XmlBeans.getContextTypeLoader().parse(reader, type, null);
        }

        public static IEdiVvFlexFields parse(Reader reader, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IEdiVvFlexFields)XmlBeans.getContextTypeLoader().parse(reader, type, xmlOptions);
        }

        public static IEdiVvFlexFields parse(XMLStreamReader xMLStreamReader) throws XmlException {
            return (IEdiVvFlexFields)XmlBeans.getContextTypeLoader().parse(xMLStreamReader, type, null);
        }

        public static IEdiVvFlexFields parse(XMLStreamReader xMLStreamReader, XmlOptions xmlOptions) throws XmlException {
            return (IEdiVvFlexFields)XmlBeans.getContextTypeLoader().parse(xMLStreamReader, type, xmlOptions);
        }

        public static IEdiVvFlexFields parse(Node node) throws XmlException {
            return (IEdiVvFlexFields)XmlBeans.getContextTypeLoader().parse(node, type, null);
        }

        public static IEdiVvFlexFields parse(Node node, XmlOptions xmlOptions) throws XmlException {
            return (IEdiVvFlexFields)XmlBeans.getContextTypeLoader().parse(node, type, xmlOptions);
        }

        @Deprecated
        public static IEdiVvFlexFields parse(XMLInputStream xMLInputStream) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (IEdiVvFlexFields)XmlBeans.getContextTypeLoader().parse(xMLInputStream, type, null);
        }

        @Deprecated
        public static IEdiVvFlexFields parse(XMLInputStream xMLInputStream, XmlOptions xmlOptions) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (IEdiVvFlexFields)XmlBeans.getContextTypeLoader().parse(xMLInputStream, type, xmlOptions);
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
