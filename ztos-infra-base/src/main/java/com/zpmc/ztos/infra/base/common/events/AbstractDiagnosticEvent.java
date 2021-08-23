package com.zpmc.ztos.infra.base.common.events;

//import com.sun.istack.NotNull;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;//import com.sun.istack.internal.NotNull;
import com.zpmc.ztos.infra.base.business.interfaces.IDiagnosticEvent;
import com.zpmc.ztos.infra.base.common.exceptions.BizFailure;
import com.zpmc.ztos.infra.base.common.utils.DateUtil;
import com.zpmc.ztos.infra.base.utils.CarinaUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractDiagnosticEvent implements IDiagnosticEvent {
    private int _sequence;
    private String _description;
    Serializable _requestId;
    private String _inEventType;
    private Long _duration;
    private Date _eventTime = new Date();
    private List<String> _messages = new ArrayList<String>();
    IDiagnosticEvent _parent;
    private static final Logger LOGGER = Logger.getLogger(AbstractDiagnosticEvent.class);

    public AbstractDiagnosticEvent(String inInEventType, Date inEventTime) {
        this._inEventType = inInEventType;
        this._eventTime = inEventTime;
    }

    @Override
    public String getEventType() {
        return this._inEventType;
    }

    @Override
    public Date getEventStartTime() {
        return this._eventTime;
    }

    @Override
    public Long getDuration() {
        return this._duration;
    }

    @Override
    public void setDuration(Long inDuration) {
        this._duration = inDuration;
    }

    @Override
    public void endDuration() {
        this._duration = Long.valueOf(System.currentTimeMillis()) - this._eventTime.getTime();
    }

    @Override
    public void appendMessage(String inString) {
        this._messages.add(inString);
    }

    @Override
    public void appendStackTrace(String inCaller) {
        try {
            String stack = CarinaUtils.getCompactStackTrace(BizFailure.create("Failure created just to get diagnostic stacktrace"));
            int index = StringUtils.lastIndexOf((String)stack, (String)inCaller);
            if (index > 1) {
                int lastLineIndex = StringUtils.indexOf((String)stack, (String)"\n", (int)index);
                if (lastLineIndex > 1) {
                    this.appendMessage("Call stack: " + stack.substring(lastLineIndex));
                } else {
                    this.appendMessage("Call stack: " + stack);
                }
            } else {
                this.appendMessage("Call stack: " + stack);
            }
        }
        catch (Throwable t) {
            LOGGER.error((Object)("Trouble getting method name for diagnostics because " + t));
        }
    }

    @Override
    public List<String> getMessages() {
        return this._messages;
    }

    @Override
    public int getEventSequence() {
        return this._sequence;
    }

    @Override
    public void setEventSequence(int inSequence) {
        this._sequence = inSequence;
    }

    @Override
    public String getDetailedDiagnostics() {
        if (this._messages.size() > 0) {
            return "\n" + org.springframework.util.StringUtils.collectionToDelimitedString(this._messages, (String)"\n", (String)" -", (String)"");
        }
        return "";
    }

    @Override
    public String getDescription() {
        return this._description;
    }

    @Override
    public void setDescription(String inDescription) {
        this._description = inDescription;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(this._inEventType + " At " + DateUtil.getMillisecondFormattedDate(this._eventTime));
        if (this._duration != null) {
            buf.append(" taking " + this._duration + "ms");
        }
        if (StringUtils.isNotEmpty((String)this._description)) {
            buf.append(" " + this._description);
        }
        return buf.toString();
    }

    @Override
    public IDiagnosticEvent getParent() {
        return this._parent;
    }

    @Override
    public void setParent(IDiagnosticEvent inParent) {
        this._parent = inParent;
    }

    public int compareTo(@NotNull Object inEvent) {
        if (inEvent == this) {
            return 0;
        }
        if (inEvent instanceof IDiagnosticEvent && inEvent != null && ((IDiagnosticEvent)inEvent).getEventSequence() < this._sequence) {
            return 1;
        }
        return -1;
    }
}
