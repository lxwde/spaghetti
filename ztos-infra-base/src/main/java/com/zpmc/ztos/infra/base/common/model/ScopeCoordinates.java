package com.zpmc.ztos.infra.base.common.model;


import com.sun.istack.Nullable;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class ScopeCoordinates implements Serializable
{
    public static final Long GLOBAL_LEVEL = 1L;
    public static final Long SCOPE_LEVEL_1 = 2L;
    public static final Long SCOPE_LEVEL_2 = 3L;
    public static final Long SCOPE_LEVEL_3 = 4L;
    public static final Long SCOPE_LEVEL_4 = 5L;
    public static final Long SCOPE_LEVEL_5 = 6L;
    public static final ScopeCoordinates GLOBAL_SCOPE = new ScopeCoordinates(new Long[0]);
    private static final Long[] A = new Long[]{SCOPE_LEVEL_1, SCOPE_LEVEL_2, SCOPE_LEVEL_3, SCOPE_LEVEL_4, SCOPE_LEVEL_5};
 //   public static final PropertyKey[] SCOPE_KEYS = new PropertyKey[]{PropertyKeyFactory.valueOf("scope.name.0"), PropertyKeyFactory.valueOf("scope.name.1"), PropertyKeyFactory.valueOf("scope.name.2"), PropertyKeyFactory.valueOf("scope.name.3"), PropertyKeyFactory.valueOf("scope.name.4"), PropertyKeyFactory.valueOf("scope.name.5")};
    private static final String E = ".";
    public static final String GLOBAL_SCOPE_ADDRESS = "GLOBAL";
    private volatile int B;
    private final Serializable[] D;
    private String[] C;

    public ScopeCoordinates(Serializable[] arrserializable) {
        this.D = arrserializable;
    }

    public int hashCode() {
        int n = this.B;
        if (n == 0) {
            n = 17;
            for (int i = 0; i < this.D.length; ++i) {
                long l = this.A(i);
                n = 31 * n + (int)(l ^ l >>> 32);
            }
            this.B = n;
        }
        return n;
    }

//    public static PropertyKey getPropertyKey(Long l) {
//        int n = l.intValue();
//        return SCOPE_KEYS[n];
//    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ScopeCoordinates)) {
            return false;
        }
        ScopeCoordinates scopeCoordinates = (ScopeCoordinates)object;
        if (scopeCoordinates.getDepth() != this.getDepth()) {
            return false;
        }
        for (int i = 0; i < this.getDepth(); ++i) {
            if (scopeCoordinates.A(i) == this.A(i)) continue;
            return false;
        }
        return true;
    }

    public boolean isSubCoordinate(ScopeCoordinates scopeCoordinates) {
        if (scopeCoordinates.getDepth() < this.getDepth()) {
            return false;
        }
        for (int i = 0; i < this.getDepth(); ++i) {
            if (scopeCoordinates.A(i) == this.A(i)) continue;
            return false;
        }
        return true;
    }

    public ScopeCoordinates getParentCoordinate() {
        Serializable[] arrserializable = new Serializable[this.D.length - 1];
        System.arraycopy(this.D, 0, arrserializable, 0, arrserializable.length);
        return new ScopeCoordinates(arrserializable);
    }

    public int getDepth() {
        return this.D.length;
    }

    private long A(int n) {
        return this.D[n].hashCode();
    }

    private static int B(int n) {
        return n - 2;
    }

    public boolean isValidScopeLevel(int n) {
        if (n == GLOBAL_LEVEL.intValue()) {
            return true;
        }
        int n2 = ScopeCoordinates.B(n);
        return n2 < this.D.length;
    }

    @Nullable
    public Serializable getScopeLevelCoord(int n) {
        if (n <= GLOBAL_LEVEL.intValue()) {
            return null;
        }
        int n2 = ScopeCoordinates.B(n);
        if (n2 < this.D.length) {
            return this.D[n2];
        }
        return null;
    }

    @Nullable
    public static Long getScopeId(int n) {
        if (n <= GLOBAL_LEVEL.intValue()) {
            return null;
        }
        return A[ScopeCoordinates.B(n)];
    }

    public int getMaxScopeLevel() {
        if (this.D.length == 0) {
            return GLOBAL_LEVEL.intValue();
        }
        return A[this.D.length - 1].intValue();
    }

    public int getMostSpecificScopeLevel() {
        return this.getMaxScopeLevel();
    }

    public int getMinScopeLevel() {
        return A[0].intValue();
    }

    public boolean isScopeGlobal() {
        return this.D.length == 0;
    }

    public String getScopeAddress() {
        if (this.isScopeGlobal()) {
            return GLOBAL_SCOPE_ADDRESS;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.getDepth(); ++i) {
            stringBuilder.append(this.D[i]);
            if (i >= this.getDepth() - 1) continue;
            stringBuilder.append(E);
        }
        return stringBuilder.toString();
    }

    public String toString() {
        return this.getScopeAddress();
    }

    public String getBusinessCoords() {
        return this.getBusinessCoords("/");
    }

    public String getBusinessCoords(String string) {
        String string2 = "";
        if (this.C != null) {
            string2 = org.springframework.util.StringUtils.arrayToDelimitedString((Object[])this.C, (String)string);
            string2 = string2.replaceAll(String.valueOf(null), "-");
        }
        return string2;
    }

    public void setBusinessCoords(String[] arrstring) {
        this.C = arrstring;
    }

    @Nullable
    public static ScopeCoordinates getScopeCoordinatesFromScopeAddress(String string) {
        if (GLOBAL_SCOPE_ADDRESS.equals(string)) {
            return GLOBAL_SCOPE;
        }
        ScopeCoordinates scopeCoordinates = null;
        String[] arrstring = StringUtils.splitPreserveAllTokens((String)string, (String)E);
        if (arrstring != null) {
            Serializable[] arrserializable = new Serializable[arrstring.length];
            try {
                boolean bl = true;
                for (int i = 0; i < arrstring.length; ++i) {
                    if (StringUtils.isEmpty((String)arrstring[i])) {
                        bl = false;
                        break;
                    }
                    arrserializable[i] = StringUtils.isNumeric((String)arrstring[i]) ? new Long(arrstring[i]) : arrstring[i];
                }
                if (bl) {
                    scopeCoordinates = new ScopeCoordinates(arrserializable);
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return scopeCoordinates;
    }

}
