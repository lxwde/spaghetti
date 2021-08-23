package com.zpmc.ztos.infra.base.common.model;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.xps.AbstractXpsYardBin;
import com.zpmc.ztos.infra.base.utils.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.math.NumberUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RefIDPosition {

    @NotNull
    private final String _refID;
    @Nullable
    private final Character _qualifier;
    @Nullable
    private final String _location;
    @Nullable
    private final String _block;
    @Nullable
    private final String _row;
    @Nullable
    private final String _column;
    @Nullable
    private final String _tier;
  //  @NonNls
    public static final String ATTRIBUTE_NAME = "refID";
    public static final Pattern REFID_PATTERN = Pattern.compile("(\\w+)\\.(\\w+):([a-zA-Z0-9(-)]*)\\.(\\w*)\\.(\\w*)\\.(\\w*)");
    public static final Pattern RAIL_REFID_PATTERN = Pattern.compile("(\\w+)\\.([a-zA-Z0-9-/]*):([a-zA-Z0-9(-)\\s]*)\\.(\\w*)\\.(\\w*)\\.(\\w*)");
    public static final String RAIL_REFID_QUALIFIER = "R";

    @Nullable
    public static RefIDPosition create(@Nullable String inRefID) {
        if (inRefID == null) {
            return null;
        }
        if (StringUtils.isEmpty((String)inRefID)) {
            return new RefIDPosition(inRefID, AbstractXpsYardBin.LOC_TYPE_UNKNOWN, "", "", "", "", "");
        }
        String[] refIDparts = RefIDPosition.parseRefIDString(inRefID);
        if (refIDparts == null) {
            return null;
        }
        Character qualifier = Character.valueOf(refIDparts[0].charAt(0));
        RefIDPosition refIDAttribute = new RefIDPosition(inRefID, qualifier, refIDparts[1], refIDparts[2], refIDparts[3], refIDparts[4], refIDparts[5]);
        return refIDAttribute;
    }

    @Nullable
    public static RefIDPosition create(@Nullable String inStrRefID, @NotNull String inColumn, @NotNull String inTier) {
        RefIDPosition newRefID;
        if (inStrRefID == null) {
            return null;
        }
        RefIDPosition inRefID = RefIDPosition.create(inStrRefID);
        if (inRefID == null) {
            return null;
        }
        Character qualifier = inRefID.getQualifier();
        int colLength = inRefID.getColumn().length();
        if (colLength == 1 || !NumberUtils.isDigits((String)inColumn)) {
            newRefID = new RefIDPosition(inStrRefID, qualifier, inRefID.getLocation(), inRefID.getBlock(), inRefID.getRow(), inColumn, inTier);
        } else {
            String col1 = new String(new char[colLength]).replace('\u0000', '0');
            String col = col1.substring(0, col1.length() - inColumn.length()) + inColumn;
            newRefID = new RefIDPosition(inStrRefID, qualifier, inRefID.getLocation(), inRefID.getBlock(), inRefID.getRow(), col, inTier);
        }
        return newRefID;
    }

    private RefIDPosition(@NotNull String inRefID, @NotNull Character inQualifier, @NotNull String inLocation, @NotNull String inBlock, @NotNull String inRow, @NotNull String inColumn, @NotNull String inTier) {
        this._refID = inRefID;
        this._qualifier = inQualifier;
        this._location = inLocation;
        this._block = inBlock;
        this._row = inRow;
        this._column = inColumn;
        this._tier = inTier;
    }

    @Nullable
    private static String[] parseRefIDString(@Nullable String inRefID) {
        Matcher matcher = inRefID != null && inRefID.startsWith(RAIL_REFID_QUALIFIER) ? RAIL_REFID_PATTERN.matcher(inRefID) : REFID_PATTERN.matcher(inRefID);
        boolean patternMatch = matcher.matches();
        if (!patternMatch) {
            return null;
        }
        String[] returnRefID = new String[]{matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5), matcher.group(6)};
        if (!RefIDPosition.isValidRefID(returnRefID)) {
            return null;
        }
        return returnRefID;
    }

    private static boolean isValidRefID(@NotNull String[] inRefID) {
        String qualString = inRefID[0];
        String location = inRefID[1];
        String block = inRefID[2];
        String row = inRefID[3];
        String column = inRefID[4];
        String tier = inRefID[5];
        if (qualString.length() != 1) {
            return false;
        }
        Character qualifier = Character.valueOf(qualString.charAt(0));
        if (StringUtils.isEmpty((String)location)) {
            return false;
        }
        if (AbstractXpsYardBin.LOC_TYPE_YARD.equals(qualifier)) {
            if (StringUtils.isEmpty((String)block)) {
                return false;
            }
        } else if (AbstractXpsYardBin.LOC_TYPE_RAIL.equals(qualifier) || AbstractXpsYardBin.LOC_TYPE_VESSEL.equals(qualifier)) {
            if (StringUtils.isEmpty((String)block) || StringUtils.isEmpty((String)row) || StringUtils.isEmpty((String)column) || StringUtils.isEmpty((String)tier)) {
                return false;
            }
        } else if (AbstractXpsYardBin.LOC_TYPE_TRUCK.equals(qualifier) || AbstractXpsYardBin.LOC_TYPE_CHE.equals(qualifier)) {
            if (StringUtils.isEmpty((String)block) || !StringUtils.isEmpty((String)row) || !StringUtils.isEmpty((String)column)) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public String toString() {
        ToStringBuilder builder = new ToStringBuilder((Object)this);
        builder.append(ATTRIBUTE_NAME, (Object)this._refID);
        builder.append((Object)",");
        builder.append("_qualifer", (Object)this._qualifier);
        builder.append((Object)",");
        builder.append("_location", (Object)this._location);
        builder.append((Object)",");
        builder.append("_block", (Object)this._block);
        builder.append((Object)",");
        builder.append("_row", (Object)this._row);
        builder.append((Object)",");
        builder.append("_column", (Object)this._column);
        builder.append((Object)",");
        builder.append("_tier", (Object)this._tier);
        return builder.toString();
    }

    @NotNull
    public String getRefID() {
        return this._refID;
    }

    @Nullable
    public Character getQualifier() {
        return this._qualifier;
    }

    @Nullable
    public String getLocation() {
        return this._location;
    }

    @Nullable
    public String getTier() {
        return this._tier;
    }

    @Nullable
    public String getColumn() {
        return this._column;
    }

    @Nullable
    public String getRow() {
        return this._row;
    }

    @Nullable
    public String getBlock() {
        return this._block;
    }
}
