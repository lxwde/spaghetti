package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.common.model.DatabaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class UserOptionDO extends DatabaseEntity implements Serializable {
    private Long useroptGKey;
    private Boolean userOptAutoRefresh;
    private Long userOptFetchRowLimit;
    private List useroptWndOpenNodes;
    private List useroptWndFavoriteNodes;
    private Map useroptWndViewBounds;

    @Override
    public Serializable getPrimaryKey() {
        return this.getUseroptGKey();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        Long id = this.getUseroptGKey();
        if (id == null) {
            return false;
        }
        if (!(other instanceof UserOptionDO)) {
            return false;
        }
        UserOptionDO that = (UserOptionDO)other;
        return ((Object)id).equals(that.getUseroptGKey());
    }

    public int hashCode() {
        Long id = this.getUseroptGKey();
        return id == null ? System.identityHashCode(this) : ((Object)id).hashCode();
    }

    public Long getUseroptGKey() {
        return this.useroptGKey;
    }

    public void setUseroptGKey(Long useroptGKey) {
        this.useroptGKey = useroptGKey;
    }

    public Boolean getUserOptAutoRefresh() {
        return this.userOptAutoRefresh;
    }

    public void setUserOptAutoRefresh(Boolean userOptAutoRefresh) {
        this.userOptAutoRefresh = userOptAutoRefresh;
    }

    public Long getUserOptFetchRowLimit() {
        return this.userOptFetchRowLimit;
    }

    public void setUserOptFetchRowLimit(Long userOptFetchRowLimit) {
        this.userOptFetchRowLimit = userOptFetchRowLimit;
    }

    public List getUseroptWndOpenNodes() {
        return this.useroptWndOpenNodes;
    }

    public void setUseroptWndOpenNodes(List useroptWndOpenNodes) {
        this.useroptWndOpenNodes = useroptWndOpenNodes;
    }

    public List getUseroptWndFavoriteNodes() {
        return this.useroptWndFavoriteNodes;
    }

    public void setUseroptWndFavoriteNodes(List useroptWndFavoriteNodes) {
        this.useroptWndFavoriteNodes = useroptWndFavoriteNodes;
    }

    public Map getUseroptWndViewBounds() {
        return this.useroptWndViewBounds;
    }

    public void setUseroptWndViewBounds(Map useroptWndViewBounds) {
        this.useroptWndViewBounds = useroptWndViewBounds;
    }

}
