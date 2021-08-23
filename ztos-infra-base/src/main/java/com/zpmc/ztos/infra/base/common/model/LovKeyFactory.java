package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.ILovKey;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;

public class LovKeyFactory {
    private LovKeyFactory() {
    }

    public static ILovKey valueOf(String inLovKey) {
        //return new SimpleLovKeyImpl(inLovKey);
        return null;
    }

    public static ILovKey valueOf(String inLovKey, IMetafieldId inMetafieldId)
    {
        //return new MetafieldIdLovKeyImpl(inLovKey, inMetafieldId);
        return null;
    }

 //   public static IDomainQueryLovKey valueOf(IDomainQuery inQuery) {
 //       return new DomainQueryLovKeyImpl(inQuery);
 //   }

}
