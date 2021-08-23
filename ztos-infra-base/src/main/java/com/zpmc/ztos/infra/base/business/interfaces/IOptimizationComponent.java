package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

public interface IOptimizationComponent {
    public void setProblemSolver(IProblemSolver var1);

    public IProblemSolver getProblemSolver();

    @Nullable
    public String getComponentName();
}
