package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;

public interface ICarrierField extends IArgoRefField {
    public static final IMetafieldId SRVC_ITIN_VAO = IArgoBizMetafield.SRVC_ITIN_VAO;
    public static final IMetafieldId CVD_ITIN_VAO = IArgoBizMetafield.CVD_ITIN_VAO;
    public static final IMetafieldId CVD_SRVC_ITIN_VAO = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoField.CVD_SERVICE, (IMetafieldId) IArgoBizMetafield.SRVC_ITIN_VAO);
    public static final IMetafieldId ITIN_CALL_VAA = IArgoBizMetafield.ITIN_CALL_VAA;
    public static final IMetafieldId CALL_DEST_VAA = IArgoBizMetafield.CALL_DEST_VAA;
    public static final IMetafieldId CALL_POINT_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.CALL_POINT, (IMetafieldId) IArgoRefField.POINT_ID);
    public static final IMetafieldId POINT_PLACE_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.POINT_UN_LOC, (IMetafieldId) IArgoRefField.UNLOC_PLACE_NAME);
    public static final IMetafieldId POINT_UNLOC_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.POINT_UN_LOC, (IMetafieldId) IArgoRefField.UNLOC_ID);
    public static final IMetafieldId CALL_PLACE_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.CALL_POINT, (IMetafieldId)POINT_PLACE_NAME);
    public static final IMetafieldId CALL_UNLOC_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.CALL_POINT, (IMetafieldId)POINT_UNLOC_ID);
    public static final IMetafieldId DEST_POINT_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.DEST_POINT, (IMetafieldId) IArgoRefField.POINT_ID);
    public static final IMetafieldId DEST_PLACE_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.DEST_POINT, (IMetafieldId)POINT_PLACE_NAME);
    public static final IMetafieldId DEST_UNLOC_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoRefField.DEST_POINT, (IMetafieldId)POINT_UNLOC_ID);
    public static final IMetafieldId VEHICLE_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId) IArgoField.CV_CVD, (IMetafieldId) IArgoBizMetafield.CARRIER_VEHICLE_NAME);

}
