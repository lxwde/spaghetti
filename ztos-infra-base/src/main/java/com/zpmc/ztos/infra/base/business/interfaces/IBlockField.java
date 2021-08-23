package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;

public interface IBlockField {

    public static final IMetafieldId AST_COL_INDEX = MetafieldIdFactory.valueOf((String)"astColIndex");
    public static final IMetafieldId ASN_ROW_INDEX = MetafieldIdFactory.valueOf((String)"asnRowIndex");
    public static final IMetafieldId ASN_ROW_PAIRED_INTO = MetafieldIdFactory.valueOf((String)"asnRowPairedInto");
    public static final IMetafieldId ASN_ROW_PAIRED_FROM = MetafieldIdFactory.valueOf((String)"asnRowPairedFrom");
    public static final IMetafieldId ASN_CONV_ID_COL = MetafieldIdFactory.valueOf((String)"asnConvIdCol");
    public static final IMetafieldId ASN_COL_TABLE_OFFSET = MetafieldIdFactory.valueOf((String)"asnColTableOffset");
    public static final IMetafieldId ABL_CONV_ID_ROW_STD = MetafieldIdFactory.valueOf((String)"ablConvIdRowStd");
    public static final IMetafieldId ABL_CONV_ID_ROW_ALT = MetafieldIdFactory.valueOf((String)"ablConvIdRowAlt");
    public static final IMetafieldId ABL_CONV_ID_TIER = MetafieldIdFactory.valueOf((String)"ablConvIdTier");

}
