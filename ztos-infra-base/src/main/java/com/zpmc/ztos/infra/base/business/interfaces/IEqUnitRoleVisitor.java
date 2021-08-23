package com.zpmc.ztos.infra.base.business.interfaces;

public interface IEqUnitRoleVisitor {
    public void visitPrimaryRole();

    public void visitCarriageRole();

    public void visitAccessoryRole();

    public void visitAccessoryOnChsRole();

    public void visitPayloadRole();
}
