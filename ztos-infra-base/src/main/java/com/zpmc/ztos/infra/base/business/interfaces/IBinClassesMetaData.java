package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.AbstractBin;

public interface IBinClassesMetaData {

    public static final String BEAN_ID = "binClassesMetaData";

    public void buildTree();

    public boolean isBinAnInstanceOf(AbstractBin var1, Class var2);

    public boolean isBinSubTypeAnInstanceOf(String var1, Class var2);

    public Class getClassForBin(AbstractBin var1);
}
