package com.zpmc.ztos.infra.base.business.enums.argo;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.common.enums.AtomizedEnum;
import com.zpmc.ztos.infra.base.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AbstractCheInstructionTypeEnum extends AtomizedEnum {
    private List<String> _categories = new ArrayList<String>();
    protected static final String CATEGORY_ASC = "ASC";
    protected static final String CATEGORY_AGV = "AGV";

    protected AbstractCheInstructionTypeEnum(String inKey, String inDescriptionResKey, String inCodeResKey, String inBackgroundColor, String inForegroundColor, String inIconIdPath, String inCheInstructionTypeCategories) {
        super(inKey, inDescriptionResKey, inCodeResKey, inBackgroundColor, inForegroundColor, inIconIdPath);
        String[] categories = StringUtils.split((String)inCheInstructionTypeCategories, (char)',');
        if (categories.length > 0) {
            this._categories.addAll(Arrays.asList(categories));
        }
    }

    @NotNull
    public List<String> getCategories() {
        return Collections.unmodifiableList(this._categories);
    }

    public boolean isAscInstructionType() {
        return this.isCategory(CATEGORY_ASC);
    }

    public boolean isAgvInstructionType() {
        return this.isCategory(CATEGORY_AGV);
    }

    public boolean isCategory(@NotNull String inCategory) {
        return this._categories.contains(inCategory);
    }

    public boolean isCategory(@NotNull CheInstructionTypeEnum inOtherInstructionType) {
        for (String category : inOtherInstructionType.getCategories()) {
            if (!this._categories.contains(category)) continue;
            return true;
        }
        return false;
    }

}
