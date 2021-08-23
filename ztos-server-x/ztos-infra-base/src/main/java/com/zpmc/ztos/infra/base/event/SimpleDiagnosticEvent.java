package com.zpmc.ztos.infra.base.event;

public class SimpleDiagnosticEvent extends EventBase{
    private String cause;
    private String solution;

    public SimpleDiagnosticEvent(){};

    public SimpleDiagnosticEvent(String cause, String solution) {
        this.cause = cause;
        this.solution = solution;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    @Override
    public String toString() {
        return "SimpleDiagnosticEvent{" +
                "cause='" + cause + '\'' +
                ", solution='" + solution + '\'' +
                "} " + super.toString();
    }
}
