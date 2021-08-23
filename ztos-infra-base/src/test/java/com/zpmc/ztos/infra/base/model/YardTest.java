package com.zpmc.ztos.infra.base.model;

import com.zpmc.ztos.infra.base.ZtosInfraBaseApplication;
import com.zpmc.ztos.infra.base.business.enums.argo.CheckDigitAlgorithmEnum;
import com.zpmc.ztos.infra.base.business.interfaces.IServicesManager;
import com.zpmc.ztos.infra.base.business.model.Container;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.math.CheckDigitAlgorithm;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.zpmc.ztos.infra.base.common.scopes.Facility;
import com.zpmc.ztos.infra.base.common.scopes.Yard;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZtosInfraBaseApplication.class)
public class YardTest
//        extends BaseArgoTestCase
{
    private static final Logger log = Logger.getLogger(YardTest.class);
    private UserContext _testUser;
    private String _eqId1 = "APLU123456";
    private String _expectedCheckDigit1 = "4";
    private String _eqId2 = "MSKU123456";
    private String _expectedCheckDigit2 = "5";
    private String _eqIdShort = "MSKU1234";
    private String _eqIdIllegal = "MSKU12345$";
    private Long _eqpfxGkey = null;
    private String _prefix = "YYYU";
    private String _eqId3 = "HLCU400593";
    private String _expectedCheckDigit3 = "8";


    @Test
    public void TestYard() {
        Facility facility = new Facility();
        facility.setPrimaryKey(111111);
//        Yard yard3 = Yard.findYard("y3", ContextHelper.getThreadFacility());
        Yard yard3 = Yard.findYard("y3", facility);
    }

    @Test
    public void TestYardInComplex() {
        Yard yard3 = Yard.findYardInComplex("y3", ContextHelper.getThreadComplex());
    }

    @Test
    public void TestYardImportReadyYards() {

        List<Yard> lstYard = Yard.findActiveImportReadyYards();
    }

    @Test
    public void TestYardDigitCheck() {

        //  this.startHibernateWithUser(this._testUser);
        String checkDigit = null;
        String _eqId3 = "HLCU400593";
        checkDigit = CheckDigitAlgorithm.calc(CheckDigitAlgorithmEnum.HAPPAGLLOYD, _eqId3);
        String _eqId1 = "APLU123456";
        checkDigit = CheckDigitAlgorithm.calc(CheckDigitAlgorithmEnum.STANDARD, _eqId1);

    }

    @Test
    public void testCheckDigitCalc() {

        IServicesManager _sm = (IServicesManager) Roastery.getBean((String) "servicesManager");

        //       BaseArgoTestCase baseArgoTestCase  = new BaseArgoTestCase("");
//        this.startHibernateWithUser(this._testUser);
        String checkDigit = null;
        checkDigit = CheckDigitAlgorithm.calc(CheckDigitAlgorithmEnum.STANDARD, this._eqId1);
        log.info((Object) ("Calculated check digit for " + this._eqId1 + " = " + checkDigit));
        //       CheckDigitSaTestSuite.assertEquals((String)"Check digit calculation failed", (String)this._expectedCheckDigit1, (String)checkDigit);
        checkDigit = CheckDigitAlgorithm.calc(CheckDigitAlgorithmEnum.STANDARD, this._eqId2);
        log.info((Object) ("Calculated check digit for " + this._eqId2 + " = " + checkDigit));
        //       CheckDigitSaTestSuite.assertEquals((String)"Check digit calculation failed", (String)this._expectedCheckDigit2, (String)checkDigit);
        checkDigit = CheckDigitAlgorithm.calc(CheckDigitAlgorithmEnum.STANDARD, this._eqIdShort);
        log.info((Object) ("Calculated check digit for " + this._eqIdShort + " = " + checkDigit));
//        CheckDigitSaTestSuite.assertNull((String)("Check digit calculation not null for " + this._eqIdShort), (Object)checkDigit);
        checkDigit = CheckDigitAlgorithm.calc(CheckDigitAlgorithmEnum.STANDARD, this._eqIdIllegal);
        log.info((Object) ("Calculated check digit for " + this._eqIdIllegal + " = " + checkDigit));
//        CheckDigitSaTestSuite.assertNull((String)("Check digit calculation not null for " + this._eqIdIllegal), (Object)checkDigit);
        checkDigit = Container.calcCheckDigit(this._eqId1);
        boolean isCorrect = Container.isCheckDigitCorrect(this._eqId1 + checkDigit);
        log.info((Object) ("Check digit for " + this._eqId1 + checkDigit + " is correct = " + isCorrect));
//        CheckDigitSaTestSuite.assertTrue((String)("Good check digit is incorrect! for " + this._eqId1 + checkDigit), (boolean)isCorrect);
        isCorrect = Container.isCheckDigitCorrect(this._eqId1 + "$");
        log.info((Object) ("Check digit for " + this._eqId1 + "$ is correct = " + isCorrect));
//        CheckDigitSaTestSuite.assertFalse((String)("Bad check digit is correct! for " + this._eqId1 + "$"), (boolean)isCorrect);
        //       this.endHibernate();
    }


//    public static void main(String[] args) {
//        Yard yard3 = Yard.findYard("y3", ContextHelper.getThreadFacility());
//    }

}