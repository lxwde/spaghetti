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

public interface IEdiTvFlexFields extends XmlObject {
    public static final SchemaType type = (SchemaType) XmlBeans.typeSystemForClassLoader((ClassLoader) IEdiTvFlexFields.class.getClassLoader(), (String)"schemaorg_apache_xmlbeans.system.s7B6816315C323FA4243DDCCA8685AE51").resolveHandle("editvflexfields71a6type");

    public String getRvdtlsFlexString01();

    public XmlString xgetRvdtlsFlexString01();

    public boolean isSetRvdtlsFlexString01();

    public void setRvdtlsFlexString01(String var1);

    public void xsetRvdtlsFlexString01(XmlString var1);

    public void unsetRvdtlsFlexString01();

    public String getRvdtlsFlexString02();

    public XmlString xgetRvdtlsFlexString02();

    public boolean isSetRvdtlsFlexString02();

    public void setRvdtlsFlexString02(String var1);

    public void xsetRvdtlsFlexString02(XmlString var1);

    public void unsetRvdtlsFlexString02();

    public String getRvdtlsFlexString03();

    public XmlString xgetRvdtlsFlexString03();

    public boolean isSetRvdtlsFlexString03();

    public void setRvdtlsFlexString03(String var1);

    public void xsetRvdtlsFlexString03(XmlString var1);

    public void unsetRvdtlsFlexString03();

    public String getRvdtlsFlexString04();

    public XmlString xgetRvdtlsFlexString04();

    public boolean isSetRvdtlsFlexString04();

    public void setRvdtlsFlexString04(String var1);

    public void xsetRvdtlsFlexString04(XmlString var1);

    public void unsetRvdtlsFlexString04();

    public String getRvdtlsFlexString05();

    public XmlString xgetRvdtlsFlexString05();

    public boolean isSetRvdtlsFlexString05();

    public void setRvdtlsFlexString05(String var1);

    public void xsetRvdtlsFlexString05(XmlString var1);

    public void unsetRvdtlsFlexString05();

    public String getRvdtlsFlexString06();

    public XmlString xgetRvdtlsFlexString06();

    public boolean isSetRvdtlsFlexString06();

    public void setRvdtlsFlexString06(String var1);

    public void xsetRvdtlsFlexString06(XmlString var1);

    public void unsetRvdtlsFlexString06();

    public String getRvdtlsFlexString07();

    public XmlString xgetRvdtlsFlexString07();

    public boolean isSetRvdtlsFlexString07();

    public void setRvdtlsFlexString07(String var1);

    public void xsetRvdtlsFlexString07(XmlString var1);

    public void unsetRvdtlsFlexString07();

    public String getRvdtlsFlexString08();

    public XmlString xgetRvdtlsFlexString08();

    public boolean isSetRvdtlsFlexString08();

    public void setRvdtlsFlexString08(String var1);

    public void xsetRvdtlsFlexString08(XmlString var1);

    public void unsetRvdtlsFlexString08();

    public static final class Factory {
        public static IEdiTvFlexFields newInstance() {
            return (IEdiTvFlexFields)XmlBeans.getContextTypeLoader().newInstance(type, null);
        }

        public static IEdiTvFlexFields newInstance(XmlOptions xmlOptions) {
            return (IEdiTvFlexFields)XmlBeans.getContextTypeLoader().newInstance(type, xmlOptions);
        }

        public static IEdiTvFlexFields parse(String string) throws XmlException {
            return (IEdiTvFlexFields)XmlBeans.getContextTypeLoader().parse(string, type, null);
        }

        public static IEdiTvFlexFields parse(String string, XmlOptions xmlOptions) throws XmlException {
            return (IEdiTvFlexFields)XmlBeans.getContextTypeLoader().parse(string, type, xmlOptions);
        }

        public static IEdiTvFlexFields parse(File file) throws XmlException, IOException {
            return (IEdiTvFlexFields)XmlBeans.getContextTypeLoader().parse(file, type, null);
        }

        public static IEdiTvFlexFields parse(File file, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IEdiTvFlexFields)XmlBeans.getContextTypeLoader().parse(file, type, xmlOptions);
        }

        public static IEdiTvFlexFields parse(URL uRL) throws XmlException, IOException {
            return (IEdiTvFlexFields)XmlBeans.getContextTypeLoader().parse(uRL, type, null);
        }

        public static IEdiTvFlexFields parse(URL uRL, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IEdiTvFlexFields)XmlBeans.getContextTypeLoader().parse(uRL, type, xmlOptions);
        }

        public static IEdiTvFlexFields parse(InputStream inputStream) throws XmlException, IOException {
            return (IEdiTvFlexFields)XmlBeans.getContextTypeLoader().parse(inputStream, type, null);
        }

        public static IEdiTvFlexFields parse(InputStream inputStream, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IEdiTvFlexFields)XmlBeans.getContextTypeLoader().parse(inputStream, type, xmlOptions);
        }

        public static IEdiTvFlexFields parse(Reader reader) throws XmlException, IOException {
            return (IEdiTvFlexFields)XmlBeans.getContextTypeLoader().parse(reader, type, null);
        }

        public static IEdiTvFlexFields parse(Reader reader, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IEdiTvFlexFields)XmlBeans.getContextTypeLoader().parse(reader, type, xmlOptions);
        }

        public static IEdiTvFlexFields parse(XMLStreamReader xMLStreamReader) throws XmlException {
            return (IEdiTvFlexFields)XmlBeans.getContextTypeLoader().parse(xMLStreamReader, type, null);
        }

        public static IEdiTvFlexFields parse(XMLStreamReader xMLStreamReader, XmlOptions xmlOptions) throws XmlException {
            return (IEdiTvFlexFields)XmlBeans.getContextTypeLoader().parse(xMLStreamReader, type, xmlOptions);
        }

        public static IEdiTvFlexFields parse(Node node) throws XmlException {
            return (IEdiTvFlexFields)XmlBeans.getContextTypeLoader().parse(node, type, null);
        }

        public static IEdiTvFlexFields parse(Node node, XmlOptions xmlOptions) throws XmlException {
            return (IEdiTvFlexFields)XmlBeans.getContextTypeLoader().parse(node, type, xmlOptions);
        }

        @Deprecated
        public static IEdiTvFlexFields parse(XMLInputStream xMLInputStream) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (IEdiTvFlexFields)XmlBeans.getContextTypeLoader().parse(xMLInputStream, type, null);
        }

        @Deprecated
        public static IEdiTvFlexFields parse(XMLInputStream xMLInputStream, XmlOptions xmlOptions) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (IEdiTvFlexFields)XmlBeans.getContextTypeLoader().parse(xMLInputStream, type, xmlOptions);
        }

        @Deprecated
        public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xMLInputStream) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xMLInputStream, type, null);
        }

        @Deprecated
        public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xMLInputStream, XmlOptions xmlOptions) throws XmlException, XMLStreamException {
            try {
                return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xMLInputStream, type, xmlOptions);
            } catch (org.apache.xmlbeans.xml.stream.XMLStreamException e) {
                e.printStackTrace();
            }
            return null;
        }

        private Factory() {
        }
    }

}
