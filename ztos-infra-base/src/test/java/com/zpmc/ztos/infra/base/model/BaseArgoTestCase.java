package com.zpmc.ztos.infra.base.model;


import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import com.zpmc.ztos.infra.base.business.enums.argo.*;
import com.zpmc.ztos.infra.base.business.enums.core.PredicateParmEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.FieldImportanceEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.business.enums.framework.PredicateVerbEnum;
import com.zpmc.ztos.infra.base.business.equipments.Chassis;
import com.zpmc.ztos.infra.base.business.equipments.EquipType;
import com.zpmc.ztos.infra.base.business.equipments.Equipment;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.*;
import com.zpmc.ztos.infra.base.business.predicate.EntityMappingPredicate;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.callbacks.CarinaPersistenceCallback;
import com.zpmc.ztos.infra.base.common.configs.LongConfig;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.database.PersistenceTemplate;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.helps.DatabaseHelper;
import com.zpmc.ztos.infra.base.common.messages.MessageCollectorFactory;
import com.zpmc.ztos.infra.base.common.model.*;
import com.zpmc.ztos.infra.base.common.scopes.*;
import com.zpmc.ztos.infra.base.common.security.ArgoSecRole;
import com.zpmc.ztos.infra.base.common.security.ArgoUser;
import com.zpmc.ztos.infra.base.common.security.BaseUser;
import com.zpmc.ztos.infra.base.common.security.SecRole;
import com.zpmc.ztos.infra.base.common.systems.ArgoSequenceProvider;
import com.zpmc.ztos.infra.base.common.type.FileUtil;
import com.zpmc.ztos.infra.base.common.utils.*;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import lombok.Data;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.hibernate.FlushMode;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;


import java.io.*;
import java.rmi.RemoteException;
import java.util.*;

@Data
public class BaseArgoTestCase extends ManualHibernateTestCase{
    protected static final String SNX_ROOT_START = "<snx>";
    protected static final String SNX_ROOT_END = "</snx>";
    public static final String OPR1 = "OPR1";
    protected static final String OPR2 = "OPR2";
    public static final String CPX11 = "CPX11";
    protected static final String CPX12 = "CPX12";
    protected static final String CPX21 = "CPX21";
    public static final String FCY111 = "FCY111";
    protected static final String FCY11NO = "FCY11NO";
    public static final String FCY112 = "FCY112";
    protected static final String FCY113 = "FCY113";
    protected static final String FCY114 = "FCY114";
    protected static final String FCY115 = "FCY115";
    protected static final String FCY121 = "FCY121";
    protected static final String FCY211 = "FCY211";
    public static final String YRD1111 = "YRD1111";
    protected static final String YRD1112 = "YRD1112";
    protected static final String YRD1211 = "YRD1211";
    public static final String YRD1121 = "YRD1121";
    public static final String YRD1131 = "YRD1131";
    protected static final String YRD1141 = "YRD1141";
    protected static final String YRD2111 = "YRD2111";
    protected static final String YRD2112 = "YRD2112";
    protected static final String CNTR_ID1 = "APMU275389";
    protected static final String CNTR_ID2 = "AMFU861426";
    protected static final String CNTR_ID3 = "CAXU229457";
    protected static final String CNTR_ID4 = "BAXU2602843";
    protected static final String VSL_SERVICE1 = "PSX";
    protected static final String CARRIER_INTINERARY1 = "PSX-STD";
    protected static final String ROUTING_POINT_1_ID = "OAK";
    protected static final String ROUTING_POINT_1_UN = "USOAK";
    public static final String ROUTING_POINT_2_ID = "LYT";
    protected static final String ROUTING_POINT_2_UN = "NZLYT";
    protected static final String ROUTING_POINT_3_ID = "LAX";
    protected static final String ROUTING_POINT_3_UN = "USLAX";
    protected static final String ROUTING_POINT_4_ID = "KHH";
    protected static final String ROUTING_POINT_4_UN = "TWKHH";
    protected static final String ROUTING_POINT_5_ID = "SIN";
    protected static final String ROUTING_POINT_5_UN = "SGSIN";
    protected static final String ROUTING_POINT_6_ID = "HKG";
    protected static final String ROUTING_POINT_6_UN = "CNHKG";
    protected static final String ROUTING_POINT_7_ID = "HON";
    protected static final String ROUTING_POINT_7_UN = "USHON";
    public static final String LINE_OP_1_ID = "POPEYE";
    public static final String LINE_OP_2_ID = "CHARON";
    public static final String FTP_SERVER_IP_ADDRESS = "10.47.101.41";
    public static final String FTP_SERVER_IP_ADDRESS_PORT = "10.47.101.41:21";
    public static final String FTP_SERVER_USER_NAME = "webdepot";
    public static final String FTP_SERVER_PASSWORD = "webdepot";
    public static final String FTP_TST_IN_FOLDER = "/home/webdepot/test/TST/IN";
    public static final String FTP_WALMART_IN_FOLDER = "/home/webdepot/test/walmart/in";
    public static final String FTP_APL_IN_FOLDER = "/home/webdepot/test/APL/IN";
    public static final String FTP_APL_OUT_FOLDER = "/home/webdepot/test/APL/OUT";
    public static final String FTP_FERGUSSON_OUT_FOLDER = "/home/webdepot/test/FERGUSSON/OUT";
    public static final String FTP_FERGUSSON_LOADSAMPLE_FOLDER = "/home/webdepot/test/FERGUSSON/LOADSAMPLE";
    protected static final String A_LIFE_CYCLE_STATE = "life-cycle-state";
//   protected static final SnxTestCase SNX_TEST_CASE = new SnxTestCase();
    private static final Logger LOGGER = Logger.getLogger(BaseArgoTestCase.class);
    public static final String TEST_YARD_FILE = "testdata/lpc_yard.nyd";
    public static final String TEST_LPC_BERTHS_FILE = "testdata/lpc_berths.txt";
    public static final String TEST_MAH_YARD_FILE = "testdata/mah_yard.nyd";
    public static final String TEST_MAT_YARD_FILE = "testdata/si_yard.nyd";
    public static final String TEST_AGW_YARD_FILE = "testdata/agw_yard.nyd";
    public static final String TEST_AGW_SPARCS_SETTINGS_FILE = "testdata/agw_sparcs_settings.txt";
    public static final String RP001 = "HJH";
    public static final String RP002 = "NYC";
    public static final String RP003 = "BOS";
    public static final String RP004 = "AKL";
    public static final String RP005 = "ADL";
    public static final String RP006 = "LAX";
    public static final String RP007 = "KHH";
    public static final String LYT = "LYT";
    public static final String SIN = "SIN";
    protected static final String STOW_POS = "123456";
    public static final String EQ_TYPE_ID_2200 = "2200";
    public static final String EQ_TYPE_ID_4200 = "4200";
    private static final String USE_EMPTY_YARD_BIN_MODEL = "com.navis.useEmptyYardBinModel";
 //   private final CrudBizDelegate _delegate = CrudDelegate.getBizDelegate();
    private Serializable _opr1Gkey;
    private Serializable _opr2Gkey;
    private Serializable _cpx11Gkey;
    private Serializable _cpx12Gkey;
    private Serializable _cpx21Gkey;
    private Serializable _fcy111Gkey;
    private Serializable _fcy112Gkey;
    private Serializable _fcy113Gkey;
    private Serializable _fcy115Gkey;
    private Serializable _fcy121Gkey;
    private Serializable _fcy211Gkey;
    private Serializable _fcy11NonOpGkey;
    private Serializable _yrd1111Gkey;
    private Serializable _yrd1112Gkey;
    private Serializable _yrd1131Gkey;
    private Serializable _yrd1141Gkey;
    private Serializable _yrd1121Gkey;
    private Serializable _yrd1211Gkey;
    private Serializable _yrd2111Gkey;
    private Serializable _yrd2112Gkey;
    public static final String TESTDATA_DIR = "testdata/";
    protected static final String CNTR_ID5 = "AXXX2090900";
    protected static final String BOMBCART_EQUIP_TYPE = "40CB";
    protected static final String USERNAME_PROPERTY = "admin";
    protected static final String PWD_PROPERTY = "admin";
    private static final String CONFIGURATION_PROPERTIES = "ConfigurationProperties";
    public static final BaseConfigurationProperties BC = (BaseConfigurationProperties) Roastery.getBean((String)"ConfigurationProperties");
    protected static final String ARGO_SERVICE_URL = "http://localhost:" + BC.getPort() + "/apex/services/argoservice";
    private static final String TEMP_XML = "<icu>\n    <units>\n        <unit-identity id=\"SPKU1234567\" type=\"CONTAINERIZED\">\n        </unit-identity>\n    </units>\n    <properties>\n        <property tag=\"SealNbr1\" value=\"P\"/>\n    </properties>\n    <event   user-id=\"IcuTester\"   id=\"UNIT_PREADVISE\"/>\n</icu>";


    protected void setUp() throws Exception {
        super.setUp();
        this.startHibernateAtCpx11();
        this.createEquipmentTypesRoutingPointsAndBizUnits();
        this.getTestLineOp1();
        this.endHibernate();
        this.startHibernateAtCpx21();
        this.createEquipmentTypesRoutingPointsAndBizUnits();
        this.endHibernate();
//        if (IArchiveTestCase.class.isAssignableFrom(((Object)((Object)this)).getClass())) {
//            this.startHibernateAtCpx11();
//            PurgeSettingAnnotationProcessor.processSetUpAnnotations(((Object)((Object)this)).getClass());
//            this.endHibernate();
//        }
    }

    protected void tearDown() throws Exception {
        super.tearDown();
//        if (IArchiveTestCase.class.isAssignableFrom(((Object)((Object)this)).getClass())) {
//            this.startHibernateAtCpx11();
//            PurgeSettingAnnotationProcessor.processTearDownAnnotations(((Object)((Object)this)).getClass());
//            this.endHibernate();
//        }
    }

    protected IArgoServicePort locateArgoService() {
        IArgoServicePort port = null;
//        try {
//            ArgoServiceLocator service = new ArgoServiceLocator();
//            port = service.getArgoServicePort(new URL(ARGO_SERVICE_URL));
//            Stub stub = (Stub)port;
//            stub._setProperty("javax.xml.rpc.security.auth.username", (Object)"admin");
//            stub._setProperty("javax.xml.rpc.security.auth.password", (Object)"admin");
//        }
//        catch (Exception e) {
//            this.failOnException("locateArgoService() failed with: ", e);
//        }
//        BaseArgoTestCase.assertNotNull(port);
        return port;
    }

    public Exception invokePurgeBean()
    {
//        try {
//            UserContext uc = this.getTestUserContextAtCpx11();
//            ArgoDatabasePurgeBean purgeBean = (ArgoDatabasePurgeBean)Roastery.getBean((String)"argoDatabasePurgeBean");
//            purgeBean.purgeDatabaseWithoutJobContext(uc, null, null);
//        }
//        catch (Exception inEx) {
//            BaseArgoTestCase.fail((String)("Purging failed with following error\n" + CarinaUtils.getCompactStackTrace((Throwable)inEx)));
//            return inEx;
//        }
        return null;
    }

    public CarrierVisit createTestVesselVisit(String inCvId, String inLineId) {
        return BaseArgoTestCase.createTestVesselVisit(inCvId, inLineId, ContextHelper.getThreadFacility());
    }

    public static CarrierVisit createTestVesselVisit(String inCvId, @Nullable String inVesselClassName, String inLineId, Facility inFacility) {
        String obVoy;
        String lloydsId;
        String vesSrvc;
        String vesId;
        BaseArgoTestCase.assertNotNull((Object)inFacility);
        String crntPt = inFacility.getFcyRoutingPoint().getPointId();
        String vesName = vesId = (vesSrvc = inCvId);
        if (inCvId.length() > 8) {
            inCvId = inCvId.substring(0, 8);
            vesSrvc = inCvId + "SRVC";
            vesId = inCvId.substring(0, 6) + "ID";
            vesName = inCvId + "NAME";
            if (inVesselClassName == null) {
                inVesselClassName = inCvId.substring(0, 4) + "CLSS";
            }
        } else if (inVesselClassName == null) {
            inVesselClassName = vesName;
        }
        if ((lloydsId = "AB" + new LloydsIdProvider().getNextLloydsId()).length() > 8) {
            lloydsId = lloydsId.substring(0, 8);
        }
        String callSign = inCvId + "_R";
        VoyageIdProvider voyageId = new VoyageIdProvider();
        String ibVoy = String.valueOf(voyageId.getNextVoyageId());
        if (ibVoy.length() > 6) {
            ibVoy = "I" + ibVoy.substring(0, 5);
        }
        if ((obVoy = String.valueOf(voyageId.getNextVoyageId())).length() > 6) {
            obVoy = "O" + obVoy.substring(0, 5);
        }
        CarrierVisit cv = BaseArgoTestCase.getVvFinder().createTestVesselVisit(inFacility, inVesselClassName, vesSrvc, inLineId, inCvId, vesId, vesName, lloydsId, callSign, ibVoy, obVoy, null);
        BaseArgoTestCase.assertNotNull((String)"carrier visit is null ", (Object)cv);
        VisitDetails visitDetails = cv.getCvCvd();
        BaseArgoTestCase.assertNotNull((Object)visitDetails);
        CarrierItinerary itinerary = visitDetails.getCvdItinerary();
        BaseArgoTestCase.assertNotNull((Object)itinerary);
        itinerary.addCarrierCall(RP001, "H", new String[]{RP002, RP003});
        itinerary.addCarrierCall(crntPt, "L", new String[]{RP002, RP003});
        itinerary.addCarrierCall(RP005, "A", new String[]{RP002, RP003});
        itinerary.addCarrierCall("LAX", "B", new String[]{RP002, RP003});
        itinerary.addCarrierCall("LAX", "H", new String[]{RP002, RP003});
        itinerary.addCarrierCall("LYT", "H", new String[]{RP002, RP003});
        itinerary.addCarrierCall("SIN", "H", new String[]{RP002, RP003});
        return cv;
    }

    public static CarrierVisit createTestVesselVisit(String inCvId, String inVesselClassId, String inLineId) {
        String obVoy;
        String lloydsId = "AB" + new LloydsIdProvider().getNextLloydsId();
        String callSign = inCvId + "_R";
        VoyageIdProvider voyageId = new VoyageIdProvider();
        String ibVoy = String.valueOf(voyageId.getNextVoyageId());
        if (ibVoy.length() > 6) {
            ibVoy = "I" + ibVoy.substring(0, 5);
        }
        if ((obVoy = String.valueOf(voyageId.getNextVoyageId())).length() > 6) {
            obVoy = "O" + obVoy.substring(0, 5);
        }
        CarrierVisit cv = BaseArgoTestCase.getVvFinder().createTestVesselVisit(ContextHelper.getThreadFacility(), inVesselClassId, inCvId, inLineId, inCvId, inCvId, inCvId, lloydsId, callSign, ibVoy, obVoy, null);
        LogUtils.forceLogAtInfo((Logger)LOGGER, (Object) String.format("Carrier visit %s created.", cv));
        return cv;
    }

    public static CarrierVisit createTestVesselVisit(String inCvId, String inLineId, Facility inFacility) {
        return BaseArgoTestCase.createTestVesselVisit(inCvId, null, inLineId, inFacility);
    }

    public static IVesselVisitFinder getVvFinder() {
        return (IVesselVisitFinder) Roastery.getBean((String)"vesselVisitFinder");
    }

    protected Document loadDocumentFromClasspath(String inXmlFileName) {
        InputStream inputStream;
        DefaultResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource(TESTDATA_DIR + inXmlFileName);
        try {
            inputStream = resource.getInputStream();
        }
        catch (IOException e) {
            LOGGER.error((Object)("FileUtil.getClassPath().." + FileUtil.getClassPath() + "..." + FileUtil.getBuildFolderPath()));
            throw BizFailure.create((String)("could not open file: " + e));
        }
        try {
//            SAXBuilder builder = new SAXBuilder();
//            Document document = builder.build(inputStream);
//            return document;
        }
        catch (Exception e) {
            throw BizFailure.create((String)("could not build JDOM Tree from file content: " + e));
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException iOException) {}
        }
        return null;
    }

    protected Document buildDocumentFromByteArray(byte[] inSnx) {
        ByteArrayInputStream is = new ByteArrayInputStream(inSnx);
        try {
//            SAXBuilder builder = new SAXBuilder();
//            Document document = builder.build((InputStream)is);
//            return document;
        }
        catch (Exception e) {
            throw BizFailure.create((String)("could not build JDOM Tree from file content: " + e));
        }
        finally {
            try {
                ((InputStream)is).close();
            }
            catch (IOException iOException) {}
        }
        return null;
    }

    public static byte[] loadBinaryFileFromClasspath(String inFileName) {
        DefaultResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource(TESTDATA_DIR + inFileName);
        try {
            int result;
            InputStream inputStream = resource.getInputStream();
            int bytesToRead = inputStream.available();
            byte[] bytes = new byte[bytesToRead];
            for (int bytesRead = 0; bytesRead < bytesToRead && (result = inputStream.read(bytes, bytesRead, bytesToRead - bytesRead)) != -1; bytesRead += result) {
            }
            return bytes;
        }
        catch (IOException e) {
            throw BizFailure.create((String)("could not open file: " + e));
        }
    }

    public String loadFileAsStringFromClasspath(String inFileName) {
        return ArgoUtils.loadClasspathFileAsString(TESTDATA_DIR + inFileName);
    }

    public void importEntityViaSnx(String inEntityName, Element inElement) {
        try {
            this.tryImportingEntityViaSnx(inElement);
        }
        catch (BizViolation bizViolation) {
            this.failOnException("Import failed <" + inEntityName + ">", (Exception)((Object)bizViolation));
        }
    }

    public void importSnxFromFile(String inFilename, UserContext inUc) {
        Document document = null;
        try {
            document = this.loadDocumentFromClasspath(inFilename);
        }
        catch (Exception e) {
            this.failOnException("Could not load Document from File", e);
        }
        this.importSnx(document, inUc);
    }

    public Element getRootElement(String inFilename) {
        Document document = null;
        try {
            document = this.loadDocumentFromClasspath(inFilename);
        }
        catch (Exception e) {
            this.failOnException("Could not load Document from File", e);
        }
        assert (document != null);
        return document.getRootElement();
    }

    public void importSnx(Document inSnx, UserContext inUc) {
        try {
            PersistenceTemplate pt = new PersistenceTemplate(inUc);
            Element rootElement = inSnx.getRootElement();
//            final String userId = rootElement.getAttributeValue("user-id");
//            final String sequenceNumber = rootElement.getAttributeValue("sequence-number");
//            final String eventTime = rootElement.getAttributeValue("event-time");
//            List elementList = rootElement.getChildren();
//            for (final Element inElement : elementList) {
//                ISnxImporter importer;
//                String elementName = inElement.getName();
//                try {
//                    importer = SnxUtil.getSnxImporterForElement(elementName);
//                }
//                catch (Exception e) {
//                    this.failOnException("Could not find Importer", e);
//                    return;
//                }
//                CarinaPersistenceCallback callback = new com.zpmc.ztos.infra.base.common.database.CarinaPersistenceCallback(){
//
//                    protected void doInTransaction() {
//                        try {
//                            importer.setScopeParameters();
//                            ContextHelper.setThreadDataSource(DataSourceEnum.SNX);
//                            if (userId != null) {
//                                ContextHelper.setThreadExternalUser(userId);
//                            }
//                            if (sequenceNumber != null) {
//                                ContextHelper.setThreadExternalSequenceNumber(sequenceNumber);
//                            }
//                            if (eventTime != null) {
//                                ContextHelper.setThreadExternalEventTime(XmlUtil.toDate(eventTime, ContextHelper.getThreadUserTimezone()));
//                            }
//                            importer.parseElement(inElement);
//                        }
//                        catch (Exception e) {
//                            LOGGER.error((Object)("doInTransaction: " + CarinaUtils.getStackTrace((Throwable)e)));
//                            TransactionParms parms = TransactionParms.getBoundParms();
//                            IMessageCollector msgCollector = parms.getMessageCollector();
//                            msgCollector.registerExceptions((Throwable)e);
//                        }
//                    }
//                };
//                IMessageCollector msgs = pt.invoke(callback);
//                BaseArgoTestCase.assertFalse((String)msgs.toString(), (boolean)msgs.hasError());
//            }
        }
        catch (Exception e) {
            this.failOnException("SNX Import failed", e);
        }
    }

    public IMessageCollector importSnxDocument(Document inSnx, UserContext inUc) {
        final IMessageCollector[] mc = new IMessageCollector[1];
        try {
            PersistenceTemplate pt = new PersistenceTemplate(inUc);
            Element rootElement = inSnx.getRootElement();
//            final String userId = rootElement.getAttributeValue("user-id");
//            final String sequenceNumber = rootElement.getAttributeValue("sequence-number");
//            final String eventTime = rootElement.getAttributeValue("event-time");
//            List elementList = rootElement.getChildren();
//            for (final Element inElement : elementList) {
//                ISnxImporter importer;
//                String elementName = inElement.getName();
//                try {
//                    importer = SnxUtil.getSnxImporterForElement(elementName);
//                }
//                catch (Exception e) {
//                    this.failOnException("Could not find Importer", e);
//                    return null;
//                }
//                CarinaPersistenceCallback callback = new CarinaPersistenceCallback(){
//
//                    public void doInTransaction() {
//                        try {
//                            mc[0] = MessageCollectorFactory.createMessageCollector();
//                            importer.setScopeParameters();
//                            ContextHelper.setThreadDataSource(DataSourceEnum.SNX);
//                            if (userId != null) {
//                                ContextHelper.setThreadExternalUser(userId);
//                            }
//                            if (sequenceNumber != null) {
//                                ContextHelper.setThreadExternalSequenceNumber(sequenceNumber);
//                            }
//                            if (eventTime != null) {
//                                ContextHelper.setThreadExternalEventTime(XmlUtil.toDate(eventTime, ContextHelper.getThreadUserTimezone()));
//                            }
//                            importer.parseElement(inElement);
//                        }
//                        catch (Exception inEx) {
//                            LOGGER.error((Object)("doInTransaction: " + CarinaUtils.getStackTrace((Throwable)inEx)));
//                            mc[0].registerExceptions((Throwable)inEx);
//                        }
//                    }
//                };
//                IMessageCollector messageCollector = pt.invoke(callback);
//                if (!messageCollector.hasError()) continue;
//                mc[0].registerExceptions((Throwable)CarinaUtils.convertToBizViolation((IMessageCollector)messageCollector));
//            }
        }
        catch (Exception e) {
            this.failOnException("SNX Import failed", e);
        }
        return mc[0];
    }

    @Nullable
    public IMessageCollector importSnxElement(Document inSnx, UserContext inUc) {
        try {
            ISnxImporter importer;
            PersistenceTemplate pt = new PersistenceTemplate(inUc);
            Element rootElement = inSnx.getRootElement();
//            final String userId = rootElement.getAttributeValue("user-id");
//            final String sequenceNumber = rootElement.getAttributeValue("sequence-number");
//            final String eventTime = rootElement.getAttributeValue("event-time");
//            List elementList = rootElement.getChildren();
//            final Element element = (Element)elementList.iterator().next();
//            String elementName = element.getName();
//            try {
//                importer = SnxUtil.getSnxImporterForElement(elementName);
//            }
//            catch (Exception e) {
//                this.failOnException("Could not find Importer", e);
//                return null;
//            }
//            CarinaPersistenceCallback callback = new CarinaPersistenceCallback(){
//
//                protected void doInTransaction() {
//                    try {
//                        importer.setScopeParameters();
//                        ContextHelper.setThreadDataSource(DataSourceEnum.SNX);
//                        if (userId != null) {
//                            ContextHelper.setThreadExternalUser(userId);
//                        }
//                        if (sequenceNumber != null) {
//                            ContextHelper.setThreadExternalSequenceNumber(sequenceNumber);
//                        }
//                        if (eventTime != null) {
//                            ContextHelper.setThreadExternalEventTime(XmlUtil.toDate(eventTime, ContextHelper.getThreadUserTimezone()));
//                        }
//                        importer.parseElement(element);
//                    }
//                    catch (Exception e) {
//                        LOGGER.error((Object)("doInTransaction: " + CarinaUtils.getStackTrace((Throwable)e)));
//                        TransactionParms parms = TransactionParms.getBoundParms();
//                        IMessageCollector msgCollector = parms.getMessageCollector();
//                        msgCollector.registerExceptions((Throwable)e);
//                    }
//                }
//            };
//            return pt.invoke(callback);
        }
        catch (Exception e) {
            this.failOnException("SNX Import failed", e);
            return null;
        }
        return null;
    }

    public void tryImportingEntityViaSnx(Element inElement) throws BizViolation {
//        Element rootElement = inElement.getParentElement();
//        if (rootElement != null) {
//            String userId = rootElement.getAttributeValue("user-id");
//            String sequenceNumber = rootElement.getAttributeValue("sequence-number");
//            String eventTime = rootElement.getAttributeValue("event-time");
//            if (userId != null) {
//                ContextHelper.setThreadExternalUser(userId);
//            }
//            if (sequenceNumber != null) {
//                ContextHelper.setThreadExternalSequenceNumber(sequenceNumber);
//            }
//            if (eventTime != null) {
//                ContextHelper.setThreadExternalEventTime(XmlUtil.toDate(eventTime, ContextHelper.getThreadUserTimezone()));
//            }
//        }
        ISnxImporter importer = SnxUtil.getSnxImporterForElement(inElement.getName());
        importer.setScopeParameters();
        ContextHelper.setThreadDataSource(DataSourceEnum.SNX);
        importer.parseElement(inElement);
    }

    public void importEntityAtYrd1111(String inXmlFileName, String inEntityName, String inEntityElement) {
        this.importEntityForSession(this.sessionForYard1111(), inXmlFileName, inEntityName, inEntityElement);
    }

    public void importEntityAtFcy111(String inXmlFileName, String inEntityName, String inEntityElement) {
        this.importEntityForSession(this.sessionForFacility111(), inXmlFileName, inEntityName, inEntityElement);
    }

    private void importEntityForSession(PersistenceTemplate inSession, String inXmlFileName, String inEntityName, String inEntityElement) {
        IMessageCollector msgs = inSession.invoke(this.importEntityFromXml(inXmlFileName, inEntityName, inEntityElement));
        BaseArgoTestCase.assertFalse((String)msgs.toString(), (boolean)msgs.hasError());
    }

    private CarinaPersistenceCallback importEntityFromXml(final String inXmlFileName, final String inEntityName, final String inEntityElement) {
        return new CarinaPersistenceCallback(){

            public void doInTransaction() {
                Document d = BaseArgoTestCase.this.loadDocumentFromClasspath(inXmlFileName);
//                BaseArgoTestCase.this.importEntityViaSnx(inEntityName, d.getRootElement().getChild(inEntityElement));
            }
        };
    }

    public Element exportEntityViaSnx(String inEntityName, Serializable[] inPrimaryKeys) {
        IEntityXmlExporter exporter = SnxUtil.getSnxExporterForEntity(inEntityName);
        exporter.setPrimaryKeys(inPrimaryKeys);
        return exporter.getElementIterator().next();
    }

    public void assertSnxMissingAttribute(String inEntityName, Element inEntityElement, String inAttributeName) {
        this.assertSnxException(inEntityElement, inAttributeName, IArgoPropertyKeys.MISSING_ATTRIBUTE);
    }

    public void assertSnxRequiredViolation(String inEntityName, Element inEntityElement, String inAttributeName) {
        this.assertSnxException(inEntityElement, inAttributeName, IArgoPropertyKeys.VALIDATION_REQUIRED_FIELD);
    }

    public void assertSnxValueViolation(String inEntityName, Element inEntityElement, String inAttributeName) {
        this.assertSnxException(inEntityElement, inAttributeName, IArgoPropertyKeys.VALIDATION_INVALID_VALUE_FOR_FIELD);
    }

    private void assertSnxException(Element inEntityElement, String inAttributeName, IPropertyKey inExceptionKey) {
        try {
            this.tryImportingEntityViaSnx(inEntityElement);
        }
        catch (BizViolation e) {
            this.assertException((Exception)((Object)e), inExceptionKey);
            BaseArgoTestCase.assertEquals((Object)inAttributeName, (Object)e.getParms()[0]);
        }
    }

    public BaseArgoTestCase(String inS) {
        super(inS);
    }
    public BaseArgoTestCase() {
        super(SNX_ROOT_START);
    }

    public UserContext getTestUserContextAtYrd1111() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        return contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.YARD, this._yrd1111Gkey, Roastery.getBeanFactory());
    }

    public UserContext getTestUserContextAtYrd1112() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        return contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.YARD, this._yrd1112Gkey, Roastery.getBeanFactory());
    }

    public UserContext getTestUserContextAtFcy11No() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        return contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.FACILITY, this._fcy11NonOpGkey, Roastery.getBeanFactory());
    }

    public void doCrudDelegateTest(String inEntity, FieldChanges inCreateSpec, FieldChanges inUpdateSpec) {
//        BizResponse response = this._crudDelegate.requestCreate(this.getTestUserContextAtYrd1111(), inEntity, null, inCreateSpec);
//        Serializable createdKey = response.getCreatedPrimaryKey();
//        LOGGER.info((Object)("doCrudDelegateTest: createdKey = " + createdKey));
//        this.assertTrueResponseSuccess((IMessageCollector)response);
//        response = this._crudDelegate.requestUpdate(this.getTestUserContextAtYrd1111(), inEntity, createdKey, inUpdateSpec);
//        this.assertTrueResponseSuccess((IMessageCollector)response);
//        response = this._crudDelegate.requestDelete(this.getTestUserContextAtYrd1111(), inEntity, createdKey);
//        this.assertTrueResponseSuccess((IMessageCollector)response);
    }

    public UserContext getTestUserContextAtYrd1211() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        UserContext userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.YARD, this._yrd1211Gkey, Roastery.getBeanFactory());
        return userContext;
    }

    public UserContext getTestUserContextAtYrd1121() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        UserContext userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.YARD, this._yrd1121Gkey, Roastery.getBeanFactory());
        return userContext;
    }

    public UserContext getTestUserContextAtYrd1131() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        UserContext userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.YARD, this._yrd1131Gkey, Roastery.getBeanFactory());
        return userContext;
    }

    public UserContext getTestUserContextAtYrd1141() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        UserContext userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.YARD, this._yrd1141Gkey, Roastery.getBeanFactory());
        return userContext;
    }

    public UserContext getTestUserContextAtFcy111() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        UserContext userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.FACILITY, this._fcy111Gkey, Roastery.getBeanFactory());
        return userContext;
    }

    public UserContext getTestUserContextAtFcy112() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        UserContext userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.FACILITY, this._fcy112Gkey, Roastery.getBeanFactory());
        return userContext;
    }

    public UserContext getTestUserContextAtFcy113() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        UserContext userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.FACILITY, this._fcy113Gkey, Roastery.getBeanFactory());
        return userContext;
    }

    public UserContext getTestUserContextAtFcy115() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        UserContext userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.FACILITY, this._fcy115Gkey, Roastery.getBeanFactory());
        return userContext;
    }

    public UserContext getTestUserContextAtFcy121() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        UserContext userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.FACILITY, this._fcy121Gkey, Roastery.getBeanFactory());
        return userContext;
    }

    public UserContext getTestUserContextAtFcy211() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        UserContext userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.FACILITY, this._fcy211Gkey, Roastery.getBeanFactory());
        return userContext;
    }

    public Date getValidDateForPurging(LongConfig inExpireParm) {
        UserContext uc = this.getTestUserContextAtCpx11();
        return new Date(new Date().getTime() - 86400000L * (inExpireParm.getValue(uc) + 2L));
    }

    public UserContext getTestUserContextAtCpx11() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        UserContext userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.COMPLEX, this._cpx11Gkey, Roastery.getBeanFactory());
        return userContext;
    }

    public UserContext getTestUserContextAtCpx12() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        UserContext userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.COMPLEX, this._cpx12Gkey, Roastery.getBeanFactory());
        return userContext;
    }

    public UserContext getTestUserContextAtCpx21() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        UserContext userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.COMPLEX, this._cpx21Gkey, Roastery.getBeanFactory());
        return userContext;
    }

    public UserContext getTestUserContextAtYrd2111() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        UserContext userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.YARD, this._yrd2111Gkey, Roastery.getBeanFactory());
        return userContext;
    }

    public UserContext getTestUserContextAtYrd2112() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        UserContext userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.YARD, this._yrd2112Gkey, Roastery.getBeanFactory());
        return userContext;
    }

    public UserContext getTestUserContextAtOpr1() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        UserContext userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.OPERATOR, this._opr1Gkey, Roastery.getBeanFactory());
        return userContext;
    }

    public UserContext getTestUserContextAtOpr2() {
        this.initializeTestScopes();
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
        UserContext userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.OPERATOR, this._opr2Gkey, Roastery.getBeanFactory());
        return userContext;
    }

    public void startHibernateAtYard1111() {
        UserContext uc = this.getTestUserContextAtYrd1111();
        this.startHibernateWithUser(uc);
    }

    public void startHibernateAtYard1121() {
        UserContext uc = this.getTestUserContextAtYrd1121();
        this.startHibernateWithUser(uc);
    }

    public void startHibernateAtYard1131() {
        UserContext uc = this.getTestUserContextAtYrd1131();
        this.startHibernateWithUser(uc);
    }

    public void startHibernateAtYard1141() {
        UserContext uc = this.getTestUserContextAtYrd1141();
        this.startHibernateWithUser(uc);
    }

    public void startHibernateAtYard1211() {
        UserContext uc = this.getTestUserContextAtYrd1211();
        this.startHibernateWithUser(uc);
    }

    public void startHibernateAtYard2111() {
        UserContext uc = this.getTestUserContextAtYrd2111();
        this.startHibernateWithUser(uc);
    }

    public void startHibernateAtFcy111() {
        UserContext uc = this.getTestUserContextAtFcy111();
        this.startHibernateWithUser(uc);
    }

    public void startHibernateAtFcy112() {
        UserContext uc = this.getTestUserContextAtFcy112();
        this.startHibernateWithUser(uc);
    }

    public void startHibernateAtFcy113() {
        UserContext uc = this.getTestUserContextAtFcy113();
        this.startHibernateWithUser(uc);
    }

    public void startHibernateAtFcy115() {
        UserContext uc = this.getTestUserContextAtFcy115();
        this.startHibernateWithUser(uc);
    }

    public void startHibernateAtCpx11() {
        UserContext uc = this.getTestUserContextAtCpx11();
        this.startHibernateWithUser(uc);
    }

    public void startHibernateAtCpx21() {
        UserContext uc = this.getTestUserContextAtCpx21();
        this.startHibernateWithUser(uc);
    }

    public void startHibernateAtFcy11No() {
        UserContext uc = this.getTestUserContextAtFcy11No();
        this.startHibernateWithUser(uc);
    }

    public void startHibernateWithUser(UserContext inUserContext) {
        super.startHibernateWithUser(inUserContext);
        this.startTransaction();
    }

    public void endHibernate() {
        if (this._transaction != null) {
            this.endTransactionAndSession();
        }
        super.endHibernate();
    }

    public Operator getOpr1() {
        return (Operator)this._hibernateApi.load(Operator.class, this._opr1Gkey);
    }

    public Operator getOpr2() {
        return (Operator)this._hibernateApi.load(Operator.class, this._opr2Gkey);
    }

    public Complex getCpx11() {
        return (Complex)this._hibernateApi.load(Complex.class, this._cpx11Gkey);
    }

    public Complex getCpx12() {
        return (Complex)this._hibernateApi.load(Complex.class, this._cpx12Gkey);
    }

    public Complex getCpx21() {
        return (Complex)this._hibernateApi.load(Complex.class, this._cpx21Gkey);
    }

    public Facility getFcy111() {
        return (Facility)this._hibernateApi.load(Facility.class, this._fcy111Gkey);
    }

    public Facility getFcy11NonOp() {
        return (Facility)this._hibernateApi.load(Facility.class, this._fcy11NonOpGkey);
    }

    public Facility getFcy112() {
        return (Facility)this._hibernateApi.load(Facility.class, this._fcy112Gkey);
    }

    public Facility getFcy113() {
        return (Facility)this._hibernateApi.load(Facility.class, this._fcy113Gkey);
    }

    public Facility getFcy115() {
        return (Facility)this._hibernateApi.load(Facility.class, this._fcy115Gkey);
    }

    public Facility getFcy121() {
        return (Facility)this._hibernateApi.load(Facility.class, this._fcy121Gkey);
    }

    public Facility getFcy211() {
        return (Facility)this._hibernateApi.load(Facility.class, this._fcy211Gkey);
    }

    public Yard getYrd1111() {
        return (Yard)this._hibernateApi.load(Yard.class, this._yrd1111Gkey);
    }

    public Yard getYrd1112() {
        return (Yard)this._hibernateApi.load(Yard.class, this._yrd1112Gkey);
    }

    public Yard getYrd1211() {
        return (Yard)this._hibernateApi.load(Yard.class, this._yrd1211Gkey);
    }

    public Yard getYrd1121() {
        return (Yard)this._hibernateApi.load(Yard.class, this._yrd1121Gkey);
    }

    public Yard getYrd1131() {
        return (Yard)this._hibernateApi.load(Yard.class, this._yrd1131Gkey);
    }

    public Yard getYrd1141() {
        return (Yard)this._hibernateApi.load(Yard.class, this._yrd1141Gkey);
    }

    public Yard getYrd2111() {
        return (Yard)this._hibernateApi.load(Yard.class, this._yrd2111Gkey);
    }

    public void initializeTestScopes() {
        if (this._yrd1111Gkey == null) {
            this.startHibernateWithGlobalUser();
            HibernateApi.getInstance().setFlushMode(FlushMode.COMMIT);
            Operator opr1 = Operator.findOrCreateOperator(OPR1, OPR1);
            Operator opr2 = Operator.findOrCreateOperator(OPR2, OPR2);
            Complex cpx11 = Complex.findOrCreateComplex(CPX11, CPX11, opr1, "CST6CDT");
            Complex cpx12 = Complex.findOrCreateComplex(CPX12, CPX12, opr1, "CST6CDT");
            Complex cpx21 = Complex.findOrCreateComplex(CPX21, CPX21, opr2, "CST6CDT");
            this._opr1Gkey = opr1.getOprGkey();
            this._opr2Gkey = opr2.getOprGkey();
            this._cpx11Gkey = cpx11.getCpxGkey();
            this._cpx12Gkey = cpx12.getCpxGkey();
            this._cpx21Gkey = cpx21.getCpxGkey();
            this.endHibernate();
            IEntitySetManager esm = (IEntitySetManager) Roastery.getBean((String)"entitySetManager");
            ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
            UserContext userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.COMPLEX, this._cpx11Gkey, Roastery.getBeanFactory());
            this.startHibernateWithUser(userContext);
            esm.ensureEntitySetsForOperator(ContextHelper.getThreadOperator(), true);
            HibernateApi.getInstance().setFlushMode(FlushMode.COMMIT);
            Facility fcy111 = Facility.findOrCreateFacility(FCY111, FCY111, cpx11, this.getRoutingPoint1());
            Facility fcy11NonOp = Facility.findOrCreateFacility(FCY11NO, FCY11NO, cpx11, this.getRoutingPoint6());
            fcy11NonOp.setFieldValue(IArgoField.FCY_IS_NON_OPERATIONAL, Boolean.TRUE);
            Facility fcy112 = Facility.findOrCreateFacility(FCY112, FCY112, cpx11, this.getRoutingPoint2());
            Facility fcy113 = Facility.findOrCreateFacility(FCY113, FCY113, cpx11, this.getRoutingPoint1());
            Facility fcy114 = Facility.findOrCreateFacility(FCY114, FCY114, cpx11, this.getRoutingPoint4());
            Facility fcy115 = Facility.findOrCreateFacility(FCY115, FCY115, cpx11, this.getRoutingPoint7());
            Yard yrd1111 = Yard.findOrCreateYard(YRD1111, YRD1111, fcy111);
            Set yardSet = fcy111.getFcyYrdSet();
            if (yardSet != null) {
                for (Object ob : yardSet) {
                    Yard yard = (Yard)ob;
                    if (yard == null || YRD1111.equals(yard.getYrdId())) continue;
                    this._hibernateApi.delete((Object)yard, false);
                }
            }
            Yard yrd1112 = Yard.findOrCreateYard(YRD1112, YRD1112, fcy114);
            Yard yrd1121 = Yard.findOrCreateYard(YRD1121, YRD1121, fcy112);
            Yard yrd1131 = Yard.findOrCreateYard(YRD1131, YRD1131, fcy113);
            Yard yrd1141 = Yard.findOrCreateYard(YRD1141, YRD1141, fcy115);
            this._fcy111Gkey = fcy111.getFcyGkey();
            this._fcy11NonOpGkey = fcy11NonOp.getFcyGkey();
            this._fcy112Gkey = fcy112.getFcyGkey();
            this._fcy113Gkey = fcy113.getFcyGkey();
            this._fcy115Gkey = fcy115.getFcyGkey();
            this._yrd1111Gkey = yrd1111.getYrdGkey();
            this._yrd1112Gkey = yrd1112.getYrdGkey();
            this._yrd1121Gkey = yrd1121.getYrdGkey();
            this._yrd1131Gkey = yrd1131.getYrdGkey();
            this._yrd1141Gkey = yrd1141.getYrdGkey();
            this._yrd1112Gkey = yrd1112.getYrdGkey();
            this.loadYardFile(yrd1111, TEST_YARD_FILE);
            this.loadYardFile(yrd1121, TEST_MAH_YARD_FILE);
            this.loadYardFile(yrd1131, TEST_AGW_YARD_FILE);
            this.loadYardFile(yrd1141, TEST_MAT_YARD_FILE);
            this.loadSparcsSettingsFile(yrd1131, TEST_AGW_SPARCS_SETTINGS_FILE);
            this.loadBerthsFile(yrd1111, TEST_LPC_BERTHS_FILE);
            FacilityRelay.findOrCreateFacilityRelay(fcy112, fcy111, LocTypeEnum.VESSEL, 36L, 8L);
            FacilityRelay.findOrCreateFacilityRelay(fcy111, fcy112, LocTypeEnum.VESSEL, 48L, 0L);
            FacilityRelay.findOrCreateFacilityRelay(fcy111, fcy113, LocTypeEnum.VESSEL, 48L, 0L);
            FacilityRelay.findOrCreateFacilityRelay(fcy115, fcy111, LocTypeEnum.VESSEL, 48L, 0L);
            EquipType.createReferenceDataForTests();
            AbstractBin model = yrd1111.getYrdBinModel();
            Long yrd1111modelGkey = model != null ? model.getAbnGkey() : null;
            model = yrd1121.getYrdBinModel();
            Long yrd1121modelGkey = model != null ? model.getAbnGkey() : null;
            model = yrd1131.getYrdBinModel();
            Long yrd1131modelGkey = model != null ? model.getAbnGkey() : null;
            model = yrd1141.getYrdBinModel();
            Long yrd1141modelGkey = model != null ? model.getAbnGkey() : null;
            this.endHibernate();
            userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.COMPLEX, this._cpx12Gkey, Roastery.getBeanFactory());
            this.startHibernateWithUser(userContext);
            HibernateApi.getInstance().setFlushMode(FlushMode.COMMIT);
            Facility fcy121 = Facility.findOrCreateFacility(FCY121, FCY121, cpx12, this.getRoutingPoint2());
            Yard yrd1211 = Yard.findOrCreateYard(YRD1211, YRD1211, fcy121);
            this._fcy121Gkey = fcy121.getFcyGkey();
            this._yrd1211Gkey = yrd1211.getYrdGkey();
            EquipType.createReferenceDataForTests();
            this.endHibernate();
            userContext = contextProvider.getSystemUserContextForScope((IScopeEnum) ScopeEnum.COMPLEX, this._cpx21Gkey, Roastery.getBeanFactory());
            this.startHibernateWithUser(userContext);
            esm.ensureEntitySetsForOperator(ContextHelper.getThreadOperator(), true);
            HibernateApi.getInstance().setFlushMode(FlushMode.COMMIT);
            Facility fcy211 = Facility.findOrCreateFacility(FCY211, FCY211, cpx21, this.getRoutingPoint3());
            Yard yrd2111 = Yard.findOrCreateYard(YRD2111, YRD2111, fcy211);
            Yard yrd2112 = Yard.findOrCreateYard(YRD2112, YRD2112, fcy211);
            this._fcy211Gkey = fcy211.getFcyGkey();
            this._yrd2111Gkey = yrd2111.getYrdGkey();
            this._yrd2112Gkey = yrd2112.getYrdGkey();
            EquipType.createReferenceDataForTests();
            this.endHibernate();
            esm.initializeUpperLevelModules();
            if (Boolean.getBoolean(USE_EMPTY_YARD_BIN_MODEL)) {
                this.setUpEmptyBinModel(this._yrd1111Gkey, yrd1111modelGkey);
                this.setUpEmptyBinModel(this._yrd1121Gkey, yrd1111modelGkey);
                this.setUpEmptyBinModel(this._yrd1131Gkey, yrd1111modelGkey);
                this.setUpEmptyBinModel(this._yrd1141Gkey, yrd1111modelGkey);
            } else {
                this.setUpPersistentBinModel(this._yrd1111Gkey, yrd1111modelGkey);
                this.setUpPersistentBinModel(this._yrd1121Gkey, yrd1121modelGkey);
                this.setUpPersistentBinModel(this._yrd1131Gkey, yrd1131modelGkey);
                this.setUpPersistentBinModel(this._yrd1141Gkey, yrd1141modelGkey);
            }
        }
    }

    private void setUpEmptyBinModel(Serializable inYardGkey, Serializable inYardModelGkey) {
        UserContext userContext = ContextHelper.getSystemUserContextForScope((IScopeEnum) ScopeEnum.YARD, inYardGkey);
        if (inYardModelGkey == null) {
            IArgoYardUtils yardUtils = (IArgoYardUtils) Roastery.getBean((String)"argoYardUtils");
            CodeTimer ct = new CodeTimer(LOGGER, Level.WARN);
            try {
                Serializable yardModelGkey = yardUtils.findOrCreateYardModel(userContext, inYardGkey);
                yardUtils.updateYardModel(userContext, inYardGkey, yardModelGkey);
            }
            catch (BizViolation yardModelGkey) {
                // empty catch block
            }
            long ctMs = ct.getTotalMillis();
            ct.report("setUpEmptyBinModel: findOrCreateYardModel executed in " + ctMs / 1000L + " seconds. " + inYardModelGkey);
        }
    }

    private void setUpPersistentBinModel(Serializable inYardGkey, Serializable inYardModelGkey) {
        UserContext userContext = ContextHelper.getSystemUserContextForScope((IScopeEnum) ScopeEnum.YARD, inYardGkey);
        if (inYardModelGkey == null) {
            IArgoYardUtils yardUtils = (IArgoYardUtils) Roastery.getBean((String)"argoYardUtils");
            CodeTimer ct = new CodeTimer(LOGGER, Level.WARN);
            try {
                yardUtils.updateOrCreateYardModelFromXpsFiles(userContext, inYardGkey);
            }
            catch (BizViolation bizViolation) {
                // empty catch block
            }
            long ctMs = ct.getTotalMillis();
            ct.report("setUpPersistentBinModel: updateOrCreateYardModelFromXpsFiles executed in " + ctMs / 1000L + " seconds. " + inYardModelGkey);
        }
    }

    protected void loadYardFile(Yard inYard, String inFilename) {
        byte[] compiledYardFile = ArgoUtils.loadBinaryFileFromDisk(inFilename);
        inYard.setFieldValue(IArgoField.YRD_COMPILED_YARD, compiledYardFile);
    }

    private void loadSparcsSettingsFile(Yard inYard, String inSparcsSettingsPath) {
        String sparcsSettingsFile = ArgoUtils.loadFileAsString(inSparcsSettingsPath);
        inYard.setFieldValue(IArgoField.YRD_SPARCS_SETTINGS, sparcsSettingsFile);
    }

    protected void loadBerthsFile(Yard inYard, String inFilename) {
        byte[] berthsTextFile = ArgoUtils.loadBinaryFileFromDisk(inFilename);
        inYard.setFieldValue(IArgoField.YRD_BERTH_TEXT_FILE, berthsTextFile);
    }

    public void createAndAssignAdminRole() {
        this.startHibernateWithGlobalUser();
        ArgoUser adminUser = ArgoUser.findOrCreateArgoUser("admin", "", "admin", "User");
        ArgoSecRole adminRole = ArgoSecRole.findOrCreateSecRole("Admin");
 //       this.addPrivilegeToRole(adminRole, FrameworkUlcPrivs.ACCESS_ALL_NAVIGATION_NODES);
        this.assignRoleToUser(adminUser, adminRole);
        this.endHibernate();
    }

    private void assignRoleToUser(BaseUser inUser, SecRole inRole) {
        Set roleUsers = inUser.getBuserRoleList();
        if (roleUsers == null) {
            inUser.setBuserRoleList(new HashSet());
            roleUsers = inUser.getBuserRoleList();
        }
        roleUsers.add(inRole);
  //      this._persister.saveOrUpdateEntity((IEntity)inUser);
    }

    private void addPrivilegeToRole(SecRole inRole, IPrivilege inPriv) {
        String[] privs = inRole.getRolePrivileges();
        boolean privExists = false;
        for (String privId : privs) {
//            if (!privId.equals(inPriv.getAttribute())) continue;
            privExists = true;
        }
        if (!privExists) {
//            SecRolePrivilegeMapping privilege = new SecRolePrivilegeMapping();
//            privilege.setRlprivmPrivId(inPriv.getAttribute());
//            privilege.setRlprivmRoleGkey(inRole);
//            this._persister.saveOrUpdateEntity((IEntity)privilege);
        }
//        this._persister.saveOrUpdateEntity((IEntity)inRole);
    }

    @Nullable
    private Equipment findOrCreateContainer(String inCtrId, String inIso) {
        Equipment ctr = Equipment.findEquipment(inCtrId);
        if (ctr == null) {
            try {
                ctr = Container.findOrCreateContainer(inCtrId, inIso, DataSourceEnum.TESTING);
            }
            catch (BizViolation inBizViolation) {
                LOGGER.error((Object)("findOrCreateContainer: failed with " + (Object)((Object)inBizViolation)));
            }
        }
        return ctr;
    }

    public RoutingPoint getRoutingPoint1() {
        return RoutingPoint.findOrCreateRoutingPoint(ROUTING_POINT_1_ID, ROUTING_POINT_1_UN);
    }

    public RoutingPoint getRoutingPoint2() {
        return RoutingPoint.findOrCreateRoutingPoint("LYT", ROUTING_POINT_2_UN);
    }

    public RoutingPoint getRoutingPoint3() {
        return RoutingPoint.findOrCreateRoutingPoint("LAX", ROUTING_POINT_3_UN);
    }

    public RoutingPoint getRoutingPoint4() {
        return RoutingPoint.findOrCreateRoutingPoint("KHH", ROUTING_POINT_4_UN);
    }

    public RoutingPoint getRoutingPoint5() {
        return RoutingPoint.findOrCreateRoutingPoint("SIN", ROUTING_POINT_5_UN);
    }

    public RoutingPoint getRoutingPoint6() {
        return RoutingPoint.findOrCreateRoutingPoint(ROUTING_POINT_6_ID, ROUTING_POINT_6_UN);
    }

    public RoutingPoint getRoutingPoint7() {
        return RoutingPoint.findOrCreateRoutingPoint(ROUTING_POINT_7_ID, ROUTING_POINT_7_UN);
    }

    protected RoutingPoint[] getFakeRoutingPointArray() {
        RoutingPoint[] rpa = new RoutingPoint[30];
        for (int i = 0; i < rpa.length; ++i) {
            String rpId = i < 10 ? "00" + i : "0" + i;
            rpa[i] = RoutingPoint.findOrCreateRoutingPoint(rpId, "00" + rpId);
        }
        return rpa;
    }

    @Nullable
    protected Equipment getCtr1() {
        return this.findOrCreateContainer(CNTR_ID1, EQ_TYPE_ID_4200);
    }

    @Nullable
    protected Equipment getCtr2() {
        return this.findOrCreateContainer(CNTR_ID2, EQ_TYPE_ID_4200);
    }

    @Nullable
    protected Equipment getCtr3() {
        return this.findOrCreateContainer(CNTR_ID3, EQ_TYPE_ID_4200);
    }

    @Nullable
    protected Equipment getCtr4() {
        return this.findOrCreateContainer(CNTR_ID4, EQ_TYPE_ID_4200);
    }

    @Nullable
    protected Equipment getCtr5() {
        return this.findOrCreateContainer(CNTR_ID5, EQ_TYPE_ID_4200);
    }

    protected CarrierService getVslService1() {
        CarrierService service = CarrierService.findCarrierService(VSL_SERVICE1);
        if (service == null) {
            CarrierItinerary itinerary = CarrierItinerary.findCarrierItinerary(CARRIER_INTINERARY1);
            if (itinerary != null) {
                ArgoUtils.carefulDelete(itinerary);
            }
            itinerary = this.getCarrierItinerary1();
            service = CarrierService.createCarrierService(VSL_SERVICE1, "Pacifc South Express", LocTypeEnum.VESSEL, itinerary);
        }
        return service;
    }

    public CarrierItinerary getCarrierItinerary1() {
        CarrierItinerary itinerary = CarrierItinerary.findCarrierItinerary(CARRIER_INTINERARY1);
        if (itinerary == null) {
            RoutingPoint[] rpa = this.getFakeRoutingPointArray();
            itinerary = CarrierItinerary.createCarrierItinerary(CARRIER_INTINERARY1, null);
            itinerary.addCarrierCall(rpa[0].getPointId(), "1", "0", new String[]{rpa[8].getPointId(), rpa[9].getPointId()});
            itinerary.addCarrierCall(rpa[1].getPointId(), "1", "1", new String[]{rpa[10].getPointId(), rpa[11].getPointId()});
            itinerary.addCarrierCall(rpa[2].getPointId(), "1", "2", new String[]{rpa[12].getPointId(), rpa[13].getPointId(), rpa[14].getPointId()});
            itinerary.addCarrierCall(rpa[3].getPointId(), "1", "3", new String[]{rpa[15].getPointId()});
            itinerary.addCarrierCall(rpa[4].getPointId(), "1", "4", new String[]{rpa[16].getPointId()});
            itinerary.addCarrierCall(rpa[2].getPointId(), "2", "2", new String[]{rpa[17].getPointId(), rpa[18].getPointId()});
            itinerary.addCarrierCall(rpa[3].getPointId(), "2", "3", new String[]{rpa[19].getPointId(), rpa[20].getPointId()});
            this._hibernateApi.saveOrUpdate((Object)itinerary);
        }
        return itinerary;
    }

    public void purgeCarrierVisit(String inCvId) {
        ArgoTestUtil.purgeCarrierVisit(ContextHelper.getThreadComplex().getPrimaryKey(), inCvId);
    }

    @Deprecated
    public void purgeAllVisits(Complex inComplex, String inCvId) {
        ArgoTestUtil.purgeCarrierVisit(inComplex.getPrimaryKey(), inCvId);
    }

    protected Organization getOrg1() {
        return Organization.findOrCreateOrganization((String)"ORG1", (String)"Test Organization #1", (String)"TENANT");
    }

    protected Organization getOrg2() {
        return Organization.findOrCreateOrganization((String)"ORG2", (String)"Test Organization #2", (String)"'Navis'");
    }

    @Nullable
    public ScopedBizUnit getTestLineOp1() {
        return this.findOrCreateTestScopedBizUnit(LINE_OP_1_ID, BizRoleEnum.LINEOP);
    }

    @Nullable
    protected ScopedBizUnit getTestLineOp2() {
        return this.findOrCreateTestScopedBizUnit(LINE_OP_2_ID, BizRoleEnum.LINEOP);
    }

    @Nullable
    protected ScopedBizUnit findOrCreateTestScopedBizUnit(String inId, BizRoleEnum inRole) {
        HibernateApi.getInstance().flush();
        ScopedBizUnit scopedBizUnit = ScopedBizUnit.findScopedBizUnit(inId, inRole);
        if (scopedBizUnit == null) {
            scopedBizUnit = this.getBizUnitManager().findOrCreateScopedBizUnit(inId, inRole);
        }
        return scopedBizUnit;
    }

    protected void startHibernateWithGlobalUser() {
        UserContext globalUserContext = this.getTestUserContextGlobal();
        this.startHibernateWithUser(globalUserContext);
    }

    public UserContext getTestUserContextGlobal() {
        ScopeCoordinates sc = new ScopeCoordinates((Serializable[])new Long[0]);
        UserContext globalUserContext = UserContextUtils.createUserContext((Object)0L, (String)"global", (ScopeCoordinates)sc);
        return globalUserContext;
    }

    protected void deleteByGkey(Class<?> inClass, Serializable inGkey) {
        if (inGkey == null) {
            return;
        }
        Object object = HibernateApi.getInstance().load(inClass, inGkey);
        ArgoUtils.carefulDelete(object);
    }

    protected void createEquipmentTypesRoutingPointsAndBizUnits() {
        HibernateApi.getInstance().setFlushMode(FlushMode.COMMIT);
        this.getFakeRoutingPointArray();
        this.getRoutingPoint1();
        this.getRoutingPoint2();
        this.getRoutingPoint3();
        this.getRoutingPoint4();
        this.getRoutingPoint5();
        this.getRoutingPoint6();
        this.getRoutingPoint7();
        EquipType.findOrCreateEquipType(EQ_TYPE_ID_4200);
        EquipType bombCartEquipType = EquipType.findOrCreateEquipType(BOMBCART_EQUIP_TYPE);
        bombCartEquipType.updateChsSlotLabel("1,2,3,4,5");
        String acryTypeId = "A01";
        EquipType acryTyp = EquipType.findEquipType(acryTypeId);
        if (acryTyp == null) {
            EquipType.createEquipType(acryTypeId, EquipClassEnum.ACCESSORY);
        }
    }

    protected void arriveCarrierToFacility(CarrierVisit inCarrierVisit, Date inTime) {
        if (CarrierVisitPhaseEnum.ARRIVED.equals((Object)inCarrierVisit.getCvVisitPhase())) {
            LOGGER.error((Object)("arriveCarrierToFacility: redundant call for: " + inCarrierVisit));
            return;
        }
        inCarrierVisit.skipToPhase(CarrierVisitPhaseEnum.INBOUND);
        inCarrierVisit.arriveToFacility(inTime);
    }

    protected void departCarrierFromFacility(CarrierVisit inCarrierVisit, Date inTime) {
        if (CarrierVisitPhaseEnum.DEPARTED.equals((Object)inCarrierVisit.getCvVisitPhase())) {
            LOGGER.error((Object)("departCarrierFromFacility: redundant call for: " + inCarrierVisit));
            return;
        }
        inCarrierVisit.skipToPhase(CarrierVisitPhaseEnum.COMPLETE);
        inCarrierVisit.departFromFacility(inTime);
    }

    public Container findOrCreateTestContainer(String inCtrNbr, String inEqTypeId) throws BizViolation {
        return Container.findOrCreateContainer(inCtrNbr, inEqTypeId, DataSourceEnum.TESTING);
    }

    public Container findOrCreateTestContainer(String inCtrNbr, String inEqTypeId, Long inEquipmentOperator) throws BizViolation {
        Container ctr = Container.findOrCreateContainer(inCtrNbr, inEqTypeId, DataSourceEnum.TESTING);
        ctr.setFieldValue(IArgoBizMetafield.EQUIPMENT_OPERATOR, inEquipmentOperator);
        HibernateApi.getInstance().saveOrUpdate((Object)ctr);
        return ctr;
    }

    public Chassis findOrCreateTestChassis(String inChsNbr, String inEqTypeId) throws BizViolation {
        return Chassis.findOrCreateChassis(inChsNbr, inEqTypeId, DataSourceEnum.TESTING);
    }

    public Chassis findOrCreateTestChassis(String inChsNbr, String inEqTypeId, Long inEquipmentOperator) throws BizViolation {
        Chassis chs = Chassis.findOrCreateChassis(inChsNbr, inEqTypeId, DataSourceEnum.TESTING);
        chs.setFieldValue(IArgoBizMetafield.EQUIPMENT_OPERATOR, inEquipmentOperator);
        HibernateApi.getInstance().saveOrUpdate((Object)chs);
        return chs;
    }

    @Nullable
    public Container findOrCreateTestCtr(String inCtrNbr, String inEqTypeId) {
        Container ctr = null;
        try {
            ctr = Container.findOrCreateContainer(inCtrNbr, inEqTypeId, DataSourceEnum.TESTING);
        }
        catch (BizViolation bv) {
            this.failOnException("", (Exception)((Object)bv));
        }
        return ctr;
    }

    @Nullable
    public Chassis findOrCreateTestChs(String inChsNbr) {
        Chassis chs = null;
        if (inChsNbr != null) {
            try {
                chs = Chassis.findOrCreateChassis(inChsNbr, "CH40", DataSourceEnum.TESTING);
            }
            catch (BizViolation bv) {
                this.failOnException("", (Exception)((Object)bv));
            }
        }
        return chs;
    }

    @Nullable
    public Accessory findOrCreateTestAcry(String inAccNbr) {
        Accessory acry = null;
        try {
            acry = Accessory.findOrCreateAccessory(inAccNbr, "A01", DataSourceEnum.TESTING);
        }
        catch (BizViolation bv) {
            this.failOnException("", (Exception)((Object)bv));
        }
        return acry;
    }

    protected PersistenceTemplate sessionForFacility11No() {
        return new PersistenceTemplate(this.getTestUserContextAtFcy11No());
    }

    public PersistenceTemplate sessionForYard1111() {
        return new PersistenceTemplate(this.getTestUserContextAtYrd1111());
    }

    public PersistenceTemplate sessionForYard1211() {
        return new PersistenceTemplate(this.getTestUserContextAtYrd1211());
    }

    protected PersistenceTemplate sessionForYard1112() {
        return new PersistenceTemplate(this.getTestUserContextAtYrd1112());
    }

    protected PersistenceTemplate sessionForFacility111() {
        return new PersistenceTemplate(this.getTestUserContextAtFcy111());
    }

    protected PersistenceTemplate sessionForCpx11() {
        return new PersistenceTemplate(this.getTestUserContextAtCpx11());
    }

    protected PersistenceTemplate sessionForCpx12() {
        return new PersistenceTemplate(this.getTestUserContextAtCpx12());
    }

    protected PersistenceTemplate sessionForFacility112() {
        return new PersistenceTemplate(this.getTestUserContextAtFcy112());
    }

    public PersistenceTemplate sessionForYard1121() {
        return new PersistenceTemplate(this.getTestUserContextAtYrd1121());
    }

    public PersistenceTemplate sessionForYard1131() {
        return new PersistenceTemplate(this.getTestUserContextAtYrd1131());
    }

    protected PersistenceTemplate sessionForYard1141() {
        return new PersistenceTemplate(this.getTestUserContextAtYrd1141());
    }

    protected PersistenceTemplate sessionForYard2111() {
        return new PersistenceTemplate(this.getTestUserContextAtYrd2111());
    }

    protected PersistenceTemplate sessionForYard2112() {
        return new PersistenceTemplate(this.getTestUserContextAtYrd2112());
    }

    public IBizUnitManager getBizUnitManager() {
        Object bean = Roastery.getBean((String)"bizUnitManager");
        return (IBizUnitManager)bean;
    }

    protected void deleteTestData(UserContext inUserContext, String inEntityName, Serializable inEntityGkey) {
        if (inEntityGkey == null) {
            LOGGER.info((Object)("Key is null, delete ignored: " + inEntityName + ""));
        } else {
            LOGGER.info((Object)("Delete " + inEntityName + " with key " + inEntityGkey));
            try {
  //              this._delegate.requestDelete(inUserContext, inEntityName, inEntityGkey);
            }
            catch (Exception e) {
                LOGGER.error((Object)("Delete exception:" + e.getMessage()));
            }
        }
    }

    protected SavedPredicate makePredicate(IMetafieldId inMfid, PredicateVerbEnum inVerb, Object inValue) {
        ValueObject vaoInn = new ValueObject("SavedPredicate");
        vaoInn.setFieldValue(ISimpleSavedQueryField.PRDCT_METAFIELD, (Object)inMfid);
        vaoInn.setFieldValue(ISimpleSavedQueryField.PRDCT_VERB, (Object)inVerb);
        vaoInn.setFieldValue(ISimpleSavedQueryField.PRDCT_VALUE, inValue);
        vaoInn.setFieldValue(ISimpleSavedQueryField.PRDCT_ORDER, (Object)1L);
        vaoInn.setFieldValue(ISimpleSavedQueryField.PRDCT_NEGATED, (Object) Boolean.FALSE);
        vaoInn.setFieldValue(ISimpleSavedQueryField.PRDCT_PARAMETER_TYPE, (Object) PredicateParmEnum.NO_PARM);
        SavedPredicate sp = new SavedPredicate((IValueHolder)vaoInn);
        return sp;
    }

    protected EntityMappingPredicate makeEntityMappingPredicate(IMetafieldId inMfid, PredicateVerbEnum inVerb, Object inValue) {
        return new EntityMappingPredicate(ArgoTestUtil.createEntityMappingPredicateValueHolder(inMfid, inVerb, inValue));
    }

    public void importSnxStringElement(String inXml) {
        StringBuilder bfr = new StringBuilder();
        bfr.append(SNX_ROOT_START);
        bfr.append(inXml);
        bfr.append(SNX_ROOT_END);
        ByteArrayInputStream is = new ByteArrayInputStream(bfr.toString().getBytes());
//        try {
//            SAXBuilder builder = new SAXBuilder();
//            Document doc = builder.build((InputStream)is);
//            this.importSnx(doc, this.getTestUserContextAtYrd1111());
//        }
//        catch (Exception e) {
//            throw BizFailure.create((String)("could not build JDOM Tree from file content: " + e));
//        }
//        finally {
//            try {
//                ((InputStream)is).close();
//            }
//            catch (IOException iOException) {}
//        }
    }

    protected void createUserAtYrd1111(final String inUserId) {
        Serializable[] userGkey = new Serializable[1];
        IMessageCollector mc = this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                ArgoUser user = ArgoUser.findOrCreateArgoUser(inUserId, inUserId, inUserId, inUserId);
                user.setScope(BaseArgoTestCase.this.getOpr1().getId(), BaseArgoTestCase.this.getCpx11().getId(), BaseArgoTestCase.this.getFcy111().getFcyId(), BaseArgoTestCase.this.getYrd1111().getYrdId());
                user.setBuserActive("Y");
                user.setFieldValue(IUserArgoField.ARGOUSER_MY_LIST_CHOICE, (Object) KeySetOwnerEnum.USER);
            }
        });
        this.assertTrueResponseSuccess(mc);
    }

    protected Serializable createRoleAndPrivilegs(final String inRoleId, IPrivilege[] inArgoPrivs) {
        final Serializable[] roleGkey = new Serializable[1];
        this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                ArgoSecRole role = ArgoSecRole.findOrCreateSecRole(inRoleId);
                role.setRoleSecName(inRoleId);
                role.setRoleDescription(inRoleId);
                role.setRoleCreator("admin");
                role.setArgroleOperator(ContextHelper.getThreadOperator());
                roleGkey[0] = role.getRoleGkey();
            }
        });
        ArrayList<String> privilegesList = new ArrayList<String>();
        IMessageCollector mc = MessageCollectorFactory.createMessageCollector();
        for (IPrivilege argoPrivs : inArgoPrivs) {
            privilegesList.add(argoPrivs.getPrivilegeId().getKey());
        }
        this.startHibernateAtYard1111();
        ArgoSecRole role = (ArgoSecRole) HibernateApi.getInstance().load(ArgoSecRole.class, roleGkey[0]);
        String[] privs = privilegesList.toArray(new String[privilegesList.size()]);
        FieldChanges changes = new FieldChanges();
        changes.setFieldChange(ISecurityBizMetafield.ROLE_PRIVILEGES, (Object)role.getRolePrivileges(), (Object)privs);
        role.applyFieldChanges(changes);
        this.endHibernate();
        return roleGkey[0];
    }

    protected void assignUserToRole(final String inUserId, final Serializable inRoleGkey) {
        this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                SecRole role = (SecRole)this.getHibernateApi().load(SecRole.class, inRoleGkey);
                ArgoUser user = ArgoUser.findArgoUser(inUserId);
                HashSet<ArgoUser> userList = new HashSet<ArgoUser>();
                userList.add(user);
                role.setRoleBuserList(userList);
            }
        });
        this.sessionForYard1111().invoke(new CarinaPersistenceCallback(){

            public void doInTransaction() {
                SecRole role = (SecRole)this.getHibernateApi().load(SecRole.class, inRoleGkey);
                ArgoUser user = ArgoUser.findArgoUser(inUserId);
                HashSet<SecRole> roles = new HashSet<SecRole>();
                roles.add(role);
                user.setBuserRoleList(roles);
            }
        });
    }

    @Nullable
    public IMessageCollector importSnxAsString(String inXml) {
        IMessageCollector messageCollector = null;
        StringBuilder bfr = new StringBuilder();
        bfr.append(SNX_ROOT_START);
        bfr.append(inXml);
        bfr.append(SNX_ROOT_END);
        ByteArrayInputStream is = new ByteArrayInputStream(bfr.toString().getBytes());
        try {
//            SAXBuilder builder = new SAXBuilder();
//            Document doc = builder.build((InputStream)is);
//            messageCollector = this.importSnxElement(doc, this.getTestUserContextAtYrd1111());
        }
        catch (Exception e) {
            throw BizFailure.create((String)("could not build JDOM Tree from file content: " + e));
        }
        finally {
            try {
                ((InputStream)is).close();
            }
            catch (IOException iOException) {}
        }
        return messageCollector;
    }

    @Nullable
    public IMessageCollector importCompleteSnxAsString(String inXml) {
        IMessageCollector messageCollector = null;
        StringBuilder bfr = new StringBuilder();
        bfr.append(inXml);
        ByteArrayInputStream is = new ByteArrayInputStream(bfr.toString().getBytes());
        try {
//            SAXBuilder builder = new SAXBuilder();
//            Document doc = builder.build((InputStream)is);
//            messageCollector = this.importSnxElement(doc, this.getTestUserContextAtYrd1111());
        }
        catch (Exception e) {
            LOGGER.error((Object)bfr.toString());
            throw BizFailure.create((String)("could not build JDOM Tree from file content: " + e));
        }
        finally {
            try {
                ((InputStream)is).close();
            }
            catch (IOException iOException) {}
        }
        return messageCollector;
    }

    public void verifySnxImportWithMinProperties(IEntity inEntity, IPredicate inPredicate) {
        this.verifySnxImport(inEntity, inPredicate, false);
    }

    public void verifySnxImportWithAllProperties(IEntity inEntity, IPredicate inPredicate) {
        this.verifySnxImport(inEntity, inPredicate, true);
    }

    public void verifySnxImport(final IEntity inEntity, IPredicate inPredicate, boolean inAssertPropertyEquals) {
        CarinaPersistenceCallback purgeCallback = new CarinaPersistenceCallback(){

            public void doInTransaction() {
                boolean oldValue = BaseArgoTestCase.this._hibernateApi.getObsoletableDeleteEnabled();
                BaseArgoTestCase.this._hibernateApi.setObsoletableDeleteEnabled(false);
  //              SnxTestCase.deleteChildEntities(inEntity);
                IEntity entity = (IEntity)BaseArgoTestCase.this._hibernateApi.load(inEntity.getClass(), inEntity.getPrimaryKey());
                ArgoUtils.carefulDelete((Object)entity);
                BaseArgoTestCase.this._hibernateApi.setObsoletableDeleteEnabled(oldValue);
            }
        };
        this.verifySnxImport(inEntity, inPredicate, inAssertPropertyEquals, purgeCallback);
    }

    public void verifySnxImport(IEntity inEntity, IPredicate inPredicate, boolean inAssertPropertyEquals, CarinaPersistenceCallback inPurgeCallback) {
//        ValueObject oldVao = SnxTestCase.getEntityValueObject(inEntity);
//        this.startHibernateAtYard1111();
//        String xml = this.getSnxExportAsString(inEntity.getEntityName(), new Serializable[]{inEntity.getPrimaryKey()});
//        this.endHibernate();
//        this.sessionForYard1111().invoke(inPurgeCallback);
//        this.importSnxStringElement(xml);
//        this.startHibernateAtYard1111();
//        String entityName = HiberCache.getEntityNameFromClassName((String)inEntity.getEntityName());
//        IDomainQuery dq = QueryUtils.createDomainQuery((String)entityName).addDqPredicate(inPredicate);
//        IEntity entity = this._hibernateApi.getUniqueEntityByDomainQuery(dq);
//        if (inAssertPropertyEquals) {
//            SNX_TEST_CASE.assertSnxPropertyEquals(entityName, oldVao, SnxTestCase.getEntityValueObject(entity));
//        }
//        this.endHibernate();
    }

    protected final String getSnxExportAsString(String inEntityName, Serializable[] inPrimaryKeys) {
        StringBuilder sbf = new StringBuilder();
        IEntityXmlExporter exporter = SnxUtil.getSnxExporterForEntity(inEntityName);
        exporter.setPrimaryKeys(inPrimaryKeys);
        Iterator<Element> itr = exporter.getElementIterator();
        while (itr.hasNext()) {
            Element e = itr.next();
//            sbf.append(XmlUtil.toString(e, true));
        }
        String xml = sbf.toString();
        LOGGER.warn((Object)("\n" + xml));
        return xml;
    }

    protected Element assertXmlValidationSuccess(String inXmlInstance, String inXmlSchema) {
        BaseArgoTestCase.assertNotNull((Object)inXmlInstance);
//        String schemaPath = XmlUtil.getSchemaFromClasspath(inXmlSchema);
//        Document doc = null;
//        try {
//            doc = XmlUtil.parse(inXmlInstance, schemaPath, XmlUtil.ARGO_NAMESPACE);
//        }
//        catch (JDOMException jdome) {
//            BaseArgoTestCase.fail((String)("\n" + inXmlInstance + "\n\n" + ExceptionUtils.getStackTrace((Throwable)jdome)));
//        }
//        BaseArgoTestCase.assertNotNull((Object)doc);
//        return doc.getRootElement();
        return null;
    }

    public void sleep(int inSecs) {
        try {
            Thread.sleep(inSecs * 1000);
        }
        catch (InterruptedException e) {
            this.failOnException("", e);
        }
    }

    public void setSetting(IConfig inConfig, Object inValue) {
//        IConfigProvider cp = (IConfigProvider)Roastery.getBean((String)"configProvider");
//        UserContext userContext = ContextHelper.getThreadUserContext();
//        ScopeCoordinates scopeCoordinates = userContext.getScopeCoordinate();
//        long scope = scopeCoordinates.getMaxScopeLevel();
//        Serializable scopeGkey = scopeCoordinates.getScopeLevelCoord((int)scope);
//        cp.persistConfigSetting(userContext, inConfig, inValue, Long.valueOf(scope), scopeGkey);
    }

    public void setStorageCueCpx(Object inValue, IConfig... inConfig) {
        if (inConfig != null && inConfig.length > 0) {
            this.startHibernateAtCpx11();
            for (IConfig cfg : inConfig) {
                this.setSetting(cfg, inValue);
            }
            this.endHibernate();
        }
    }

    public void setStorageCueFcy(Object inValue, IConfig... inConfig) {
        if (inConfig != null && inConfig.length > 0) {
            this.startHibernateAtFcy111();
            for (IConfig cfg : inConfig) {
                this.setSetting(cfg, inValue);
            }
            this.endHibernate();
        }
    }

    public void setLineStorageCueFcy(Object inValue, IConfig... inConfig) {
        if (inConfig != null && inConfig.length > 0) {
            this.startHibernateAtFcy111();
            for (IConfig cfg : inConfig) {
                this.setSetting(cfg, inValue);
            }
            this.endHibernate();
        }
    }

    public void setLineStorageCueCpx(Object inValue, IConfig... inConfig) {
        if (inConfig != null && inConfig.length > 0) {
            this.startHibernateAtFcy111();
            for (IConfig cfg : inConfig) {
                this.setSetting(cfg, inValue);
            }
            this.endHibernate();
        }
    }

    public void setSetting(IConfig inConfig, Object inValue, UserContext inUserContext) {
        IConfigProvider cp = (IConfigProvider) Roastery.getBean((String)"configProvider");
        ScopeCoordinates scopeCoordinates = inUserContext.getScopeCoordinate();
        long scope = scopeCoordinates.getMaxScopeLevel();
        Serializable scopeGkey = scopeCoordinates.getScopeLevelCoord((int)scope);
        cp.persistConfigSetting(inUserContext, inConfig, inValue, Long.valueOf(scope), scopeGkey);
    }

    public void clearSetting(IConfig inConfig) {
        IConfigProvider cp = (IConfigProvider) Roastery.getBean((String)"configProvider");
        UserContext userContext = ContextHelper.getThreadUserContext();
        ScopeCoordinates scopeCoordinates = userContext.getScopeCoordinate();
        long scope = scopeCoordinates.getMaxScopeLevel();
        Serializable scopeGkey = scopeCoordinates.getScopeLevelCoord((int)scope);
        cp.clearConfigSetting(userContext, inConfig);
    }

    public Object getSetting(IConfig inConfig) {
        IConfigProvider cp = (IConfigProvider) Roastery.getBean((String)"configProvider");
        UserContext userContext = ContextHelper.getThreadUserContext();
        return cp.getEffectiveSetting(userContext, inConfig);
    }

    protected void assertAttributeEquals(Element inElement, String inAttributeName, String inExpectedValue) {
        Namespace ns = inElement.getNamespace();
        String attributeMsg = "Attribute " + inAttributeName + " in <" + inElement.getName() + ">";
//        BaseArgoTestCase.assertNotNull((String)(attributeMsg + " is missing"), (Object)inElement.getAttribute(inAttributeName, ns));
//        BaseArgoTestCase.assertEquals((String)attributeMsg, (String)inExpectedValue, (String)XmlUtil.getAttributeStringValue(inElement, inAttributeName, ns));
    }

    public UserContext getAdminUserContextAtYrd1111() {
        return this.getAdminUserContextForYard((Serializable)((Object)YRD1111));
    }

    public UserContext getAdminUserContextForYard(Serializable inYrdGkey) {
        this.initializeTestScopes();
        this.startHibernateWithGlobalUser();
//        N4EntityScoper scoper = (N4EntityScoper)((Object)Roastery.getBeanFactory().getBean("entityScoper"));
  //      ScopeCoordinates scopeCoordinates = YRD1111.equals(inYrdGkey) ? scoper.getScopeCoordinates((IScopeEnum)ScopeEnum.YARD, this.getYrd1111().getYrdGkey()) : scoper.getScopeCoordinates((IScopeEnum)ScopeEnum.YARD, inYrdGkey);
//        UserContext userContext = AuthenticationUtils.getAuthenticatedUserContext((String)"admin", (String)"admin", (ScopeCoordinates)scopeCoordinates);
        this.endHibernate();
//        return userContext;
        return null;
    }

    public UserContext getAdminUserContextForFacility(Serializable inFacilityGkey) {
        this.initializeTestScopes();
        this.startHibernateWithGlobalUser();
        ArgoUser user = ArgoUser.findOrCreateArgoUser("admin", "admin", "admin", "admin");
        ArgoUserContextProvider contextProvider = (ArgoUserContextProvider)((Object) PortalApplicationContext.getBean((String)"userContextProvider"));
//        N4EntityScoper scoper = (N4EntityScoper)((Object)Roastery.getBeanFactory().getBean("entityScoper"));
//        ScopeCoordinates scopeCoordinates = scoper.getScopeCoordinates((IScopeEnum)ScopeEnum.FACILITY, inFacilityGkey);
//        UserContext userContext = contextProvider.createUserContext(user.getBuserGkey(), "admin", scopeCoordinates);
//        this.endHibernate();
  //      return userContext;
        return null;
    }

    public UserContext getAdminUserContextForFacility(UserContext inYardContext) {
        if ((long)inYardContext.getScopeCoordinate().getMostSpecificScopeLevel() != ScopeCoordinates.SCOPE_LEVEL_4) {
            throw new IllegalArgumentException("requires user context at yard scope");
        }
        Serializable facilityGkey = inYardContext.getScopeCoordinate().getScopeLevelCoord(ScopeCoordinates.SCOPE_LEVEL_3.intValue());
        return this.getAdminUserContextForFacility(facilityGkey);
    }

    public FieldChanges configureFlexFieldChanges(String inMfId, String inShortName, String inLongName, Object inScopeKey, String inGroupId) {
        FieldChanges mfFcs = new FieldChanges();
        if (inScopeKey instanceof Long) {
            inScopeKey = inScopeKey.toString();
        }
        mfFcs.setFieldChange(IConfigSettingField.MFDO_ID, (Object)inMfId);
        mfFcs.setFieldChange(IConfigSettingField.MFDO_SCOPE_GKEY, inScopeKey);
        mfFcs.setFieldChange(IConfigSettingField.MFDO_SCOPE, (Object)5L);
        mfFcs.setFieldChange(IConfigSettingField.MFDO_IMPORTANCE, (Object) FieldImportanceEnum.OPTIONAL);
        mfFcs.setFieldChange(IConfigSettingField.MFDO_SHORT_NAME, (Object)inShortName);
        mfFcs.setFieldChange(IConfigSettingField.MFDO_LONG_NAME, (Object)inLongName);
        mfFcs.setFieldChange(IConfigSettingField.MFDO_HELP_LABEL, (Object)("Overriding FlexField " + inMfId + "through Unit Testing"));
        mfFcs.setFieldChange(IConfigSettingField.MFDO_GROUP_ID, (Object)inGroupId);
        mfFcs.setFieldChange(IConfigSettingField.MFDO_MAX_CHARS, (Object)87);
        return mfFcs;
    }

    public void purgeFlexFieldsById(String inFieldId) {
        IDomainQuery dqFlexFieldsForUser = QueryUtils.createDomainQuery((String)"DbMetafield").addDqPredicate(PredicateFactory.eq((IMetafieldId) IConfigSettingField.MFDO_ID, (Object)inFieldId));
        List lst = HibernateApi.getInstance().findEntitiesByDomainQuery(dqFlexFieldsForUser);
        for (Object dbm : lst) {
            ArgoUtils.carefulDelete((Object)dbm);
        }
    }

    public BizResponse overrideFlexFields(String inMfId, String inShortName, String inLongName, Object inScopeKey, String inGroupId) {
        return this.overrideFlexFields(this.getAdminUserContextAtYrd1111(), inMfId, inShortName, inLongName, inScopeKey, inGroupId);
    }

    public BizResponse overrideFlexFields(UserContext inUserContext, String inMfId, String inShortName, String inLongName, Object inScopeKey, String inGroupId) {
        FieldChanges mfFcs = this.configureFlexFieldChanges(inMfId, inShortName, inLongName, inScopeKey, inGroupId);
  //      return CrudDelegate.requestCreate((UserContext)inUserContext, (String)"DbMetafield", null, (FieldChanges)mfFcs);
        return null;
    }

    public void failOnSevereError(IMessageCollector inMsgCollector, String inMessage) {
        if (inMsgCollector != null && inMsgCollector.containsMessageLevel(MessageLevelEnum.SEVERE)) {
            StringBuilder sb = new StringBuilder();
            for (Object um : inMsgCollector.getMessages(MessageLevelEnum.SEVERE)) {
                sb.append(um.toString() + "\n");
            }
            BaseArgoTestCase.fail((String)(inMessage + " : " + sb));
        }
    }

    protected String getUniqueId(String inCtrId, int inOffset) {
        String strToApnd = String.valueOf(System.currentTimeMillis());
        return inCtrId + strToApnd.substring(strToApnd.length() - inOffset, strToApnd.length());
    }

    public Calendar getDate(int inRequestedDay, int inHour, int inMinute, int inMillSec, int inSec) {
        this.startHibernateAtYard1111();
        GregorianCalendar calendar = new GregorianCalendar(this.getTestUserContextAtYrd1111().getTimeZone());
        calendar.set(11, inHour);
        calendar.set(12, inMinute);
        calendar.set(14, inMillSec);
        calendar.set(13, inSec);
        ((Calendar)calendar).add(5, inRequestedDay);
        this.endHibernate();
        return calendar;
    }

    @Nullable
    protected XmlObject loadEdiXmlFileFromPath(String inSampleXmlId) {
        File sampleFile = FileUtil.getFile(inSampleXmlId);
        LOGGER.info((Object)("Sample file path.." + sampleFile));
        String strSampleFile = FileUtil.getFileAsStringFromSystem(inSampleXmlId);
        XmlObject ediBean = null;
        try {
            ediBean = XmlObject.Factory.parse((String)strSampleFile);
        }
        catch (XmlException e) {
            this.failOnException("Unable to parse the file", (Exception)((Object)e));
        }
        BaseArgoTestCase.assertNotNull((String)"Unable to parse the file", (Object)ediBean);
        return ediBean;
    }

    public static XmlObject getEDITransactionFromXml(XmlObject inEdiBean, EdiMessageClassEnum inEdiMessageClass) {
        BaseArgoTestCase.assertNotNull((String)"MessageClass is null", (Object)((Object)inEdiMessageClass));
        BaseArgoTestCase.assertNotSame((String)"Message class is Uknown", (Object)((Object) EdiMessageClassEnum.UNKNOWN), (Object)((Object)inEdiMessageClass));
        if (!(EdiMessageClassEnum.ACTIVITY.equals((Object)inEdiMessageClass) || EdiMessageClassEnum.ACKNOWLEDGEMENT.equals((Object)inEdiMessageClass) || EdiMessageClassEnum.ACTIVITY_BY_CV.equals((Object)inEdiMessageClass) || EdiMessageClassEnum.APPOINTMENT.equals((Object)inEdiMessageClass) || EdiMessageClassEnum.BOOKING.equals((Object)inEdiMessageClass) || EdiMessageClassEnum.CREDIT.equals((Object)inEdiMessageClass) || EdiMessageClassEnum.DISCHLIST.equals((Object)inEdiMessageClass) || EdiMessageClassEnum.HAZARD.equals((Object)inEdiMessageClass) || EdiMessageClassEnum.INVENTORY.equals((Object)inEdiMessageClass) || EdiMessageClassEnum.INVOICE.equals((Object)inEdiMessageClass) || EdiMessageClassEnum.LOADLIST.equals((Object)inEdiMessageClass))) {
            if (EdiMessageClassEnum.MANIFEST.equals((Object)inEdiMessageClass)) {
//                BlTransactionsDocument manifest = (BlTransactionsDocument)inEdiBean;
//                BlTransactionsDocument.BlTransactions transactions = manifest.getBlTransactions();
//                BlTransactionDocument.BlTransaction transaction = transactions.getBlTransactionArray(0);
//                return transaction;
            }
            if (!EdiMessageClassEnum.PERFORMANCE.equals((Object)inEdiMessageClass)) {
                if (EdiMessageClassEnum.PREADVISE.equals((Object)inEdiMessageClass)) {
//                    PreadviseTransactionsDocument preadviseTransactions = (PreadviseTransactionsDocument)inEdiBean;
//                    PreadviseTransactionsDocument.PreadviseTransactions transactions = preadviseTransactions.getPreadviseTransactions();
//                    PreadviseTransactionDocument.PreadviseTransaction transaction = transactions.getPreadviseTransactionArray(0);
//                    return transaction;
                }
                if (!EdiMessageClassEnum.RAILCONSIST.equals((Object)inEdiMessageClass) && !EdiMessageClassEnum.RAILORDER.equals((Object)inEdiMessageClass)) {
                    if (EdiMessageClassEnum.RAILWAYBILL.equals((Object)inEdiMessageClass)) {
//                        RailWayBillTransactionsDocument railwayBill = (RailWayBillTransactionsDocument)inEdiBean;
//                        RailWayBillTransactionsDocument.RailWayBillTransactions transactions = railwayBill.getRailWayBillTransactions();
//                        RailWayBillTransactionDocument.RailWayBillTransaction transaction = transactions.getRailWayBillTransactionArray(0);
//                        return transaction;
                    }
                    if (!(EdiMessageClassEnum.RELEASE.equals((Object)inEdiMessageClass) || EdiMessageClassEnum.SAUDILDP.equals((Object)inEdiMessageClass) || EdiMessageClassEnum.SAUDIMANIFEST.equals((Object)inEdiMessageClass) || EdiMessageClassEnum.SNX.equals((Object)inEdiMessageClass) || EdiMessageClassEnum.STOWPLAN.equals((Object)inEdiMessageClass) || EdiMessageClassEnum.VESSELACTIVITY.equals((Object)inEdiMessageClass) || EdiMessageClassEnum.VESSELACTIVITY_BY_CV.equals((Object)inEdiMessageClass))) {
                        BaseArgoTestCase.fail((String)("Message class[" + inEdiMessageClass.getKey() + "] is not supported currently"));
                    }
                }
            }
        }
        return null;
    }

    public DatabaseHelper getDatabaseHelper() {
        return (DatabaseHelper) Roastery.getBean((String)"dbHelper");
    }

    public Date getEffectiveDate(int inRequestedDay) {
        return this.getDate(inRequestedDay, 0, 0, 0, 0).getTime();
    }

    public void nullifyDataQuality() {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"DataQuality");
        List qualities = HibernateApi.getInstance().findEntitiesByDomainQuery(dq);
        if (qualities != null && !qualities.isEmpty()) {
//            for (DataQuality dataQuality : qualities) {
//                dataQuality.setDqMappingPredicateDto(null);
//            }
        }
        HibernateApi.getInstance().deleteByDomainQuery(dq);
    }

    public IMessageCollector isTomcatUpAndReadyToInvokeWebRequest() {
        IMessageCollector mc = MessageCollectorFactory.createMessageCollector();
        try {
            this.invokeGenericRequest();
        }
        catch (RemoteException inE) {
            LOGGER.error((Object)inE);
            mc.registerExceptions((Throwable)inE);
        }
        return mc;
    }

    public void invokeGenericRequest() throws RemoteException {
        IArgoServicePort port = this.locateArgoService();
        if (port != null) {
            port.genericInvoke(ArgoTestUtil.getScopeCoordinatesWSType(OPR1, CPX11, FCY111, YRD1111), TEMP_XML);
        }
    }

    static interface IResolver<T, M> {
        public T resolveEntity(M var1);
    }

    protected abstract class EntityResolver
            implements IResolver<IEntity, Integer> {
        private Integer _defaultMaxTime = 120;

        protected EntityResolver() {
        }

        @Override
        public IEntity resolveEntity(Integer inMaxTime) {
            Integer maxTime = inMaxTime < this._defaultMaxTime ? inMaxTime : this._defaultMaxTime;
            for (int i = 0; i < maxTime; ++i) {
                IEntity entity = this.executeStrategy();
                if (entity != null) {
                    return entity;
                }
                BaseArgoTestCase.this.sleep(1);
            }
            return null;
        }

        public IEntity resolveEntity() {
            return this.resolveEntity(this._defaultMaxTime);
        }

        public abstract IEntity executeStrategy();
    }

    public static class VoyageIdProvider
            extends ArgoSequenceProvider {
        private String _extractVoyageIdSeq = "VOYAGE_SEQUENCE";

        public Long getNextVoyageId() {
            return super.getNextSeqValue(this._extractVoyageIdSeq, (Long) ContextHelper.getThreadComplexKey());
        }
    }

    public static class LloydsIdProvider
            extends ArgoSequenceProvider {
        private String _extractLloydsIdSeq = "lLOYDS_SEQUENCE";

        public Long getNextLloydsId() {
            return super.getNextSeqValue(this._extractLloydsIdSeq, (Long) ContextHelper.getThreadComplexKey());
        }
    }

}
