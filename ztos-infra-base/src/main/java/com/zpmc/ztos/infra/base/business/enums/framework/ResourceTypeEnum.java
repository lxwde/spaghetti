package com.zpmc.ztos.infra.base.business.enums.framework;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ResourceTypeEnum extends AbstractResourceTypeEnum {

    public static final ResourceTypeEnum DEFAULT = new ResourceTypeEnum("DEFAULT", "atom.ResourceTypeEnum.DEFAULT.description", "atom.ResourceTypeEnum.DEFAULT.code", "", "", "", null, 80, "", "");
    public static final ResourceTypeEnum ENTITY = new ResourceTypeEnum("ENTITY", "atom.ResourceTypeEnum.ENTITY.description", "atom.ResourceTypeEnum.ENTITY.code", "", "", "", null, 0, "entity.", "");
    public static final ResourceTypeEnum ENTITY_NAME = new ResourceTypeEnum("ENTNAME", "atom.ResourceTypeEnum.ENTITY_NAME.description", "atom.ResourceTypeEnum.ENTITY_NAME.code", "", "", "", ENTITY, 20, "entity.", ".name");
    public static final ResourceTypeEnum ENTITY_PLURAL_NAME = new ResourceTypeEnum("ENTPANME", "atom.ResourceTypeEnum.ENTITY_PLURAL_NAME.description", "atom.ResourceTypeEnum.ENTITY_PLURAL_NAME.code", "", "", "", ENTITY, 20, "entity.", ".name.plural");
    public static final ResourceTypeEnum METAFIELD = new ResourceTypeEnum("MFD", "atom.ResourceTypeEnum.METAFIELD.description", "atom.ResourceTypeEnum.METAFIELD.code", "", "", "", null, 5, "fields.", "");
    public static final ResourceTypeEnum METAFIELD_LABEL_LONG = new ResourceTypeEnum("MFD_LONG", "atom.ResourceTypeEnum.METAFIELD_LABEL_LONG.description", "atom.ResourceTypeEnum.METAFIELD_LABEL_LONG.code", "", "", "", METAFIELD, 30, "fields.", ".label-long");
    public static final ResourceTypeEnum METAFIELD_LABEL_SHORT = new ResourceTypeEnum("MFD_SHORT", "atom.ResourceTypeEnum.METAFIELD_LABEL_SHORT.description", "atom.ResourceTypeEnum.METAFIELD_LABEL_SHORT.code", "", "", "", METAFIELD, 10, "fields.", ".label-short");
    public static final ResourceTypeEnum METAFIELD_LABEL_HELP = new ResourceTypeEnum("MFD_HELP", "atom.ResourceTypeEnum.METAFIELD_LABEL_HELP.description", "atom.ResourceTypeEnum.METAFIELD_LABEL_HELP.code", "", "", "", METAFIELD, 10, "fields.", ".help");
    public static final ResourceTypeEnum METAFIELD_LABEL_DESC = new ResourceTypeEnum("MFD_DESC", "atom.ResourceTypeEnum.METAFIELD_LABEL_DESC.description", "atom.ResourceTypeEnum.METAFIELD_LABEL_DESC.code", "", "", "", METAFIELD, 10, "fields.", ".description");
    public static final ResourceTypeEnum GROUP_LABEL = new ResourceTypeEnum("GROUP_LABEL", "atom.ResourceTypeEnum.GROUP_LABEL.description", "atom.ResourceTypeEnum.GROUP_LABEL.code", "", "", "", null, 40, "FIELD_GROUP_", "");
    public static final ResourceTypeEnum ENUM = new ResourceTypeEnum("ENUM", "atom.ResourceTypeEnum.ENUM.description", "atom.ResourceTypeEnum.ENUM.code", "", "", "", null, 20, "atom.", "");
    public static final ResourceTypeEnum CONFIG = new ResourceTypeEnum("CONFIG", "atom.ResourceTypeEnum.CONFIG.description", "atom.ResourceTypeEnum.CONFIG.code", "", "", "", null, 20, "config.", "");
    public static final ResourceTypeEnum FORM = new ResourceTypeEnum("FORM", "atom.ResourceTypeEnum.FORM.description", "atom.ResourceTypeEnum.FORM.code", "", "", "", null, 20, "variform.", "");
    public static final ResourceTypeEnum FORM_TITLE = new ResourceTypeEnum("FORM_TITLE", "atom.ResourceTypeEnum.FORM_TITLE.description", "atom.ResourceTypeEnum.FORM_TITLE.code", "", "", "", FORM, 20, "variform.", ".title");
    public static final ResourceTypeEnum NODE = new ResourceTypeEnum("NODE", "atom.ResourceTypeEnum.NODE.description", "atom.ResourceTypeEnum.NODE.code", "", "", "", null, 30, "node.", "");
    public static final ResourceTypeEnum NODE_TEXT = new ResourceTypeEnum("NODE_TEXT", "atom.ResourceTypeEnum.NODE_TEXT.description", "atom.ResourceTypeEnum.NODE_TEXT.code", "", "", "", NODE, 15, "node.", ".text");
    public static final ResourceTypeEnum MENU = new ResourceTypeEnum("MENU", "atom.ResourceTypeEnum.MENU.description", "atom.ResourceTypeEnum.MENU.code", "", "", "", null, 60, "menu.", "");
    public static final ResourceTypeEnum MENU_TEXT = new ResourceTypeEnum("MENU_TEXT", "atom.ResourceTypeEnum.MENU_TEXT.description", "atom.ResourceTypeEnum.MENU_TEXT.code", "", "", "", MENU, 25, "menu.", ".text");
    public static final ResourceTypeEnum LABELS = new ResourceTypeEnum("LABELS", "atom.ResourceTypeEnum.LABELS.description", "atom.ResourceTypeEnum.LABELS.code", "", "", "", null, 255, "LABEL_", "");

    public static ResourceTypeEnum getEnum(String inName) {
        return (ResourceTypeEnum) ResourceTypeEnum.getEnum(ResourceTypeEnum.class, (String)inName);
    }

    public static Map getEnumMap() {
        return ResourceTypeEnum.getEnumMap(ResourceTypeEnum.class);
    }

    public static List getEnumList() {
        return ResourceTypeEnum.getEnumList(ResourceTypeEnum.class);
    }

    public static Collection getList() {
        return ResourceTypeEnum.getEnumList(ResourceTypeEnum.class);
    }

    public static Iterator iterator() {
        return ResourceTypeEnum.iterator(ResourceTypeEnum.class);
    }

    protected ResourceTypeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, AbstractResourceTypeEnum inParentEnum, int inMaxLength, String inPropertyPrefix, String inPropertySuffix) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath, inParentEnum, inMaxLength, inPropertyPrefix, inPropertySuffix);
    }

    @Override
    public String getMappingClassName() {
        return "com.navis.framework.persistence.atoms.UserTypeResourceTypeEnum";
    }

}
