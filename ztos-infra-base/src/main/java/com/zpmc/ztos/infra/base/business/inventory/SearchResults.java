package com.zpmc.ztos.infra.base.business.inventory;

import java.io.Serializable;
import java.util.Set;

public class SearchResults {
    private int _foundCount;
    private Serializable _foundPrimaryKey;
    private Set _foundMultiplePrimaryKeys;

    private SearchResults(int inFoundCount, Serializable inFoundPrimaryKey) {
        this._foundCount = inFoundCount;
        this._foundPrimaryKey = inFoundPrimaryKey;
    }

    private SearchResults(int inFoundCount, Set inFoundPrimaryKeys) {
        this._foundCount = inFoundCount;
        this._foundMultiplePrimaryKeys = inFoundPrimaryKeys;
    }

    public int getFoundCount() {
        return this._foundCount;
    }

    public Serializable getFoundPrimaryKey() {
        return this._foundPrimaryKey;
    }

    public Set getFoundMultiplePrimaryKeys() {
        return this._foundMultiplePrimaryKeys;
    }

    public static SearchResults create(int inFoundCount, Serializable inFoundPrimaryKey) {
        return new SearchResults(inFoundCount, inFoundPrimaryKey);
    }

    public static SearchResults create(int inFoundCount, Set inFoundPrimaryKeys) {
        return new SearchResults(inFoundCount, inFoundPrimaryKeys);
    }
}
