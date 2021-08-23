package com.zpmc.ztos.infra.base.business.dataobject;//package com.zpmc.ztos.infra.base.business.dataobject;
//
//import com.zpmc.ztos.infra.base.business.interfaces.IDefaultCreateValuesContext;
//import com.zpmc.ztos.infra.base.business.interfaces.IMetafieldId;
//import com.zpmc.ztos.infra.base.business.model.MetafieldIdList;
//import com.zpmc.ztos.infra.base.common.exceptions.BizViolation;
//import com.zpmc.ztos.infra.base.common.model.FieldChanges;
//import com.zpmc.ztos.infra.base.common.model.ValueObject;
//import lombok.Data;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Table;
//import java.io.Serializable;
//import java.util.Date;
//import java.util.Set;
//
///**
// * 船
// *
// * @Author Java002
// * @Date 2021/6/8 9:03
// */
//@Data
//@Entity
//@Table(name = "vessel")
//public  class VesselDOForTest extends CommonDO {
//
//
//    /**
//     * 船体的唯一身份标志
//     */
//    @Column(name = "id")//数据库字段名
//    private String id;
//
//    /**
//     * 船名称
//     */
//    @Column(name = "name")//数据库字段名
//    private String name;
//
//    /**
//     * 船型,外键
//     */
//    @Column(name = "vessel_class_key")//数据库字段名
//    private int vesselClassKey;
//
//    /**
//     * 船公司（所有者）
//     */
//    @Column(name = "owner")//数据库字段名
//    private String owner;
//
//    /**
//     * 船长
//     */
//    @Column(name = "captain")//数据库字段名
//    private String captain;
//
//    /**
//     * 无线呼叫标志，被发送站唯一指定
//     */
//    @Column(name = "radio_call_sign")//数据库字段名
//    private String radioCallSign;
//
//    /**
//     * 船的国别
//     */
//    @Column(name = "country_code")//数据库字段名
//    private String countryCode;
//
//    /**
//     * 船上使用的测量系统，测量单位
//     */
//    @Column(name = "unit_system")//数据库字段名
//    private String unitSystem;
//
//    /**
//     * 船上使用的温度测量单位，摄氏或华氏
//     */
//    @Column(name = "temperature_unit")//数据库字段名
//    private String temperatureUnit;
//
//    /**
//     * 注释
//     */
//    @Column(name = "notes")//数据库字段名
//    private String notes;
//
//
//    /**
//     * 设置主键的值
//     *
//     * @param var
//     */
//    @Override
//    public void setPrimaryKey(Serializable var) {
//        setGkey((Integer) var);
//    }
//
//    @Override
//    public IMetafieldId getPrimaryKeyMetaFieldId() {
//        return null;
//    }
//
//    @Override
//    public String getHumanReadableKey() {
//        return null;
//    }
//
//    @Override
//    public Object getField(IMetafieldId var1) {
//        return null;
//    }
//
//    @Override
//    public Object[] extractFieldValueArray(MetafieldIdList var1, boolean var2) {
//        return new Object[0];
//    }
//
//    @Override
//    public ValueObject getValueObject(MetafieldIdList var1) {
//        return null;
//    }
//
//    @Override
//    public ValueObject getValueObject() {
//        return null;
//    }
//
//    @Override
//    public ValueObject getDefaultCreateValues(IDefaultCreateValuesContext var1) {
//        return null;
//    }
//
//    @Override
//    public void setFieldValue(IMetafieldId var1, Object var2) {
//
//    }
//
//    @Override
//    public void applyFieldChanges(FieldChanges var1) {
//
//    }
//
//    @Override
//    public String getFieldString(IMetafieldId var1) {
//        return null;
//    }
//
//    @Override
//    public Long getFieldLong(IMetafieldId var1) {
//        return null;
//    }
//
//    @Override
//    public Date getFieldDate(IMetafieldId var1) {
//        return null;
//    }
//
//    @Override
//    public void setSelfAndFieldChange(IMetafieldId var1, Object var2, FieldChanges var3) {
//
//    }
//
//    @Override
//    public BizViolation validateChanges(FieldChanges var1) {
//        return null;
//    }
//
//    @Override
//    public BizViolation validateDeletion() {
//        return null;
//    }
//
//    @Override
//    public void populate(Set var1, FieldChanges var2) {
//
//    }
//
//    @Override
//    public void preProcessInsert(FieldChanges var1) {
//
//    }
//
//    @Override
//    public void preProcessUpdate(FieldChanges var1, FieldChanges var2) {
//
//    }
//
//    @Override
//    public void preProcessDelete(FieldChanges var1) {
//
//    }
//
//    @Override
//    public void preProcessInsertOrUpdate(FieldChanges var1) {
//
//    }
//
//    /**
//     * 获得主键的值
//     *
//     * @return
//     */
//    @Override
//    public Serializable getPrimaryKey() {
//        return null;
//    }
//
//
//    public VesselDOForTest() {
//    }
//
//    public VesselDOForTest(Integer gkey, String id, String name, int vesselClassKey, String owner, String captain, String radioCallSign, String countryCode, String unitSystem, String temperatureUnit, String notes, Date created, String creator, Date changed, String changer) {
//        super.setGkey(gkey);
//        super.setChanged(changed);
//        super.setChanger(changer);
//        super.setCreated(created);
//        super.setCreator(creator);
//        this.id = id;
//        this.name = name;
//        this.vesselClassKey = vesselClassKey;
//        this.owner = owner;
//        this.captain = captain;
//        this.radioCallSign = radioCallSign;
//        this.countryCode = countryCode;
//        this.unitSystem = unitSystem;
//        this.temperatureUnit = temperatureUnit;
//        this.notes = notes;
//    }
//
//    @Override
//    public String getPrimaryKeyFieldId() {
//        return null;
//    }
//
//    @Override
//    public ValueObject getValueObject(String[] var1) {
//        return null;
//    }
//
//    @Override
//    public Object getFieldValue(String var1) {
//        return null;
//    }
//
//    @Override
//    public void setFieldValue(String var1, Object var2) {
//
//    }
//
//    @Override
//    public String getFieldString(String var1) {
//        return null;
//    }
//
//    @Override
//    public Long getFieldLong(String var1) {
//        return null;
//    }
//
//    @Override
//    public Date getFieldDate(String var1) {
//        return null;
//    }
//
//    @Override
//    public void setSelfAndFieldChange(String var1, Object var2, FieldChanges var3) {
//
//    }
//
//    @Override
//    public String getEntityName() {
//        return null;
//    }
//
//    @Override
//    public Object getFieldValue(IMetafieldId var1) {
//        return null;
//    }
//}
