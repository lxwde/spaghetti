package com.zpmc.ztos.infra.base.business.interfaces;

import java.util.List;

public interface IMultiEntityImportResponse extends IEntityImportResponse {
    public List<IEntityImportResponse> getResponses();
}
