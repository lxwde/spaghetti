package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.model.ProblemSolution;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.List;

public interface IProblemSolutionFinder {

    public static final String BEAN_ID = "problemSolutionFinder";

    @Nullable
    public List<ProblemSolution> findActiveProblemSolution(@NotNull Serializable var1, int var2);

    @Nullable
    public ProblemSolution findActiveProblemSolutionById(@NotNull String var1, UserContext var2);

    public List<ProblemSolution> findSystemSeededProblemSolutions(@NotNull UserContext var1, @NotNull IMessageCollector var2, @NotNull Serializable var3, int var4);

    public ProblemSolution findSystemSeededProblemSolution(@NotNull UserContext var1, @NotNull IMessageCollector var2, @Nullable Serializable var3, int var4);

    public Serializable findProblemTypeGkey(String var1);

    public List<ProblemSolution> findProblemSolutions(@NotNull Serializable var1, int var2);

    public List<ProblemSolution> findEffectiveScopedProblemSolutions(@NotNull UserContext var1, @NotNull Serializable var2);

}
