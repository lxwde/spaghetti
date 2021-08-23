package com.zpmc.ztos.infra.base.common.events;

import com.zpmc.ztos.infra.base.business.interfaces.IEvent;
import com.zpmc.ztos.infra.base.business.interfaces.IProblemSolverEvent;
import org.apache.log4j.Logger;

import java.util.Date;

public abstract class ProblemSolverEventListener <T extends IProblemSolverEvent>
        extends AbstractEventListener{

    private static final Logger LOGGER = Logger.getLogger(ProblemSolverEventListener.class);

    public ProblemSolverEventListener(boolean inIsAsynchronous) {
        super(inIsAsynchronous);
    }

    @Override
    protected final Class getBaseEventListenerClass() {
        return ProblemSolverEventListener.class;
    }

    @Override
    public void onEvent(IEvent inEvent) {
        this.onEvent((T)((IProblemSolverEvent)inEvent));
    }

    public abstract void onEvent(T var1);

    protected void finalize() throws Throwable {
        super.finalize();
        LOGGER.info((Object)("Finalized at: " + new Date()));
    }
}
