package com.zpmc.ztos.infra.base.business.interfaces;

import java.util.List;

public interface IEdiEdifactMessageFormat extends IEdiMessageFormat {

    public List createCodecoDetailRules();

    public List createCoparnDetailRules();

    public List createCopinoDetailRules();

    public List createCoarriDetailRules();

    public List createCoprarDetailRules();

    public List createBaplieDetailRules();

    public List createAperakDetailRules();

    public List createTpfrepDetailRules();
}
