package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.IDiagnosticEvent;
import com.zpmc.ztos.infra.base.business.interfaces.IDiagnosticRequestManager;
import com.zpmc.ztos.infra.base.common.events.SimpleDiagnosticEvent;
import com.zpmc.ztos.infra.base.common.utils.DiagnosticUtils;
import com.zpmc.ztos.infra.base.common.utils.LogUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.text.NumberFormat;

public class CodeTimer {
    private boolean _allowRecording;
    private IDiagnosticEvent _event;
    private final boolean _alwaysPrintMillis;
    private long _startMillis;
    private long _lastReportMillis;
    private final long _thresholdMillis;
    private final Logger _logger;
    private final Level _level;
    private static final NumberFormat SECONDS_FORMAT = CodeTimer.initializeNumberFormat();

    public CodeTimer(Logger inLogger) {
        this(inLogger, Level.INFO, 0L, false);
    }

    public CodeTimer(Logger inLogger, Level inLevel) {
        this(inLogger, inLevel, 0L, false);
    }

    public CodeTimer(Logger inLogger, Level inLevel, long inThresholdMillis) {
        this(inLogger, inLevel, inThresholdMillis, false);
    }

    public CodeTimer(Logger inLogger, Level inLevel, long inThresholdMillis, boolean inAlwaysPrintMillis) {
        this._alwaysPrintMillis = inAlwaysPrintMillis;
        this._logger = inLogger;
        this._level = inLevel;
        this._lastReportMillis = this._startMillis = System.currentTimeMillis();
        this._thresholdMillis = inThresholdMillis;
    }

    public void report(String inTaskDescription) {
        this.report(this._level, inTaskDescription);
    }

    public void report(Level inLevel, String inTaskDescription) {
        this.report(inLevel, inTaskDescription, false);
    }

    public void report(Level inLevel, String inTaskDescription, boolean inForceToInfo) {
        long nowMillis;
        long elapsedSinceLastReportMillis;
        if ((LogUtils.isLoggerEnabledFor(this._logger, (Priority)inLevel) || this.shouldRecord() || inForceToInfo) && ((elapsedSinceLastReportMillis = (nowMillis = System.currentTimeMillis()) - this._lastReportMillis) > this._thresholdMillis || this.shouldRecord())) {
            StringBuilder msg = new StringBuilder(inTaskDescription);
            msg.append(": ");
            msg.append(this.formatTimeDiff(elapsedSinceLastReportMillis));
            if (this._lastReportMillis != this._startMillis) {
                msg.append(". Total: ");
                msg.append(this.formatTimeDiff(nowMillis - this._startMillis));
            }
            if (inForceToInfo) {
                LogUtils.forceLogAtInfo(this._logger, msg.toString());
            } else {
                this._logger.log((Priority)inLevel, (Object)msg.toString());
            }
            this.recordDiagnostic(msg.toString());
            this._lastReportMillis = nowMillis;
        }
    }

    public void reportInterval(String inTaskDescription) {
        this.reportInterval(inTaskDescription, false);
    }

    public void reportInterval(String inTaskDescription, boolean inResetStart) {
        long elapsedSinceLastReportMillis;
        long nowMillis = System.currentTimeMillis();
        if ((LogUtils.isLoggerEnabledFor(this._logger, (Priority)this._level) || this.shouldRecord()) && ((elapsedSinceLastReportMillis = nowMillis - this._lastReportMillis) > this._thresholdMillis || this.shouldRecord())) {
            StringBuilder msg = new StringBuilder();
            msg.append(inTaskDescription);
            msg.append(": ");
            msg.append(this.formatTimeDiff(elapsedSinceLastReportMillis));
            this._logger.log((Priority)this._level, (Object)msg.toString());
            this.recordDiagnostic(msg.toString());
        }
        this._lastReportMillis = nowMillis;
        if (inResetStart) {
            this._startMillis = nowMillis;
        }
    }

    public void reportTotalInterval(String inTaskDescription) {
        long elapsedTotalMillis;
        long nowMillis = System.currentTimeMillis();
        if ((LogUtils.isLoggerEnabledFor(this._logger, (Priority)this._level) || this.shouldRecord()) && ((elapsedTotalMillis = nowMillis - this._startMillis) > this._thresholdMillis || this.shouldRecord())) {
            StringBuilder msg = new StringBuilder();
            msg.append(inTaskDescription);
            msg.append(": ");
            msg.append(this.formatTimeDiff(elapsedTotalMillis));
            this._logger.log((Priority)this._level, (Object)msg.toString());
            this.recordDiagnostic(msg.toString());
        }
        this._lastReportMillis = nowMillis;
    }

    public long getTotalMillis() {
        return System.currentTimeMillis() - this._startMillis;
    }

    private String formatTimeDiff(long inMillis) {
        if (this._alwaysPrintMillis) {
            return Long.toString(inMillis) + "ms";
        }
        double elapsedSecs = (double)inMillis / 1000.0;
        return SECONDS_FORMAT.format(elapsedSecs) + "s";
    }

    private static NumberFormat initializeNumberFormat() {
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(3);
        return format;
    }

    boolean shouldRecord() {
        return this._allowRecording && this._event != null;
    }

    private void recordDiagnostic(String inMessage) {
        if (this.shouldRecord()) {
            this._event.appendMessage(inMessage);
            Long lastKnownDuration = this._lastReportMillis - this._startMillis;
            this._event.setDuration(lastKnownDuration);
        }
    }

    @Nullable
    public IDiagnosticEvent enableDiagnostic(String inDiagnosticType) {
        this._allowRecording = true;
        this._event = this.createDiagnostic(inDiagnosticType);
        return this._event;
    }

    public void enableDiagnostic(IDiagnosticEvent inEvent) {
        this._allowRecording = true;
        this._event = inEvent;
    }

    @Nullable
    private IDiagnosticEvent createDiagnostic(String inDiagnosticType) {
        IDiagnosticEvent event = null;
        IDiagnosticRequestManager requestManager = DiagnosticUtils.shouldRecordDiagnostic();
        if (requestManager != null) {
            event = SimpleDiagnosticEvent.createEvent(inDiagnosticType);
            requestManager.recordEvent(event);
        }
        return event;
    }

    public long getStartMillis() {
        return this._startMillis;
    }

}
