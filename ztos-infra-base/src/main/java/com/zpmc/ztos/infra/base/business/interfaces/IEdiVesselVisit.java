package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.CarrierService;
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

public interface IEdiVesselVisit {
    public static final SchemaType type = (SchemaType) XmlBeans.typeSystemForClassLoader((ClassLoader) IEdiVesselVisit.class.getClassLoader(), (String)"schemaorg_apache_xmlbeans.system.s7B6816315C323FA4243DDCCA8685AE51").resolveHandle("edivesselvisit46c5type");

    public String getVisitId();

    public XmlString xgetVisitId();

    public boolean isSetVisitId();

    public void setVisitId(String var1);

    public void xsetVisitId(XmlString var1);

    public void unsetVisitId();

    public IShippingLine getShippingLine();

    public boolean isSetShippingLine();

    public void setShippingLine(IShippingLine var1);

    public IShippingLine addNewShippingLine();

    public void unsetShippingLine();

    public IPort getLoadPort();

    public boolean isSetLoadPort();

    public void setLoadPort(IPort var1);

    public IPort addNewLoadPort();

    public void unsetLoadPort();

    public IPort getNextPortOfCall();

    public boolean isSetNextPortOfCall();

    public void setNextPortOfCall(IPort var1);

    public IPort addNewNextPortOfCall();

    public void unsetNextPortOfCall();

    public IVesselCodes getVesselCodes();

    public boolean isSetVesselCodes();

    public void setVesselCodes(IVesselCodes var1);

    public IVesselCodes addNewVesselCodes();

    public void unsetVesselCodes();

    public IEdiVvFlexFields getVesselVisitFlexFields();

    public boolean isSetVesselVisitFlexFields();

    public void setVesselVisitFlexFields(IEdiVvFlexFields var1);

    public IEdiVvFlexFields addNewVesselVisitFlexFields();

    public void unsetVesselVisitFlexFields();

    public CarrierService getCarrierService();

    public boolean isSetCarrierService();

    public void setCarrierService(CarrierService var1);

    public CarrierService addNewCarrierService();

    public void unsetCarrierService();

    public String getInVoyageNbr();

    public XmlString xgetInVoyageNbr();

    public boolean isSetInVoyageNbr();

    public void setInVoyageNbr(String var1);

    public void xsetInVoyageNbr(XmlString var1);

    public void unsetInVoyageNbr();

    public String getOutVoyageNbr();

    public XmlString xgetOutVoyageNbr();

    public boolean isSetOutVoyageNbr();

    public void setOutVoyageNbr(String var1);

    public void xsetOutVoyageNbr(XmlString var1);

    public void unsetOutVoyageNbr();

    public String getInOperatorVoyageNbr();

    public XmlString xgetInOperatorVoyageNbr();

    public boolean isSetInOperatorVoyageNbr();

    public void setInOperatorVoyageNbr(String var1);

    public void xsetInOperatorVoyageNbr(XmlString var1);

    public void unsetInOperatorVoyageNbr();

    public String getOutOperatorVoyageNbr();

    public XmlString xgetOutOperatorVoyageNbr();

    public boolean isSetOutOperatorVoyageNbr();

    public void setOutOperatorVoyageNbr(String var1);

    public void xsetOutOperatorVoyageNbr(XmlString var1);

    public void unsetOutOperatorVoyageNbr();

    public String getCustomsVoyageNbr();

    public XmlString xgetCustomsVoyageNbr();

    public boolean isSetCustomsVoyageNbr();

    public void setCustomsVoyageNbr(String var1);

    public void xsetCustomsVoyageNbr(XmlString var1);

    public void unsetCustomsVoyageNbr();

    public String getVesselId();

    public XmlString xgetVesselId();

    public void setVesselId(String var1);

    public void xsetVesselId(XmlString var1);

    public String getVesselIdConvention();

    public VesselIdConvention xgetVesselIdConvention();

    public boolean isSetVesselIdConvention();

    public void setVesselIdConvention(String var1);

    public void xsetVesselIdConvention(VesselIdConvention var1);

    public void unsetVesselIdConvention();

    public String getVesselName();

    public XmlString xgetVesselName();

    public boolean isSetVesselName();

    public void setVesselName(String var1);

    public void xsetVesselName(XmlString var1);

    public void unsetVesselName();

    public String getVesselCountryCode();

    public XmlString xgetVesselCountryCode();

    public boolean isSetVesselCountryCode();

    public void setVesselCountryCode(String var1);

    public void xsetVesselCountryCode(XmlString var1);

    public void unsetVesselCountryCode();

    public String getEstimatedTimeArrival();

    public XmlString xgetEstimatedTimeArrival();

    public boolean isSetEstimatedTimeArrival();

    public void setEstimatedTimeArrival(String var1);

    public void xsetEstimatedTimeArrival(XmlString var1);

    public void unsetEstimatedTimeArrival();

    public String getActualTimeArrival();

    public XmlString xgetActualTimeArrival();

    public boolean isSetActualTimeArrival();

    public void setActualTimeArrival(String var1);

    public void xsetActualTimeArrival(XmlString var1);

    public void unsetActualTimeArrival();

    public String getEstimatedTimeDeparture();

    public XmlString xgetEstimatedTimeDeparture();

    public boolean isSetEstimatedTimeDeparture();

    public void setEstimatedTimeDeparture(String var1);

    public void xsetEstimatedTimeDeparture(XmlString var1);

    public void unsetEstimatedTimeDeparture();

    public String getActualTimeDeparture();

    public XmlString xgetActualTimeDeparture();

    public boolean isSetActualTimeDeparture();

    public void setActualTimeDeparture(String var1);

    public void xsetActualTimeDeparture(XmlString var1);

    public void unsetActualTimeDeparture();

    public String getTimeStartWork();

    public XmlString xgetTimeStartWork();

    public boolean isSetTimeStartWork();

    public void setTimeStartWork(String var1);

    public void xsetTimeStartWork(XmlString var1);

    public void unsetTimeStartWork();

    public String getTimeEndWork();

    public XmlString xgetTimeEndWork();

    public boolean isSetTimeEndWork();

    public void setTimeEndWork(String var1);

    public void xsetTimeEndWork(XmlString var1);

    public void unsetTimeEndWork();

    public String getLabourOnBoard();

    public XmlString xgetLabourOnBoard();

    public boolean isSetLabourOnBoard();

    public void setLabourOnBoard(String var1);

    public void xsetLabourOnBoard(XmlString var1);

    public void unsetLabourOnBoard();

    public String getLabourOnShore();

    public XmlString xgetLabourOnShore();

    public boolean isSetLabourOnShore();

    public void setLabourOnShore(String var1);

    public void xsetLabourOnShore(XmlString var1);

    public void unsetLabourOnShore();

    public String getVesselClassification();

    public VesselClassification xgetVesselClassification();

    public boolean isSetVesselClassification();

    public void setVesselClassification(String var1);

    public void xsetVesselClassification(VesselClassification var1);

    public void unsetVesselClassification();

    public String getVesselType();

    public VesselType xgetVesselType();

    public boolean isSetVesselType();

    public void setVesselType(String var1);

    public void xsetVesselType(VesselType var1);

    public void unsetVesselType();

    public String getNotes();

    public XmlString xgetNotes();

    public boolean isSetNotes();

    public void setNotes(String var1);

    public void xsetNotes(XmlString var1);

    public void unsetNotes();

    public static final class Factory {
        public static IEdiVesselVisit newInstance() {
            return (IEdiVesselVisit)XmlBeans.getContextTypeLoader().newInstance(type, null);
        }

        public static IEdiVesselVisit newInstance(XmlOptions xmlOptions) {
            return (IEdiVesselVisit)XmlBeans.getContextTypeLoader().newInstance(type, xmlOptions);
        }

        public static IEdiVesselVisit parse(String string) throws XmlException {
            return (IEdiVesselVisit)XmlBeans.getContextTypeLoader().parse(string, type, null);
        }

        public static IEdiVesselVisit parse(String string, XmlOptions xmlOptions) throws XmlException {
            return (IEdiVesselVisit)XmlBeans.getContextTypeLoader().parse(string, type, xmlOptions);
        }

        public static IEdiVesselVisit parse(File file) throws XmlException, IOException {
            return (IEdiVesselVisit)XmlBeans.getContextTypeLoader().parse(file, type, null);
        }

        public static IEdiVesselVisit parse(File file, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IEdiVesselVisit)XmlBeans.getContextTypeLoader().parse(file, type, xmlOptions);
        }

        public static IEdiVesselVisit parse(URL uRL) throws XmlException, IOException {
            return (IEdiVesselVisit)XmlBeans.getContextTypeLoader().parse(uRL, type, null);
        }

        public static IEdiVesselVisit parse(URL uRL, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IEdiVesselVisit)XmlBeans.getContextTypeLoader().parse(uRL, type, xmlOptions);
        }

        public static IEdiVesselVisit parse(InputStream inputStream) throws XmlException, IOException {
            return (IEdiVesselVisit)XmlBeans.getContextTypeLoader().parse(inputStream, type, null);
        }

        public static IEdiVesselVisit parse(InputStream inputStream, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IEdiVesselVisit)XmlBeans.getContextTypeLoader().parse(inputStream, type, xmlOptions);
        }

        public static IEdiVesselVisit parse(Reader reader) throws XmlException, IOException {
            return (IEdiVesselVisit)XmlBeans.getContextTypeLoader().parse(reader, type, null);
        }

        public static IEdiVesselVisit parse(Reader reader, XmlOptions xmlOptions) throws XmlException, IOException {
            return (IEdiVesselVisit)XmlBeans.getContextTypeLoader().parse(reader, type, xmlOptions);
        }

        public static IEdiVesselVisit parse(XMLStreamReader xMLStreamReader) throws XmlException {
            return (IEdiVesselVisit)XmlBeans.getContextTypeLoader().parse(xMLStreamReader, type, null);
        }

        public static IEdiVesselVisit parse(XMLStreamReader xMLStreamReader, XmlOptions xmlOptions) throws XmlException {
            return (IEdiVesselVisit)XmlBeans.getContextTypeLoader().parse(xMLStreamReader, type, xmlOptions);
        }

        public static IEdiVesselVisit parse(Node node) throws XmlException {
            return (IEdiVesselVisit)XmlBeans.getContextTypeLoader().parse(node, type, null);
        }

        public static IEdiVesselVisit parse(Node node, XmlOptions xmlOptions) throws XmlException {
            return (IEdiVesselVisit)XmlBeans.getContextTypeLoader().parse(node, type, xmlOptions);
        }

        @Deprecated
        public static IEdiVesselVisit parse(XMLInputStream xMLInputStream) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (IEdiVesselVisit)XmlBeans.getContextTypeLoader().parse(xMLInputStream, type, null);
        }

        @Deprecated
        public static IEdiVesselVisit parse(XMLInputStream xMLInputStream, XmlOptions xmlOptions) throws XmlException, XMLStreamException, org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (IEdiVesselVisit)XmlBeans.getContextTypeLoader().parse(xMLInputStream, type, xmlOptions);
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

    public static interface VesselType
            extends XmlAnySimpleType {
        public static final SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader((ClassLoader)VesselType.class.getClassLoader(), (String)"schemaorg_apache_xmlbeans.system.s7B6816315C323FA4243DDCCA8685AE51").resolveHandle("vesseltypefd25attrtype");

        public Object getObjectValue();

        public void setObjectValue(Object var1);

        @Deprecated
        public Object objectValue();

        @Deprecated
        public void objectSet(Object var1);

        public SchemaType instanceType();

        public static final class Factory {
            public static VesselType newValue(Object object) {
                return (VesselType)type.newValue(object);
            }

            public static VesselType newInstance() {
                return (VesselType)XmlBeans.getContextTypeLoader().newInstance(type, null);
            }

            public static VesselType newInstance(XmlOptions xmlOptions) {
                return (VesselType)XmlBeans.getContextTypeLoader().newInstance(type, xmlOptions);
            }

            private Factory() {
            }
        }

        public static interface Member2
                extends XmlString {
            public static final SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader((ClassLoader)Member2.class.getClassLoader(), (String)"schemaorg_apache_xmlbeans.system.s7B6816315C323FA4243DDCCA8685AE51").resolveHandle("anonfbcbtype");

            public static final class Factory {
                public static Member2 newValue(Object object) {
                    return (Member2)type.newValue(object);
                }

                public static Member2 newInstance() {
                    return (Member2)XmlBeans.getContextTypeLoader().newInstance(type, null);
                }

                public static Member2 newInstance(XmlOptions xmlOptions) {
                    return (Member2)XmlBeans.getContextTypeLoader().newInstance(type, xmlOptions);
                }

                private Factory() {
                }
            }
        }

        public static interface Member
                extends XmlString {
            public static final SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader((ClassLoader)Member.class.getClassLoader(), (String)"schemaorg_apache_xmlbeans.system.s7B6816315C323FA4243DDCCA8685AE51").resolveHandle("anonf2catype");

            public static final class Factory {
                public static Member newValue(Object object) {
                    return (Member)type.newValue(object);
                }

                public static Member newInstance() {
                    return (Member)XmlBeans.getContextTypeLoader().newInstance(type, null);
                }

                public static Member newInstance(XmlOptions xmlOptions) {
                    return (Member)XmlBeans.getContextTypeLoader().newInstance(type, xmlOptions);
                }

                private Factory() {
                }
            }
        }
    }

    public static interface VesselClassification
            extends XmlAnySimpleType {
        public static final SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader((ClassLoader)VesselClassification.class.getClassLoader(), (String)"schemaorg_apache_xmlbeans.system.s7B6816315C323FA4243DDCCA8685AE51").resolveHandle("vesselclassification4779attrtype");

        public Object getObjectValue();

        public void setObjectValue(Object var1);

        @Deprecated
        public Object objectValue();

        @Deprecated
        public void objectSet(Object var1);

        public SchemaType instanceType();

        public static final class Factory {
            public static VesselClassification newValue(Object object) {
                return (VesselClassification)type.newValue(object);
            }

            public static VesselClassification newInstance() {
                return (VesselClassification)XmlBeans.getContextTypeLoader().newInstance(type, null);
            }

            public static VesselClassification newInstance(XmlOptions xmlOptions) {
                return (VesselClassification)XmlBeans.getContextTypeLoader().newInstance(type, xmlOptions);
            }

            private Factory() {
            }
        }

        public static interface Member2
                extends XmlString {
            public static final SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader((ClassLoader)Member2.class.getClassLoader(), (String)"schemaorg_apache_xmlbeans.system.s7B6816315C323FA4243DDCCA8685AE51").resolveHandle("anonae9ftype");

            public static final class Factory {
                public static Member2 newValue(Object object) {
                    return (Member2)type.newValue(object);
                }

                public static Member2 newInstance() {
                    return (Member2)XmlBeans.getContextTypeLoader().newInstance(type, null);
                }

                public static Member2 newInstance(XmlOptions xmlOptions) {
                    return (Member2)XmlBeans.getContextTypeLoader().newInstance(type, xmlOptions);
                }

                private Factory() {
                }
            }
        }

        public static interface Member
                extends XmlString {
            public static final SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader((ClassLoader)Member.class.getClassLoader(), (String)"schemaorg_apache_xmlbeans.system.s7B6816315C323FA4243DDCCA8685AE51").resolveHandle("anon32detype");

            public static final class Factory {
                public static Member newValue(Object object) {
                    return (Member)type.newValue(object);
                }

                public static Member newInstance() {
                    return (Member)XmlBeans.getContextTypeLoader().newInstance(type, null);
                }

                public static Member newInstance(XmlOptions xmlOptions) {
                    return (Member)XmlBeans.getContextTypeLoader().newInstance(type, xmlOptions);
                }

                private Factory() {
                }
            }
        }
    }

    public static interface VesselIdConvention
            extends XmlAnySimpleType {
        public static final SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader((ClassLoader)VesselIdConvention.class.getClassLoader(), (String)"schemaorg_apache_xmlbeans.system.s7B6816315C323FA4243DDCCA8685AE51").resolveHandle("vesselidconvention5433attrtype");

        public Object getObjectValue();

        public void setObjectValue(Object var1);

        @Deprecated
        public Object objectValue();

        @Deprecated
        public void objectSet(Object var1);

        public SchemaType instanceType();

        public static final class Factory {
            public static VesselIdConvention newValue(Object object) {
                return (VesselIdConvention)type.newValue(object);
            }

            public static VesselIdConvention newInstance() {
                return (VesselIdConvention)XmlBeans.getContextTypeLoader().newInstance(type, null);
            }

            public static VesselIdConvention newInstance(XmlOptions xmlOptions) {
                return (VesselIdConvention)XmlBeans.getContextTypeLoader().newInstance(type, xmlOptions);
            }

            private Factory() {
            }
        }

        public static interface Member2
                extends XmlString {
            public static final SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader((ClassLoader)Member2.class.getClassLoader(), (String)"schemaorg_apache_xmlbeans.system.s7B6816315C323FA4243DDCCA8685AE51").resolveHandle("anon4cd9type");

            public static final class Factory {
                public static Member2 newValue(Object object) {
                    return (Member2)type.newValue(object);
                }

                public static Member2 newInstance() {
                    return (Member2)XmlBeans.getContextTypeLoader().newInstance(type, null);
                }

                public static Member2 newInstance(XmlOptions xmlOptions) {
                    return (Member2)XmlBeans.getContextTypeLoader().newInstance(type, xmlOptions);
                }

                private Factory() {
                }
            }
        }

        public static interface Member
                extends XmlString {
            public static final SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader((ClassLoader)Member.class.getClassLoader(), (String)"schemaorg_apache_xmlbeans.system.s7B6816315C323FA4243DDCCA8685AE51").resolveHandle("anond4d8type");

            public static final class Factory {
                public static Member newValue(Object object) {
                    return (Member)type.newValue(object);
                }

                public static Member newInstance() {
                    return (Member)XmlBeans.getContextTypeLoader().newInstance(type, null);
                }

                public static Member newInstance(XmlOptions xmlOptions) {
                    return (Member)XmlBeans.getContextTypeLoader().newInstance(type, xmlOptions);
                }

                private Factory() {
                }
            }
        }
    }

}
