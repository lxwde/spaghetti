package com.zpmc.ztos.infra.base.business.interfaces;

import com.zpmc.ztos.infra.base.common.scopes.ScopeCoordinateIdsWsType;
import com.zpmc.ztos.infra.base.common.type.GenericInvokeResponseWsType;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IArgoServicePort extends Remote {
    public GenericInvokeResponseWsType genericInvoke(ScopeCoordinateIdsWsType var1, String var2) throws RemoteException;
}
