package com.zpmc.ztos.infra.base.business.interfaces;

public interface IHibernateDeleteApi {
    public void deleteByDomainQuery(IDomainQuery var1);

    public void delete(Object var1, boolean var2);

    public void delete(Object var1);

    @Deprecated
    public void batchDelete(String var1, Object[] var2);

}
