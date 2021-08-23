package com.zpmc.ztos.infra.base.business.interfaces;

public interface IExecutableQuery extends IBasicQuery, IBounded, IOrdered{

    public String getQueryEntityName();

    public IEntityId getEntityId();
}
