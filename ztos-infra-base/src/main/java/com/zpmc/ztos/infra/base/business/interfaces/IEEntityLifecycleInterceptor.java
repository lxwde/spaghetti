package com.zpmc.ztos.infra.base.business.interfaces;

public interface IEEntityLifecycleInterceptor {
    public void onCreate(IEEntityView var1, IEFieldChangesView var2, IEFieldChanges var3);

    public void onUpdate(IEEntityView var1, IEFieldChangesView var2, IEFieldChanges var3);

    public void validateChanges(IEEntityView var1, IEFieldChangesView var2);

    public void validateDelete(IEEntityView var1);

    public void preDelete(IEntity var1);
}
