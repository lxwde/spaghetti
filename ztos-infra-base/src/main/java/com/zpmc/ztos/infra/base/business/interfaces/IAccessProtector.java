package com.zpmc.ztos.infra.base.business.interfaces;

public interface IAccessProtector {

    public void assertReadAccessGranted();
//
    public void assertWriteAccessGranted();
//
    public void assertNoReadAccessGranted();
//
    public void assertNoWriteAccessGranted();

}
