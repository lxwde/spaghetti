package com.zpmc.ztos.infra.base.business.edi;

import com.zpmc.ztos.infra.base.business.interfaces.IArgoField;
import com.zpmc.ztos.infra.base.business.interfaces.IEdiField;
import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
import com.zpmc.ztos.infra.base.business.model.MetafieldIdFactory;
import com.zpmc.ztos.infra.base.common.database.HiberCache;
import org.apache.log4j.Logger;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public class EdiCompoundField implements IEdiField {
    public static final IMetafieldId EDIBATCH_SESSION_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIBATCH_SESSION, (IMetafieldId)EDISESS_NAME);
    public static final IMetafieldId EDIBATCH_CARRIER_VISITT_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIBATCH_CARRIER_VISIT, (IMetafieldId) IArgoField.CV_ID);
    public static final IMetafieldId EDIBATCH_SESSION_MSGMAP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIBATCH_SESSION, (IMetafieldId)EDISESS_MSG_MAP);
    public static final IMetafieldId EDIBATCH_SESSION_IS_AUTO_POSTED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIBATCH_SESSION, (IMetafieldId)EDISESS_IS_AUTO_POSTED);
    public static final IMetafieldId EDIBATCH_SESSION_PARTNER_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIBATCH_SESSION, (IMetafieldId)EDISESS_TRADING_PARTNER);
    public static final IMetafieldId EDIBATCH_SESSION_MSGTYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIBATCH_SESSION_MSGMAP, (IMetafieldId)EDIMAP_EDI_MSG_TYPE);
    public static final IMetafieldId EDIBATCH_SESSION_MSGCLASS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIBATCH_SESSION, (IMetafieldId)EDISESS_MESSAGE_CLASS);
    public static final IMetafieldId EDIBATCH_SESSION_MAP_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIBATCH_SESSION_MSGMAP, (IMetafieldId)EDIMAP_ID);
    public static final IMetafieldId EDIBATCH_SESSION_MSGTYPE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIBATCH_SESSION_MSGTYPE, (IMetafieldId)EDIMSG_ID);
    public static final IMetafieldId EDIINT_PTNR_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIINT_TRADING_PARTNER, (IMetafieldId)EDIPTNR_NAME);
    public static final IMetafieldId EDIINT_PTNR_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIINT_TRADING_PARTNER, (IMetafieldId)EDIPTNR_GKEY);
    public static final IMetafieldId EDIINT_MLBX_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIINT_MAILBOX, (IMetafieldId)EDIMLBX_NAME);
    public static final IMetafieldId EDIBATCH_PTNR_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIBATCH_INTERCHANGE, (IMetafieldId)EDIINT_PTNR_NAME);
    public static final IMetafieldId EDIBATCH_PTNR_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIBATCH_INTERCHANGE, (IMetafieldId)EDIINT_PTNR_GKEY);
    public static final IMetafieldId EDIPROC_SESSION_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIPROC_BATCH, (IMetafieldId)EDIBATCH_SESSION_NAME);
    public static final IMetafieldId EDIBATCH_INTERCHANGE_NUMBER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIBATCH_INTERCHANGE, (IMetafieldId)EDIINT_INTERCHANGE_NBR);
    public static final IMetafieldId EDISEG_INTERCHANGE_NUMBER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDISEG_INTERCHANGE, (IMetafieldId)EDIINT_INTERCHANGE_NBR);
    public static final IMetafieldId EDISEG_INTERCHANGE_PARTNER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDISEG_INTERCHANGE, (IMetafieldId)EDIINT_PTNR_NAME);
    public static final IMetafieldId EDISEG_INTERCHANGE_DIRECTION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDISEG_INTERCHANGE, (IMetafieldId)EDIINT_DIRECTION);
    public static final IMetafieldId EDISEG_INTERCHANGE_DELIMITERS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDISEG_INTERCHANGE, (IMetafieldId)EDIINT_DELIMITERS);
    public static final IMetafieldId EDITRAN_BATCH_MSGCLASS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDITRAN_BATCH, (IMetafieldId)EDIBATCH_SESSION_MSGCLASS);
    public static final IMetafieldId EDITRAN_BATCH_INTERCHANGE_PARTNER_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDITRAN_BATCH, (IMetafieldId)EDIBATCH_PTNR_NAME);
    public static final IMetafieldId EDITRAN_BATCH_DIRECTION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDITRAN_BATCH, (IMetafieldId)EDIBATCH_DIRECTION);
    public static final IMetafieldId EDITRAN_BATCH_INTERCHANGE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDITRAN_BATCH, (IMetafieldId)EDIBATCH_INTERCHANGE);
    public static final IMetafieldId EDITRAN_BATCH_INTERCHANGE_NUMBER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDITRAN_BATCH, (IMetafieldId)EDIBATCH_INTERCHANGE_NUMBER);
    public static final IMetafieldId EDITRAN_BATCH_SESSION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDITRAN_BATCH, (IMetafieldId)EDIBATCH_SESSION);
    public static final IMetafieldId EDITRAN_BATCH_SESSION_IS_AUTOPOST = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDITRAN_BATCH_SESSION, (IMetafieldId)EDISESS_IS_AUTO_POSTED);
    public static final IMetafieldId EDITRAN_BATCH_SESSION_PARTNER_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDITRAN_BATCH, (IMetafieldId)EDIBATCH_SESSION_PARTNER_ID);
    public static final IMetafieldId EDITRANS_MSGTYPE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIMAP_EDI_MSG_TYPE, (IMetafieldId)EDIMSG_ID);
    public static final IMetafieldId EDITRANS_MSGTYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDISESS_MSG_MAP, (IMetafieldId)EDITRANS_MSGTYPE_ID);
    public static final IMetafieldId EDITRANS_MSGMAP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIBATCH_SESSION, (IMetafieldId)EDITRANS_MSGTYPE);
    public static final IMetafieldId EDITRAN_MSGTYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDITRAN_BATCH, (IMetafieldId)EDITRANS_MSGMAP);
    public static final IMetafieldId EDIERR_TRANSACTION_STATUS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIERR_TRANSACTION, (IMetafieldId)EDITRAN_STATUS);
    public static final IMetafieldId EDIERR_BATCH = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIERR_TRANSACTION, (IMetafieldId)EDITRAN_BATCH);
    public static final IMetafieldId EDIERR_PROC_BATCH = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIERR_BATCH_PROCESS, (IMetafieldId)EDIPROC_BATCH);
    public static final IMetafieldId EDIERR_BATCH_GKEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIERR_BATCH, (IMetafieldId)EDIBATCH_GKEY);
    public static final IMetafieldId EDIPROC_BATCH_CREATED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIPROC_BATCH, (IMetafieldId)EDIBATCH_CREATED);
    public static final IMetafieldId EDIERR_BATCH_CREATED = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIERR_BATCH_PROCESS, (IMetafieldId)EDIPROC_BATCH_CREATED);
    public static final IMetafieldId EDIERR_MSG_PROPERTY_KEY = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIERR_MESSAGE, (IMetafieldId)IEdiField.MSG_PROPERTY_KEY);
    public static final IMetafieldId EDIERR_MSG_PARMS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIERR_MESSAGE, (IMetafieldId)IEdiField.MSG_PARMS_STRING);
    public static final IMetafieldId EDIMSGTYPE_MSGCLASS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIMAP_EDI_MSG_TYPE, (IMetafieldId)EDIMSG_CLASS);
    public static final IMetafieldId EDISESS_MSGMAP_MSGTYPE_CLASS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDISESS_MSG_MAP, (IMetafieldId)EDIMSGTYPE_MSGCLASS);
    public static final IMetafieldId EDISESS_BIZU = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDISESS_TRADING_PARTNER, (IMetafieldId)EDIPTNR_BUSINESS_UNIT);
    public static final IMetafieldId EDISESS_MSG_TYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDISESS_MSG_MAP, (IMetafieldId)EDIMAP_EDI_MSG_TYPE);
    public static final IMetafieldId EDIRELMAP_EDI_MSG_TYPE_ID = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIRELMAP_EDI_MSG_TYPE, (IMetafieldId)EDIMSG_ID);
    public static final IMetafieldId EDIRELMAP_EDI_MSG_TYPE_VERSION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIRELMAP_EDI_MSG_TYPE, (IMetafieldId)EDIMSG_VERSION);
    public static final IMetafieldId EDIRELMAP_EDI_MSG_TYPE_RELEASE_NUMBER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIRELMAP_EDI_MSG_TYPE, (IMetafieldId)EDIMSG_RELEASE_NBR);
    public static final IMetafieldId EDIPROC_BATCH_DIRECTION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIPROC_BATCH, (IMetafieldId)EDIBATCH_DIRECTION);
    public static final IMetafieldId EDIERR_PROC_SEQUENCE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIERR_BATCH_PROCESS, (IMetafieldId)EDIPROC_SEQUENCE);
    public static final IMetafieldId EDISESS_PARTNER_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDISESS_TRADING_PARTNER, (IMetafieldId)EDIPTNR_NAME);
    public static final IMetafieldId EDISESS_PARTNER_LIFE_CYCLE_STATE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)IEdiField.EDISESS_TRADING_PARTNER, (IMetafieldId)EDIPTNR_LIFE_CYCLE_STATE);
    public static final IMetafieldId EDIMAP_MESSAGE_CLASS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDIMAP_EDI_MSG_TYPE, (IMetafieldId)EDIMSGTYPE_MSGCLASS);
    public static final IMetafieldId EDISESSMLBX_SESS_PARTNER = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDISESSMLBX_SESSION, (IMetafieldId)EDISESS_TRADING_PARTNER);
    public static final IMetafieldId EDISESSMLBX_SESS_MSGCLASS = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDISESSMLBX_SESSION, (IMetafieldId)EDISESS_MESSAGE_CLASS);
    public static final IMetafieldId EDISESSMLBX_SESS_MSG_MAP_TYPE = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDISESSMLBX_SESSION, (IMetafieldId)EDISESS_MSG_TYPE);
    public static final IMetafieldId EDISESSMLBX_SESS_MSG_MAP = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDISESSMLBX_SESSION, (IMetafieldId)EDISESS_MSG_MAP);
    public static final IMetafieldId EDISESSMLBX_SESS_DIRECTION = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDISESSMLBX_SESSION, (IMetafieldId)EDISESS_DIRECTION);
    public static final IMetafieldId EDISESSFLTR_SESSION_NAME = MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDISESSFLTR_SESSION, (IMetafieldId)EDISESS_NAME);
    private static final char DELIMITTER = '.';
    private static final Logger LOGGER = Logger.getLogger(EdiCompoundField.class);

    @Nullable
    public static IMetafieldId getQualifiedField(IMetafieldId inMfid, String inBaseEntity) {
        String leftNode = inMfid.getQualifiedId();
        int delimitter = leftNode.indexOf(46);
        if (delimitter > 0) {
            leftNode = leftNode.substring(0, delimitter);
        }
        String entityOfField = HiberCache.getEntityNameForField((String)leftNode);
        if ("EdiSession".equals(inBaseEntity)) {
            if ("EdiSession".equals(entityOfField)) {
                return inMfid;
            }
            if ("EdiTradingPartner".equals(entityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDISESS_PARTNER_NAME, (IMetafieldId)inMfid);
            }
            if ("ScopedBizUnit".equals(entityOfField)) {
                return MetafieldIdFactory.getCompoundMetafieldId((IMetafieldId)EDISESS_BIZU, (IMetafieldId)inMfid);
            }
        }
        LOGGER.error((Object)("getQualifiedField: could not map <" + (Object)inMfid + "> of class <" + entityOfField + '>'));
        return inMfid;
    }
}
