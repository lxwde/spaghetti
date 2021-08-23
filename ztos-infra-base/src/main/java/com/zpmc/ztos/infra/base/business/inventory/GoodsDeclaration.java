package com.zpmc.ztos.infra.base.business.inventory;

import com.zpmc.ztos.infra.base.business.dataobject.GoodsDeclarationDO;
import com.zpmc.ztos.infra.base.business.enums.argo.DataSourceEnum;
import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.business.model.GoodsBase;
import com.zpmc.ztos.infra.base.business.predicate.PredicateFactory;
import com.zpmc.ztos.infra.base.common.database.HibernateApi;
import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
import com.zpmc.ztos.infra.base.common.helps.ContextHelper;
import com.zpmc.ztos.infra.base.common.model.FieldChanges;
import com.zpmc.ztos.infra.base.common.model.Roastery;
import com.zpmc.ztos.infra.base.utils.QueryUtils;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class GoodsDeclaration extends GoodsDeclarationDO {
    public Long getGdsdocTableKey() {
        return this.getGdsdeclGkey();
    }

    @Nullable
    public IValueHolder[] getCustomsDocumentVao() {
        Set customsDocumentSet = this.getGdsdeclDocuments();
        if (customsDocumentSet == null || customsDocumentSet.isEmpty()) {
            return null;
        }
        IValueHolder[] customsDocVao = new IValueHolder[customsDocumentSet.size()];
        Iterator custDocIter = customsDocumentSet.iterator();
        int i = 0;
//        while (custDocIter.hasNext()) {
//            GoodsDocuments goodsDocument = (GoodsDocuments)custDocIter.next();
//            ValueObject vao = goodsDocument.getValueObject();
//            vao.setFieldValue(IInventoryField.GDSDOC_ID, (Object)goodsDocument.getGdsdocId());
//            vao.setFieldValue(IInventoryField.GDSDOC_TYPE, (Object)goodsDocument.getGdsdocType());
//            customsDocVao[i] = vao;
//            ++i;
//        }
        return customsDocVao;
    }

    public BizViolation validateChanges(FieldChanges inChanges) {
        Set gdsDeclarationSet;
        BizViolation bv = super.validateChanges(inChanges);
        if (this.getGdsdeclGoods() != null && (gdsDeclarationSet = this.getGdsdeclGoods().getGdsDeclaration()) != null) {
//            long maxDecAllowed = InventoryConfig.MAX_DECLARATIONS.getValue(ContextHelper.getThreadUserContext());
//            if ((long)gdsDeclarationSet.size() >= maxDecAllowed) {
//                bv = BizViolation.create((IPropertyKey) IInventoryPropertyKeys.INV_GOODS_DECLARATION_MAX_EXCEEDED, (Throwable)null, (BizViolation)bv, null, (Object[])new Object[]{maxDecAllowed});
//            }
        }
        return bv;
    }

    public BizViolation validateDeletion() {
        BizViolation bv = super.validateDeletion();
        DataSourceEnum src = ContextHelper.getThreadDataSource();
        if (!(src != null && DataSourceEnum.PURGE_JOB.equals((Object)src) || this.getGdsdeclDocuments().isEmpty())) {
            bv = BizViolation.create((IPropertyKey)IInventoryPropertyKeys.INV_CANNOT_DELETE_DECLARATION, (Throwable)null, (BizViolation)bv, null, null);
        }
        return bv;
    }

    public static GoodsDeclaration createGoodsDeclaration(GoodsBase inGoodsBase, long inSeq) {
        GoodsDeclaration gdsDec = new GoodsDeclaration();
        gdsDec.setGdsdeclGoods(inGoodsBase);
        gdsDec.setGdsdeclSeq(inSeq);
        HashSet<GoodsDeclaration> gdsDecSet = (HashSet<GoodsDeclaration>) inGoodsBase.getGdsDeclaration();
        if (gdsDecSet == null) {
            gdsDecSet = new HashSet<GoodsDeclaration>();
            inGoodsBase.setGdsDeclaration(gdsDecSet);
        }
        gdsDecSet.add(gdsDec);
        HibernateApi.getInstance().save((Object)gdsDec);
        return gdsDec;
    }

    public static GoodsDeclaration findOrCreateGoodsDeclaration(GoodsBase inGoodsBase, long inSeq) {
        GoodsDeclaration gdsdec = GoodsDeclaration.findGoodsDeclaration(inGoodsBase, inSeq);
        if (gdsdec == null) {
            gdsdec = GoodsDeclaration.createGoodsDeclaration(inGoodsBase, inSeq);
        }
        return gdsdec;
    }

    @Nullable
    public static GoodsDeclaration findGoodsDeclaration(GoodsBase inGoodsBase, long inSeq) {
        IDomainQuery dq = QueryUtils.createDomainQuery((String)"GoodsDeclaration").addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.GDSDECL_GOODS, (Object)inGoodsBase.getGdsGkey())).addDqPredicate(PredicateFactory.eq((IMetafieldId)IInventoryField.GDSDECL_SEQ, (Object)inSeq));
        List declarations = Roastery.getHibernateApi().findEntitiesByDomainQuery(dq);
        if (declarations != null && !declarations.isEmpty()) {
            return (GoodsDeclaration)declarations.get(0);
        }
        return null;
    }

//    public GoodsDocuments addDocument(String inId, String inType) {
//        GoodsDocuments doc = new GoodsDocuments();
//        doc.setGdsdocGoodsDeclaration(this);
//        doc.setGdsdocId(inId);
//        doc.setGdsdocType(inType);
//        HashSet<GoodsDocuments> gdsDocSet = this.getGdsdeclDocuments();
//        if (gdsDocSet == null) {
//            gdsDocSet = new HashSet<GoodsDocuments>();
//            this.setGdsdeclDocuments(gdsDocSet);
//        }
//        gdsDocSet.add(doc);
//        HibernateApi.getInstance().save((Object)doc);
//        return doc;
//    }

    public Class getArchiveClass() {
 //       return ArchiveGoodsDeclaration.class;
        return null;
    }

    public boolean doArchive() {
        return true;
    }

}
