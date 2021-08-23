package com.zpmc.ztos.infra.base.business.interfaces;

import com.sun.istack.NotNull;
import com.zpmc.ztos.infra.base.business.enums.framework.MessageLevelEnum;
import com.zpmc.ztos.infra.base.common.model.UserContext;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Collection;
import java.util.List;

public interface IMessageCollector extends IAttributeHolder<String, Object>{


    public void appendMessage(IUserMessage var1);

    public void appendMessage(MessageLevelEnum var1, IPropertyKey var2, String var3, Object[] var4);

    public void registerExceptions(Throwable var1);

    public List getMessages();

    public int getMessageCount();

    public boolean containsMessage();

    public boolean containsMessage(IPropertyKey var1);

    public List getMessages(MessageLevelEnum var1);

    public int getMessageCount(MessageLevelEnum var1);

    public boolean containsMessageLevel(MessageLevelEnum var1);

    public boolean hasError();

    @NotNull
    public Collection<IUserMessage> getOverriddenErrors();

    public Collection<IUserMessage> getOverrideableErrors();

    @Nullable
    public IErrorOverrides getErrorOverrides();

    public String toLoggableString(UserContext var1);

    public String toCompactString();


}
