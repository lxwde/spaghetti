package com.zpmc.ztos.infra.base.common.model;

import java.io.Serializable;
import java.security.SecureRandom;

public class UniqueGkeyFactory
//        implements IdentifierGenerator
{
    private static final SecureRandom PRNG = new SecureRandom();

//    public Serializable generate(SessionImplementor inSImpl, Object inHEntity) throws HibernateException {
//        return Long.valueOf(PRNG.nextLong());
//    }

    public static Serializable generate() {
        return Long.valueOf(PRNG.nextLong());
    }
}
