package com.zpmc.ztos.infra.base.business.interfaces;

public interface ISolveStatusEvent extends IProblemSolverEvent {
    public IMessageCollector getMessages();

    public SolveStatusCode getSolveStatusCode();

    public ISolveCompletionEvent getCompletionEvent();

    public boolean isSuccess();

    public boolean isFailure();

    public static enum SolveStatusCode {
        SUCCESS,
        FAILURE;

    }
}
