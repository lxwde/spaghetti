package com.zpmc.ztos.infra.base.common.model;

import org.apache.commons.lang.CharUtils;
import org.hibernate.HibernateException;

import org.hibernate.usertype.UserType;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;
import org.hibernate.util.EqualsHelper;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmptyToSpaceCharUserType implements UserType {
    public int[] sqlTypes() {
        return new int[]{1};
    }

    public Class returnedClass() {
        return Character.class;
    }

    public boolean equals(Object inX, Object inY) throws HibernateException {
        return EqualsHelper.equals((Object)inX, (Object)inY);
    }

    public int hashCode(Object inObject) throws HibernateException {
        return inObject.hashCode();
    }

    @Nullable
    public Object nullSafeGet(ResultSet inRs, String[] inNames, Object inOwner) throws HibernateException, SQLException {
        String str = inRs.getString(inNames[0]);
        if (str == null) {
            return null;
        }
        if (str.isEmpty()) {
            return CharUtils.toCharacterObject((char)" ".charAt(0));
        }
        return Character.valueOf(str.charAt(0));
    }

    public void nullSafeSet(PreparedStatement inSt, Object inValue, int inDex) throws HibernateException, SQLException {
        if (inValue == null) {
            inSt.setNull(inDex, 1);
        } else {
            inSt.setString(inDex, inValue.toString());
        }
    }

    public Object deepCopy(Object inValue) throws HibernateException {
        return inValue;
    }

    public boolean isMutable() {
        return false;
    }

    public Serializable disassemble(Object inObject) throws HibernateException {
        return (Serializable)inObject;
    }

    public Object assemble(Serializable inCached, Object inOwner) throws HibernateException {
        return inCached;
    }

    public Object replace(Object inOriginal, Object inTarget, Object inOwner) throws HibernateException {
        return inOriginal;
    }
}
