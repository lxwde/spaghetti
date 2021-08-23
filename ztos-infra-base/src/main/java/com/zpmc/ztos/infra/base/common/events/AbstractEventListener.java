package com.zpmc.ztos.infra.base.common.events;

import com.zpmc.ztos.infra.base.business.interfaces.IEvent;
import com.zpmc.ztos.infra.base.business.interfaces.IEventListener;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractEventListener implements IEventListener {

    private boolean _asynchronous;
    private Class _resolvedClass = UnassignableClass.class;

    public AbstractEventListener(boolean inAsynchronous) {
        this._asynchronous = inAsynchronous;
    }

    @Override
    public final Class getEventClass() {
        if (this._resolvedClass == UnassignableClass.class) {
            List<Class<?>> typeParameters = AbstractEventListener.getTypeArguments(this.getBaseEventListenerClass(), this.getClass());
            this._resolvedClass = !typeParameters.isEmpty() && typeParameters.get(0) != null ? typeParameters.get(0) : IEvent.class;
        }
        return this._resolvedClass;
    }

    protected Class getBaseEventListenerClass() {
        return AbstractEventListener.class;
    }

    @Nullable
    private static Class<?> getClass(Type inType) {
        if (inType instanceof Class) {
            return (Class)inType;
        }
        if (inType instanceof ParameterizedType) {
            return AbstractEventListener.getClass(((ParameterizedType)inType).getRawType());
        }
        if (inType instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType)inType).getGenericComponentType();
            Class<?> componentClass = AbstractEventListener.getClass(componentType);
            if (componentClass != null) {
                return Array.newInstance(componentClass, 0).getClass();
            }
            return null;
        }
        return null;
    }

    private static <T> List<Class<?>> getTypeArguments(Class<T> inBaseClass, Class<? extends T> inChildClass) {
        HashMap resolvedTypes = new HashMap();
        Type type = inChildClass;
        while (!AbstractEventListener.getClass(type).equals(inBaseClass)) {
            if (type instanceof Class) {
                type = ((Class)type).getGenericSuperclass();
                continue;
            }
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Class rawType = (Class)parameterizedType.getRawType();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            TypeVariable<Class<T>>[] typeParameters = rawType.getTypeParameters();
            for (int i = 0; i < actualTypeArguments.length; ++i) {
                resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
            }
            if (rawType.equals(inBaseClass)) continue;
            type = rawType.getGenericSuperclass();
        }
        Type[] actualTypeArguments = type instanceof Class ? ((Class)type).getTypeParameters() : ((ParameterizedType)type).getActualTypeArguments();
        ArrayList typeArgumentsAsClasses = new ArrayList();
        for (Type baseType : actualTypeArguments) {
            if (resolvedTypes.containsKey(baseType)) {
                baseType = (Type)resolvedTypes.get(baseType);
            }
            typeArgumentsAsClasses.add(AbstractEventListener.getClass(baseType));
        }
        return typeArgumentsAsClasses;
    }

    @Override
    public boolean isAsynchronous() {
        return this._asynchronous;
    }

    private static final class UnassignableClass {
        private UnassignableClass() {
        }
    }

}
